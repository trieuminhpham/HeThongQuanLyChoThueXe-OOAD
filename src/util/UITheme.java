package util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UITheme {
    public static final Color PRIMARY = new Color(219, 234, 254);
    public static final Color PRIMARY_DARK = new Color(147, 197, 253);
    public static final Color BG = new Color(244, 247, 250);
    public static final Color CARD = Color.WHITE;
    public static final Color TEXT = new Color(15, 23, 42);
    public static final Color MUTED_TEXT = new Color(100, 116, 139);
    public static final Color BORDER = new Color(148, 163, 184);
    public static final Color SIDEBAR = new Color(224, 242, 254);
    public static final Color SUCCESS = new Color(209, 250, 229);
    public static final Color WARNING = new Color(254, 243, 199);
    public static final Color DANGER = new Color(254, 226, 226);

    public static void install() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        Font font = new Font("Segoe UI", Font.PLAIN, 13);
        UIManager.put("Label.font", font);
        UIManager.put("Button.font", font);
        UIManager.put("TextField.font", font);
        UIManager.put("PasswordField.font", font);
        UIManager.put("ComboBox.font", font);
        UIManager.put("Table.font", font);
        UIManager.put("TableHeader.font", font.deriveFont(Font.BOLD));
        UIManager.put("Panel.background", BG);
        UIManager.put("Button.background", CARD);
        UIManager.put("Button.foreground", TEXT);
        UIManager.put("Button.disabledText", MUTED_TEXT);
        UIManager.put("Table.background", CARD);
        UIManager.put("Table.foreground", TEXT);
        UIManager.put("Table.selectionBackground", new Color(204, 251, 241));
        UIManager.put("Table.selectionForeground", TEXT);
    }

    public static void applyFrameStyle(JFrame frame) {
        frame.getContentPane().setBackground(BG);
    }

    public static JLabel title(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 22));
        label.setForeground(TEXT);
        label.setBorder(new EmptyBorder(15, 10, 15, 10));
        return label;
    }

    public static JButton primaryButton(String text) {
        return normalButton(text);
    }

    public static JButton normalButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setFocusPainted(false);
        return btn;
    }

    public static JButton successButton(String text) {
        return normalButton(text);
    }

    public static JButton dangerButton(String text) {
        return normalButton(text);
    }

    public static JPanel cardPanel(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(CARD);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(16, 16, 16, 16)
        ));
        return panel;
    }

    public static JLabel sectionTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setForeground(TEXT);
        label.setBorder(new EmptyBorder(4, 4, 12, 4));
        return label;
    }

    public static void styleTable(JTable table) {
        table.setRowHeight(30);
        table.setShowVerticalLines(false);
        table.setGridColor(BORDER);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setSelectionBackground(new Color(204, 251, 241));
        table.setSelectionForeground(TEXT);
        table.getTableHeader().setPreferredSize(new Dimension(0, 34));
        table.getTableHeader().setBackground(new Color(241, 245, 249));
        table.getTableHeader().setForeground(TEXT);
        table.getTableHeader().setReorderingAllowed(false);
    }
}
