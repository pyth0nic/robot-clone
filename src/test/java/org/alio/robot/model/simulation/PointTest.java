package org.alio.robot.model.simulation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    void eq_True_ForEqualPoint() {
        var result = Point.eq(new Point(1,1,null), new Point(1,1,null) );
        assertTrue(result);
    }

    @Test
    void eq_False_ForNonEqualPoint() {
        var result = Point.eq(new Point(1,1,null), new Point(1,5,null) );
        var result2 = Point.eq(new Point(1,1,null), new Point(5,1,null) );
        var result3 = Point.eq(new Point(1,1,null), new Point(5,5,null) );
        assertFalse(result);
        assertFalse(result2);
        assertFalse(result3);
    }

    @Test
    void isValidPoint_For_Valid_Points() {
        var dimension = 4;
        var result = Point.isValidPoint(new Point(1,1,null), dimension);
        assertTrue(result);
    }

    @Test
    void isValidPoint_For_Invalid_Points() {
        var dimension = 4;
        var result = Point.isValidPoint(new Point(-1,1,null), dimension);
        var result2 = Point.isValidPoint(new Point(5,1,null), dimension);
        var result3 = Point.isValidPoint(new Point(1,5,null), dimension);
        var result4 = Point.isValidPoint(new Point(5,5,null), dimension);

        assertFalse(result);
        assertFalse(result2);
        assertFalse(result3);
        assertFalse(result4);
    }
}