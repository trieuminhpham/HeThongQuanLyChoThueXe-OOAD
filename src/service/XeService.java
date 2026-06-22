package service;

import dao.XeDAO;
import model.Xe;
import util.StatusUtil;
import util.ValidationUtil;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

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

    public List<String> getHangXeOptions() {
        Set<String> values = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        for (Xe x : getAllXe()) {
            if (!ValidationUtil.isEmpty(x.getTenLoaiXe())) values.add(x.getTenLoaiXe().trim());
        }
        return new ArrayList<>(values);
    }

    public List<Integer> getSoChoOptions() {
        Set<Integer> values = new TreeSet<>();
        for (Xe x : getAllXe()) {
            if (x.getSoCho() > 0) values.add(x.getSoCho());
        }
        return new ArrayList<>(values);
    }

    public List<Xe> searchForCustomer(String hangXe, Integer soCho) {
        List<Xe> result = new ArrayList<>();
        for (Xe x : getAllXe()) {
            boolean matchHang = ValidationUtil.isEmpty(hangXe)
                    || (x.getTenLoaiXe() != null && x.getTenLoaiXe().equalsIgnoreCase(hangXe.trim()));
            boolean matchSoCho = soCho == null || x.getSoCho() == soCho;
            if (matchHang && matchSoCho) result.add(x);
        }
        return result;
    }

    public boolean updateTrangThai(int maXe, String trangThai) {
        if (!util.PermissionUtil.canUpdateTechnicalStatus()) return false;
        if (!(StatusUtil.XE_SAN_SANG.equals(trangThai)
                || StatusUtil.XE_BAO_TRI.equals(trangThai)
                || StatusUtil.XE_BAO_DUONG.equals(trangThai))) return false;
        Xe current = xeDAO.findById(maXe);
        if (current == null || StatusUtil.isXeBookedOrRented(current.getTrangThaiXe())) return false;
        return xeDAO.updateTrangThaiXe(maXe, trangThai);
    }

    public boolean insertXe(Xe x) {
        if (!util.PermissionUtil.canManageVehicleCatalog()) return false;
        if (!isValidXe(x)) return false;
        if (ValidationUtil.isEmpty(x.getTrangThaiXe())) {
            x.setTrangThaiXe(StatusUtil.XE_SAN_SANG);
        }
        return xeDAO.insert(x);
    }

    public boolean updateXe(Xe x) {
        if (!util.PermissionUtil.canManageVehicleCatalog()) return false;
        if (x.getMaXe() <= 0 || !isValidXe(x)) return false;
        Xe current = xeDAO.findById(x.getMaXe());
        if (current == null) return false;
        if (StatusUtil.isXeBookedOrRented(current.getTrangThaiXe())
                && !current.getTrangThaiXe().equals(x.getTrangThaiXe())) return false;
        return xeDAO.update(x);
    }

    public boolean deleteXe(int maXe) {
        if (!util.PermissionUtil.canManageVehicleCatalog()) return false;
        Xe x = xeDAO.findById(maXe);
        if (x == null) return false;
        if (StatusUtil.XE_DANG_THUE.equals(x.getTrangThaiXe())
                || StatusUtil.XE_DA_DAT_COC.equals(x.getTrangThaiXe())
                || StatusUtil.XE_GIU_CHO.equals(x.getTrangThaiXe())
                || StatusUtil.XE_CHO_QUYET_TOAN.equals(x.getTrangThaiXe())) {
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
