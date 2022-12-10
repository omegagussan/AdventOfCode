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
        7,
        Day6.part1("mjqjpqmgbljsphdztnvjfqwrcgsmlb"));
  }
  @Test
  public void testExample1() {
    assertEquals(
        5,
        Day6.part1("bvwbjplbgvbhsrlpgdmjqwftvncz"));
  }
  @Test
  public void testExample2() {
    assertEquals(
        6,
        Day6.part1("nppdvjthqldpwncqszvftbrmjlhg"));
  }
  @Test
  public void testExample3() {
    assertEquals(
        10,
        Day6.part1("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"));
  }
  @Test
  public void testExample4() {
    assertEquals(
        11,
        Day6.part1("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"));
  }

  @Test
  public void testExample5() {
    assertEquals(
        19,
        Day6.part2("mjqjpqmgbljsphdztnvjfqwrcgsmlb"));
  }

}
