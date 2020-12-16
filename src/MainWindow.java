import geometry.Line;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    MainWindow(Line line, Model model) {
        Color background = new Color(50, 50, 50);

        this.setSize(1100, 850);
        this.setMinimumSize(new Dimension(900, 850));
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(background);

        int settingsWidth = 300;
        this.add(new View(line, settingsWidth), BorderLayout.WEST);
        this.add(new SettingsPanel(model), BorderLayout.EAST);
        this.setVisible(true);
    }
}
