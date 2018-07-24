/*******************************************************************************
 * © 2005-2017
 * Fidelity National Information Services, Inc. and/or its subsidiaries.
 * All Rights Reserved worldwide.
 * This document is protected under the trade secret and copyright laws as the
 * property of Fidelity National Information Services, Inc. and/or its
 * subsidiaries. Copying, reproduction or distribution should be limited and
 * only to employees with a "need to know" to do their job. Any disclosure of
 * this document to third parties is strictly prohibited.
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by iid on 3/17/18.
 */
public class BruteCollinearPoints {

    private int n;
    private ArrayList<LineSegment> segments;

    /**
     * finds all line segments containing 4 points
     * should include each line segment containing 4 points exactly once.
     * If 4 points appear on a line segment in the order p→q→r→s, then you should include either the line segment p→s or s→p
     * (but not both) and you should not include subsegments such as p→r or q→r. For simplicity, we will not supply any input
     * to BruteCollinearPoints that has 5 or more collinear points.
     * Throw a java.lang.IllegalArgumentException if the argument to the constructor is null, if any point in the array is null,
     * or if the argument to the constructor contains a repeated point.
     * @param points
     */
    public BruteCollinearPoints(Point[] points){
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
                    if(points[i]==points[j])
                        throw new java.lang.IllegalArgumentException("Provided array contains a repeated Point!!!");
                }
            }
            if(i>2)
                break;
        }
        segments = new ArrayList<LineSegment>(0);
        n = 0;
        if(points.length>=4) {
            double s1;
            double s2;
            double s3;
            for (int i = 0; i < points.length; i++) {
                for (int j = i + 1; j < points.length; j++) {
                    s1 = points[i].slopeTo(points[j]);
                    for (int k = j + 1; k < points.length; k++) {
                        s2 = points[i].slopeTo(points[k]);
                        if (Double.compare(s1, s2) != 0)
                            continue;
                        for (int l = k + 1; l < points.length; l++) {
                            if (points[l] == null)
                                throw new java.lang.IllegalArgumentException("Provided array contains a null Point!!!");
                            if (points[k] == points[l])
                                throw new java.lang.IllegalArgumentException("Provided array contains a repeated Point!!!");
                            s3 = points[i].slopeTo(points[l]);
                            if (Double.compare(s1, s3) == 0) {
                                addSegment(points[i], points[j], points[k], points[l]);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * sort the points, check if the LineSegment was not added before and add it to the array
     * @param p1
     * @param p2
     * @param p3
     * @param p4
     */
    private void addSegment(Point p1, Point p2, Point p3, Point p4){
        Point[] points = new Point[]{p1, p2, p3, p4};
        Arrays.sort(points);
        LineSegment newLs = new LineSegment(points[0], points[3]);
        boolean exists = false;
        for(LineSegment ls: segments){
            if(ls.toString().equals(newLs.toString()))
                exists = true;
        }
        if(!exists) {
            segments.add(newLs);
            n++;
        }
    }

    /**
     * the number of line segments
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
            StdDraw.show();
        }
        StdDraw.show();
    }
}

