import geometry.Line;
import geometry.Point;

import javax.swing.*;

public class Model implements Runnable {
    private double sigma;
    private double rho;
    private double beta;
    private double dt;
    private Point start;
    private int pointsByIteration = 50;

    private JFrame view;
    private final Line line;

    public Model(Line line) {
        this.line = line;
        setDefault();
    }

    private double dxdt(double x, double y, double z) {
        return (sigma * (y - x));
    }

    private double dydt(double x, double y, double z) {
        return (x * (rho - z) - y);
    }

    private double dzdt(double x, double y, double z) {
        return (x * y - beta * z);
    }

    public Point next(Point prev) {
        double x = prev.x;
        double y = prev.y;
        double z = prev.z;
        return new Point(x + dt * dxdt(x, y, z),
                y + dt * dydt(x, y, z),
                z + dt * dzdt(x, y, z));
    }

    public void setConstants(double rho, double sigma, double beta, double dt) {
        synchronized (line) {
            this.rho = rho;
            this.sigma = sigma;
            this.beta = beta;
            this.dt = dt;
        }
    }

    public double getSigma() {
        return sigma;
    }

    public double getRho() {
        return rho;
    }

    public double getBeta() {
        return beta;
    }

    public double getDt() {
        return dt;
    }

    public void setDefault() {
        rho = 28.;
        sigma = 11.;
        beta = 8. / 3.;
        dt = 0.0008;
        start = new Point(1., 1., 1.);
    }

    private void generate(Line line, Point point) {
        for (int i = 0; i < pointsByIteration; ++i) {
            point = next(point);
            line.add(point);
        }
    }

    @Override
    public void run() {
        int sleep;
        restart(start);
        while (true) {
            synchronized (line) {
                generate(line, line.back());
                sleep = 15 - line.size() / 5_000;
            }
            if (view != null) {
                view.repaint();
            }
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setView(JFrame view) {
        this.view = view;
    }

    public void restart(Point start) {
        synchronized (line) {
            line.clear();
            this.start = new Point(start.x, start.y, start.z);
            line.add(start);
        }
    }

    public Point getStart() {
        return new Point(start.x, start.y, start.z);
    }

    public int getPointsByIteration() {
        return pointsByIteration;
    }

    public void setPointsByIteration(int pointsByIteration) {
        synchronized (line) {
            this.pointsByIteration = pointsByIteration;
        }
    }
}
