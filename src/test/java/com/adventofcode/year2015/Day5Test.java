
package com.adventofcode.year2015;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class Day5Test {
    @Test
    public void testExample(){
        assertEquals("simple case", 1, Day5.part2(List.of("qjhvhtzxzqqjkmpb")));
    }

    @Test
    public void testExample2(){
        assertEquals("simple case2", 1, Day5.part2(List.of("xxyxx")));
    }

    @Test
    public void testExampleNegative(){
        assertEquals("negative case", 0, Day5.part2(List.of("uurcxstgmygtbstg")));
    }

    @Test
    public void testExampleNegative2(){
        assertEquals("negative case2", 0, Day5.part2(List.of("ieodomkazucvgmuy")));
    }
}
