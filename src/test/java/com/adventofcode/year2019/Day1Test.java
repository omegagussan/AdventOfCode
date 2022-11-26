package com.adventofcode.year2019;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class Day1Test {
    @Test
    public void testExample(){
        assertEquals("simple case", 2, com.adventofcode.year2019.Day1.part1("12"));
    }

    @Test
    public void testExample2(){
        assertEquals("simple case2", 2, com.adventofcode.year2019.Day1.part1("14"));
    }

    @Test
    public void testExample3(){
        assertEquals("simple case3", 33583, com.adventofcode.year2019.Day1.part1("100756"));
    }

    @Test
    public void testExample4(){
        assertEquals("simple case4", 654, com.adventofcode.year2019.Day1.part1("1969"));
    }

    @Test
    public void testComplex(){
        assertEquals("complex case", 4, com.adventofcode.year2019.Day1.part1("12\n14"));
    }

    @Test
    public void testPart2(){
        assertEquals("complex case", 966, com.adventofcode.year2019.Day1.fuelConsumptionRecursive(1969));
    }
}

