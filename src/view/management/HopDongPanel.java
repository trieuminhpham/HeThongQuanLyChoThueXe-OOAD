package view.management;

import model.HopDong;
import service.HopDongService;
import util.DateUtil;
import util.MessageUtil;
import util.MoneyUtil;
import util.StatusUtil;
import view.dialog.BanGiaoXeDialog;
import view.dialog.ThanhToanDialog;
import view.dialog.TraXeDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HopDongPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private final HopDongService service = new HopDongService();

    public HopDongPanel() {
        setLayout(new BorderLayout());
        JLabel title = new JLabel("QUẢN LÝ HỢP ĐỒNG THEO LUỒNG", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        model = new DefaultTableModel(new Object[]{"Mã HĐ", "Khách hàng", "SĐT", "Xe", "Ngày nhận", "Ngày trả", "Tổng tiền", "Trạng thái"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(26);

        JButton btnBanGiao = new JButton("3. Bàn giao xe");
        JButton btnTraXe = new JButton("4. Nhận xe trả");
        JButton btnQuyetToan = new JButton("5. Quyết toán");
        JButton btnRefresh = new JButton("Làm mới");

        btnBanGiao.addActionListener(e -> openBanGiao());
        btnTraXe.addActionListener(e -> openTraXe());
        btnQuyetToan.addActionListener(e -> openQuyetToan());
        btnRefresh.addActionListener(e -> loadData());

        JPanel note = new JPanel(new GridLayout(2,1));
        note.add(title);
        note.add(new JLabel("Luồng bắt buộc: Đã đặt cọc → Bàn giao → Đang thuê → Nhận xe trả → Chờ quyết toán → Quyết toán", SwingConstants.CENTER));

        JPanel buttons = new JPanel();
        buttons.add(btnBanGiao); buttons.add(btnTraXe); buttons.add(btnQuyetToan); buttons.add(btnRefresh);

        add(note, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        for (HopDong h : service.findAll()) {
            model.addRow(new Object[]{h.getMaHopDong(), h.getTenKhachHang(), h.getSoDienThoai(), h.getBienSo(),
                    DateUtil.formatDateTime(h.getNgayNhanXe()), DateUtil.formatDateTime(h.getNgayTraDuKien()),
                    MoneyUtil.formatVND(h.getTongTien()), StatusUtil.displayHopDong(h.getTrangThaiHopDong())});
        }
    }

    private Integer selectedMaHopDong() {
        int row = table.getSelectedRow();
        if (row < 0) { MessageUtil.error(this, "Vui lòng chọn hợp đồng"); return null; }
        return Integer.parseInt(table.getValueAt(row, 0).toString());
    }

    private void openBanGiao() {
        Integer id = selectedMaHopDong(); if (id == null) return;
        HopDong hd = service.findById(id);
        if (!StatusUtil.HD_DA_DAT_COC.equals(hd.getTrangThaiHopDong())) {
            MessageUtil.error(this, "Chỉ hợp đồng Đã đặt cọc mới được bàn giao xe.");
            return;
        }
        new BanGiaoXeDialog(null, true, id).setVisible(true);
        loadData();
    }

    private void openTraXe() {
        Integer id = selectedMaHopDong(); if (id == null) return;
        HopDong hd = service.findById(id);
        if (!StatusUtil.HD_DANG_THUE.equals(hd.getTrangThaiHopDong())) {
            MessageUtil.error(this, "Chỉ hợp đồng Đang thuê mới được nhận xe trả.");
            return;
        }
        new TraXeDialog(null, true, id).setVisible(true);
        loadData();
    }

    private void openQuyetToan() {
        Integer id = selectedMaHopDong(); if (id == null) return;
        HopDong hd = service.findById(id);
        if (!StatusUtil.HD_CHO_QUYET_TOAN.equals(hd.getTrangThaiHopDong())) {
            MessageUtil.error(this, "Chỉ hợp đồng Chờ quyết toán mới được quyết toán.");
            return;
        }
        new ThanhToanDialog(null, true, id, true).setVisible(true);
        loadData();
    }
}
