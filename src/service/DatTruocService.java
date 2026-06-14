package service;

import dao.HopDongDAO;
import dao.KhachHangDAO;
import dao.XeDAO;
import model.HopDong;
import model.KhachHang;
import util.StatusUtil;
import util.ValidationUtil;

import java.util.List;

public class DatTruocService {
    private final KhachHangDAO khachHangDAO = new KhachHangDAO();
    private final HopDongDAO hopDongDAO = new HopDongDAO();
    private final XeDAO xeDAO = new XeDAO();

    public boolean datTruocXe(KhachHang khachHang, HopDong hopDong) {
        if (ValidationUtil.isEmpty(khachHang.getTenKhachHang())) return false;
        if (!ValidationUtil.isValidPhone(khachHang.getSoDienThoai())) return false;
        if (!ValidationUtil.isValidBookingTime(hopDong.getNgayNhanXe(), hopDong.getNgayTraDuKien())) return false;

        // Chặn lỗi logic: khách không được gửi yêu cầu nếu xe đã giữ chỗ, đặt cọc, đang thuê hoặc đang xử lý.
        if (!xeDAO.isAvailable(hopDong.getMaXe())) return false;

        KhachHang old = khachHangDAO.findBySoDienThoai(khachHang.getSoDienThoai());
        int maKhachHang;
        if (old == null) maKhachHang = khachHangDAO.insertAndReturnId(khachHang);
        else maKhachHang = old.getMaKhachHang();

        if (maKhachHang <= 0) return false;

        hopDong.setMaKhachHang(maKhachHang);
        hopDong.setTrangThaiHopDong(StatusUtil.HD_CHO_DUYET);
        hopDong.setMaNhanVien(null);
        return hopDongDAO.insertDatTruoc(hopDong);
    }

    public List<HopDong> getYeuCauChoDuyet() {
        return hopDongDAO.findByStatus(StatusUtil.HD_CHO_DUYET);
    }
}
