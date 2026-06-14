package view.management;

import service.BaoCaoService;
import util.MoneyUtil;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {
    private JLabel lbl1, lbl2, lbl3, lbl4, lbl5, lbl6;

    public DashboardPanel() {
        setLayout(new BorderLayout());
        JLabel title = new JLabel("TỔNG QUAN HỆ THỐNG", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        JPanel body = new JPanel(new GridLayout(6, 1, 10, 10));
        body.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));
        lbl1 = new JLabel(); lbl2 = new JLabel(); lbl3 = new JLabel();
        lbl4 = new JLabel(); lbl5 = new JLabel(); lbl6 = new JLabel();
        body.add(lbl1); body.add(lbl2); body.add(lbl3); body.add(lbl4); body.add(lbl5); body.add(lbl6);

        JButton btn = new JButton("Làm mới");
        btn.addActionListener(e -> loadData());

        add(title, BorderLayout.NORTH);
        add(body, BorderLayout.CENTER);
        add(btn, BorderLayout.SOUTH);
        loadData();
    }

    private void loadData() {
        BaoCaoService s = new BaoCaoService();
        lbl1.setText("Yêu cầu chờ duyệt: " + s.soYeuCauChoDuyet());
        lbl2.setText("Hợp đồng đang thuê: " + s.soHopDongDangThue());
        lbl3.setText("Xe sẵn sàng: " + s.soXeSanSang());
        lbl4.setText("Tổng thu: " + MoneyUtil.formatVND(s.tongThu()));
        lbl5.setText("Tổng chi: " + MoneyUtil.formatVND(s.tongChi()));
        lbl6.setText("Lợi nhuận tạm tính: " + MoneyUtil.formatVND(s.loiNhuanTamTinh()));
    }
}
