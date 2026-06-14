package view.dialog;

import model.HopDong;
import service.HopDongService;
import util.MessageUtil;
import util.StatusUtil;

import javax.swing.*;
import java.awt.*;

public class BanGiaoXeDialog extends JDialog {
    private final int maHopDong;
    private JTextField txtSoKm;
    private final HopDongService service = new HopDongService();

    public BanGiaoXeDialog(Frame owner, boolean modal, int maHopDong) {
        super(owner, modal);
        this.maHopDong = maHopDong;
        setTitle("Bàn giao xe");
        setSize(450, 240);
        setLocationRelativeTo(owner);

        HopDong hd = service.findById(maHopDong);
        if (hd == null || !StatusUtil.HD_DA_DAT_COC.equals(hd.getTrangThaiHopDong())) {
            add(new JLabel("Chỉ hợp đồng Đã đặt cọc mới được bàn giao xe.", SwingConstants.CENTER));
            return;
        }

        txtSoKm = new JTextField(hd.getSoKmKhiNhan() == null ? "15000" : String.valueOf(hd.getSoKmKhiNhan()));
        JPanel form = new JPanel(new GridLayout(3, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        form.add(new JLabel("Mã hợp đồng:")); form.add(new JLabel(String.valueOf(maHopDong)));
        form.add(new JLabel("Xe:")); form.add(new JLabel(hd.getBienSo()));
        form.add(new JLabel("Số km khi nhận:")); form.add(txtSoKm);

        JButton btnOk = new JButton("Xác nhận bàn giao");
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
            double soKm = Double.parseDouble(txtSoKm.getText().trim());
            boolean ok = service.banGiaoXe(maHopDong, soKm);
            if (ok) {
                MessageUtil.info(this, "Bàn giao thành công. Hợp đồng và xe chuyển sang Đang thuê.");
                dispose();
            } else {
                MessageUtil.error(this, "Không thể bàn giao. Hợp đồng phải ở trạng thái Đã đặt cọc.");
            }
        } catch (Exception ex) {
            MessageUtil.error(this, "Số km không hợp lệ");
        }
    }
}
