import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private final ArrayList<LineSegment> segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points are null");
        }

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("point is null");
            }
        }

        Point[] pointsTemp = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            pointsTemp[i] = points[i];
        }
        Arrays.sort(pointsTemp);

        segments = new ArrayList<LineSegment>();

        for (int p = 0; p < pointsTemp.length - 3; p++) {
            for (int q = p + 1; q < pointsTemp.length - 2; q++) {
                for (int r = q + 1; r < pointsTemp.length - 1; r++) {
                    for (int s = r + 1; s < pointsTemp.length; s++) {
                        if (pointsTemp[p].slopeTo(pointsTemp[q]) == pointsTemp[p]
                                .slopeTo(pointsTemp[r])
                                &&
                                pointsTemp[p].slopeTo(pointsTemp[q]) == pointsTemp[p]
                                        .slopeTo(pointsTemp[s])) {
                            segments.add(new LineSegment(pointsTemp[p], pointsTemp[s]));
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }
}
