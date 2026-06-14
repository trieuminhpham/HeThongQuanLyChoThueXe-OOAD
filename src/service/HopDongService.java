package service;

import dao.HopDongDAO;
import dao.ThanhToanDAO;
import dao.XeDAO;
import model.HopDong;
import model.ThanhToan;
import util.StatusUtil;

import java.util.List;

public class HopDongService {
    private final HopDongDAO hopDongDAO = new HopDongDAO();
    private final XeDAO xeDAO = new XeDAO();
    private final ThanhToanDAO thanhToanDAO = new ThanhToanDAO();

    public List<HopDong> findAll() { return hopDongDAO.findAllForManagement(); }
    public List<HopDong> findChoDuyet() { return hopDongDAO.findByStatus(StatusUtil.HD_CHO_DUYET); }
    public List<HopDong> findGiuCho() { return hopDongDAO.findByStatus(StatusUtil.HD_GIU_CHO); }
    public List<HopDong> findDaDatCoc() { return hopDongDAO.findByStatus(StatusUtil.HD_DA_DAT_COC); }
    public List<HopDong> findDangThue() { return hopDongDAO.findByStatus(StatusUtil.HD_DANG_THUE); }
    public List<HopDong> findChoQuyetToan() { return hopDongDAO.findByStatus(StatusUtil.HD_CHO_QUYET_TOAN); }
    public HopDong findById(int maHopDong) { return hopDongDAO.findById(maHopDong); }

    /*
     * State machine bắt buộc:
     * ChoDuyet -> GiuCho -> DaDatCoc -> DangThue -> ChoQuyetToan -> DaQuyetToan
     * Mọi bước đều kiểm tra trạng thái hiện tại để không thể nhảy cóc.
     */

    public boolean duyetGiuCho(int maHopDong, int maNhanVien) {
        HopDong hd = hopDongDAO.findById(maHopDong);
        if (hd == null) return false;
        if (!StatusUtil.HD_CHO_DUYET.equals(hd.getTrangThaiHopDong())) return false;
        if (!xeDAO.isAvailable(hd.getMaXe())) return false;

        boolean ok1 = hopDongDAO.duyetGiuCho(maHopDong, maNhanVien);
        boolean ok2 = xeDAO.updateTrangThaiXe(hd.getMaXe(), StatusUtil.XE_GIU_CHO);
        return ok1 && ok2;
    }

    public boolean huyYeuCau(int maHopDong) {
        HopDong hd = hopDongDAO.findById(maHopDong);
        if (hd == null) return false;
        if (!(StatusUtil.HD_CHO_DUYET.equals(hd.getTrangThaiHopDong())
                || StatusUtil.HD_GIU_CHO.equals(hd.getTrangThaiHopDong()))) {
            return false;
        }

        boolean ok1 = hopDongDAO.huyYeuCau(maHopDong);
        boolean ok2 = true;
        if (StatusUtil.HD_GIU_CHO.equals(hd.getTrangThaiHopDong())) {
            ok2 = xeDAO.updateTrangThaiXe(hd.getMaXe(), StatusUtil.XE_SAN_SANG);
        }
        return ok1 && ok2;
    }

    public boolean taoHopDongThat(int maHopDong, int maNhanVien, double tienCoc,
                                  double phiBaoHiem, double tongTien, double soKmKhiNhan,
                                  String hinhThucThanhToan) {
        HopDong hd = hopDongDAO.findById(maHopDong);
        if (hd == null) return false;
        if (!StatusUtil.HD_GIU_CHO.equals(hd.getTrangThaiHopDong())) return false;
        if (tienCoc < 0 || phiBaoHiem < 0 || tongTien < 0 || soKmKhiNhan < 0) return false;

        boolean ok1 = hopDongDAO.taoHopDongThat(maHopDong, maNhanVien, tienCoc, phiBaoHiem, tongTien, soKmKhiNhan);
        boolean ok2 = xeDAO.updateTrangThaiXe(hd.getMaXe(), StatusUtil.XE_DA_DAT_COC);
        boolean ok3 = true;

        if (tienCoc > 0) {
            ThanhToan tt = new ThanhToan();
            tt.setMaHopDong(maHopDong);
            tt.setHinhThucThanhToan(hinhThucThanhToan);
            tt.setSoTien(tienCoc);
            tt.setTrangThaiGiaoDich("ThanhCong");
            tt.setLoaiThanhToan("TienCoc");
            tt.setLoaiGiaoDich("Thu");
            tt.setNoiDung("Thu tiền cọc khi tạo hợp đồng thật");
            ok3 = thanhToanDAO.insert(tt);
        }

        return ok1 && ok2 && ok3;
    }

