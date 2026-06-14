package view.auth;

import config.Session;
import model.NhanVien;
import service.AuthService;
import util.MessageUtil;
import util.UITheme;
import view.StartFrame;
import view.management.MainManagementFrame;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final String selectedRoleGroup;
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public LoginFrame(String selectedRoleGroup) {
        this.selectedRoleGroup = selectedRoleGroup;
        UITheme.applyFrameStyle(this);
        setTitle("Đăng nhập - " + roleTitle());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(460, 300);
        setLocationRelativeTo(null);

        JLabel title = UITheme.title("ĐĂNG NHẬP " + roleTitle().toUpperCase());
        JLabel note = new JLabel(roleNote(), SwingConstants.CENTER);
        note.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        txtUsername = new JTextField();
        txtPassword = new JPasswordField();

        JPanel form = UITheme.cardPanel(new GridLayout(2, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(25, 50, 20, 50));
        form.add(new JLabel("Tên đăng nhập:"));
        form.add(txtUsername);
        form.add(new JLabel("Mật khẩu:"));
        form.add(txtPassword);

        JButton btnLogin = UITheme.primaryButton("Đăng nhập");
        JButton btnBack = UITheme.normalButton("Quay lại chọn vai trò");
        btnLogin.addActionListener(e -> login());
        btnBack.addActionListener(e -> {
            new StartFrame().setVisible(true);
            dispose();
        });

        JPanel buttons = new JPanel();
        buttons.setOpaque(false);
        buttons.add(btnLogin);
        buttons.add(btnBack);

        JPanel north = new JPanel(new GridLayout(2, 1));
        north.setOpaque(false);
        north.add(title);
        north.add(note);

        add(north, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    private String roleTitle() {
        return "ChuCuaHang".equals(selectedRoleGroup) ? "Chủ cửa hàng / Quản lý" : "Nhân viên";
    }

    private String roleNote() {
        return "ChuCuaHang".equals(selectedRoleGroup)
                ? "Chỉ tài khoản có vai trò ChuCuaHang mới đăng nhập được màn hình này."
                : "Chỉ tài khoản nhân viên kinh doanh hoặc kỹ thuật mới đăng nhập được màn hình này.";
    }

    private void login() {
        AuthService service = new AuthService();
        NhanVien nv = service.loginBySelectedRole(
                txtUsername.getText(),
                new String(txtPassword.getPassword()),
                selectedRoleGroup
        );

        if (nv == null) {
            MessageUtil.error(this, "Sai tài khoản, mật khẩu hoặc tài khoản không đúng vai trò đã chọn.");
            return;
        }

        Session.currentUser = nv;
        new MainManagementFrame().setVisible(true);
        dispose();
    }
}
