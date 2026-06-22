package view.dialog;

import model.HopDong;
import service.HopDongService;
import util.MessageUtil;
import util.MoneyUtil;
import util.StatusUtil;
import util.UITheme;

import javax.swing.*;
import java.awt.*;

public class ThanhToanDialog extends JDialog {
    private final int maHopDong;
    private JComboBox<String> cboHinhThuc;
    private final HopDongService service = new HopDongService();

    public ThanhToanDialog(Frame owner, boolean modal, int maHopDong, boolean quyetToan) {
        super(owner, modal);
        this.maHopDong = maHopDong;
        setTitle("Quyết toán hợp đồng");
        setSize(500, 330);
        setLocationRelativeTo(owner);

        HopDong hd = service.findById(maHopDong);
        if (hd == null || !StatusUtil.HD_CHO_QUYET_TOAN.equals(hd.getTrangThaiHopDong())) {
            add(new JLabel("Chỉ hợp đồng Chờ quyết toán mới được quyết toán.", SwingConstants.CENTER));
            return;
        }

        double canThanhToan = hd.getTongTien() + hd.getPhiPhatSinh() - hd.getTienCoc();
        String ketQua;
        if (canThanhToan > 0) ketQua = "Khách cần trả thêm: " + MoneyUtil.formatVND(canThanhToan);
        else if (canThanhToan < 0) ketQua = "Cửa hàng cần hoàn cọc: " + MoneyUtil.formatVND(Math.abs(canThanhToan));
        else ketQua = "Không phát sinh thêm tiền.";

        cboHinhThuc = new JComboBox<>(new String[]{"TienMat", "ChuyenKhoan", "ATM"});

        JPanel form = new JPanel(new GridLayout(7, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        form.add(new JLabel("Mã hợp đồng:")); form.add(new JLabel(String.valueOf(maHopDong)));
        form.add(new JLabel("Khách hàng:")); form.add(new JLabel(hd.getTenKhachHang()));
        form.add(new JLabel("Tổng tiền thuê:")); form.add(new JLabel(MoneyUtil.formatVND(hd.getTongTien())));
        form.add(new JLabel("Tiền cọc đã thu:")); form.add(new JLabel(MoneyUtil.formatVND(hd.getTienCoc())));
        form.add(new JLabel("Phí phát sinh:")); form.add(new JLabel(MoneyUtil.formatVND(hd.getPhiPhatSinh())));
        form.add(new JLabel("Kết quả quyết toán:")); form.add(new JLabel(ketQua));
        form.add(new JLabel("Hình thức:")); form.add(cboHinhThuc);

        JButton btnOk = UITheme.successButton("Xác nhận quyết toán");
        JButton btnClose = UITheme.normalButton("Đóng");
        btnOk.addActionListener(e -> save());
        btnClose.addActionListener(e -> dispose());

        JPanel buttons = new JPanel();
        buttons.add(btnOk); buttons.add(btnClose);
        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    private void save() {
        String hinhThuc = cboHinhThuc.getSelectedItem().toString();
        boolean ok = service.quyetToan(maHopDong, hinhThuc);
        if (ok) {
            MessageUtil.info(this, "Quyết toán thành công. Hợp đồng kết thúc, xe quay lại Sẵn sàng.");
            dispose();
        } else {
            MessageUtil.error(this, "Không thể quyết toán. Hợp đồng phải ở trạng thái Chờ quyết toán.");
        }
    }
}
