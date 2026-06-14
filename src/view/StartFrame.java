package view;

import util.UITheme;
import view.auth.LoginFrame;
import view.customer.CustomerHomeFrame;

import javax.swing.*;
import java.awt.*;

public class StartFrame extends JFrame {
    public StartFrame() {
        UITheme.applyFrameStyle(this);
        setTitle("Hệ thống quản lý cho thuê xe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(560, 360);
        setLocationRelativeTo(null);

        JLabel title = UITheme.title("HỆ THỐNG CHO THUÊ XE TỰ LÁI");
        JLabel subtitle = new JLabel("Chọn vai trò trước khi sử dụng hệ thống", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JButton btnKhachHang = UITheme.primaryButton("Khách hàng - Xem và đặt xe");
        JButton btnNhanVien = UITheme.normalButton("Nhân viên - Xử lý hợp đồng");
        JButton btnChu = UITheme.normalButton("Chủ cửa hàng / Quản lý");
        JButton btnThoat = UITheme.normalButton("Thoát");

        btnKhachHang.addActionListener(e -> {
            new CustomerHomeFrame().setVisible(true);
            dispose();
        });

        btnNhanVien.addActionListener(e -> {
            new LoginFrame("NhanVien").setVisible(true);
            dispose();
        });

        btnChu.addActionListener(e -> {
            new LoginFrame("ChuCuaHang").setVisible(true);
            dispose();
        });

        btnThoat.addActionListener(e -> System.exit(0));

        JPanel center = UITheme.cardPanel(new GridLayout(4, 1, 12, 12));
        center.setBorder(BorderFactory.createEmptyBorder(25, 80, 25, 80));
        center.add(btnKhachHang);
        center.add(btnNhanVien);
        center.add(btnChu);
        center.add(btnThoat);

        JPanel north = new JPanel(new GridLayout(2, 1));
        north.setOpaque(false);
        north.add(title);
        north.add(subtitle);

        add(north, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
    }
}
