package model;

import java.time.LocalDate;

public class KhachHang {
    private int maKhachHang;
    private String tenKhachHang;
    private String cccd;
    private String soBangLai;
    private LocalDate ngayHetHanBangLai;
    private String soDienThoai;
    private String email;
    private int tongDiemTichLuy;
    private String diaChi;
    private String loaiKhachHang;

    public int getMaKhachHang() { return maKhachHang; }
    public void setMaKhachHang(int maKhachHang) { this.maKhachHang = maKhachHang; }
    public String getTenKhachHang() { return tenKhachHang; }
    public void setTenKhachHang(String tenKhachHang) { this.tenKhachHang = tenKhachHang; }
    public String getCccd() { return cccd; }
    public void setCccd(String cccd) { this.cccd = cccd; }
    public String getSoBangLai() { return soBangLai; }
    public void setSoBangLai(String soBangLai) { this.soBangLai = soBangLai; }
    public LocalDate getNgayHetHanBangLai() { return ngayHetHanBangLai; }
    public void setNgayHetHanBangLai(LocalDate ngayHetHanBangLai) { this.ngayHetHanBangLai = ngayHetHanBangLai; }
    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getTongDiemTichLuy() { return tongDiemTichLuy; }
    public void setTongDiemTichLuy(int tongDiemTichLuy) { this.tongDiemTichLuy = tongDiemTichLuy; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public String getLoaiKhachHang() { return loaiKhachHang; }
    public void setLoaiKhachHang(String loaiKhachHang) { this.loaiKhachHang = loaiKhachHang; }
}
