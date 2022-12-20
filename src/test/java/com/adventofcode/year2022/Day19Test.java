package com.adventofcode.year2022;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class Day19Test {

    public static final String TEST_INSTRUCTION = """
        Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.
        Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.""";

    @Test
    public void testPart1(){
        assertEquals(9, Day19.part1(TEST_INSTRUCTION.split("\n")[0], Day19.TIME));
    }

    @Test
    public void testPartFirstGeode(){
        assertEquals(1, Day19.part1(TEST_INSTRUCTION.split("\n")[0], 19));
    }

    @Test
    public void testPartEpoch6(){
        assertEquals(0, Day19.part1(TEST_INSTRUCTION.split("\n")[0], 6));
    }

    @Test
    public void testPart1Full(){
        assertEquals(33, Day19.part1(TEST_INSTRUCTION, Day19.TIME));
    }

//    @Test
//    public void testPart1Epoch1(){
//        var bp=  Day19.parseInput(TEST_INSTRUCTION).toList().get(0);
//        var v= Day19.maxGeodes(bp, List.of(List.of(1,0,0,0,0,0,0,0)), 0, new HashMap<>(), 10, 1);
//        assertEquals(List.of(List.of(1,0,0,0,1,0,0,0)), v);
//    }
//
//    @Test
//    public void testPart1Epoch2(){
//        var bp=  Day19.parseInput(TEST_INSTRUCTION).toList().get(0);
//        var v= Day19.maxGeodes(bp, List.of(List.of(1,0,0,0,0,0,0,0)), 0, new HashMap<>(), 10, 2);
//        assertEquals(List.of(List.of(1,0,0,0,2,0,0,0)), v);
//    }
//
//
//    @Test
//    public void testPart1Epoch6(){
//        var bp=  Day19.parseInput(TEST_INSTRUCTION).toList().get(0);
//        var v= Day19.maxGeodes(bp, List.of(List.of(1,0,0,0,0,0,0,0)), 0, new HashMap<>(), 10, 6);
//        assertTrue(v.contains(List.of(1, 2, 0, 0, 2, 4, 0, 0)));
//    }
//
//    @Test
//    public void testPart1Epoch10(){
//        var bp=  Day19.parseInput(TEST_INSTRUCTION).toList().get(0);
//        var v= Day19.maxGeodes(bp, List.of(List.of(1,0,0,0,0,0,0,0)), 0, new HashMap<>(), 20, 10);
//        assertTrue(v.contains(List.of(1, 3, 0, 0, 4, 15, 0, 0)));
//    }
//
//    @Test
//    public void testPart1Epoch11(){
//        var bp=  Day19.parseInput(TEST_INSTRUCTION).toList().get(0);
//        var v= Day19.maxGeodes(bp, List.of(List.of(1, 3, 0, 0, 4, 15, 0, 0)), 10, new HashMap<>(), 20, 11);
//        assertTrue(v.contains(List.of(1, 3, 1, 0, 2, 4, 0, 0)));
//    }


//    @Test
//    public void testPart1Epoch24(){
//        var bp=  Day19.parseInput(TEST_INSTRUCTION).toList().get(0);
//        var v= Day19.extracted(bp, List.of(List.of(1,4,2,2,5,37,6,7)), 23, new HashMap<>(), 10, 24);
//        assertTrue(v.contains(List.of(1, 4, 2, 2, 6, 41, 8, 9)));
//    }

//    @Test
//    public void testPart1Epoch12(){
//        var bp=  Day19.parseInput(TEST_INSTRUCTION).toList().get(0);
//        var v= Day19.extracted(bp, List.of(List.of(1,0,0,0,0,0,0,0)), 0, new HashMap<>(), 10, 12);
//        assertTrue(v.contains(List.of(1, 3, 1, 0, 1, 7, 1, 0)));
//    }
}

