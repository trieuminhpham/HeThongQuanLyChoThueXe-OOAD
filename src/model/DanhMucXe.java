package model;

public class DanhMucXe {
    private int maLoaiXe;
    private String tenLoaiXe;
    private String moTa;
    private String nhienLieu;
    private int soCho;
    private String phanKhuc;

    public int getMaLoaiXe() { return maLoaiXe; }
    public void setMaLoaiXe(int maLoaiXe) { this.maLoaiXe = maLoaiXe; }
    public String getTenLoaiXe() { return tenLoaiXe; }
    public void setTenLoaiXe(String tenLoaiXe) { this.tenLoaiXe = tenLoaiXe; }
    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
    public String getNhienLieu() { return nhienLieu; }
    public void setNhienLieu(String nhienLieu) { this.nhienLieu = nhienLieu; }
    public int getSoCho() { return soCho; }
    public void setSoCho(int soCho) { this.soCho = soCho; }
    public String getPhanKhuc() { return phanKhuc; }
    public void setPhanKhuc(String phanKhuc) { this.phanKhuc = phanKhuc; }

    @Override
    public String toString() {
        return tenLoaiXe;
    }
}
