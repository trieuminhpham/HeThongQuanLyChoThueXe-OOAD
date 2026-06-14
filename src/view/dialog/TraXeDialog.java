package view.dialog;

import model.HopDong;
import service.HopDongService;
import util.MessageUtil;
import util.StatusUtil;

import javax.swing.*;
import java.awt.*;

public class TraXeDialog extends JDialog {
    private final int maHopDong;
    private JTextField txtSoKmTra;
    private JTextField txtPhiPhatSinh;
    private final HopDongService service = new HopDongService();

    public TraXeDialog(Frame owner, boolean modal, int maHopDong) {
        super(owner, modal);
        this.maHopDong = maHopDong;
        setTitle("Nhận xe trả");
        setSize(450, 280);
        setLocationRelativeTo(owner);

        HopDong hd = service.findById(maHopDong);
        if (hd == null || !StatusUtil.HD_DANG_THUE.equals(hd.getTrangThaiHopDong())) {
            add(new JLabel("Chỉ hợp đồng Đang thuê mới được nhận xe trả.", SwingConstants.CENTER));
            return;
        }

        txtSoKmTra = new JTextField("15500");
        txtPhiPhatSinh = new JTextField("0");

        JPanel form = new JPanel(new GridLayout(4, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        form.add(new JLabel("Mã hợp đồng:")); form.add(new JLabel(String.valueOf(maHopDong)));
        form.add(new JLabel("Xe:")); form.add(new JLabel(hd.getBienSo()));
        form.add(new JLabel("Số km khi trả:")); form.add(txtSoKmTra);
        form.add(new JLabel("Phí phát sinh:")); form.add(txtPhiPhatSinh);

        JButton btnOk = new JButton("Xác nhận nhận xe");
        JButton btnClose = new JButton("Đóng");
        btnOk.addActionListener(e -> save());
        btnClose.addActionListener(e -> dispose());

        JPanel buttons = new JPanel();
        buttons.add(btnOk); buttons.add(btnClose);
        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    private void save() {
        try {
            double soKmTra = Double.parseDouble(txtSoKmTra.getText().trim());
            double phi = Double.parseDouble(txtPhiPhatSinh.getText().trim());
            boolean ok = service.nhanXeTra(maHopDong, soKmTra, phi);
            if (ok) {
                MessageUtil.info(this, "Đã nhận xe trả. Hợp đồng chuyển sang Chờ quyết toán.");
                dispose();
            } else {
                MessageUtil.error(this, "Không thể nhận xe trả. Hợp đồng phải ở trạng thái Đang thuê.");
            }
        } catch (Exception ex) {
            MessageUtil.error(this, "Dữ liệu không hợp lệ");
        }
    }
}
