package dao;

import config.DBConnection;
import model.Xe;
import util.StatusUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class XeDAO {
    private Xe map(ResultSet rs) throws SQLException {
        Xe x = new Xe();
        x.setMaXe(rs.getInt("maXe"));
        x.setBienSo(rs.getString("bienSo"));
        x.setSoKhung(rs.getString("soKhung"));
        x.setSoMay(rs.getString("soMay"));
        x.setMauXe(rs.getString("mauXe"));
        x.setNamSanXuat(rs.getInt("namSanXuat"));
        x.setTrangThaiXe(rs.getString("trangThaiXe"));
        x.setSoKmHienTai(rs.getDouble("soKmHienTai"));
        x.setDonGiaThueNgay(rs.getDouble("donGiaThueNgay"));
        x.setMaLoaiXe(rs.getInt("maLoaiXe"));

        try { x.setTenLoaiXe(rs.getString("tenLoaiXe")); } catch (SQLException ignored) {}
        try { x.setNhienLieu(rs.getString("nhienLieu")); } catch (SQLException ignored) {}
        try { x.setSoCho(rs.getInt("soCho")); } catch (SQLException ignored) {}
        try { x.setPhanKhuc(rs.getString("phanKhuc")); } catch (SQLException ignored) {}

        return x;
    }

    private String baseSelect() {
        return "SELECT x.*, d.tenLoaiXe, d.nhienLieu, d.soCho, d.phanKhuc "
                + "FROM Xe x JOIN DanhMucXe d ON x.maLoaiXe = d.maLoaiXe ";
    }

    public List<Xe> findAll() {
        return query(baseSelect() + " ORDER BY x.maXe");
    }

    public List<Xe> findXeSanSang() {
        return query(baseSelect() + " WHERE x.trangThaiXe = N'SanSang' ORDER BY x.maXe");
    }

    public Xe findById(int maXe) {
        String sql = baseSelect() + " WHERE x.maXe = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maXe);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Xe> query(String sql) {
        List<Xe> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean isAvailable(int maXe) {
        Xe x = findById(maXe);
        return x != null && StatusUtil.XE_SAN_SANG.equals(x.getTrangThaiXe());
    }

    public boolean updateTrangThaiXe(int maXe, String trangThaiMoi) {
        String sql = "UPDATE Xe SET trangThaiXe = ? WHERE maXe = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, trangThaiMoi);
            ps.setInt(2, maXe);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateSoKm(int maXe, double soKmMoi) {
        String sql = "UPDATE Xe SET soKmHienTai = ? WHERE maXe = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, soKmMoi);
            ps.setInt(2, maXe);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insert(Xe x) {
        String sql = "INSERT INTO Xe "
                + "(bienSo, soKhung, soMay, mauXe, namSanXuat, trangThaiXe, soKmHienTai, donGiaThueNgay, maLoaiXe) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, x.getBienSo());
            ps.setString(2, x.getSoKhung());
            ps.setString(3, x.getSoMay());
            ps.setString(4, x.getMauXe());
            ps.setInt(5, x.getNamSanXuat());
            ps.setString(6, x.getTrangThaiXe());
            ps.setDouble(7, x.getSoKmHienTai());
            ps.setDouble(8, x.getDonGiaThueNgay());
            ps.setInt(9, x.getMaLoaiXe());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Xe x) {
        String sql = "UPDATE Xe SET bienSo=?, soKhung=?, soMay=?, mauXe=?, namSanXuat=?, "
                + "trangThaiXe=?, soKmHienTai=?, donGiaThueNgay=?, maLoaiXe=? WHERE maXe=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, x.getBienSo());
            ps.setString(2, x.getSoKhung());
            ps.setString(3, x.getSoMay());
            ps.setString(4, x.getMauXe());
            ps.setInt(5, x.getNamSanXuat());
            ps.setString(6, x.getTrangThaiXe());
            ps.setDouble(7, x.getSoKmHienTai());
            ps.setDouble(8, x.getDonGiaThueNgay());
            ps.setInt(9, x.getMaLoaiXe());
            ps.setInt(10, x.getMaXe());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean softDelete(int maXe) {
        return updateTrangThaiXe(maXe, StatusUtil.XE_DA_THANH_LY);
    }
}
