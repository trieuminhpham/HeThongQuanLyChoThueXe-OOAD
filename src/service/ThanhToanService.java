package service;

import dao.ThanhToanDAO;
import model.ThanhToan;

import java.util.List;

public class ThanhToanService {
    private final ThanhToanDAO dao = new ThanhToanDAO();

    public boolean insert(ThanhToan tt) {
        return dao.insert(tt);
    }

    public List<ThanhToan> findAll() {
        return dao.findAll();
    }

    public double tongThu() {
        return dao.sumByLoaiGiaoDich("Thu");
    }

    public double tongChi() {
        return dao.sumByLoaiGiaoDich("Chi");
    }
}
