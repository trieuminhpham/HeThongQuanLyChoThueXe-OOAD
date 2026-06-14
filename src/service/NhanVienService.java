package service;

import config.Session;
import dao.NhanVienDAO;
import dao.TaiKhoanDAO;
import model.NhanVien;
import util.PasswordUtil;
import util.ValidationUtil;

import java.util.List;

public class NhanVienService {
    private final NhanVienDAO nhanVienDAO = new NhanVienDAO();
    private final TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();

    public List<NhanVien> findAll() {
        return nhanVienDAO.findAll();
    }

    public NhanVien findById(int maNhanVien) {
        return nhanVienDAO.findById(maNhanVien);
    }

    public int insertNhanVien(NhanVien nv) {
        if (!isOwner()) return -1;
        if (!isValid(nv)) return -1;
        return nhanVienDAO.insertAndReturnId(nv);
    }

    public boolean updateNhanVien(NhanVien nv) {
        if (!isOwner()) return false;
        if (nv.getMaNhanVien() <= 0 || !isValid(nv)) return false;
        return nhanVienDAO.update(nv);
    }

    public boolean deleteNhanVien(int maNhanVien) {
        if (!isOwner()) return false;
        if (maNhanVien == Session.getMaNhanVien()) return false;
        NhanVien nv = nhanVienDAO.findById(maNhanVien);
        if (nv == null) return false;
        if ("ChuCuaHang".equals(nv.getVaiTro())) return false;
        boolean ok1 = nhanVienDAO.softDelete(maNhanVien);
        boolean ok2 = taiKhoanDAO.lockByNhanVien(maNhanVien);
        return ok1 && ok2;
    }

    public boolean taoTaiKhoan(String username, String password, int maNhanVien) {
        if (!isOwner()) return false;
        if (ValidationUtil.isEmpty(username) || ValidationUtil.isEmpty(password)) return false;
        return taiKhoanDAO.createAccount(username, PasswordUtil.sha256(password), maNhanVien);
    }

    public boolean doiMatKhau(int maNhanVien, String passwordMoi) {
        if (!isOwner()) return false;
        if (ValidationUtil.isEmpty(passwordMoi)) return false;
        return taiKhoanDAO.updatePasswordByNhanVien(maNhanVien, PasswordUtil.sha256(passwordMoi));
    }

    private boolean isOwner() {
        return "ChuCuaHang".equals(Session.getVaiTro());
    }

    private boolean isValid(NhanVien nv) {
        return nv != null
                && !ValidationUtil.isEmpty(nv.getTenNhanVien())
                && !ValidationUtil.isEmpty(nv.getVaiTro())
                && nv.getLuongCoBan() >= 0
                && nv.getThuong() >= 0;
    }
}
