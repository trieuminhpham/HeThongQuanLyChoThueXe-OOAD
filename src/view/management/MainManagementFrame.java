package view.management;

import config.Session;
import view.StartFrame;

import javax.swing.*;
import java.awt.*;

public class MainManagementFrame extends JFrame {
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private JButton btnNhanVien, btnBaoCao, btnThanhToan, btnDatTruoc, btnHopDong, btnXe, btnKhachHang;

    public MainManagementFrame() {
        setTitle("Giao diện quản lý");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 650);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        contentPanel.add(new DashboardPanel(), "dashboard");
        contentPanel.add(new BookingRequestPanel(), "booking");
        contentPanel.add(new XePanel(), "xe");
        contentPanel.add(new KhachHangPanel(), "khachhang");
        contentPanel.add(new HopDongPanel(), "hopdong");
        contentPanel.add(new ThanhToanPanel(), "thanhtoan");
        contentPanel.add(new NhanVienPanel(), "nhanvien");
        contentPanel.add(new BaoCaoPanel(), "baocao");

        JLabel header = new JLabel("  Xin chào: " + Session.getTenNhanVien() + " - " + Session.getVaiTro());
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        add(header, BorderLayout.NORTH);
        add(createSidebar(), BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
        applyPermission();
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(new GridLayout(9, 1, 5, 5));
        sidebar.setPreferredSize(new Dimension(210, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        JButton btnDashboard = new JButton("Tổng quan");
        btnDatTruoc = new JButton("Yêu cầu đặt trước");
        btnXe = new JButton("Quản lý xe");
        btnKhachHang = new JButton("Khách hàng");
        btnHopDong = new JButton("Hợp đồng");
        btnThanhToan = new JButton("Thanh toán");
        btnNhanVien = new JButton("Nhân viên");
        btnBaoCao = new JButton("Báo cáo");
        JButton btnDangXuat = new JButton("Đăng xuất");

        btnDashboard.addActionListener(e -> cardLayout.show(contentPanel, "dashboard"));
        btnDatTruoc.addActionListener(e -> cardLayout.show(contentPanel, "booking"));
        btnXe.addActionListener(e -> cardLayout.show(contentPanel, "xe"));
        btnKhachHang.addActionListener(e -> cardLayout.show(contentPanel, "khachhang"));
        btnHopDong.addActionListener(e -> cardLayout.show(contentPanel, "hopdong"));
        btnThanhToan.addActionListener(e -> cardLayout.show(contentPanel, "thanhtoan"));
        btnNhanVien.addActionListener(e -> cardLayout.show(contentPanel, "nhanvien"));
        btnBaoCao.addActionListener(e -> cardLayout.show(contentPanel, "baocao"));
        btnDangXuat.addActionListener(e -> {
            Session.logout();
            new StartFrame().setVisible(true);
            dispose();
        });

        sidebar.add(btnDashboard);
        sidebar.add(btnDatTruoc);
        sidebar.add(btnXe);
        sidebar.add(btnKhachHang);
        sidebar.add(btnHopDong);
        sidebar.add(btnThanhToan);
        sidebar.add(btnNhanVien);
        sidebar.add(btnBaoCao);
        sidebar.add(btnDangXuat);
        return sidebar;
    }

    private void applyPermission() {
        String role = Session.getVaiTro();

        if ("NhanVienKinhDoanh".equals(role)) {
            btnNhanVien.setVisible(false);
            btnBaoCao.setVisible(false);
        } else if ("NhanVienKyThuat".equals(role)) {
            btnDatTruoc.setVisible(false);
            btnKhachHang.setVisible(false);
            btnThanhToan.setVisible(false);
            btnNhanVien.setVisible(false);
            btnBaoCao.setVisible(false);
        }
    }
}
