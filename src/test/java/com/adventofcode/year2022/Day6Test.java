package com.adventofcode.year2022;
import static org.junit.Assert.assertEquals;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class Day6Test {
  @Test
  public void testExample() {
    assertEquals(
        "simple case",
        7,
        Day6.part1("mjqjpqmgbljsphdztnvjfqwrcgsmlb"));
  }
  @Test
  public void testExample1() {
    assertEquals(
        "simple case",
        5,
        Day6.part1("bvwbjplbgvbhsrlpgdmjqwftvncz"));
  }
  @Test
  public void testExample2() {
    assertEquals(
        "simple case",
        6,
        Day6.part1("nppdvjthqldpwncqszvftbrmjlhg"));
  }
  @Test
  public void testExample3() {
    assertEquals(
        "simple case",
        10,
        Day6.part1("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"));
  }
  @Test
  public void testExample4() {
    assertEquals(
        "simple case",
        11,
        Day6.part1("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"));
  }

  @Test
  public void testExample5() {
    assertEquals(
        "simple case",
        19,
        Day6.part2("mjqjpqmgbljsphdztnvjfqwrcgsmlb"));
  }

}
