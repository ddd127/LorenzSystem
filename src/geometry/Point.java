package geometry;

public class Point {
    public final double x;
    public final double y;
    public final double z;

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point move(Vector vector) {
        return new Point(vector.x + x, vector.y + y, vector.z + z);
    }
}
