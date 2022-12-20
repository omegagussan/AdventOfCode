package com.adventofcode.year2022;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.Test;

public class Day20Test {

    public static final String TEST_INSTRUCTION = """
    1
    2
    -3
    3
    -2
    0
    4
  """;
    @Test
    public void testPart1(){
        assertEquals(3L, Day20.part1(TEST_INSTRUCTION));
    }

    @Test
    public void testPart2(){
        assertEquals(1623178306L, Day20.part2(TEST_INSTRUCTION));
    }
    @Test
    public void testGetAnswerList(){
        var given = List.of(1L, 2L, -3L, 4L, 0L, 3L, -2L);
        var result = Day20.getAnswer(given);
        List<Long> expected = List.of(4L, -3L, 2L);
        assertEquals(3, result.size());
        assertEquals(expected, result);
    }
}

