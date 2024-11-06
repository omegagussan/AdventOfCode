package com.adventofcode.year2017;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class Day5Test {
    @Test
    public void testPart1() {
        assertEquals(5, Day5.part1(Arrays.asList(0, 3, 0, 1, -3)));
    }

    @Test
    public void testPart2() {
        assertEquals(10, Day5.part2(Arrays.asList(0, 3, 0, 1, -3)));
    }
}