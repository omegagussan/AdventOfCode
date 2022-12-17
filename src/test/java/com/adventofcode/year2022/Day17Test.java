package com.adventofcode.year2022;

import static org.junit.Assert.assertEquals;

import com.adventofcode.utils.Point;
import java.lang.instrument.Instrumentation;
import java.util.Set;
import org.junit.Test;

public class Day17Test {

    public static final String TEST_INSTRUCTION = ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>";

    @Test
    public void getShape(){
        //line -
        assertEquals(Set.of(new Point(2,3), new Point(3,3), new Point(4,3), new Point(5,3)), Day17.getShape(-1, 0));
        //cross +
        assertEquals(Set.of(new Point(3,5), new Point(4,5), new Point(3,6), new Point(2,5), new Point(3,4)), Day17.getShape(0, 1));
        // shape L
        assertEquals(Set.of(new Point(2,3), new Point(3,3), new Point(4,3), new Point(4,4), new Point(4,5)), Day17.getShape(-1, 2));
        // line |
        assertEquals(Set.of(new Point(2,3), new Point(2,4), new Point(2,5), new Point(2,6)), Day17.getShape(-1, 3));
        // box
        assertEquals(Set.of(new Point(2,3), new Point(3,3), new Point(2,4), new Point(3,4)), Day17.getShape(-1, 4));
    }

    @Test
    public void getShapeAdjustHeight(){
        //line -
        assertEquals(Set.of(new Point(2,4), new Point(3,4), new Point(4,4), new Point(5,4)), Day17.getShape(0, 0));
        //cross +
        assertEquals(Set.of(new Point(3,6), new Point(4,6), new Point(3,7), new Point(2,6), new Point(3,5)), Day17.getShape(1, 1));
        // shape L
        assertEquals(Set.of(new Point(2,4), new Point(3,4), new Point(4,4), new Point(4,5), new Point(4,6)), Day17.getShape(0, 2));
        // line |
        assertEquals(Set.of(new Point(2,4), new Point(2,5), new Point(2,6), new Point(2,7)), Day17.getShape(0, 3));
        // box
        assertEquals(Set.of(new Point(2,5), new Point(3,5), new Point(2,4), new Point(3,4)), Day17.getShape(0, 4));
    }

    @Test
    public void testPart1(){
        assertEquals(3068, Day17.part1(TEST_INSTRUCTION));
    }

    @Test
    public void testPart2(){
        assertEquals(1514285714288L, Day17.part2(TEST_INSTRUCTION));
    }
}

