package view.management;

import model.KhachHang;
import service.KhachHangService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class KhachHangPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private final KhachHangService service = new KhachHangService();

    public KhachHangPanel() {
        setLayout(new BorderLayout());
        JLabel title = new JLabel("QUẢN LÝ KHÁCH HÀNG", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        model = new DefaultTableModel(new Object[]{"Mã KH", "Tên KH", "CCCD", "GPLX", "SĐT", "Email", "Điểm", "Loại"}, 0);
        table = new JTable(model);
        JButton btnRefresh = new JButton("Làm mới");
        btnRefresh.addActionListener(e -> loadData());
        add(title, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(btnRefresh, BorderLayout.SOUTH);
        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        for (KhachHang k : service.findAll()) {
            model.addRow(new Object[]{k.getMaKhachHang(), k.getTenKhachHang(), k.getCccd(), k.getSoBangLai(), k.getSoDienThoai(), k.getEmail(), k.getTongDiemTichLuy(), k.getLoaiKhachHang()});
        }
    }
}
