package config;

import model.NhanVien;

public class Session {
    public static NhanVien currentUser;

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static int getMaNhanVien() {
        return currentUser == null ? 0 : currentUser.getMaNhanVien();
    }

    public static String getTenNhanVien() {
        if (currentUser == null || currentUser.getTenNhanVien() == null) {
            return "";
        }
        return currentUser.getTenNhanVien();
    }

    public static String getVaiTro() {
        if (currentUser == null || currentUser.getVaiTro() == null) {
            return "";
        }
        return currentUser.getVaiTro().trim();
    }

    public static boolean isChuCuaHang() {
        return "ChuCuaHang".equalsIgnoreCase(getVaiTro());
    }

    public static boolean isNhanVienKinhDoanh() {
        return "NhanVienKinhDoanh".equalsIgnoreCase(getVaiTro());
    }

    public static boolean isNhanVienKyThuat() {
        return "NhanVienKyThuat".equalsIgnoreCase(getVaiTro());
    }

    public static void logout() {
        currentUser = null;
    }
}