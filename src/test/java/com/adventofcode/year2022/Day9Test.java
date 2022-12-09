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

    @Test
    public void testExample2(){
    assertEquals(
        "simple case",
        36,
        Day9.part2(
            "R 5\n" + "U 8\n" + "L 8\n" + "D 3\n" + "R 17\n" + "D 10\n" + "L 25\n" + "U 20"));
    }
}

