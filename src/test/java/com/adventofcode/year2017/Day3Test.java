package com.adventofcode.year2017;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day3Test {

    @Test
    void testPart1() {
        assertEquals(0, Day3.part1(1));
    }

    @Test
    void testPart11() {
        assertEquals(2, Day3.part1(23));
    }

    @Test
    void testPart12() {
        assertEquals(3, Day3.part1(24));
    }

    @Test
    void testPart13() {
        assertEquals(3, Day3.part1(22));
    }

    @Test
    void testPart14() {
        assertEquals(3, Day3.part1(12));
    }


    @Test
    void testPart15() {
        assertEquals(31, Day3.part1(1024));
    }
}