package view.dialog;

import dao.DanhMucXeDAO;
import model.DanhMucXe;
import model.Xe;
import service.XeService;
import util.MessageUtil;
import util.StatusUtil;
import util.UITheme;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class XeDialog extends JDialog {
    private final Integer maXeEdit;
    private JTextField txtBienSo, txtSoKhung, txtSoMay, txtMau, txtNam, txtKm, txtGia;
    private JComboBox<DanhMucXe> cboLoai;
    private JComboBox<String> cboTrangThai;
    private List<DanhMucXe> danhMucList;

    public XeDialog(Frame owner, boolean modal) {
        this(owner, modal, null);
    }

    public XeDialog(Frame owner, boolean modal, Integer maXeEdit) {
        super(owner, modal);
        this.maXeEdit = maXeEdit;
        setTitle(maXeEdit == null ? "Thêm xe mới" : "Sửa xe");
        setSize(520, 430);
        setLocationRelativeTo(owner);

        txtBienSo = new JTextField(); txtSoKhung = new JTextField(); txtSoMay = new JTextField();
        txtMau = new JTextField(); txtNam = new JTextField("2024"); txtKm = new JTextField("0"); txtGia = new JTextField("1000000");
        cboLoai = new JComboBox<>();
        cboTrangThai = new JComboBox<>(new String[]{StatusUtil.XE_SAN_SANG, StatusUtil.XE_BAO_TRI, StatusUtil.XE_BAO_DUONG, StatusUtil.XE_DA_THANH_LY});
        loadDanhMuc();

        JPanel form = new JPanel(new GridLayout(9, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(20, 30, 15, 30));
        form.add(new JLabel("Biển số:")); form.add(txtBienSo);
        form.add(new JLabel("Số khung:")); form.add(txtSoKhung);
        form.add(new JLabel("Số máy:")); form.add(txtSoMay);
        form.add(new JLabel("Màu xe:")); form.add(txtMau);
        form.add(new JLabel("Năm sản xuất:")); form.add(txtNam);
        form.add(new JLabel("Số km hiện tại:")); form.add(txtKm);
        form.add(new JLabel("Đơn giá thuê/ngày:")); form.add(txtGia);
        form.add(new JLabel("Loại xe:")); form.add(cboLoai);
        form.add(new JLabel("Trạng thái:")); form.add(cboTrangThai);

        if (maXeEdit != null) loadXe();

        JButton btnSave = UITheme.primaryButton("Lưu");
        JButton btnClose = UITheme.normalButton("Đóng");
        btnSave.addActionListener(e -> save());
        btnClose.addActionListener(e -> dispose());
        JPanel buttons = new JPanel();
        buttons.add(btnSave); buttons.add(btnClose);
        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    private void loadDanhMuc() {
        danhMucList = new DanhMucXeDAO().findAll();
        for (DanhMucXe d : danhMucList) cboLoai.addItem(d);
    }

    private void loadXe() {
        Xe x = new XeService().findById(maXeEdit);
        if (x == null) return;
        txtBienSo.setText(x.getBienSo());
        txtSoKhung.setText(x.getSoKhung());
        txtSoMay.setText(x.getSoMay());
        txtMau.setText(x.getMauXe());
        txtNam.setText(String.valueOf(x.getNamSanXuat()));
        txtKm.setText(String.valueOf(x.getSoKmHienTai()));
        txtGia.setText(String.valueOf(x.getDonGiaThueNgay()));
        boolean hasStatus = false;
        for (int i = 0; i < cboTrangThai.getItemCount(); i++) {
            if (cboTrangThai.getItemAt(i).equals(x.getTrangThaiXe())) hasStatus = true;
        }
        if (!hasStatus) cboTrangThai.addItem(x.getTrangThaiXe());
        cboTrangThai.setSelectedItem(x.getTrangThaiXe());
        if (StatusUtil.isXeBookedOrRented(x.getTrangThaiXe())) {
            cboTrangThai.setEnabled(false);
            cboTrangThai.setToolTipText("Trạng thái xe đang trong hợp đồng chỉ thay đổi theo quy trình thuê xe.");
        }
        for (int i = 0; i < cboLoai.getItemCount(); i++) {
            if (cboLoai.getItemAt(i).getMaLoaiXe() == x.getMaLoaiXe()) {
                cboLoai.setSelectedIndex(i);
                break;
            }
        }
    }

    private void save() {
        try {
            DanhMucXe dm = (DanhMucXe) cboLoai.getSelectedItem();
            if (dm == null) { MessageUtil.error(this, "Chưa có danh mục xe"); return; }
            Xe x = new Xe();
            if (maXeEdit != null) x.setMaXe(maXeEdit);
            x.setBienSo(txtBienSo.getText().trim());
            x.setSoKhung(txtSoKhung.getText().trim());
            x.setSoMay(txtSoMay.getText().trim());
            x.setMauXe(txtMau.getText().trim());
            x.setNamSanXuat(Integer.parseInt(txtNam.getText().trim()));
            x.setSoKmHienTai(Double.parseDouble(txtKm.getText().trim()));
            x.setDonGiaThueNgay(Double.parseDouble(txtGia.getText().trim()));
            x.setMaLoaiXe(dm.getMaLoaiXe());
            x.setTrangThaiXe(cboTrangThai.getSelectedItem().toString());

            XeService service = new XeService();
            boolean ok = maXeEdit == null ? service.insertXe(x) : service.updateXe(x);
            if (ok) { MessageUtil.info(this, "Lưu xe thành công"); dispose(); }
            else MessageUtil.error(this, "Không lưu được xe. Kiểm tra dữ liệu hoặc trạng thái xe.");
        } catch (Exception ex) {
            MessageUtil.error(this, "Dữ liệu xe không hợp lệ");
        }
    }
}
