import geometry.Camera;
import geometry.Line;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class View extends JPanel {

    private final Line points;
    private final Camera camera;

    public View(Line points) {
        this.points = points;
        this.camera = new Camera();
        this.setFocusable(true);
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

    private void paintLine(Graphics2D g) {
        Color color = new Color(127, 0, 127);
        g.setColor(color);
        camera.drawLine(g, points, getWidth() / 2, getHeight() / 2);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        if (points == null) {
            return;
        }
        synchronized (points) {
            this.setPreferredSize(new Dimension(this.getParent().getWidth() - 300, this.getParent().getHeight()));
            this.setSize(new Dimension(this.getParent().getWidth() - 300, this.getParent().getHeight()));
            graphics.setColor(Color.black);
            graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
            paintLine((Graphics2D) graphics);
        }
    }

    public void moveUp() {
        synchronized (points) {
            camera.moveUp();
        }
    }

    public void moveDown() {
        synchronized (points) {
            camera.moveDown();
        }
    }

    public void moveRight() {
        synchronized (points) {
            camera.moveRight();
        }
    }

    public void moveLeft() {
        synchronized (points) {
            camera.moveLeft();
        }
    }

    public void zoomUp() {
        synchronized (points) {
            camera.zoomUp();
        }
    }

    public void zoomDown() {
        synchronized (points) {
            camera.zoomDown();
        }
    }

    public void camUp() {
        synchronized (points) {
            camera.camUp();
        }
    }

    public void camDown() {
        synchronized (points) {
            camera.camDown();
        }
    }

    public void camRight() {
        synchronized (points) {
            camera.camRight();
        }
    }

    public void camLeft() {
        synchronized (points) {
            camera.camLeft();
        }
    }
}
