package com.adventofcode.year2022;

import static org.junit.Assert.assertEquals;

import com.adventofcode.utils.LongPoint;
import java.lang.instrument.Instrumentation;
import java.util.Set;
import org.junit.Test;

public class Day17Test {

    public static final String TEST_INSTRUCTION = ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>";

    @Test
    public void getShapeLong(){
        //line -
        assertEquals(Set.of(new LongPoint(2L,3L), new LongPoint(3L,3L), new LongPoint(4L,3L), new LongPoint(5L,3L)), Day17.getShapeLong(-1L, 0));
        //cross +
        assertEquals(Set.of(new LongPoint(3L,5L), new LongPoint(4L,5L), new LongPoint(3L,6L), new LongPoint(2L,5L), new LongPoint(3L,4L)), Day17.getShapeLong(0L, 1));
        // shape L
        assertEquals(Set.of(new LongPoint(2L,3L), new LongPoint(3L,3L), new LongPoint(4L,3L), new LongPoint(4L,4L), new LongPoint(4L,5L)), Day17.getShapeLong(-1L, 2));
        // line |
        assertEquals(Set.of(new LongPoint(2L,3L), new LongPoint(2L,4L), new LongPoint(2L,5L), new LongPoint(2L,6L)), Day17.getShapeLong(-1L, 3));
        // box
        assertEquals(Set.of(new LongPoint(2L,3L), new LongPoint(3L,3L), new LongPoint(2L,4L), new LongPoint(3L,4L)), Day17.getShapeLong(-1L, 4));
    }

    @Test
    public void testPart1(){
        assertEquals(3068, Day17.part(TEST_INSTRUCTION, 2022L));
    }

    @Test
    public void testPart2(){
        assertEquals(1514285714288L, Day17.part(TEST_INSTRUCTION, Day17.ELEPHANT_ARE_IMPRESSED_LIMIT));
    }
}

