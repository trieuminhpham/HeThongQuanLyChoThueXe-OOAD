package view.management;

import model.NhanVien;
import service.NhanVienService;
import util.MessageUtil;
import view.dialog.NhanVienDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class NhanVienPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private final NhanVienService service = new NhanVienService();

    public NhanVienPanel() {
        setLayout(new BorderLayout());
        JLabel title = new JLabel("CRUD NHÂN VIÊN", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        model = new DefaultTableModel(new Object[]{"Mã NV", "Tên", "CCCD", "SĐT", "Email", "Vai trò", "Lương", "Thưởng", "Trạng thái"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(26);

        JButton btnThem = new JButton("Thêm nhân viên + tài khoản");
        JButton btnSua = new JButton("Sửa nhân viên / reset mật khẩu");
        JButton btnXoa = new JButton("Xóa mềm / khóa tài khoản");
        JButton btnRefresh = new JButton("Làm mới");
        btnThem.addActionListener(e -> { new NhanVienDialog(null, true).setVisible(true); loadData(); });
        btnSua.addActionListener(e -> sua());
        btnXoa.addActionListener(e -> xoa());
        btnRefresh.addActionListener(e -> loadData());

        JPanel buttons = new JPanel();
        buttons.add(btnThem); buttons.add(btnSua); buttons.add(btnXoa); buttons.add(btnRefresh);
        add(title, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        for (NhanVien nv : service.findAll()) {
            model.addRow(new Object[]{nv.getMaNhanVien(), nv.getTenNhanVien(), nv.getCccd(), nv.getSoDienThoai(), nv.getEmail(), nv.getVaiTro(), nv.getLuongCoBan(), nv.getThuong(), nv.getTrangThaiLamViec()});
        }
    }

    private Integer selectedId() {
        int row = table.getSelectedRow();
        if (row < 0) { MessageUtil.error(this, "Vui lòng chọn nhân viên"); return null; }
        return Integer.parseInt(table.getValueAt(row, 0).toString());
    }

    private void sua() {
        Integer id = selectedId(); if (id == null) return;
        new NhanVienDialog(null, true, id).setVisible(true);
        loadData();
    }

    private void xoa() {
        Integer id = selectedId(); if (id == null) return;
        if (!MessageUtil.confirm(this, "Xóa mềm nhân viên này và khóa tài khoản đăng nhập?")) return;
        boolean ok = service.deleteNhanVien(id);
        if (ok) MessageUtil.info(this, "Đã xóa mềm nhân viên và khóa tài khoản");
        else MessageUtil.error(this, "Không xóa được. Không được xóa chính mình hoặc chủ cửa hàng.");
        loadData();
    }
}
