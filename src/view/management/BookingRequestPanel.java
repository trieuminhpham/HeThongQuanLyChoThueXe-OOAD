package view.management;

import config.Session;
import model.HopDong;
import service.HopDongService;
import util.DateUtil;
import util.MessageUtil;
import util.StatusUtil;
import view.dialog.TaoHopDongDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BookingRequestPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private final HopDongService service = new HopDongService();

    public BookingRequestPanel() {
        setLayout(new BorderLayout());
        JLabel title = new JLabel("YÊU CẦU ĐẶT TRƯỚC", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        model = new DefaultTableModel(new Object[]{"Mã HĐ", "Khách hàng", "SĐT", "Biển số", "Loại xe", "Ngày nhận", "Ngày trả", "Trạng thái"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(26);

        JButton btnDuyet = new JButton("1. Duyệt giữ chỗ");
        JButton btnHuy = new JButton("Hủy yêu cầu");
        JButton btnTaoHD = new JButton("2. Tạo hợp đồng + thu cọc");
        JButton btnLamMoi = new JButton("Làm mới");

        btnDuyet.addActionListener(e -> duyet());
        btnHuy.addActionListener(e -> huy());
        btnTaoHD.addActionListener(e -> taoHopDong());
        btnLamMoi.addActionListener(e -> loadData());

        JPanel buttons = new JPanel();
        buttons.add(btnDuyet); buttons.add(btnTaoHD); buttons.add(btnHuy); buttons.add(btnLamMoi);

        add(title, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        List<HopDong> list = service.findChoDuyet();
        list.addAll(service.findGiuCho());
        for (HopDong h : list) {
            model.addRow(new Object[]{h.getMaHopDong(), h.getTenKhachHang(), h.getSoDienThoai(), h.getBienSo(), h.getTenLoaiXe(),
                    DateUtil.formatDateTime(h.getNgayNhanXe()), DateUtil.formatDateTime(h.getNgayTraDuKien()), StatusUtil.displayHopDong(h.getTrangThaiHopDong())});
        }
    }

    private Integer selectedMaHopDong() {
        int row = table.getSelectedRow();
        if (row < 0) { MessageUtil.error(this, "Vui lòng chọn yêu cầu"); return null; }
        return Integer.parseInt(table.getValueAt(row, 0).toString());
    }

    private void duyet() {
        Integer id = selectedMaHopDong(); if (id == null) return;
        HopDong hd = service.findById(id);
        if (!StatusUtil.HD_CHO_DUYET.equals(hd.getTrangThaiHopDong())) {
            MessageUtil.error(this, "Chỉ yêu cầu Chờ duyệt mới được duyệt giữ chỗ.");
            return;
        }
        boolean ok = service.duyetGiuCho(id, Session.getMaNhanVien());
        if (ok) MessageUtil.info(this, "Đã duyệt giữ chỗ. Xe đã chuyển sang trạng thái Giữ chỗ.");
        else MessageUtil.error(this, "Duyệt thất bại. Có thể xe không còn sẵn sàng.");
        loadData();
    }

    private void huy() {
        Integer id = selectedMaHopDong(); if (id == null) return;
        if (!MessageUtil.confirm(this, "Hủy yêu cầu này?")) return;
        boolean ok = service.huyYeuCau(id);
        if (ok) MessageUtil.info(this, "Đã hủy yêu cầu. Nếu xe đã giữ chỗ thì xe được trả về Sẵn sàng.");
        else MessageUtil.error(this, "Hủy thất bại. Chỉ hủy được trạng thái Chờ duyệt hoặc Giữ chỗ.");
        loadData();
    }

    private void taoHopDong() {
        Integer id = selectedMaHopDong(); if (id == null) return;
        HopDong hd = service.findById(id);
        if (!StatusUtil.HD_GIU_CHO.equals(hd.getTrangThaiHopDong())) {
            MessageUtil.error(this, "Phải Duyệt giữ chỗ trước, sau đó mới được tạo hợp đồng thật.");
            return;
        }
        new TaoHopDongDialog(null, true, id).setVisible(true);
        loadData();
    }
}
