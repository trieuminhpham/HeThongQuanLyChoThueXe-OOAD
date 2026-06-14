package service;

import dao.KhachHangDAO;
import model.KhachHang;

import java.time.LocalDate;
import java.util.List;

public class KhachHangService {
    private final KhachHangDAO dao = new KhachHangDAO();

    public List<KhachHang> findAll() {
        return dao.findAll();
    }

    public boolean updateGiayTo(int maKhachHang, String cccd, String soBangLai,
                                LocalDate ngayHetHan, String diaChi) {
        return dao.updateGiayTo(maKhachHang, cccd, soBangLai, ngayHetHan, diaChi);
    }
}
