package com.adventofcode.year2022;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Day4Test {
  @Test
  public void testExample() {
    assertEquals(
        "simple case",
        2,
        Day4.part1(
            "2-4,6-8\n" + "2-3,4-5\n" + "5-7,7-9\n" + "2-8,3-7\n" + "6-6,4-6\n" + "2-6,4-8"));
  }
  @Test
  public void testExample2() {
    assertEquals(
        "simple case",
        4,
        Day4.part2(
            "2-4,6-8\n" + "2-3,4-5\n" + "5-7,7-9\n" + "2-8,3-7\n" + "6-6,4-6\n" + "2-6,4-8"));
  }
}
