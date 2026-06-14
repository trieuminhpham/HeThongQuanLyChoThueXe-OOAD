package service;

import dao.HopDongDAO;
import dao.XeDAO;

public class BaoCaoService {
    private final ThanhToanService thanhToanService = new ThanhToanService();
    private final HopDongDAO hopDongDAO = new HopDongDAO();
    private final XeDAO xeDAO = new XeDAO();

    public double tongThu() {
        return thanhToanService.tongThu();
    }

    public double tongChi() {
        return thanhToanService.tongChi();
    }

    public double loiNhuanTamTinh() {
        return tongThu() - tongChi();
    }

    public int soYeuCauChoDuyet() {
        return hopDongDAO.findByStatus("ChoDuyet").size();
    }

    public int soHopDongDangThue() {
        return hopDongDAO.findByStatus("DangThue").size();
    }

    public int soXeSanSang() {
        return xeDAO.findXeSanSang().size();
    }
}
