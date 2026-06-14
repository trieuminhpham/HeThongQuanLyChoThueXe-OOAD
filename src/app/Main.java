package app;

import javax.swing.SwingUtilities;
import view.StartFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StartFrame().setVisible(true));
    }
}
