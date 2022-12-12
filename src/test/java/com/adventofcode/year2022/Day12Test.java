package com.adventofcode.year2022;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Day12Test {
    @Test
    public void testExample(){
    assertEquals(
        31,
        Day12.part1("""
        Sabqponm
        abcryxxl
        accszExk
        acctuvwj
        abdefghi"""));
    }
}

