package com.adventofcode.year2022;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.adventofcode.utils.Point;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.util.List;
import java.util.Set;
import org.javatuples.Pair;
import org.junit.Test;

public class Day15Test {
    @Test
    public void testInput(){
        var expected = Day15.parseInput(
            """
                Sensor at x=2, y=18: closest beacon is at x=-2, y=15
                Sensor at x=9, y=16: closest beacon is at x=10, y=16
                Sensor at x=13, y=2: closest beacon is at x=15, y=3
                Sensor at x=12, y=14: closest beacon is at x=10, y=16
                Sensor at x=10, y=20: closest beacon is at x=10, y=16
                Sensor at x=14, y=17: closest beacon is at x=10, y=16
                Sensor at x=8, y=7: closest beacon is at x=2, y=10
                Sensor at x=2, y=0: closest beacon is at x=2, y=10
                Sensor at x=0, y=11: closest beacon is at x=2, y=10
                Sensor at x=20, y=14: closest beacon is at x=25, y=17
                Sensor at x=17, y=20: closest beacon is at x=21, y=22
                Sensor at x=16, y=7: closest beacon is at x=15, y=3
                Sensor at x=14, y=3: closest beacon is at x=15, y=3
                Sensor at x=20, y=1: closest beacon is at x=15, y=3
                """).toList();
        assertEquals(14, expected.size());
        assertTrue(expected.contains(List.of(2,18, -2,15)));
        assertTrue(expected.contains(List.of(20,1,15,3)));
    }

    @Test
    public void testPart1(){
        var expected = Day15.part1(
            """
                Sensor at x=2, y=18: closest beacon is at x=-2, y=15
                Sensor at x=9, y=16: closest beacon is at x=10, y=16
                Sensor at x=13, y=2: closest beacon is at x=15, y=3
                Sensor at x=12, y=14: closest beacon is at x=10, y=16
                Sensor at x=10, y=20: closest beacon is at x=10, y=16
                Sensor at x=14, y=17: closest beacon is at x=10, y=16
                Sensor at x=8, y=7: closest beacon is at x=2, y=10
                Sensor at x=2, y=0: closest beacon is at x=2, y=10
                Sensor at x=0, y=11: closest beacon is at x=2, y=10
                Sensor at x=20, y=14: closest beacon is at x=25, y=17
                Sensor at x=17, y=20: closest beacon is at x=21, y=22
                Sensor at x=16, y=7: closest beacon is at x=15, y=3
                Sensor at x=14, y=3: closest beacon is at x=15, y=3
                Sensor at x=20, y=1: closest beacon is at x=15, y=3
                """, 10);
        assertEquals(26, expected);
    }

    @Test
    public void testPart2(){
        var expected = Day15.part2(
            """
                Sensor at x=2, y=18: closest beacon is at x=-2, y=15
                Sensor at x=9, y=16: closest beacon is at x=10, y=16
                Sensor at x=13, y=2: closest beacon is at x=15, y=3
                Sensor at x=12, y=14: closest beacon is at x=10, y=16
                Sensor at x=10, y=20: closest beacon is at x=10, y=16
                Sensor at x=14, y=17: closest beacon is at x=10, y=16
                Sensor at x=8, y=7: closest beacon is at x=2, y=10
                Sensor at x=2, y=0: closest beacon is at x=2, y=10
                Sensor at x=0, y=11: closest beacon is at x=2, y=10
                Sensor at x=20, y=14: closest beacon is at x=25, y=17
                Sensor at x=17, y=20: closest beacon is at x=21, y=22
                Sensor at x=16, y=7: closest beacon is at x=15, y=3
                Sensor at x=14, y=3: closest beacon is at x=15, y=3
                Sensor at x=20, y=1: closest beacon is at x=15, y=3
                """, 20);
        assertEquals(56000011, expected);
    }

    @Test
    public void testGetVicinity(){
        var expected = Day15.getVicinity(new Point(0,0), 1).toList();
        assertEquals(4, expected.size());
        assertFalse(expected.contains(new Point(0, 0)));
    }

    @Test
    public void testGetVicinity2(){
        var expected = Day15.getVicinity(new Point(0,0), 2).toList();
        assertEquals(8, expected.size());
    }

    @Test
    public void testGetVicinity3(){
        var expected = Day15.getVicinity(new Point(0,0), 3).toList();
        assertEquals(12, expected.size());
    }
    @Test
    public void testGetVicinityOffset(){
        var expected = Day15.getVicinity(new Point(1,0), 1).toList();
        assertTrue(expected.contains(new Point(2, 0)));
        assertTrue(expected.contains(new Point(0, 0)));
        assertFalse(expected.contains(new Point(-1, 0)));
    }
}

