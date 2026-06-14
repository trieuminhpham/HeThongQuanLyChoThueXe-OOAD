package view.dialog;

import javax.swing.*;
import java.awt.*;

public class DuyetDatTruocDialog extends JDialog {
    public DuyetDatTruocDialog(Frame owner, boolean modal) {
        super(owner, modal);
        setTitle("DuyetDatTruocDialog");
        setSize(360, 160);
        setLocationRelativeTo(owner);
        add(new JLabel("Chức năng duyệt đã xử lý trong BookingRequestPanel", SwingConstants.CENTER));
    }
}
