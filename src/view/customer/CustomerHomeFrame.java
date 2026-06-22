package view.customer;

import model.Xe;
import service.XeService;
import util.MoneyUtil;
import util.MessageUtil;
import util.StatusUtil;
import util.UITheme;
import view.StartFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CustomerHomeFrame extends JFrame {
    private JTable tblXe;
    private DefaultTableModel tableModel;
    private JLabel lblTotal, lblBusy, lblAvailable;
    private JComboBox<String> cboHangXe;
    private JComboBox<Object> cboSoCho;
    private final XeService xeService = new XeService();

    public CustomerHomeFrame() {
        UITheme.applyFrameStyle(this);
        setTitle("Khách hàng - Xem xe và đặt trước");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1050, 620);
        setLocationRelativeTo(null);

        JLabel title = UITheme.title("TÌM XE PHÙ HỢP VỚI BẠN");

        lblTotal = statLabel("Tổng số xe: 0");
        lblBusy = statLabel("Đã thuê/đặt chỗ: 0");
        lblAvailable = statLabel("Còn sẵn sàng: 0");
        JPanel statPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        statPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 12, 20));
        statPanel.setOpaque(false);
        statPanel.add(lblTotal);
        statPanel.add(lblBusy);
        statPanel.add(lblAvailable);

        cboHangXe = new JComboBox<>();
        cboSoCho = new JComboBox<>();
        loadFilterOptions();

        JButton btnTimKiem = UITheme.primaryButton("Tìm kiếm");
        JButton btnXoaLoc = UITheme.normalButton("Xóa bộ lọc");
        btnTimKiem.addActionListener(e -> loadData());
        btnXoaLoc.addActionListener(e -> {
            cboHangXe.setSelectedIndex(0);
            cboSoCho.setSelectedIndex(0);
            loadData();
        });
        cboHangXe.addActionListener(e -> loadData());
        cboSoCho.addActionListener(e -> loadData());

        JPanel filterPanel = UITheme.cardPanel(new FlowLayout(FlowLayout.CENTER, 12, 6));
        filterPanel.add(new JLabel("Hãng / dòng xe:"));
        filterPanel.add(cboHangXe);
        filterPanel.add(new JLabel("Số chỗ ngồi:"));
        filterPanel.add(cboSoCho);
        filterPanel.add(btnTimKiem);
        filterPanel.add(btnXoaLoc);

        JPanel top = new JPanel(new BorderLayout(0, 8));
        top.setOpaque(false);
        top.add(title, BorderLayout.NORTH);
        top.add(statPanel, BorderLayout.CENTER);
        top.add(filterPanel, BorderLayout.SOUTH);

        tableModel = new DefaultTableModel(
                new Object[]{"Mã xe", "Biển số", "Loại xe", "Số chỗ", "Nhiên liệu", "Phân khúc", "Giá/ngày", "Trạng thái"}, 0
        ) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tblXe = new JTable(tableModel);
        UITheme.styleTable(tblXe);

        JButton btnDatTruoc = UITheme.primaryButton("Đặt trước xe đã chọn");
        JButton btnLamMoi = UITheme.normalButton("Làm mới");
        JButton btnQuayLai = UITheme.normalButton("Quay lại");

        btnDatTruoc.addActionListener(e -> datTruoc());
        btnLamMoi.addActionListener(e -> loadData());
        btnQuayLai.addActionListener(e -> {
            new StartFrame().setVisible(true);
            dispose();
        });

        JPanel buttons = new JPanel();
        buttons.setOpaque(false);
        buttons.add(btnDatTruoc);
        buttons.add(btnLamMoi);
        buttons.add(btnQuayLai);

        add(top, BorderLayout.NORTH);
        JScrollPane scroll = new JScrollPane(tblXe);
        scroll.setBorder(BorderFactory.createEmptyBorder(12, 20, 8, 20));
        add(scroll, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        loadData();
    }

    private void loadFilterOptions() {
        cboHangXe.addItem("Tất cả hãng xe");
        for (String value : xeService.getHangXeOptions()) cboHangXe.addItem(value);
        cboSoCho.addItem("Tất cả số chỗ");
        for (Integer value : xeService.getSoChoOptions()) cboSoCho.addItem(value);
    }

    private JLabel statLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 15));
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 230)),
                BorderFactory.createEmptyBorder(12, 8, 12, 8)
        ));
        return label;
    }

    private void loadData() {
        tableModel.setRowCount(0);
        String hangXe = cboHangXe.getSelectedIndex() <= 0 ? null : cboHangXe.getSelectedItem().toString();
        Integer soCho = cboSoCho.getSelectedIndex() <= 0 ? null : (Integer) cboSoCho.getSelectedItem();
        List<Xe> list = xeService.searchForCustomer(hangXe, soCho);
        int total = list.size();
        int available = 0;
        int busy = 0;

        for (Xe x : list) {
            if (StatusUtil.isXeAvailable(x.getTrangThaiXe())) available++;
            if (StatusUtil.isXeBookedOrRented(x.getTrangThaiXe())) busy++;

            tableModel.addRow(new Object[]{
                    x.getMaXe(),
                    x.getBienSo(),
                    x.getTenLoaiXe(),
                    x.getSoCho(),
                    x.getNhienLieu(),
                    x.getPhanKhuc(),
                    MoneyUtil.formatVND(x.getDonGiaThueNgay()),
                    StatusUtil.displayXe(x.getTrangThaiXe())
            });
        }

        lblTotal.setText("Kết quả tìm thấy: " + total + " xe");
        lblBusy.setText("Đã thuê/đặt chỗ: " + busy);
        lblAvailable.setText("Còn sẵn sàng: " + available);
    }

    private void datTruoc() {
        int row = tblXe.getSelectedRow();
        if (row < 0) {
            MessageUtil.error(this, "Vui lòng chọn một xe");
            return;
        }

        int maXe = Integer.parseInt(tblXe.getValueAt(row, 0).toString());
        Xe x = xeService.findById(maXe);
        if (x == null || !StatusUtil.XE_SAN_SANG.equals(x.getTrangThaiXe())) {
            MessageUtil.error(this, "Xe này hiện không còn sẵn sàng. Vui lòng chọn xe khác.");
            loadData();
            return;
        }

        BookingDialog dialog = new BookingDialog(this, true, maXe);
        dialog.setVisible(true);
        loadData();
    }
}
