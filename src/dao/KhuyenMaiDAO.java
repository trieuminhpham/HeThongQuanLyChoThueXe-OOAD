package dao;

import config.DBConnection;
import model.KhuyenMai;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhuyenMaiDAO {
    public List<KhuyenMai> findAllActive() {
        List<KhuyenMai> list = new ArrayList<>();
        String sql = "SELECT * FROM KhuyenMai WHERE trangThaiKhuyenMai = N'HoatDong' ORDER BY maKhuyenMai";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                KhuyenMai km = new KhuyenMai();
                km.setMaKhuyenMai(rs.getInt("maKhuyenMai"));
                km.setTenChuongTrinh(rs.getString("tenChuongTrinh"));
                km.setGiaTriGiam(rs.getDouble("giaTriGiam"));
                km.setDieuKienApDung(rs.getString("dieuKienApDung"));
                Date bd = rs.getDate("ngayBatDau");
                Date kt = rs.getDate("ngayKetThuc");
                km.setNgayBatDau(bd == null ? null : bd.toLocalDate());
                km.setNgayKetThuc(kt == null ? null : kt.toLocalDate());
                km.setTrangThaiKhuyenMai(rs.getString("trangThaiKhuyenMai"));
                list.add(km);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
