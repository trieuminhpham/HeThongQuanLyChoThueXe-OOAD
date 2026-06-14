package view.management;

import model.ThanhToan;
import service.ThanhToanService;
import util.DateUtil;
import util.MoneyUtil;
import util.MessageUtil;
import view.dialog.BienLaiDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ThanhToanPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JComboBox<String> cboLoai;
    private JTextField txtMaHopDong;
    private JLabel lblTong;
    private List<ThanhToan> currentList = new ArrayList<>();
    private final ThanhToanService service = new ThanhToanService();

    public ThanhToanPanel() {
        setLayout(new BorderLayout());
        JLabel title = new JLabel("MÀN HÌNH THANH TOÁN - GIAO DỊCH", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        model = new DefaultTableModel(new Object[]{"Mã GD", "Mã HĐ", "Loại thanh toán", "Loại GD", "Hình thức", "Số tiền", "Ngày giờ", "Trạng thái", "Nội dung"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(26);

        cboLoai = new JComboBox<>(new String[]{"TatCa", "TienCoc", "ThuThem", "HoanCoc"});
        txtMaHopDong = new JTextField(8);
        lblTong = new JLabel("Tổng tiền đang hiển thị: 0 VNĐ");

        JButton btnSearch = new JButton("Tra cứu");
        JButton btnReceipt = new JButton("In biên lai giao dịch chọn");
        JButton btnRefresh = new JButton("Làm mới");
        btnSearch.addActionListener(e -> loadData());
        btnRefresh.addActionListener(e -> { txtMaHopDong.setText(""); cboLoai.setSelectedIndex(0); loadData(); });
        btnReceipt.addActionListener(e -> printReceipt());

        JPanel filter = new JPanel();
        filter.add(new JLabel("Loại:")); filter.add(cboLoai);
        filter.add(new JLabel("Mã HĐ:")); filter.add(txtMaHopDong);
        filter.add(btnSearch); filter.add(btnReceipt); filter.add(btnRefresh);

        JPanel top = new JPanel(new GridLayout(3, 1));
        top.add(title);
        top.add(filter);
        top.add(lblTong);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        currentList.clear();
        String loai = cboLoai.getSelectedItem().toString();
        String maHDText = txtMaHopDong.getText().trim();
        double tong = 0;

        for (ThanhToan t : service.findAll()) {
            if (!"TatCa".equals(loai) && !loai.equals(t.getLoaiThanhToan())) continue;
            if (!maHDText.isEmpty()) {
                if (t.getMaHopDong() == null || !String.valueOf(t.getMaHopDong()).equals(maHDText)) continue;
            }
            currentList.add(t);
            tong += t.getSoTien();
            model.addRow(new Object[]{t.getMaGiaoDich(), t.getMaHopDong(), t.getLoaiThanhToan(), t.getLoaiGiaoDich(),
                    t.getHinhThucThanhToan(), MoneyUtil.formatVND(t.getSoTien()), DateUtil.formatDateTime(t.getNgayGio()),
                    t.getTrangThaiGiaoDich(), t.getNoiDung()});
        }
        lblTong.setText("Tổng tiền đang hiển thị: " + MoneyUtil.formatVND(tong)
                + "  |  TienCoc = tiền cọc, ThuThem = thu thêm quyết toán, HoanCoc = hoàn cọc");
    }

    private void printReceipt() {
        int row = table.getSelectedRow();
        if (row < 0) {
            MessageUtil.error(this, "Vui lòng chọn một giao dịch");
            return;
        }
        int maGD = Integer.parseInt(table.getValueAt(row, 0).toString());
        for (ThanhToan t : currentList) {
            if (t.getMaGiaoDich() == maGD) {
                new BienLaiDialog(null, true, t).setVisible(true);
                return;
            }
        }
    }
}
