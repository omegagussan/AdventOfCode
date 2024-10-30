package com.adventofcode.year2015;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day17Test {

    @Test
    void part1() {
        var input = List.of(20, 15, 10, 5, 5);
        assertEquals(4, Day17.part1(input, 25));
    }
}