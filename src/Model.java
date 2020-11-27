import geometry.Line;
import geometry.Point;

import javax.swing.*;

public class Model implements Runnable {
    double sigma;
    double rho;
    double beta;
    double dt;

    private final JFrame view;

    private final Line line;

    public Model(Line line, JFrame view) {
        this.view = view;
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

    public double getSigma() {
        return sigma;
    }

    public void setSigma(double sigma) {
        this.sigma = sigma;
    }

    public double getRho() {
        return rho;
    }

    public void setRho(double rho) {
        this.rho = rho;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public double getDt() {
        return dt;
    }

    public void setDt(double dt) {
        this.dt = dt;
    }

    public void setDefault() {
        rho = 28.;
        sigma = 11.;
        beta = 8. / 3.;
        dt = 0.0008;
    }

    private Point generate(Line line, Point point, int count) {
        for (int i = 0; i < count; ++i) {
            point = next(point);
            line.add(point);
        }
        return point;
    }

    @Override
    public void run() {
        int sleep;
        while (true) {
            synchronized (line) {
                generate(line, line.back(), 100);
                sleep = 15 - line.size() / 10_000;
            }
            view.repaint();
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
