package com.adventofcode.year2022;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Day2Test {
    @Test
    public void testExample(){
    assertEquals("simple case", 15, com.adventofcode.year2022.Day2.part1("A Y\n" + "B X\n" + "C Z"));
    }
    @Test
    public void testWrapped(){
        assertEquals("simple case", 3, com.adventofcode.year2022.Day2.part1("A Z"));
    }
    @Test
    public void testWrappedBackwards(){
        assertEquals("simple case", 7, com.adventofcode.year2022.Day2.part1("C X"));
    }

    @Test
    public void test2(){
        assertEquals("simple case", 12, com.adventofcode.year2022.Day2.part2("A Y\n" + "B X\n" + "C Z"));
    }
}

