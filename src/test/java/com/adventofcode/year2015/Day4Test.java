
package com.adventofcode.year2015;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;

public class Day4Test {
    @Test
    public void testExample() throws NoSuchAlgorithmException {
        assertEquals("simple case", 609043, Day4.part1("abcdef", 609000, "00000"));
    }

    @Test
    public void testExample2() throws NoSuchAlgorithmException {
        assertEquals("simple case", 1048000, Day4.part1("pqrstuv", 609000, "00000"));
    }
}
