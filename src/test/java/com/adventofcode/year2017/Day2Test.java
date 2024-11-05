package com.adventofcode.year2017;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day2Test {

    @Test
    void testPart1() {
        var given = """
                5 1 9 5
                7 5 3
                2 4 6 8
                """;
        var instructions = given.lines().toList();
        assertEquals(18, Day2.part1(instructions));
    }

    @Test
    void testParseLine() {
        var given = "5 1 9 5";
        assertEquals(8, Day2.parseLine(given));
    }
}