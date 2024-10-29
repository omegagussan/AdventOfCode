package com.adventofcode.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortUtilsTest {

    @Test
    void testQuickSort() {
        int[] arr = {10, 7, 8, 9, 1, 5};
        SortUtils.quickSort(arr, 0, arr.length - 1);
        assertArrayEquals(new int[]{1, 5, 7, 8, 9, 10}, arr);
    }

    @Test
    void testMergeSort() {
        int[] arr = {12, 11, 13, 5, 6, 7};
        SortUtils.mergeSort(arr, 0, arr.length - 1);
        assertArrayEquals(new int[]{5, 6, 7, 11, 12, 13}, arr);
    }
}