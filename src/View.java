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
        camera.moveUp();
    }

    public void moveDown() {
        camera.moveDown();
    }

    public void moveRight() {
        camera.moveRight();
    }

    public void moveLeft() {
        camera.moveLeft();
    }

    public void zoomUp() {
        camera.zoomUp();
    }

    public void zoomDown() {
        camera.zoomDown();
    }

    public void camUp() {
        camera.camUp();
    }

    public void camDown() {
        camera.camDown();
    }

    public void camRight() {
        camera.camRight();
    }

    public void camLeft() {
        camera.camLeft();
    }
}
