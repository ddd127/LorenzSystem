import javax.swing.*;
import java.awt.*;
import java.util.concurrent.locks.ReentrantLock;

public class MainWindow extends JFrame {

    MainWindow(ReentrantLock lock, Model model) {
        Color background = new Color(50, 50, 50);

        this.setSize(1100, 700);
        this.setMinimumSize(new Dimension(900, 700));
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(background);

        int settingsWidth = 300;
        View view = new View(lock, settingsWidth);
        this.add(view, BorderLayout.WEST);
        this.add(new SettingsPanel(model), BorderLayout.EAST);
        this.setVisible(true);

        model.setView(view);
    }
}
