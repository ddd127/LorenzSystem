package geometry;

public class Vector {
    public final double x;
    public final double y;
    public final double z;

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

    public static Vector moveAtoBonGrad(Vector a, Vector b, int grad) {
        return new Vector((90 - grad) * a.x + grad * b.x,
                (90 - grad) * a.y + grad * b.y,
                (90 - grad) * a.z + grad * b.z).normalize();
    }

    public static Vector moveBfromAonGrad(Vector a, Vector b, int grad) {
        return moveAtoBonGrad(b, a.negate(), grad);
    }
}
