import geometry.Camera;
import geometry.Line;

import javax.swing.*;
import java.awt.*;

public class View extends JPanel {

    private final Line points;
    private final Camera camera;

    public View(Line points) {
        this.points = points;
        this.camera = new Camera();
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
