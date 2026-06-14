package service;

import dao.XeDAO;
import model.Xe;
import util.StatusUtil;
import util.ValidationUtil;

import java.util.List;

public class XeService {
    private final XeDAO xeDAO = new XeDAO();

    public List<Xe> getAllXe() {
        return xeDAO.findAll();
    }

    public List<Xe> getXeSanSang() {
        return xeDAO.findXeSanSang();
    }

    public Xe findById(int maXe) {
        return xeDAO.findById(maXe);
    }

    public boolean updateTrangThai(int maXe, String trangThai) {
        return xeDAO.updateTrangThaiXe(maXe, trangThai);
    }

    public boolean insertXe(Xe x) {
        if (!isValidXe(x)) return false;
        if (ValidationUtil.isEmpty(x.getTrangThaiXe())) {
            x.setTrangThaiXe(StatusUtil.XE_SAN_SANG);
        }
        return xeDAO.insert(x);
    }

    public boolean updateXe(Xe x) {
        if (x.getMaXe() <= 0 || !isValidXe(x)) return false;
        return xeDAO.update(x);
    }

    public boolean deleteXe(int maXe) {
        Xe x = xeDAO.findById(maXe);
        if (x == null) return false;
        if (StatusUtil.XE_DANG_THUE.equals(x.getTrangThaiXe())
                || StatusUtil.XE_DA_DAT_COC.equals(x.getTrangThaiXe())
                || StatusUtil.XE_GIU_CHO.equals(x.getTrangThaiXe())) {
            return false;
        }
        return xeDAO.softDelete(maXe);
    }

    private boolean isValidXe(Xe x) {
        return x != null
                && !ValidationUtil.isEmpty(x.getBienSo())
                && x.getNamSanXuat() > 1990
                && x.getDonGiaThueNgay() >= 0
                && x.getMaLoaiXe() > 0;
    }

    public int countTotal() {
        return getAllXe().size();
    }

    public int countAvailable() {
        int count = 0;
        for (Xe x : getAllXe()) {
            if (StatusUtil.isXeAvailable(x.getTrangThaiXe())) count++;
        }
        return count;
    }

    public int countBookedOrRented() {
        int count = 0;
        for (Xe x : getAllXe()) {
            if (StatusUtil.isXeBookedOrRented(x.getTrangThaiXe())) count++;
        }
        return count;
    }
}
