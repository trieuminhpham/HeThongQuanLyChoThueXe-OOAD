package app;

import javax.swing.SwingUtilities;
import util.UITheme;
import view.StartFrame;

public class Main {
    public static void main(String[] args) {
        UITheme.install();
        SwingUtilities.invokeLater(() -> new StartFrame().setVisible(true));
    }
}
