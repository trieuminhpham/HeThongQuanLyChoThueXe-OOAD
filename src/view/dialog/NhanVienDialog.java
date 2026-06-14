package view.dialog;

import model.NhanVien;
import service.NhanVienService;
import util.MessageUtil;

import javax.swing.*;
import java.awt.*;

public class NhanVienDialog extends JDialog {
    private final Integer maNhanVienEdit;
    private JTextField txtTen, txtCccd, txtSdt, txtEmail, txtDiaChi, txtLuong, txtThuong, txtUsername, txtPassword;
    private JComboBox<String> cboVaiTro, cboTrangThai;

    public NhanVienDialog(Frame owner, boolean modal) {
        this(owner, modal, null);
    }

    public NhanVienDialog(Frame owner, boolean modal, Integer maNhanVienEdit) {
        super(owner, modal);
        this.maNhanVienEdit = maNhanVienEdit;
        setTitle(maNhanVienEdit == null ? "Thêm nhân viên và tài khoản" : "Sửa nhân viên");
        setSize(560, maNhanVienEdit == null ? 520 : 470);
        setLocationRelativeTo(owner);

        txtTen = new JTextField(); txtCccd = new JTextField(); txtSdt = new JTextField(); txtEmail = new JTextField(); txtDiaChi = new JTextField();
        cboVaiTro = new JComboBox<>(new String[]{"NhanVienKinhDoanh", "NhanVienKyThuat", "ChuCuaHang"});
        cboTrangThai = new JComboBox<>(new String[]{"DangLam", "DaNghi"});
        txtLuong = new JTextField("10000000"); txtThuong = new JTextField("0"); txtUsername = new JTextField(); txtPassword = new JTextField("123456");

        JPanel form = new JPanel(new GridLayout(maNhanVienEdit == null ? 11 : 10, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(20, 30, 15, 30));
        form.add(new JLabel("Tên nhân viên:")); form.add(txtTen);
        form.add(new JLabel("CCCD:")); form.add(txtCccd);
        form.add(new JLabel("SĐT:")); form.add(txtSdt);
        form.add(new JLabel("Email:")); form.add(txtEmail);
        form.add(new JLabel("Địa chỉ:")); form.add(txtDiaChi);
        form.add(new JLabel("Vai trò:")); form.add(cboVaiTro);
        form.add(new JLabel("Lương cơ bản:")); form.add(txtLuong);
        form.add(new JLabel("Thưởng:")); form.add(txtThuong);
        form.add(new JLabel("Trạng thái:")); form.add(cboTrangThai);

        if (maNhanVienEdit == null) {
            form.add(new JLabel("Tên đăng nhập:")); form.add(txtUsername);
            form.add(new JLabel("Mật khẩu:")); form.add(txtPassword);
        } else {
            form.add(new JLabel("Mật khẩu mới nếu muốn reset:")); form.add(txtPassword);
            txtPassword.setText("");
            loadNhanVien();
        }

        JButton btnSave = new JButton("Lưu");
        JButton btnClose = new JButton("Đóng");
        btnSave.addActionListener(e -> save());
        btnClose.addActionListener(e -> dispose());

        JPanel buttons = new JPanel();
        buttons.add(btnSave); buttons.add(btnClose);
        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    private void loadNhanVien() {
        NhanVien nv = new NhanVienService().findById(maNhanVienEdit);
        if (nv == null) return;
        txtTen.setText(nv.getTenNhanVien());
        txtCccd.setText(nv.getCccd());
        txtSdt.setText(nv.getSoDienThoai());
        txtEmail.setText(nv.getEmail());
        txtDiaChi.setText(nv.getDiaChi());
        cboVaiTro.setSelectedItem(nv.getVaiTro());
        txtLuong.setText(String.valueOf(nv.getLuongCoBan()));
        txtThuong.setText(String.valueOf(nv.getThuong()));
        cboTrangThai.setSelectedItem(nv.getTrangThaiLamViec());
    }

    private void save() {
        try {
            NhanVien nv = new NhanVien();
            if (maNhanVienEdit != null) nv.setMaNhanVien(maNhanVienEdit);
            nv.setTenNhanVien(txtTen.getText().trim());
            nv.setCccd(txtCccd.getText().trim());
            nv.setSoDienThoai(txtSdt.getText().trim());
            nv.setEmail(txtEmail.getText().trim());
            nv.setDiaChi(txtDiaChi.getText().trim());
            nv.setVaiTro(cboVaiTro.getSelectedItem().toString());
            nv.setLuongCoBan(Double.parseDouble(txtLuong.getText().trim()));
            nv.setThuong(Double.parseDouble(txtThuong.getText().trim()));
            nv.setTrangThaiLamViec(cboTrangThai.getSelectedItem().toString());

            NhanVienService service = new NhanVienService();
            if (maNhanVienEdit == null) {
                int maNV = service.insertNhanVien(nv);
                if (maNV <= 0) { MessageUtil.error(this, "Không thêm được nhân viên. Chỉ chủ cửa hàng được thêm."); return; }
                boolean okAcc = service.taoTaiKhoan(txtUsername.getText().trim(), txtPassword.getText().trim(), maNV);
                if (okAcc) { MessageUtil.info(this, "Thêm nhân viên và tài khoản thành công"); dispose(); }
                else MessageUtil.error(this, "Đã thêm nhân viên nhưng tạo tài khoản thất bại");
            } else {
                boolean ok = service.updateNhanVien(nv);
                if (!txtPassword.getText().trim().isEmpty()) {
                    ok = ok && service.doiMatKhau(maNhanVienEdit, txtPassword.getText().trim());
                }
                if (ok) { MessageUtil.info(this, "Cập nhật nhân viên thành công"); dispose(); }
                else MessageUtil.error(this, "Cập nhật thất bại. Chỉ chủ cửa hàng được sửa.");
            }
        } catch (Exception ex) {
            MessageUtil.error(this, "Dữ liệu không hợp lệ hoặc bị trùng");
        }
    }
}
