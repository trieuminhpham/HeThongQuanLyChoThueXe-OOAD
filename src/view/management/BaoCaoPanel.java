package view.management;

import service.BaoCaoService;
import util.MoneyUtil;

import javax.swing.*;
import java.awt.*;

public class BaoCaoPanel extends JPanel {
    private JLabel lblThu, lblChi, lblLoiNhuan, lblChoDuyet, lblDangThue, lblXeSanSang;

    public BaoCaoPanel() {
        setLayout(new BorderLayout());
        JLabel title = new JLabel("BÁO CÁO - THỐNG KÊ", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel body = new JPanel(new GridLayout(6, 1, 8, 8));
        body.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));
        lblThu = new JLabel(); lblChi = new JLabel(); lblLoiNhuan = new JLabel();
        lblChoDuyet = new JLabel(); lblDangThue = new JLabel(); lblXeSanSang = new JLabel();
        body.add(lblThu); body.add(lblChi); body.add(lblLoiNhuan); body.add(lblChoDuyet); body.add(lblDangThue); body.add(lblXeSanSang);

        JButton btnRefresh = new JButton("Thống kê lại");
        btnRefresh.addActionListener(e -> loadData());

        add(title, BorderLayout.NORTH);
        add(body, BorderLayout.CENTER);
        add(btnRefresh, BorderLayout.SOUTH);
        loadData();
    }

    private void loadData() {
        BaoCaoService s = new BaoCaoService();
        lblThu.setText("Tổng thu: " + MoneyUtil.formatVND(s.tongThu()));
        lblChi.setText("Tổng chi: " + MoneyUtil.formatVND(s.tongChi()));
        lblLoiNhuan.setText("Lợi nhuận tạm tính: " + MoneyUtil.formatVND(s.loiNhuanTamTinh()));
        lblChoDuyet.setText("Yêu cầu chờ duyệt: " + s.soYeuCauChoDuyet());
        lblDangThue.setText("Hợp đồng đang thuê: " + s.soHopDongDangThue());
        lblXeSanSang.setText("Xe sẵn sàng: " + s.soXeSanSang());
    }
}
