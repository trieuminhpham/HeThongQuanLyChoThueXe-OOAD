package view.customer;

import model.HopDong;
import model.KhachHang;
import service.DatTruocService;
import util.DateUtil;
import util.MessageUtil;
import util.UITheme;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class BookingDialog extends JDialog {
    private final int maXe;
    private JTextField txtTen;
    private JTextField txtSdt;
    private JTextField txtEmail;
    private JTextField txtDiaChi;
    private JTextField txtNgayNhan;
    private JTextField txtNgayTra;

    public BookingDialog(Frame owner, boolean modal, int maXe) {
        super(owner, modal);
        this.maXe = maXe;

        setTitle("Đặt trước xe");
        setSize(480, 360);
        setLocationRelativeTo(owner);

        txtTen = new JTextField();
        txtSdt = new JTextField();
        txtEmail = new JTextField();
        txtDiaChi = new JTextField();
        LocalDateTime ngayNhan = LocalDateTime.now().plusDays(1).withHour(8).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime ngayTra = ngayNhan.plusDays(3);
        txtNgayNhan = new JTextField(DateUtil.formatDateTime(ngayNhan));
        txtNgayTra = new JTextField(DateUtil.formatDateTime(ngayTra));

        JPanel form = new JPanel(new GridLayout(6, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));
        form.add(new JLabel("Họ tên:"));
        form.add(txtTen);
        form.add(new JLabel("Số điện thoại:"));
        form.add(txtSdt);
        form.add(new JLabel("Email:"));
        form.add(txtEmail);
        form.add(new JLabel("Địa chỉ:"));
        form.add(txtDiaChi);
        form.add(new JLabel("Ngày nhận yyyy-MM-dd HH:mm:"));
        form.add(txtNgayNhan);
        form.add(new JLabel("Ngày trả yyyy-MM-dd HH:mm:"));
        form.add(txtNgayTra);

        JButton btnGui = UITheme.primaryButton("Gửi yêu cầu đặt trước");
        JButton btnHuy = UITheme.normalButton("Hủy");

        btnGui.addActionListener(e -> guiYeuCau());
        btnHuy.addActionListener(e -> dispose());

        JPanel buttons = new JPanel();
        buttons.add(btnGui);
        buttons.add(btnHuy);

        add(new JLabel("THÔNG TIN ĐẶT TRƯỚC XE", SwingConstants.CENTER), BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    private void guiYeuCau() {
        try {
            KhachHang kh = new KhachHang();
            kh.setTenKhachHang(txtTen.getText());
            kh.setSoDienThoai(txtSdt.getText());
            kh.setEmail(txtEmail.getText());
            kh.setDiaChi(txtDiaChi.getText());

            HopDong hd = new HopDong();
            hd.setMaXe(maXe);
            hd.setNgayNhanXe(DateUtil.parseDateTime(txtNgayNhan.getText()));
            hd.setNgayTraDuKien(DateUtil.parseDateTime(txtNgayTra.getText()));

            boolean ok = new DatTruocService().datTruocXe(kh, hd);

            if (ok) {
                MessageUtil.info(this, "Gửi yêu cầu đặt trước thành công.");
                dispose();
            } else {
                MessageUtil.error(this, "Không gửi được yêu cầu. Kiểm tra lại thông tin.");
            }
        } catch (Exception ex) {
            MessageUtil.error(this, "Lỗi ngày giờ. Đúng định dạng: yyyy-MM-dd HH:mm");
        }
    }
}
