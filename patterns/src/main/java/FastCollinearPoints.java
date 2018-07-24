import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by iid on 3/17/18.
 */
public class FastCollinearPoints {

    private int n;
    private ArrayList<LineSegment> segments;

    /**
     * finds all line segments containing 4 or more points
     * Throw a java.lang.IllegalArgumentException if the argument to the constructor is null,
     * if any point in the array is null, or if the argument to the constructor contains a repeated point.
     * @param points
     */
    public FastCollinearPoints(Point[] points){
        if(points==null)
            throw new java.lang.IllegalArgumentException("Null argument provided!!!");
        if(points.length>0 && points[0] == null) {
            throw new java.lang.IllegalArgumentException("Provided array contains a null Point!!!");
        }
        for(int i=0; i<points.length-1; i++){
            if(points.length>1){
                for(int j=i+1; j<points.length; j++) {
                    if(points[j] == null || points[i] == null) {
                        throw new java.lang.IllegalArgumentException("Provided array contains a null Point!!!");
                    }
                    if(points[i].compareTo(points[j])==0)
                        throw new java.lang.IllegalArgumentException("Provided array contains a repeated Point!!!");
                }
            }
            if(i>2)
                break;
        }
        n=0;
        segments = new ArrayList<LineSegment>(0);
        if(points[0]==null)
            throw new java.lang.IllegalArgumentException("Provided array contains a null Point!!!");

        for(int i=0; i<points.length; i++){
            Point refPoint = points[i];
            int x = 0;
            Point[] subSegment = new Point[points.length-1];
            for(int j = 0; j<points.length; j++) {
                if (i != j) {
                    Point checkPoint = points[j];
                    if (checkPoint == null)
                        throw new java.lang.IllegalArgumentException("Provided array contains a null Point!!!");
                    if (refPoint.compareTo(checkPoint)==0)
                        throw new java.lang.IllegalArgumentException("Provided array contains a repeated Point!!!");
                    subSegment[x++] = checkPoint;
                }
            }
            Arrays.sort(subSegment, refPoint.slopeOrder());
            checkSlopes(refPoint, subSegment);
        }

    }

    private void checkSlopes(Point p, Point[] points){
        for(int i=0; i<points.length-1; i++){
            ArrayList<Point> subPoints = new ArrayList<Point>(2);
            subPoints.add(points[i]);
            for(int j=i+1; j<points.length; j++){
                boolean notInline = false;
                if(Double.compare(p.slopeTo(points[i]), p.slopeTo(points[j]))==0){
                    subPoints.add(points[j]);
                }else{
                    notInline = true;
                }
                if(notInline || j==points.length-1){
                    if(subPoints.size()>=3){
                        subPoints.add(p);
                        Point[] array = new Point[subPoints.size()];
                        array = subPoints.toArray(array);
                        Arrays.sort(array);
                        addSegment(new LineSegment(array[0], array[array.length-1]));
                    }
                    break;
                }
            }

        }
    }

    private void addSegment(LineSegment addSegment){
        boolean exists = false;
        for(LineSegment ls: segments){
            if(ls.toString().equals(addSegment.toString())){
                exists = true;
                break;
            }
        }
        if(!exists) {
            segments.add(addSegment);
            n++;
        }
    }



    /**
     * the number of line segments
     *  include each maximal line segment containing 4 (or more) points exactly once. For example,
     *  if 5 points appear on a line segment in the order p→q→r→s→t, then do not include the subsegments p→s or q→t.
     * @return
     */
    public int numberOfSegments(){
        return n;
    }

    /**
     * the line segments
     * @return
     */
    public LineSegment[] segments(){
        LineSegment[] array = new LineSegment[segments.size()];
        array = segments.toArray(array);
        return array;
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
