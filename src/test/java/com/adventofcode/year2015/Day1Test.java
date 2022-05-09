package com.adventofcode.year2015;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Day1Test {
    @Test
    public void testExample(){
        assertEquals("simple case", 0, Day1.part1("()()"));
    }

    @Test
    public void testNegativeFloor(){
        assertEquals("negative floor case", -3, Day1.part1(")())())"));
    }

    @Test
    public void testOrderDoesNotMatter(){
        assertEquals("order does not matter", Day1.part1("()()"), Day1.part1("(())"));
    }

    @Test
    public void testPart2Example(){
        assertEquals("when he enters basement", 5, Day1.part2("()())"));
    }


}
