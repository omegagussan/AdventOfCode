package com.adventofcode.year2022;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import org.javatuples.Triplet;
import org.junit.Test;

public class Day18Test {

    public static final String TEST_INSTRUCTION = """
        2,2,2
        1,2,2
        3,2,2
        2,1,2
        2,3,2
        2,2,1
        2,2,3
        2,2,4
        2,2,6
        1,2,5
        3,2,5
        2,1,5
        2,3,5""";

    @Test
    public void testPart1(){
        assertEquals(64L, Day18.part1(TEST_INSTRUCTION));
    }

    @Test
    public void testPart1easy(){
        assertEquals(10L, Day18.part1("""
            1,1,1
            2,1,1
            """));
    }

    @Test
    public void testPermutations(){
        assertEquals(6, Day18.getPermutations(new Triplet<>(0, 0, 0)).size());
    }
}

