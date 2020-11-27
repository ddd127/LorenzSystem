import geometry.Line;
import geometry.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainWindow extends JFrame {

    private final View view;

    MainWindow(Line line) {
        this.setSize(800, 837);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(new Color(0, 0, 0));

        line.add(new Point(1., 1., 1.));

        this.view = new View(line);

        this.add(view);
        this.setVisible(true);
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                int code = keyEvent.getKeyCode();
                synchronized (line) {
                    switch (code) {
                        case KeyEvent.VK_EQUALS:
                            view.zoomUp();
                            break;
                        case KeyEvent.VK_MINUS:
                            view.zoomDown();
                            break;
                        case KeyEvent.VK_LEFT:
                            view.moveRight();
                            break;
                        case KeyEvent.VK_RIGHT:
                            view.moveLeft();
                            break;
                        case KeyEvent.VK_UP:
                            view.moveDown();
                            break;
                        case KeyEvent.VK_DOWN:
                            view.moveUp();
                            break;
                        case KeyEvent.VK_W:
                            view.camUp();
                            break;
                        case KeyEvent.VK_S:
                            view.camDown();
                            break;
                        case KeyEvent.VK_A:
                            view.camLeft();
                            break;
                        case KeyEvent.VK_D:
                            view.camRight();
                            break;
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
            }
        });
    }
}
