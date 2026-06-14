package dao;

import config.DBConnection;
import model.DanhMucXe;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DanhMucXeDAO {
    public List<DanhMucXe> findAll() {
        List<DanhMucXe> list = new ArrayList<>();
        String sql = "SELECT * FROM DanhMucXe ORDER BY maLoaiXe";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                DanhMucXe d = new DanhMucXe();
                d.setMaLoaiXe(rs.getInt("maLoaiXe"));
                d.setTenLoaiXe(rs.getString("tenLoaiXe"));
                d.setMoTa(rs.getString("moTa"));
                d.setNhienLieu(rs.getString("nhienLieu"));
                d.setSoCho(rs.getInt("soCho"));
                d.setPhanKhuc(rs.getString("phanKhuc"));
                list.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
