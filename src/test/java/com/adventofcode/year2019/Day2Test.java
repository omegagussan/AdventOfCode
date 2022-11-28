package com.adventofcode.year2019;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class Day2Test {
    @Test
    public void testPart1(){
        assertEquals("simple case", 3500, Day2.part1("1,9,10,3,2,3,11,0,99,30,40,50"));
    }
    @Test
    public void testState(){
    assertEquals("simple case", List.of(3500,9,10,70,2,3,11,0,99, 30,40,50), Day2.operateOnMemory(new ArrayList<>(List.of(1,9,10,3,2,3,11,0,99,30,40,50))));
    }
    @Test
    public void testState2(){
        assertEquals("small case", List.of(2,0,0,0,99), Day2.operateOnMemory(new ArrayList<>(List.of(1,0,0,0,99))));
    }
    @Test
    public void testState3(){
        assertEquals("small case", List.of(2,3,0,6,99), Day2.operateOnMemory(new ArrayList<>(List.of(2,3,0,3,99))));
    }
    @Test
    public void testState4(){
        assertEquals("small case", List.of(2,4,4,5,99,9801), Day2.operateOnMemory(new ArrayList<>(List.of(2,4,4,5,99,0))));
    }
    @Test
    public void testState5(){
        assertEquals("simple case", List.of(30,1,1,4,2,5,6,0,99), Day2.operateOnMemory(new ArrayList<>(List.of(1,1,1,4,99,5,6,0,99))));
    }
}

