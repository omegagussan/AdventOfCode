
package com.adventofcode.year2015;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day3Test {
    @Test
    public void testExample(){
        assertEquals("simple case", 4, Day3.part1("^>v<"));
    }

    @Test
    public void testExample2(){
        assertEquals("simple case2", 2, Day3.part1("^v^v^v^v^v"));
    }

    @Test
    public void test2Example(){
        assertEquals("simple case", 3, Day3.part2("^>v<"));
    }

    @Test
    public void test2Example2(){
        assertEquals("simple case2", 11, Day3.part2("^v^v^v^v^v"));
    }
}
