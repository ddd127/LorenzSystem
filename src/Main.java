import geometry.Line;

import javax.swing.*;

public class Main extends JFrame {

    public static void main(String[] args) {
        Line line = new Line();

        Model model = new Model(line);

        JFrame window = new MainWindow(line, model);

        model.setView(window);

        new Thread(model).start();
    }
}
