package dao;

import config.DBConnection;
import model.NhanVien;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {
    private NhanVien map(ResultSet rs) throws SQLException {
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

    public List<NhanVien> findAll() {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien ORDER BY maNhanVien";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public NhanVien findById(int maNhanVien) {
        String sql = "SELECT * FROM NhanVien WHERE maNhanVien = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maNhanVien);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int insertAndReturnId(NhanVien nv) {
        String sql = "INSERT INTO NhanVien "
                + "(tenNhanVien, cccd, soDienThoai, email, diaChi, vaiTro, luongCoBan, thuong, trangThaiLamViec) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, N'DangLam')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nv.getTenNhanVien());
            ps.setString(2, nv.getCccd());
            ps.setString(3, nv.getSoDienThoai());
            ps.setString(4, nv.getEmail());
            ps.setString(5, nv.getDiaChi());
            ps.setString(6, nv.getVaiTro());
            ps.setDouble(7, nv.getLuongCoBan());
            ps.setDouble(8, nv.getThuong());
            if (ps.executeUpdate() == 0) return -1;
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean update(NhanVien nv) {
        String sql = "UPDATE NhanVien SET tenNhanVien=?, cccd=?, soDienThoai=?, email=?, diaChi=?, "
                + "vaiTro=?, luongCoBan=?, thuong=?, trangThaiLamViec=? WHERE maNhanVien=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nv.getTenNhanVien());
            ps.setString(2, nv.getCccd());
            ps.setString(3, nv.getSoDienThoai());
            ps.setString(4, nv.getEmail());
            ps.setString(5, nv.getDiaChi());
            ps.setString(6, nv.getVaiTro());
            ps.setDouble(7, nv.getLuongCoBan());
            ps.setDouble(8, nv.getThuong());
            ps.setString(9, nv.getTrangThaiLamViec());
            ps.setInt(10, nv.getMaNhanVien());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean softDelete(int maNhanVien) {
        String sql = "UPDATE NhanVien SET trangThaiLamViec = N'DaNghi' WHERE maNhanVien = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maNhanVien);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
