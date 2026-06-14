package dao;

import config.DBConnection;
import model.KhachHang;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {
    private KhachHang map(ResultSet rs) throws SQLException {
        KhachHang k = new KhachHang();
        k.setMaKhachHang(rs.getInt("maKhachHang"));
        k.setTenKhachHang(rs.getString("tenKhachHang"));
        k.setCccd(rs.getString("cccd"));
        k.setSoBangLai(rs.getString("soBangLai"));

        Date date = rs.getDate("ngayHetHanBangLai");
        k.setNgayHetHanBangLai(date == null ? null : date.toLocalDate());

        k.setSoDienThoai(rs.getString("soDienThoai"));
        k.setEmail(rs.getString("email"));
        k.setTongDiemTichLuy(rs.getInt("tongDiemTichLuy"));
        k.setDiaChi(rs.getString("diaChi"));
        k.setLoaiKhachHang(rs.getString("loaiKhachHang"));
        return k;
    }

    public List<KhachHang> findAll() {
        List<KhachHang> list = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang ORDER BY maKhachHang DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public KhachHang findBySoDienThoai(String sdt) {
        String sql = "SELECT * FROM KhachHang WHERE soDienThoai = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sdt);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public KhachHang findById(int maKhachHang) {
        String sql = "SELECT * FROM KhachHang WHERE maKhachHang = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maKhachHang);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int insertAndReturnId(KhachHang kh) {
        String sql = "INSERT INTO KhachHang "
                + "(tenKhachHang, cccd, soBangLai, ngayHetHanBangLai, "
                + "soDienThoai, email, tongDiemTichLuy, diaChi, loaiKhachHang) "
                + "VALUES (?, ?, ?, ?, ?, ?, 0, ?, N'PhoThong')";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, kh.getTenKhachHang());
            ps.setString(2, kh.getCccd());
            ps.setString(3, kh.getSoBangLai());

            if (kh.getNgayHetHanBangLai() == null) ps.setNull(4, Types.DATE);
            else ps.setDate(4, Date.valueOf(kh.getNgayHetHanBangLai()));

            ps.setString(5, kh.getSoDienThoai());
            ps.setString(6, kh.getEmail());
            ps.setString(7, kh.getDiaChi());

            if (ps.executeUpdate() == 0) return -1;

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public boolean updateGiayTo(int maKhachHang, String cccd, String soBangLai,
                                LocalDate ngayHetHan, String diaChi) {
        String sql = "UPDATE KhachHang "
                + "SET cccd = ?, soBangLai = ?, ngayHetHanBangLai = ?, diaChi = ? "
                + "WHERE maKhachHang = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cccd);
            ps.setString(2, soBangLai);

            if (ngayHetHan == null) ps.setNull(3, Types.DATE);
            else ps.setDate(3, Date.valueOf(ngayHetHan));

            ps.setString(4, diaChi);
            ps.setInt(5, maKhachHang);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
