import geometry.Camera;
import geometry.Line;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class View extends JPanel {

    private Map<EvaluateType, Line> map;
    private final ReentrantLock lock;
    private final Camera camera;
    private final int leftMargin;
    private final Map<EvaluateType, Color> colorMap;

    public View(ReentrantLock lock, int leftMargin) {
        this.leftMargin = leftMargin;
        this.lock = lock;
        this.camera = new Camera();
        this.setFocusable(true);
        this.colorMap = Map.of(
                EvaluateType.EULER, Color.BLUE,
                EvaluateType.RUNGE, Color.RED,
                EvaluateType.ADAMS, Color.MAGENTA
        );
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                int code = keyEvent.getKeyCode();
                switch (code) {
                    case KeyEvent.VK_EQUALS:
                        zoomUp();
                        break;
                    case KeyEvent.VK_MINUS:
                        zoomDown();
                        break;
                    case KeyEvent.VK_LEFT:
                        moveRight();
                        break;
                    case KeyEvent.VK_RIGHT:
                        moveLeft();
                        break;
                    case KeyEvent.VK_UP:
                        moveDown();
                        break;
                    case KeyEvent.VK_DOWN:
                        moveUp();
                        break;
                    case KeyEvent.VK_W:
                        camUp();
                        break;
                    case KeyEvent.VK_S:
                        camDown();
                        break;
                    case KeyEvent.VK_A:
                        camLeft();
                        break;
                    case KeyEvent.VK_D:
                        camRight();
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
            }
        });
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                requestFocus();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
            }
        });
        this.setOpaque(true);
        this.setVisible(true);
    }

    private void paintLine(Graphics2D g, Line line, Color color) {
        g.setColor(color);
        camera.drawLine(g, line, getWidth() / 2, getHeight() / 2);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        if (map == null) {
            return;
        }
        lock.lock();
        this.setPreferredSize(new Dimension(this.getParent().getWidth() - leftMargin, this.getParent().getHeight()));
        this.setSize(new Dimension(this.getParent().getWidth() - leftMargin, this.getParent().getHeight()));
        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
        map.forEach((type, line) -> { if (map.containsKey(type)) paintLine((Graphics2D) graphics, line, colorMap.get(type)); });
        lock.unlock();
    }

    public void repaint(Map<EvaluateType, Line> map) {
        lock.lock();
        this.map = map;
        lock.unlock();
        this.repaint();
    }

    public void moveUp() {
        lock.lock();
        camera.moveUp();
        lock.unlock();
    }

    public void moveDown() {
        lock.lock();
        camera.moveDown();
        lock.unlock();
    }

    public void moveRight() {
        lock.lock();
        camera.moveRight();
        lock.unlock();
    }

    public void moveLeft() {
        lock.lock();
        camera.moveLeft();
        lock.unlock();
    }

    public void zoomUp() {
        lock.lock();
        camera.zoomUp();
        lock.unlock();
    }

    public void zoomDown() {
        lock.lock();
        camera.zoomDown();
        lock.unlock();
    }

    public void camUp() {
        lock.lock();
        camera.camUp();
        lock.unlock();
    }

    public void camDown() {
        lock.lock();
        camera.camDown();
        lock.unlock();
    }

    public void camRight() {
        lock.lock();
        camera.camRight();
        lock.unlock();
    }

    public void camLeft() {
        lock.lock();
        camera.camLeft();
        lock.unlock();
    }
}
