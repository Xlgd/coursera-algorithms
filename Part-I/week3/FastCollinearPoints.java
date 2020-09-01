/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    
    private final ArrayList<LineSegment> segments = new ArrayList<>();

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
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
        for (int i = 0; i < pointsTemp.length - 3; i++) {
            Arrays.sort(pointsTemp);
            Arrays.sort(pointsTemp, pointsTemp[i].slopeOrder());

            for (int p = 0, first = 1, last = 2; last < pointsTemp.length; last++) {
                while (last < pointsTemp.length
                        && Double.compare(pointsTemp[p].slopeTo(pointsTemp[first]),
                                          pointsTemp[p].slopeTo(pointsTemp[last])) == 0) {
                    last++;
                }

                if (last - first >= 3 && pointsTemp[p].compareTo(pointsTemp[first]) < 0) {
                    segments.add(new LineSegment(pointsTemp[p], pointsTemp[last - 1]));
                }

                first = last;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
