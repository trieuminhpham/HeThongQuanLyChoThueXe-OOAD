package service;

import dao.HopDongDAO;
import dao.XeDAO;
import util.PermissionUtil;

public class BaoCaoService {
    private final ThanhToanService thanhToanService = new ThanhToanService();
    private final HopDongDAO hopDongDAO = new HopDongDAO();
    private final XeDAO xeDAO = new XeDAO();

    public double tongThu() {
        if (!PermissionUtil.canViewReports()) return 0;
        return thanhToanService.tongThu();
    }

    public double tongChi() {
        if (!PermissionUtil.canViewReports()) return 0;
        return thanhToanService.tongChi();
    }

    public double loiNhuanTamTinh() {
        if (!PermissionUtil.canViewReports()) return 0;
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
