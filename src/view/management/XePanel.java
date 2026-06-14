package view.management;

import config.Session;
import model.Xe;
import service.XeService;
import util.MoneyUtil;
import util.MessageUtil;
import util.StatusUtil;
import view.dialog.XeDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class XePanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JLabel lblStats;
    private final XeService service = new XeService();

    public XePanel() {
        setLayout(new BorderLayout());
        JLabel title = new JLabel("CRUD XE VÀ CẬP NHẬT TRẠNG THÁI", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblStats = new JLabel("", SwingConstants.CENTER);

        JPanel top = new JPanel(new GridLayout(2, 1));
        top.add(title); top.add(lblStats);

        model = new DefaultTableModel(new Object[]{"Mã xe", "Biển số", "Loại", "Số chỗ", "Nhiên liệu", "Giá/ngày", "Km", "Trạng thái"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(26);

        JButton btnThem = new JButton("Thêm xe");
        JButton btnSua = new JButton("Sửa xe");
        JButton btnXoa = new JButton("Thanh lý/xóa mềm");
        JButton btnSanSang = new JButton("Sẵn sàng");
        JButton btnBaoTri = new JButton("Bảo trì");
        JButton btnBaoDuong = new JButton("Bảo dưỡng");
        JButton btnRefresh = new JButton("Làm mới");

        btnThem.addActionListener(e -> { new XeDialog(null, true).setVisible(true); loadData(); });
        btnSua.addActionListener(e -> sua());
        btnXoa.addActionListener(e -> xoa());
        btnSanSang.addActionListener(e -> updateStatus(StatusUtil.XE_SAN_SANG));
        btnBaoTri.addActionListener(e -> updateStatus(StatusUtil.XE_BAO_TRI));
        btnBaoDuong.addActionListener(e -> updateStatus(StatusUtil.XE_BAO_DUONG));
        btnRefresh.addActionListener(e -> loadData());

        JPanel buttons = new JPanel();
        buttons.add(btnThem); buttons.add(btnSua); buttons.add(btnXoa); buttons.add(btnSanSang); buttons.add(btnBaoTri); buttons.add(btnBaoDuong); buttons.add(btnRefresh);

        if (!"ChuCuaHang".equals(Session.getVaiTro())) {
            btnThem.setVisible(false);
            btnSua.setVisible(false);
            btnXoa.setVisible(false);
        }

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        int total = 0, available = 0, busy = 0;
        for (Xe x : service.getAllXe()) {
            total++;
            if (StatusUtil.isXeAvailable(x.getTrangThaiXe())) available++;
            if (StatusUtil.isXeBookedOrRented(x.getTrangThaiXe())) busy++;
            model.addRow(new Object[]{x.getMaXe(), x.getBienSo(), x.getTenLoaiXe(), x.getSoCho(), x.getNhienLieu(),
                    MoneyUtil.formatVND(x.getDonGiaThueNgay()), x.getSoKmHienTai(), StatusUtil.displayXe(x.getTrangThaiXe())});
        }
        lblStats.setText("Tổng xe: " + total + " | Đã thuê/đặt chỗ: " + busy + " | Available: " + available);
    }

    private Integer selectedId() {
        int row = table.getSelectedRow();
        if (row < 0) { MessageUtil.error(this, "Vui lòng chọn xe"); return null; }
        return Integer.parseInt(table.getValueAt(row, 0).toString());
    }

    private void sua() {
        Integer id = selectedId(); if (id == null) return;
        new XeDialog(null, true, id).setVisible(true);
        loadData();
    }

    private void xoa() {
        Integer id = selectedId(); if (id == null) return;
        if (!MessageUtil.confirm(this, "Chuyển xe này sang trạng thái Đã thanh lý?")) return;
        boolean ok = service.deleteXe(id);
        if (ok) MessageUtil.info(this, "Đã thanh lý xe");
        else MessageUtil.error(this, "Không thể thanh lý xe đang giữ chỗ, đặt cọc hoặc đang thuê.");
        loadData();
    }

    private void updateStatus(String status) {
        Integer maXe = selectedId(); if (maXe == null) return;
        boolean ok = service.updateTrangThai(maXe, status);
        if (ok) MessageUtil.info(this, "Cập nhật trạng thái thành công");
        else MessageUtil.error(this, "Cập nhật thất bại");
        loadData();
    }
}
