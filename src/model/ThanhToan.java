package model;

import java.time.LocalDateTime;

public class ThanhToan {
    private int maGiaoDich;
    private String hinhThucThanhToan;
    private double soTien;
    private LocalDateTime ngayGio;
    private String trangThaiGiaoDich;
    private String loaiThanhToan;
    private String loaiGiaoDich;
    private String noiDung;
    private Integer maHopDong;

    public int getMaGiaoDich() { return maGiaoDich; }
    public void setMaGiaoDich(int maGiaoDich) { this.maGiaoDich = maGiaoDich; }
    public String getHinhThucThanhToan() { return hinhThucThanhToan; }
    public void setHinhThucThanhToan(String hinhThucThanhToan) { this.hinhThucThanhToan = hinhThucThanhToan; }
    public double getSoTien() { return soTien; }
    public void setSoTien(double soTien) { this.soTien = soTien; }
    public LocalDateTime getNgayGio() { return ngayGio; }
    public void setNgayGio(LocalDateTime ngayGio) { this.ngayGio = ngayGio; }
    public String getTrangThaiGiaoDich() { return trangThaiGiaoDich; }
    public void setTrangThaiGiaoDich(String trangThaiGiaoDich) { this.trangThaiGiaoDich = trangThaiGiaoDich; }
    public String getLoaiThanhToan() { return loaiThanhToan; }
    public void setLoaiThanhToan(String loaiThanhToan) { this.loaiThanhToan = loaiThanhToan; }
    public String getLoaiGiaoDich() { return loaiGiaoDich; }
    public void setLoaiGiaoDich(String loaiGiaoDich) { this.loaiGiaoDich = loaiGiaoDich; }
    public String getNoiDung() { return noiDung; }
    public void setNoiDung(String noiDung) { this.noiDung = noiDung; }
    public Integer getMaHopDong() { return maHopDong; }
    public void setMaHopDong(Integer maHopDong) { this.maHopDong = maHopDong; }
}
