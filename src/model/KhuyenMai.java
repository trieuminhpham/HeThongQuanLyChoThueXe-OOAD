package model;

import java.time.LocalDate;

public class KhuyenMai {
    private int maKhuyenMai;
    private String tenChuongTrinh;
    private double giaTriGiam;
    private String dieuKienApDung;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;
    private String trangThaiKhuyenMai;

    public int getMaKhuyenMai() { return maKhuyenMai; }
    public void setMaKhuyenMai(int maKhuyenMai) { this.maKhuyenMai = maKhuyenMai; }
    public String getTenChuongTrinh() { return tenChuongTrinh; }
    public void setTenChuongTrinh(String tenChuongTrinh) { this.tenChuongTrinh = tenChuongTrinh; }
    public double getGiaTriGiam() { return giaTriGiam; }
    public void setGiaTriGiam(double giaTriGiam) { this.giaTriGiam = giaTriGiam; }
    public String getDieuKienApDung() { return dieuKienApDung; }
    public void setDieuKienApDung(String dieuKienApDung) { this.dieuKienApDung = dieuKienApDung; }
    public LocalDate getNgayBatDau() { return ngayBatDau; }
    public void setNgayBatDau(LocalDate ngayBatDau) { this.ngayBatDau = ngayBatDau; }
    public LocalDate getNgayKetThuc() { return ngayKetThuc; }
    public void setNgayKetThuc(LocalDate ngayKetThuc) { this.ngayKetThuc = ngayKetThuc; }
    public String getTrangThaiKhuyenMai() { return trangThaiKhuyenMai; }
    public void setTrangThaiKhuyenMai(String trangThaiKhuyenMai) { this.trangThaiKhuyenMai = trangThaiKhuyenMai; }
}
