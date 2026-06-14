package view.dialog;

import model.ThanhToan;
import util.DateUtil;
import util.MoneyUtil;

import javax.swing.*;
import java.awt.*;

public class BienLaiDialog extends JDialog {
    public BienLaiDialog(Frame owner, boolean modal, ThanhToan tt) {
        super(owner, modal);
        setTitle("Biên lai giao dịch");
        setSize(520, 420);
        setLocationRelativeTo(owner);

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Consolas", Font.PLAIN, 14));
        area.setText(buildReceipt(tt));

        JButton btnPrint = new JButton("In biên lai");
        JButton btnClose = new JButton("Đóng");
        btnPrint.addActionListener(e -> {
            try { area.print(); } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Không in được biên lai"); }
        });
        btnClose.addActionListener(e -> dispose());

        JPanel buttons = new JPanel();
        buttons.add(btnPrint); buttons.add(btnClose);
        add(new JScrollPane(area), BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    private String buildReceipt(ThanhToan tt) {
        return "=========== BIÊN LAI THANH TOÁN ===========\n"
                + "Mã giao dịch : " + tt.getMaGiaoDich() + "\n"
                + "Mã hợp đồng  : " + tt.getMaHopDong() + "\n"
                + "Loại TT      : " + tt.getLoaiThanhToan() + "\n"
                + "Loại GD      : " + tt.getLoaiGiaoDich() + "\n"
                + "Hình thức    : " + tt.getHinhThucThanhToan() + "\n"
                + "Số tiền      : " + MoneyUtil.formatVND(tt.getSoTien()) + "\n"
                + "Ngày giờ     : " + DateUtil.formatDateTime(tt.getNgayGio()) + "\n"
                + "Trạng thái   : " + tt.getTrangThaiGiaoDich() + "\n"
                + "Nội dung     : " + tt.getNoiDung() + "\n"
                + "===========================================\n";
    }
}
