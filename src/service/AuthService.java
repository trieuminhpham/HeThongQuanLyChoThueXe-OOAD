package service;

import dao.TaiKhoanDAO;
import model.NhanVien;
import util.PasswordUtil;
import util.ValidationUtil;

public class AuthService {
    private final TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();

    public NhanVien login(String username, String password) {
        if (ValidationUtil.isEmpty(username) || ValidationUtil.isEmpty(password)) return null;
        return taiKhoanDAO.login(username.trim(), PasswordUtil.sha256(password));
    }

    public NhanVien loginBySelectedRole(String username, String password, String selectedRoleGroup) {
        NhanVien nv = login(username, password);
        if (nv == null) return null;

        if ("ChuCuaHang".equals(selectedRoleGroup)) {
            return "ChuCuaHang".equals(nv.getVaiTro()) ? nv : null;
        }

        if ("NhanVien".equals(selectedRoleGroup)) {
            return ("NhanVienKinhDoanh".equals(nv.getVaiTro())
                    || "NhanVienKyThuat".equals(nv.getVaiTro())) ? nv : null;
        }

        return null;
    }
}
