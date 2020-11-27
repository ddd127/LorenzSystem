package geometry;

import java.awt.*;

public class Camera {

    private static final Vector X = new Vector(1., 0., 0.);
    private static final Vector Y = new Vector(0., 1., 0.);
    private static final Vector Z = new Vector(0., 0., 1.);

    private static final int ROTATE_ANGLE = 3;
    private static final double ZOOM_MUL = 1.05;

    private Point position;
    private Vector view;
    private Vector top;
    private Vector right;
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
        position = position.move(top);
    }

    public void moveDown() {
        position = position.move(top.negate());
    }

    public void moveRight() {
        position = position.move(right.negate());
    }

    public void moveLeft() {
        position = position.move(right);
    }

    public void zoomUp() {
        zoom *= 1.05;
    }

    public void zoomDown() {
        zoom *= 0.95;
    }

    public void camUp() {
        Vector newView = Vector.moveAtoBonGrad(view, top, ROTATE_ANGLE);
        Vector newTop = Vector.moveBfromAonGrad(view, top, ROTATE_ANGLE);
        view = newView;
        top = newTop;
    }

    public void camDown() {
        Vector newView = Vector.moveBfromAonGrad(top, view, ROTATE_ANGLE);
        Vector newTop = Vector.moveAtoBonGrad(top, view, ROTATE_ANGLE);
        view = newView;
        top = newTop;
    }

    public void camRight() {
        Vector newView = Vector.moveAtoBonGrad(view, right, ROTATE_ANGLE);
        Vector newRight = Vector.moveBfromAonGrad(view, right, ROTATE_ANGLE);
        view = newView;
        right = newRight;
    }

    public void camLeft() {
        Vector newView = Vector.moveBfromAonGrad(right, view, ROTATE_ANGLE);
        Vector newRight = Vector.moveAtoBonGrad(right, view, ROTATE_ANGLE);
        view = newView;
        right = newRight;
    }
}
