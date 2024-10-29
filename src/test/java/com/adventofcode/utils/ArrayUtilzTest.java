package com.adventofcode.utils;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;

public class ArrayUtilzTest extends TestCase {

    @Test
    public void testFindFirstFindsElement() {
        Integer[][] input = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Point expected = new Point(1, 1);
        assertEquals(expected, ArrayUtilz.findFirst(input, 5));
    }

    @Test
    public void testFindFirstReturnsNullIfNotFound() {
        Integer[][] input = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        assertNull(ArrayUtilz.findFirst(input, 10));
    }

    @Test
    public void testFindAllFindsAllOccurrences() {
        Integer[][] input = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 5, 9}
        };
        List<Point> expected = List.of(new Point(1, 1), new Point(2, 1));
        assertEquals(expected, ArrayUtilz.findAll(input, 5));
    }

    @Test
    public void testFindAllReturnsEmptyListIfNotFound() {
        Integer[][] input = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        List<Point> expected = List.of();
        assertEquals(expected, ArrayUtilz.findAll(input, 10));
    }

    @Test
    public void testPermutationsGeneratesPermutations() {
        List<Integer> input = List.of(1, 2, 3);
        List<List<Integer>> expected = List.of(
                List.of(1, 2, 3),
                List.of(1, 3, 2),
                List.of(2, 1, 3),
                List.of(2, 3, 1),
                List.of(3, 1, 2),
                List.of(3, 2, 1)
        );
        assertEquals(expected, ArrayUtilz.permutations(input));
    }

    @Test
    public void testPermutationsEmptyList() {
        List<Integer> input = List.of();
        List<List<Integer>> expected = List.of();
        assertEquals(expected, ArrayUtilz.permutations(input));
    }
}