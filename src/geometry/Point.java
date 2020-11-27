package geometry;

public class Point {
    public double x;
    public double y;
    public double z;

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void move(Vector vector, double k) {
        x += vector.x * k;
        y += vector.y * k;
        z += vector.z * k;
    }
}