    public boolean banGiaoXe(int maHopDong, double soKmKhiNhan) {
        HopDong hd = hopDongDAO.findById(maHopDong);
        if (hd == null) return false;
        if (!StatusUtil.HD_DA_DAT_COC.equals(hd.getTrangThaiHopDong())) return false;
        if (soKmKhiNhan < 0) return false;

        boolean ok1 = hopDongDAO.banGiaoXe(maHopDong, soKmKhiNhan);
        boolean ok2 = xeDAO.updateTrangThaiXe(hd.getMaXe(), StatusUtil.XE_DANG_THUE);
        return ok1 && ok2;
    }

    public boolean nhanXeTra(int maHopDong, double soKmKhiTra, double phiPhatSinh) {
        HopDong hd = hopDongDAO.findById(maHopDong);
        if (hd == null) return false;
        if (!StatusUtil.HD_DANG_THUE.equals(hd.getTrangThaiHopDong())) return false;
        if (soKmKhiTra < 0 || phiPhatSinh < 0) return false;

        boolean ok1 = hopDongDAO.nhanXeTra(maHopDong, soKmKhiTra, phiPhatSinh);
        boolean ok2 = xeDAO.updateSoKm(hd.getMaXe(), soKmKhiTra);
        boolean ok3 = xeDAO.updateTrangThaiXe(hd.getMaXe(), StatusUtil.XE_CHO_QUYET_TOAN);
        return ok1 && ok2 && ok3;
    }

    public boolean quyetToan(int maHopDong, String hinhThucThanhToan) {
        HopDong hd = hopDongDAO.findById(maHopDong);
        if (hd == null) return false;
        if (!StatusUtil.HD_CHO_QUYET_TOAN.equals(hd.getTrangThaiHopDong())) return false;

        double canThanhToan = hd.getTongTien() + hd.getPhiPhatSinh() - hd.getTienCoc();
        boolean okPayment = true;

        if (canThanhToan > 0) {
            ThanhToan tt = new ThanhToan();
            tt.setMaHopDong(maHopDong);
            tt.setHinhThucThanhToan(hinhThucThanhToan);
            tt.setSoTien(canThanhToan);
            tt.setTrangThaiGiaoDich("ThanhCong");
            tt.setLoaiThanhToan("ThuThem");
            tt.setLoaiGiaoDich("Thu");
            tt.setNoiDung("Thu thêm khi quyết toán hợp đồng");
            okPayment = thanhToanDAO.insert(tt);
        } else if (canThanhToan < 0) {
            ThanhToan tt = new ThanhToan();
            tt.setMaHopDong(maHopDong);
            tt.setHinhThucThanhToan(hinhThucThanhToan);
            tt.setSoTien(Math.abs(canThanhToan));
            tt.setTrangThaiGiaoDich("ThanhCong");
            tt.setLoaiThanhToan("HoanCoc");
            tt.setLoaiGiaoDich("Chi");
            tt.setNoiDung("Hoàn lại tiền cọc thừa khi quyết toán");
            okPayment = thanhToanDAO.insert(tt);
        }

        boolean ok1 = hopDongDAO.updateTrangThai(maHopDong, StatusUtil.HD_DA_QUYET_TOAN);
        boolean ok2 = xeDAO.updateTrangThaiXe(hd.getMaXe(), StatusUtil.XE_SAN_SANG);
        return okPayment && ok1 && ok2;
    }

    // Giữ lại tên cũ để các màn hình cũ không lỗi, nhưng vẫn chặn nhảy cóc bằng quyetToan().
    public boolean ketThucHopDong(int maHopDong, int maXe) {
        return quyetToan(maHopDong, "TienMat");
    }
}
