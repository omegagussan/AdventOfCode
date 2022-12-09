package com.adventofcode.year2022;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Day9Test {
    @Test
    public void testExample(){
    assertEquals(
        "simple case",
        13,
        Day9.part1("R 4\n" + "U 4\n" + "L 3\n" + "D 1\n" + "R 4\n" + "D 1\n" + "L 5\n" + "R 2\n"));
    }
}

