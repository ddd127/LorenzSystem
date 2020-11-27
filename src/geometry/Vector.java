package geometry;

public class Vector {
    public double x;
    public double y;
    public double z;

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector normalize() {
        double length = Math.sqrt(x * x + y * y + z * z);
        return new Vector(x / length, y / length, z / length);
    }

    public Vector negate() {
        return new Vector(-x, -y, -z);
    }

    public static void moveAtoBonGrad(Vector a, Vector b, int grad) {
        Vector newA = new Vector((90 - grad) * a.x + grad * b.x,
                (90 - grad) * a.y + grad * b.y,
                (90 - grad) * a.z + grad * b.z).normalize();
        Vector newB = new Vector((90 - grad) * b.x - grad * a.x,
                (90 - grad) * b.y - grad * a.y,
                (90 - grad) * b.z - grad * a.z).normalize();
        a.x = newA.x;
        a.y = newA.y;
        a.z = newA.z;
        b.x = newB.x;
        b.y = newB.y;
        b.z = newB.z;
    }
}
