import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private ArrayList<LineSegment> segmentList;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();

        segmentList = new ArrayList<LineSegment>();

        for(int i = 0; i < points.length; i++) {
            for (int j = 0;j < points.length; j++) {
                if (i == j) continue;
                for (int k = 0; k < points.length; k++) {
                    if (j == k || i == k) continue;
                    for (int z = 0; z < points.length; z++) {
                        if (i == z || j == z || k == z) continue;

                        if (points[i].slopeTo(points[j]) == points[j].slopeTo(points[k])
                                && points[j].slopeTo(points[k]) == points[k].slopeTo(points[z])) {
                            segmentList.add(getEndpointsSegment(points[i], points[j], points[k], points[z]));
                        }
                    }
                }
            }
        }
    }

    private LineSegment getEndpointsSegment(Point a, Point b, Point c, Point d) {
        if (a == null || b == null || c == null || d == null) throw new IllegalArgumentException();

        Point[] pointsSegment = new Point[] {a, b, c, d};
        Arrays.sort(pointsSegment, a.slopeOrder());

        return new LineSegment(pointsSegment[0], pointsSegment[3]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segmentList.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[segmentList.size()];

        for (int i = 0; i < segmentList.size(); i++) {
            segments[i] = segmentList.get(i);
        }


        return segments;
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