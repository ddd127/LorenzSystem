import geometry.Line;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    MainWindow(Line line, Model model) {
        this.setSize(1100, 837);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(new Color(0, 0, 0));

        this.add(new View(line), BorderLayout.WEST);
        this.add(new SettingsPanel(model), BorderLayout.EAST);
        this.setVisible(true);
    }
}
