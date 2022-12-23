package com.adventofcode.year2022;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Day22Test {

    String INSTRUCTIONS = """
                ...#
                .#..
                #...
                ....
        ...#.......#
        ........#...
        ..#....#....
        ..........#.
                ...#....
                .....#..
                .#......
                ......#.
        
        10R5L5R10L4R5L5
        """;
    @Test
    public void testPart1(){
        assertEquals(6032L, Day22.part1(INSTRUCTIONS));
    }

}

