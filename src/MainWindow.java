import geometry.Line;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    MainWindow(Line line, Model model) {
        Color background = new Color(50, 50, 50);

        this.setSize(1100, 700);
        this.setPreferredSize(new Dimension(1100, 837));
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(background);

        int settingsWidth = 300;
        this.add(new View(line, settingsWidth), BorderLayout.WEST);
//        JScrollPane scrollPane = new JScrollPane(new SettingsPanel(model));
//        scrollPane.setBackground(background);
//        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        scrollPane.getVerticalScrollBar().setBackground(background);
//        scrollPane.getViewport().setBackground(background);
//        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
//            @Override
//            protected void configureScrollBarColors() {
//                this.thumbColor = Color.BLACK;
//            }
//        });
//        this.add(scrollPane, BorderLayout.EAST);
        this.add(new SettingsPanel(model), BorderLayout.EAST);
        this.setVisible(true);
    }
}
