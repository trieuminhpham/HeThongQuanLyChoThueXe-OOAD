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
        return currentUser == null ? "" : currentUser.getTenNhanVien();
    }

    public static String getVaiTro() {
        return currentUser == null ? "" : currentUser.getVaiTro();
    }

    public static void logout() {
        currentUser = null;
    }
}
