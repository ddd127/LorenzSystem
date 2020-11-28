package geometry;

import java.awt.*;

public class Camera {

    private static final Vector X = new Vector(1., 0., 0.);
    private static final Vector Y = new Vector(0., 1., 0.);
    private static final Vector Z = new Vector(0., 0., 1.);

    private static final int ROTATE_ANGLE = 2;
    private static final double ZOOM_MUL = 1.05;
    private static final double MOVE_SPEED = 10.0;

    private final Point position;
    private final Vector view;
    private final Vector top;
    private final Vector right;
    private double zoom;

    public Camera() {
        position = new Point(0., 0., 0.);
        view = Z;
        top = Y;
        right = X;
        zoom = 15.;
    }

    public void drawLine(Graphics2D g, Line line, int shiftX, int shiftY) {
        line = Geometry.lineToNewCoordinateSystem(line, position, right, top, view);

        Color color = new Color(127, 0, 255);
        g.setColor(color);

        int firstChange = Line.CAPACITY / 5;
        int secondChange = Line.CAPACITY * 4 / 5;
        int frequency = (firstChange + 126) / 127;
        int iteration = Line.CAPACITY - line.size();

        for (Segment seg : line) {
            if (iteration % frequency == 0) {
                if (iteration < firstChange) {
                    color = new Color(color.getRed(), 0, Math.max(127, color.getBlue() - 1));
                    g.setColor(color);
                } else if (iteration > secondChange) {
                    color = new Color(Math.min(color.getRed() + 1, 255), 0, color.getBlue());
                    g.setColor(color);
                }
            }
            Point a = seg.a;
            Point b = seg.b;
            g.drawLine((int) (a.x * zoom) + shiftX,
                    (int) (a.y * zoom) + shiftY,
                    (int) (b.x * zoom) + shiftX,
                    (int) (b.y * zoom) + shiftY);
            ++iteration;
        }
    }

    public void moveUp() {
        position.move(top, MOVE_SPEED / zoom);
    }

    public void moveDown() {
        position.move(top.negate(), MOVE_SPEED / zoom);
    }

    public void moveRight() {
        position.move(right.negate(), MOVE_SPEED / zoom);
    }

    public void moveLeft() {
        position.move(right, MOVE_SPEED / zoom);
    }

    public void zoomUp() {
        zoom *= ZOOM_MUL;
    }

    public void zoomDown() {
        zoom /= ZOOM_MUL;
    }

    public void camUp() {
        Vector.moveAtoBonGrad(view, top, ROTATE_ANGLE);
    }

    public void camDown() {
        Vector.moveAtoBonGrad(top, view, ROTATE_ANGLE);
    }

    public void camRight() {
        Vector.moveAtoBonGrad(right, view, ROTATE_ANGLE);
    }

    public void camLeft() {
        Vector.moveAtoBonGrad(view, right, ROTATE_ANGLE);
    }
}
