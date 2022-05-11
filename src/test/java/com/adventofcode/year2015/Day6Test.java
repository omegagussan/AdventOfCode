
package com.adventofcode.year2015;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class Day6Test {
    @Test
    public void testExample(){
        assertEquals("simple case", 1000*1000, Day6.part1(List.of("turn on 0,0 through 999,999")));
    }

    @Test
    public void testComplex(){
        assertEquals("complex case", 1000*1000 - 4, Day6.part1(List.of("turn on 0,0 through 999,999", "turn off 499,499 through 500,500")));
    }

    @Test
    public void testToggle(){
        assertEquals("toggle case", 1000*1000 - 1000, Day6.part1(List.of("turn on 0,0 through 999,999", "toggle 0,0 through 999,0")));
    }

    @Test
    public void testToggleTwice(){
        assertEquals("toggle twice case", 1000*1000, Day6.part1(List.of("turn on 0,0 through 999,999", "toggle 0,0 through 999,0", "toggle 0,0 through 999,0")));
    }
}
