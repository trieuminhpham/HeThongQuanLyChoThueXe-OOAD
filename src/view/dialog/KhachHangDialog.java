package view.dialog;

import javax.swing.*;
import java.awt.*;

public class KhachHangDialog extends JDialog {
    public KhachHangDialog(Frame owner, boolean modal) {
        super(owner, modal);
        setTitle("KhachHangDialog");
        setSize(360, 160);
        setLocationRelativeTo(owner);
        add(new JLabel("Có thể bổ sung form thêm/sửa khách hàng ở đây", SwingConstants.CENTER));
    }
}
