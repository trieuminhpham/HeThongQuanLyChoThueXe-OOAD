package util;

import java.awt.Component;
import javax.swing.JOptionPane;

public class MessageUtil {
    public static void info(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void error(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    public static boolean confirm(Component parent, String message) {
        return JOptionPane.showConfirmDialog(parent, message, "Xác nhận",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}
