package service;

import dao.KhuyenMaiDAO;
import model.KhuyenMai;
import util.PermissionUtil;
import util.ValidationUtil;

import java.util.List;

public class KhuyenMaiService {
    private final KhuyenMaiDAO dao = new KhuyenMaiDAO();

    private boolean isChuCuaHang() {
        return PermissionUtil.canManagePromotions();
    }

    public List<KhuyenMai> findAll() {
        return dao.findAll();
    }

    public List<KhuyenMai> findAllActive() {
        return dao.findAllActive();
    }

    public KhuyenMai findById(int maKhuyenMai) {
        return dao.findById(maKhuyenMai);
    }

    public boolean insert(KhuyenMai km) {
        if (!isChuCuaHang() || !isValid(km)) return false;
        return dao.insert(km);
    }

    public boolean update(KhuyenMai km) {
        if (!isChuCuaHang() || km == null || km.getMaKhuyenMai() <= 0 || !isValid(km)) return false;
        return dao.update(km);
    }

    public boolean kichHoat(int maKhuyenMai) {
        if (!isChuCuaHang()) {
            return false;
        }

        return dao.updateTrangThai(maKhuyenMai, "HoatDong");
    }

    public boolean ngungHoatDong(int maKhuyenMai) {
        if (!isChuCuaHang()) {
            return false;
        }

        return dao.updateTrangThai(maKhuyenMai, "NgungHoatDong");
    }

    public double tinhTienGiam(double tongTienGoc, KhuyenMai km) {
        if (km == null || km.getMaKhuyenMai() == 0) {
            return 0;
        }

        return tongTienGoc * km.getGiaTriGiam() / 100.0;
    }

    public double tinhTienSauGiam(double tongTienGoc, KhuyenMai km) {
        double soTienGiam = tinhTienGiam(tongTienGoc, km);
        return Math.max(0, tongTienGoc - soTienGiam);
    }

    private boolean isValid(KhuyenMai km) {
        if (km == null || ValidationUtil.isEmpty(km.getTenChuongTrinh())) return false;
        if (km.getGiaTriGiam() <= 0 || km.getGiaTriGiam() > 100) return false;
        if (km.getNgayBatDau() != null && km.getNgayKetThuc() != null
                && km.getNgayKetThuc().isBefore(km.getNgayBatDau())) return false;
        return "HoatDong".equals(km.getTrangThaiKhuyenMai())
                || "NgungHoatDong".equals(km.getTrangThaiKhuyenMai());
    }
}
