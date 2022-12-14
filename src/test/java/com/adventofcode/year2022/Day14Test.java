package com.adventofcode.year2022;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.adventofcode.utils.Point;
import java.util.Set;
import org.javatuples.Pair;
import org.junit.Test;

public class Day14Test {
    @Test
    public void testExample(){
    assertEquals(
        24,
        Day14.part1(
            """
        498,4 -> 498,6 -> 496,6
        503,4 -> 502,4 -> 502,9 -> 494,9>
        """));
    }

    @Test
    public void testGetCons(){
        Set<Point> expected = Day14.getConstrains(Day14.getLineSegments(
            """
                498,4 -> 498,6 -> 496,6
                503,4 -> 502,4 -> 502,9 -> 494,9>
                """));
        assertEquals(20, expected.size());
    }

    @Test
    public void testSegments(){
        var expected = Day14.getLineSegments(
            """
                498,4 -> 498,6 -> 496,6
                503,4 -> 502,4 -> 502,9 -> 494,9>
                """);
        assertEquals(5, expected.size());
        assertTrue(expected.contains(new Pair<>(new Point(498,4), new Point(498,6))));
        assertTrue(expected.contains(new Pair<>(new Point(502,9), new Point(494,9))));
    }
}

