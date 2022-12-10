package com.adventofcode.year2022;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Day4Test {
  @Test
  public void testExample() {
    assertEquals(
        2,
        Day4.part1(
            """
                2-4,6-8
                2-3,4-5
                5-7,7-9
                2-8,3-7
                6-6,4-6
                2-6,4-8"""));
  }
  @Test
  public void testExample2() {
    assertEquals(
        4,
        Day4.part2(
            """
                2-4,6-8
                2-3,4-5
                5-7,7-9
                2-8,3-7
                6-6,4-6
                2-6,4-8"""));
  }
}
