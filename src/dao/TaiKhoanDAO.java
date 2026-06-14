package dao;

import config.DBConnection;
import model.NhanVien;

import java.sql.*;

public class TaiKhoanDAO {
    public NhanVien login(String username, String passwordHash) {
        String sql = "SELECT nv.* FROM TaiKhoan tk "
                + "JOIN NhanVien nv ON tk.maNhanVien = nv.maNhanVien "
                + "WHERE tk.tenDangNhap = ? "
                + "AND tk.matKhauHash = ? "
                + "AND tk.trangThaiTaiKhoan = N'HoatDong' "
                + "AND nv.trangThaiLamViec = N'DangLam'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, passwordHash);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    NhanVien nv = new NhanVien();
                    nv.setMaNhanVien(rs.getInt("maNhanVien"));
                    nv.setTenNhanVien(rs.getString("tenNhanVien"));
                    nv.setCccd(rs.getString("cccd"));
                    nv.setSoDienThoai(rs.getString("soDienThoai"));
                    nv.setEmail(rs.getString("email"));
                    nv.setDiaChi(rs.getString("diaChi"));
                    nv.setVaiTro(rs.getString("vaiTro"));
                    nv.setLuongCoBan(rs.getDouble("luongCoBan"));
                    nv.setThuong(rs.getDouble("thuong"));
                    nv.setTrangThaiLamViec(rs.getString("trangThaiLamViec"));
                    return nv;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean createAccount(String username, String passwordHash, int maNhanVien) {
        String sql = "INSERT INTO TaiKhoan (tenDangNhap, matKhauHash, trangThaiTaiKhoan, maNhanVien) "
                + "VALUES (?, ?, N'HoatDong', ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, passwordHash);
            ps.setInt(3, maNhanVien);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePasswordByNhanVien(int maNhanVien, String passwordHash) {
        String sql = "UPDATE TaiKhoan SET matKhauHash = ? WHERE maNhanVien = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, passwordHash);
            ps.setInt(2, maNhanVien);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean lockByNhanVien(int maNhanVien) {
        String sql = "UPDATE TaiKhoan SET trangThaiTaiKhoan = N'BiKhoa' WHERE maNhanVien = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maNhanVien);
            return ps.executeUpdate() >= 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
