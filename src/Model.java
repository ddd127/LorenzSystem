import geometry.Line;
import geometry.Point;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class Model implements Runnable {
    private double sigma;
    private double rho;
    private double beta;
    private double dt;
    private Point start;
    private int pointsByIteration;

    private View view;
    private final Map<EvaluateType, Line> lines;
    private final Map<EvaluateType, Boolean> visible;
    private final ReentrantLock lock;

    public Model(ReentrantLock lock) {
        this.lock = lock;
        this.lines = Map.of(
                EvaluateType.EULER, new Line(),
                EvaluateType.RUNGE, new Line(),
                EvaluateType.ADAMS, new Line());
        this.visible = new HashMap<>();
        visible.put(EvaluateType.EULER, true);
        visible.put(EvaluateType.RUNGE, false);
        visible.put(EvaluateType.ADAMS, false);
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

    public void setConstants(double rho, double sigma, double beta, double dt) {
        lock.lock();
        this.rho = rho;
        this.sigma = sigma;
        this.beta = beta;
        this.dt = dt;
        lock.unlock();
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
        lock.lock();
        rho = 28.;
        sigma = 11.;
        beta = 8. / 3.;
        dt = 0.002;
        start = new Point(1., 1., 1.);
        pointsByIteration = 10;
        lock.unlock();
    }

    private Point nextEuler(Line line) {
        final Point point = line.back();
        double x = point.x;
        double y = point.y;
        double z = point.z;
        return new Point(
                x + dt * dxdt(x, y, z),
                y + dt * dydt(x, y, z),
                z + dt * dzdt(x, y, z));
    }

    private Point nextRunge(Line line) {
        final Point point = line.back();
        double x = point.x;
        double y = point.y;
        double z = point.z;

        double x1, x2, x3, x4;
        double y1, y2, y3, y4;
        double z1, z2, z3, z4;

        x1 = dxdt(x, y, z);
        y1 = dydt(x, y, z);
        z1 = dzdt(x, y, z);

        x2 = dxdt(x + dt * x1 / 2, y + dt * y1 / 2, z + dt * z1 / 2);
        y2 = dydt(x + dt * x1 / 2, y + dt * y1 / 2, z + dt * z1 / 2);
        z2 = dzdt(x + dt * x1 / 2, y + dt * y1 / 2, z + dt * z1 / 2);

        x3 = dxdt(x + dt * x2 / 2, y + dt * y2 / 2, z + dt * z2 / 2);
        y3 = dydt(x + dt * x2 / 2, y + dt * y2 / 2, z + dt * z2 / 2);
        z3 = dzdt(x + dt * x2 / 2, y + dt * y2 / 2, z + dt * z2 / 2);

        x4 = dxdt(x + dt * x3, y + dt * y3, z + dt * z3);
        y4 = dydt(x + dt * x3, y + dt * y3, z + dt * z3);
        z4 = dzdt(x + dt * x3, y + dt * y3, z + dt * z3);

        double next_x = x + dt * (x1 + 2 * x2 + 2 * x3 + x4) / 6;
        double next_y = y + dt * (y1 + 2 * y2 + 2 * y3 + y4) / 6;
        double next_z = z + dt * (z1 + 2 * z2 + 2 * z3 + z4) / 6;

        return new Point(next_x, next_y, next_z);
    }

    private Point nextAdams(Line line) {
        int n = 5;
        if (line.size() < n) {
            return nextRunge(line);
        }
        Point[] f = new Point[n];
        Point[] vals = line.lastK(n);
        for (int i = 0; i < n; ++i) {
            f[i] = new Point(
                    dxdt(vals[i].x, vals[i].y, vals[i].z),
                    dydt(vals[i].x, vals[i].y, vals[i].z),
                    dzdt(vals[i].x, vals[i].y, vals[i].z)
            );
        }
        Point prev = line.back();
        return new Point(
                prev.x + dt * (251 * f[4].x + 646 * f[3].x - 264 * f[2].x + 106 * f[1].x - 19 * f[0].x) / 720,
                prev.y + dt * (251 * f[4].y + 646 * f[3].y - 264 * f[2].y + 106 * f[1].y - 19 * f[0].y) / 720,
                prev.z + dt * (251 * f[4].z + 646 * f[3].z - 264 * f[2].z + 106 * f[1].z - 19 * f[0].z) / 720
        );
    }

    private void countNext(Line line, EvaluateType type) {
        switch (type) {
            case EULER:
                line.add(nextEuler(line));
                break;
            case RUNGE:
                line.add(nextRunge(line));
                break;
            case ADAMS:
                line.add(nextAdams(line));
                break;
        }
    }

    private void generate() {
        for (int i = 0; i < pointsByIteration; ++i) {
            for (EvaluateType type : lines.keySet()) {
                Line line = lines.get(type);
                countNext(line, type);
            }
        }
    }

    @Override
    public void run() {
        int sleep;
        restart(start);
        while (true) {
            lock.lock();
            generate();
            sleep = 12 - lines.get(EvaluateType.EULER).size() * 10 / Line.CAPACITY;
            lock.unlock();
            Map<EvaluateType, Line> map = new HashMap<>();
            for (EvaluateType type : EvaluateType.values()) {
                if (visible.get(type)) {
                    map.put(type, lines.get(type));
                }
            }
            if (view != null) {
                view.repaint(map);
            }
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setView(View view) {
        this.view = view;
    }

    public void restart(Point start) {
        lock.lock();
        for (Line line : lines.values()) {
            line.clear();
            this.start = new Point(start.x, start.y, start.z);
            line.add(start);
        }
        lock.unlock();
    }

    public Point getStart() {
        return new Point(start.x, start.y, start.z);
    }

    public int getPointsByIteration() {
        return pointsByIteration;
    }

    public void setPointsByIteration(int pointsByIteration) {
        lock.lock();
        this.pointsByIteration = pointsByIteration;
        lock.unlock();
    }

    public void setVisible(EvaluateType type, boolean value) {
        visible.put(type, value);
    }

    public boolean getVisible(EvaluateType type) {
        return visible.get(type);
    }
}
