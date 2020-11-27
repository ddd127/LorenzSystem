package geometry;

import java.util.Iterator;
import java.util.LinkedList;

public class Line implements Iterable<Segment> {
    LinkedList<Point> points;
    public static final int CAPACITY = 50_000;

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
            points.poll();
        }
        points.add(point);
    }

    public Point back() {
        return points.getLast();
    }

    public int size() {
        return points.size();
    }
}
