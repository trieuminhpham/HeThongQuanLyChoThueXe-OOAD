package view.dialog;

import config.Session;
import model.HopDong;
import service.HopDongService;
import service.KhachHangService;
import util.DateUtil;
import util.MessageUtil;
import util.StatusUtil;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class TaoHopDongDialog extends JDialog {
    private final int maHopDong;
    private final HopDongService hopDongService = new HopDongService();
    private JTextField txtCccd, txtGplx, txtHetHan, txtDiaChi, txtTienCoc, txtBaoHiem, txtTongTien, txtSoKm;
    private JComboBox<String> cboHinhThuc;

    public TaoHopDongDialog(Frame owner, boolean modal, int maHopDong) {
        super(owner, modal);
        this.maHopDong = maHopDong;
        setTitle("Tạo hợp đồng thật và thu tiền cọc");
        setSize(560, 470);
        setLocationRelativeTo(owner);

        HopDong hd = hopDongService.findById(maHopDong);
        JLabel info = new JLabel("HĐ " + maHopDong + " - " + hd.getTenKhachHang() + " - Xe: " + hd.getBienSo(), SwingConstants.CENTER);
        info.setFont(new Font("Segoe UI", Font.BOLD, 15));

        if (!StatusUtil.HD_GIU_CHO.equals(hd.getTrangThaiHopDong())) {
            add(new JLabel("Chỉ hợp đồng ở trạng thái Giữ chỗ mới được tạo hợp đồng thật.", SwingConstants.CENTER));
            return;
        }

        txtCccd = new JTextField();
        txtGplx = new JTextField();
        txtHetHan = new JTextField("2030-12-31");
        txtDiaChi = new JTextField();
        txtTienCoc = new JTextField("3000000");
        txtBaoHiem = new JTextField("300000");
        txtTongTien = new JTextField("2700000");
        txtSoKm = new JTextField("15000");
        cboHinhThuc = new JComboBox<>(new String[]{"TienMat", "ChuyenKhoan", "ATM"});

        JPanel form = new JPanel(new GridLayout(9, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        form.add(new JLabel("CCCD:")); form.add(txtCccd);
        form.add(new JLabel("Số bằng lái:")); form.add(txtGplx);
        form.add(new JLabel("Ngày hết hạn GPLX yyyy-MM-dd:")); form.add(txtHetHan);
        form.add(new JLabel("Địa chỉ:")); form.add(txtDiaChi);
        form.add(new JLabel("Tiền cọc:")); form.add(txtTienCoc);
        form.add(new JLabel("Phí bảo hiểm:")); form.add(txtBaoHiem);
        form.add(new JLabel("Tổng tiền thuê:")); form.add(txtTongTien);
        form.add(new JLabel("Số km khi nhận:")); form.add(txtSoKm);
        form.add(new JLabel("Hình thức thu cọc:")); form.add(cboHinhThuc);

        JButton btnSave = new JButton("Tạo hợp đồng + ghi nhận tiền cọc");
        JButton btnClose = new JButton("Đóng");
        btnSave.addActionListener(e -> save());
        btnClose.addActionListener(e -> dispose());

        JPanel buttons = new JPanel();
        buttons.add(btnSave); buttons.add(btnClose);

        add(info, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    private void save() {
        try {
            HopDong hd = hopDongService.findById(maHopDong);
            if (!StatusUtil.HD_GIU_CHO.equals(hd.getTrangThaiHopDong())) {
                MessageUtil.error(this, "Sai luồng: hợp đồng phải ở trạng thái Giữ chỗ mới được tạo hợp đồng thật.");
                return;
            }

            LocalDate hetHan = DateUtil.parseDate(txtHetHan.getText());
            new KhachHangService().updateGiayTo(hd.getMaKhachHang(), txtCccd.getText().trim(), txtGplx.getText().trim(), hetHan, txtDiaChi.getText().trim());

            double tienCoc = Double.parseDouble(txtTienCoc.getText().trim());
            double baoHiem = Double.parseDouble(txtBaoHiem.getText().trim());
            double tongTien = Double.parseDouble(txtTongTien.getText().trim());
            double soKm = Double.parseDouble(txtSoKm.getText().trim());
            String hinhThuc = cboHinhThuc.getSelectedItem().toString();

            boolean ok = hopDongService.taoHopDongThat(maHopDong, Session.getMaNhanVien(), tienCoc, baoHiem, tongTien, soKm, hinhThuc);

            if (ok) {
                MessageUtil.info(this, "Tạo hợp đồng thật, thu cọc và chuyển xe sang trạng thái Đã đặt cọc thành công.");
                dispose();
            } else {
                MessageUtil.error(this, "Không tạo được hợp đồng. Kiểm tra trạng thái hợp đồng hoặc dữ liệu nhập.");
            }
        } catch (Exception ex) {
            MessageUtil.error(this, "Dữ liệu không hợp lệ");
        }
    }
}
