import javax.swing.*;
import java.util.concurrent.locks.ReentrantLock;

public class Main extends JFrame {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        Model model = new Model(lock);

        new MainWindow(lock, model);

        new Thread(model).start();
    }
}
