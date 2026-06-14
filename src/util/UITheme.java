package util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UITheme {
    public static final Color PRIMARY = new Color(33, 91, 162);
    public static final Color PRIMARY_DARK = new Color(22, 63, 115);
    public static final Color BG = new Color(245, 247, 250);
    public static final Color CARD = Color.WHITE;
    public static final Color TEXT = new Color(35, 45, 60);
    public static final Color SUCCESS = new Color(32, 128, 83);
    public static final Color WARNING = new Color(190, 120, 20);
    public static final Color DANGER = new Color(180, 55, 55);

    public static void applyFrameStyle(JFrame frame) {
        frame.getContentPane().setBackground(BG);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
    }

    public static JLabel title(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 22));
        label.setForeground(TEXT);
        label.setBorder(new EmptyBorder(15, 10, 15, 10));
        return label;
    }

    public static JButton primaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBackground(PRIMARY);
        btn.setForeground(Color.WHITE);
        return btn;
    }

    public static JButton normalButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setFocusPainted(false);
        return btn;
    }

    public static JPanel cardPanel(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(CARD);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 230)),
                new EmptyBorder(12, 12, 12, 12)
        ));
        return panel;
    }
}
