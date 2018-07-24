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

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by iid on 3/17/18.
 */
public class PointsTest {

    @Test
    public void slopeToTest(){
        Point p1 = new Point(1, 1);
        //check equal points
        Point p2 = new Point(1, 1);
        assertEquals(p1.slopeTo(p2), Double.NEGATIVE_INFINITY);
        //check horizontal
        Point p3 = new Point(2, 1);
        assertEquals(p1.slopeTo(p3), +0.0);
        //check vertical
        Point p4 = new Point(1, 2);
        assertEquals(p1.slopeTo(p4), Double.POSITIVE_INFINITY);
    }
}
