package com.adventofcode.year2015;

import junit.framework.TestCase;

import java.util.List;

public class Day9Test extends TestCase {

    public void testPart1() {
        var input = List.of(
                "London to Dublin = 464",
                "London to Belfast = 518",
                "Dublin to Belfast = 141"
        );
        assertEquals(605, Day9.part1(input));
    }
}