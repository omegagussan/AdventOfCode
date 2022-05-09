
package com.adventofcode.year2015;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class Day2Test {
    @Test
    public void testExample(){
        assertEquals("simple case", 58, Day2.part1(List.of("2x3x4")));
    }

    @Test
    public void testExample2(){
        assertEquals("simple case2 ", 43, Day2.part1(List.of("1x1x10")));
    }

    @Test
    public void testExampleTwoRows(){
        assertEquals("simple add ", 43 + 58, Day2.part1(List.of("1x1x10", "2x3x4")));
    }

    @Test
    public void testPart2Example(){
        assertEquals("simple case", 34, Day2.part2(List.of("2x3x4")));
    }

    @Test
    public void testPart2Example2(){
        assertEquals("simple case2 ", 14, Day2.part2(List.of("1x1x10")));
    }
    @Test
    public void testExample2TwoRows(){
        assertEquals("simple add ", 14 + 34, Day2.part2(List.of("1x1x10", "2x3x4")));
    }
}
