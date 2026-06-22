package service;

import dao.ThanhToanDAO;
import model.ThanhToan;

import java.util.List;
import util.PermissionUtil;

public class ThanhToanService {
    private final ThanhToanDAO dao = new ThanhToanDAO();

    public boolean insert(ThanhToan tt) {
        if (!PermissionUtil.canSettleContracts()) return false;
        return dao.insert(tt);
    }

    public List<ThanhToan> findAll() {
        return dao.findAll();
    }

    public List<ThanhToan> findByHopDong(int maHopDong) {
        if (!PermissionUtil.canSettleContracts()) return java.util.Collections.emptyList();
        return dao.findByHopDong(maHopDong);
    }

    public double tongThu() {
        if (!PermissionUtil.canViewReports()) return 0;
        return dao.sumByLoaiGiaoDich("Thu");
    }

    public double tongChi() {
        if (!PermissionUtil.canViewReports()) return 0;
        return dao.sumByLoaiGiaoDich("Chi");
    }
}
