package com.adventofcode.year2022;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class Day19Test {

    public static final String TEST_INSTRUCTION = """
        Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.
        Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.""";

    @Test
    public void testPart1Row1(){
        assertEquals(9, Day19.part1(TEST_INSTRUCTION.split("\n")[0], Day19.TIME));
    }

    @Test
    public void testPart1Row2(){
        assertEquals(24, Day19.part1(TEST_INSTRUCTION.split("\n")[1], Day19.TIME));
    }

    @Test
    public void testPart1Row1FirstGeode19(){
        assertEquals(1, Day19.part1(TEST_INSTRUCTION.split("\n")[0], 19));
    }
    @Test
    public void testPart1Row1FirstGeode21(){
        assertEquals(3, Day19.part1(TEST_INSTRUCTION.split("\n")[0], 21));
    }
    @Test
    public void testPart1Row1FirstGeode23(){
        assertEquals(7, Day19.part1(TEST_INSTRUCTION.split("\n")[0], 23));
    }

    @Test
    public void testPart1(){
        assertEquals(33, Day19.part1(TEST_INSTRUCTION, Day19.TIME));
    }
}

