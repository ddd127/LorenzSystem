package geometry;

public class Geometry {

    private static final double EPS = 1e-6;

    public static Point pointToNewCoordinateSystem(Point point,
                                            Point center,
                                            double[][] mat) {
        double x = point.x - center.x;
        double y = point.y - center.y;
        double z = point.z - center.z;

        double new_x = x * mat[0][0] + y * mat[1][0] + z * mat[2][0];
        double new_y = x * mat[0][1] + y * mat[1][1] + z * mat[2][1];
        double new_z = x * mat[0][2] + y * mat[1][2] + z * mat[2][2];

        return new Point(new_x, new_y, new_z);
    }

    public static Line lineToNewCoordinateSystem(Line line,
                                          Point center,
                                          Vector newX,
                                          Vector newY,
                                          Vector newZ) {
        double[][] mat = invert(newX, newY, newZ);

        Line result = new Line();
        for (Point point : line.points) {
            result.points.add(pointToNewCoordinateSystem(point, center, mat));
        }

        return result;
    }

    public static double[][] invert(Vector newX, Vector newY, Vector newZ) {
        double[][] mat = new double[3][3];
        double[][] dop = new double[3][3];

        mat[0][0] = newX.x;
        mat[1][0] = newX.y;
        mat[2][0] = newX.z;

        mat[0][1] = newY.x;
        mat[1][1] = newY.y;
        mat[2][1] = newY.z;

        mat[0][2] = newZ.x;
        mat[1][2] = newZ.y;
        mat[2][2] = newZ.z;

        double det = (mat[0][0] * mat[1][1] * mat[2][2]
                - mat[0][0] * mat[1][2] * mat[2][1]
                - mat[0][1] * mat[1][0] * mat[2][2]
                + mat[0][1] * mat[1][2] * mat[2][0]
                + mat[0][2] * mat[1][0] * mat[2][1]
                - mat[0][2] * mat[1][1] * mat[2][0]);

        if (Double.compare(Math.abs(det), EPS) < 0) {
            throw new IllegalArgumentException("det == 0");
        }

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                dop[i][j] = A(mat, i, j) / det;
            }
        }

        return dop;
    }

    private static double A(double[][] mat, int i, int j) {
        double[] a = new double[4];
        int index = 0;
        for (int x = 0; x < 3; ++x) {
            if (x != i) {
                for (int y = 0; y < 3; ++y) {
                    if (y != j) {
                        a[index++] = mat[x][y];
                    }
                }
            }
        }
        return ((i + j) % 2 == 0 ? 1 : -1) * (a[0] * a[3] - a[1] * a[2]);
    }
}
