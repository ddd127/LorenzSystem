package geometry;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class Line implements Iterable<Segment> {
    LinkedList<Point> points;
    public static final int CAPACITY = 10_000;

    public Line() {
        points = new LinkedList<>();
    }

    @Override
    public Iterator<Segment> iterator() {
        LinkedList<Segment> segments = new LinkedList<>();
        Point prev = null;
        for (Point cur : points) {
            if (prev != null) {
                segments.add(new Segment(prev, cur));
            }
            prev = cur;
        }
        return segments.iterator();
    }

    public void add(Point point) {
        if (points.size() >= CAPACITY) {
            points.removeLast();
        }
        points.addFirst(point);
    }

    public Point back() {
        return points.getFirst();
    }

    public Point[] lastK(int k) {
        k = Math.min(k, size());
        Point[] res = new Point[k];
        ListIterator<Point> iterator = points.listIterator();
        for (int i = 0; i < k; ++i) {
            res[i] = iterator.next();
        }
        return res;
    }

    public int size() {
        return points.size();
    }

    public void clear() {
        points.clear();
    }
}
