import geometry.Line;
import geometry.Point;

import javax.swing.*;

public class Main extends JFrame {

    public static void main(String[] args) {
        Line line = new Line();
        line.add(new Point(1., 1., 1.));
        JFrame window = new MainWindow(line);
        new Thread(new Model(line, window)).start();
    }
}
