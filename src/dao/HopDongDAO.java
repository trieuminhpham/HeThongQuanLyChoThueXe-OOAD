package dao;

import config.DBConnection;
import model.HopDong;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HopDongDAO {
    private Timestamp toTimestamp(LocalDateTime value) {
        return value == null ? null : Timestamp.valueOf(value);
    }

    private HopDong map(ResultSet rs) throws SQLException {
        HopDong h = new HopDong();
        h.setMaHopDong(rs.getInt("maHopDong"));

        Timestamp ngayLap = rs.getTimestamp("ngayLap");
        h.setNgayLap(ngayLap == null ? null : ngayLap.toLocalDateTime());

        Timestamp ngayNhan = rs.getTimestamp("ngayNhanXe");
        h.setNgayNhanXe(ngayNhan == null ? null : ngayNhan.toLocalDateTime());

        Timestamp ngayTraDK = rs.getTimestamp("ngayTraDuKien");
        h.setNgayTraDuKien(ngayTraDK == null ? null : ngayTraDK.toLocalDateTime());

        Timestamp ngayTraTT = rs.getTimestamp("ngayTraThucTe");
        h.setNgayTraThucTe(ngayTraTT == null ? null : ngayTraTT.toLocalDateTime());

        h.setTienCoc(rs.getDouble("tienCoc"));
        h.setPhiBaoHiem(rs.getDouble("phiBaoHiem"));
        h.setPhiPhatSinh(rs.getDouble("phiPhatSinh"));
        h.setTongTien(rs.getDouble("tongTien"));
        h.setTrangThaiHopDong(rs.getString("trangThaiHopDong"));

        double kmNhan = rs.getDouble("soKmKhiNhan");
        h.setSoKmKhiNhan(rs.wasNull() ? null : kmNhan);

        double kmTra = rs.getDouble("soKmKhiTra");
        h.setSoKmKhiTra(rs.wasNull() ? null : kmTra);

        h.setMaKhachHang(rs.getInt("maKhachHang"));
        h.setMaXe(rs.getInt("maXe"));

        int maNV = rs.getInt("maNhanVien");
        h.setMaNhanVien(rs.wasNull() ? null : maNV);

        int maKM = rs.getInt("maKhuyenMai");
        h.setMaKhuyenMai(rs.wasNull() ? null : maKM);

        try { h.setTenKhachHang(rs.getString("tenKhachHang")); } catch (SQLException ignored) {}
        try { h.setSoDienThoai(rs.getString("soDienThoai")); } catch (SQLException ignored) {}
        try { h.setBienSo(rs.getString("bienSo")); } catch (SQLException ignored) {}
        try { h.setTenLoaiXe(rs.getString("tenLoaiXe")); } catch (SQLException ignored) {}

        return h;
    }

    public boolean insertDatTruoc(HopDong hd) {
        String sql = "INSERT INTO HopDong "
                + "(ngayNhanXe, ngayTraDuKien, tienCoc, phiBaoHiem, phiPhatSinh, "
                + "tongTien, trangThaiHopDong, soKmKhiNhan, soKmKhiTra, "
                + "maKhachHang, maXe, maNhanVien, maKhuyenMai) "
                + "VALUES (?, ?, 0, 0, 0, 0, N'ChoDuyet', NULL, NULL, ?, ?, NULL, NULL)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, toTimestamp(hd.getNgayNhanXe()));
            ps.setTimestamp(2, toTimestamp(hd.getNgayTraDuKien()));
            ps.setInt(3, hd.getMaKhachHang());
            ps.setInt(4, hd.getMaXe());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private List<HopDong> queryList(String sql, String param) {
        List<HopDong> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (param != null) ps.setString(1, param);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<HopDong> findByStatus(String status) {
        String sql = "SELECT hd.*, kh.tenKhachHang, kh.soDienThoai, x.bienSo, d.tenLoaiXe "
                + "FROM HopDong hd "
                + "JOIN KhachHang kh ON hd.maKhachHang = kh.maKhachHang "
                + "JOIN Xe x ON hd.maXe = x.maXe "
                + "JOIN DanhMucXe d ON x.maLoaiXe = d.maLoaiXe "
                + "WHERE hd.trangThaiHopDong = ? "
                + "ORDER BY hd.maHopDong DESC";
        return queryList(sql, status);
    }

    public List<HopDong> findAllForManagement() {
        String sql = "SELECT hd.*, kh.tenKhachHang, kh.soDienThoai, x.bienSo, d.tenLoaiXe "
                + "FROM HopDong hd "
                + "JOIN KhachHang kh ON hd.maKhachHang = kh.maKhachHang "
                + "JOIN Xe x ON hd.maXe = x.maXe "
                + "JOIN DanhMucXe d ON x.maLoaiXe = d.maLoaiXe "
                + "ORDER BY hd.maHopDong DESC";
        return queryList(sql, null);
    }

    public HopDong findById(int maHopDong) {
        String sql = "SELECT hd.*, kh.tenKhachHang, kh.soDienThoai, x.bienSo, d.tenLoaiXe "
                + "FROM HopDong hd "
                + "JOIN KhachHang kh ON hd.maKhachHang = kh.maKhachHang "
                + "JOIN Xe x ON hd.maXe = x.maXe "
                + "JOIN DanhMucXe d ON x.maLoaiXe = d.maLoaiXe "
                + "WHERE hd.maHopDong = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maHopDong);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean duyetGiuCho(int maHopDong, int maNhanVien) {
        String sql = "UPDATE HopDong "
                + "SET trangThaiHopDong = N'GiuCho', maNhanVien = ? "
                + "WHERE maHopDong = ? AND trangThaiHopDong = N'ChoDuyet'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maNhanVien);
            ps.setInt(2, maHopDong);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean huyYeuCau(int maHopDong) {
        return updateTrangThai(maHopDong, "DaHuy");
    }

    public boolean updateTrangThai(int maHopDong, String trangThai) {
        String sql = "UPDATE HopDong SET trangThaiHopDong = ? WHERE maHopDong = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, trangThai);
            ps.setInt(2, maHopDong);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean taoHopDongThat(int maHopDong, int maNhanVien, double tienCoc,
                                  double phiBaoHiem, double tongTien, double soKmKhiNhan) {
        String sql = "UPDATE HopDong "
                + "SET maNhanVien = ?, tienCoc = ?, phiBaoHiem = ?, tongTien = ?, "
                + "soKmKhiNhan = ?, trangThaiHopDong = N'DaDatCoc' "
                + "WHERE maHopDong = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maNhanVien);
            ps.setDouble(2, tienCoc);
            ps.setDouble(3, phiBaoHiem);
            ps.setDouble(4, tongTien);
            ps.setDouble(5, soKmKhiNhan);
            ps.setInt(6, maHopDong);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean banGiaoXe(int maHopDong, double soKmKhiNhan) {
        String sql = "UPDATE HopDong "
                + "SET trangThaiHopDong = N'DangThue', soKmKhiNhan = ? "
                + "WHERE maHopDong = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, soKmKhiNhan);
            ps.setInt(2, maHopDong);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean nhanXeTra(int maHopDong, double soKmKhiTra, double phiPhatSinh) {
        String sql = "UPDATE HopDong "
                + "SET ngayTraThucTe = SYSDATETIME(), soKmKhiTra = ?, "
                + "phiPhatSinh = ?, trangThaiHopDong = N'ChoQuyetToan' "
                + "WHERE maHopDong = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, soKmKhiTra);
            ps.setDouble(2, phiPhatSinh);
            ps.setInt(3, maHopDong);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
