package dao;

import config.DBConnection;
import model.ThanhToan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ThanhToanDAO {
    public boolean insert(ThanhToan tt) {
        String sql = "INSERT INTO ThanhToan "
                + "(hinhThucThanhToan, soTien, trangThaiGiaoDich, "
                + "loaiThanhToan, loaiGiaoDich, noiDung, maHopDong) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tt.getHinhThucThanhToan());
            ps.setDouble(2, tt.getSoTien());
            ps.setString(3, tt.getTrangThaiGiaoDich());
            ps.setString(4, tt.getLoaiThanhToan());
            ps.setString(5, tt.getLoaiGiaoDich());
            ps.setString(6, tt.getNoiDung());

            if (tt.getMaHopDong() == null) ps.setNull(7, Types.INTEGER);
            else ps.setInt(7, tt.getMaHopDong());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public double sumByLoaiGiaoDich(String loaiGiaoDich) {
        String sql = "SELECT ISNULL(SUM(soTien), 0) AS tong "
                + "FROM ThanhToan "
                + "WHERE loaiGiaoDich = ? AND trangThaiGiaoDich = N'ThanhCong'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, loaiGiaoDich);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble("tong");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public List<ThanhToan> findAll() {
        List<ThanhToan> list = new ArrayList<>();
        String sql = "SELECT * FROM ThanhToan ORDER BY maGiaoDich DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ThanhToan tt = new ThanhToan();
                tt.setMaGiaoDich(rs.getInt("maGiaoDich"));
                tt.setHinhThucThanhToan(rs.getString("hinhThucThanhToan"));
                tt.setSoTien(rs.getDouble("soTien"));
                Timestamp ts = rs.getTimestamp("ngayGio");
                tt.setNgayGio(ts == null ? null : ts.toLocalDateTime());
                tt.setTrangThaiGiaoDich(rs.getString("trangThaiGiaoDich"));
                tt.setLoaiThanhToan(rs.getString("loaiThanhToan"));
                tt.setLoaiGiaoDich(rs.getString("loaiGiaoDich"));
                tt.setNoiDung(rs.getString("noiDung"));

                int maHD = rs.getInt("maHopDong");
                tt.setMaHopDong(rs.wasNull() ? null : maHD);

                list.add(tt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
