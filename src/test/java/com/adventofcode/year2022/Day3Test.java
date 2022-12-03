package com.adventofcode.year2022;
import static org.junit.Assert.assertEquals;

import com.adventofcode.year2022.Day3.BackpackWithCompartment;
import org.junit.Test;

public class Day3Test {
  @Test
  public void testExample() {
    assertEquals(
        "simple case",
        157,
        Day3.part1(
            "vJrwpWtwJgWrhcsFMMfFFhFp\n"
                + "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL\n"
                + "PmmdzqPrVvPwwTWBwg\n"
                + "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn\n"
                + "ttgJtRGJQctTZtZT\n"
                + "CrZsJsPPZsGzwwsLwLmpwMDw\n"));
  }

  @Test
  public void testPart2() {
    assertEquals(
        "complex case",
        70,
        Day3.part2(
            "vJrwpWtwJgWrhcsFMMfFFhFp\n"
                + "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL\n"
                + "PmmdzqPrVvPwwTWBwg\n"
                + "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn\n"
                + "ttgJtRGJQctTZtZT\n"
                + "CrZsJsPPZsGzwwsLwLmpwMDw"));
  }

  @Test
  public void testPart2Big() {
    assertEquals(
        "simple case",
        18,
        Day3.part2(
            "vJrwpWtwJgWrhcsFMMfFFhFp\n"
                + "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL\n"
                + "PmmdzqPrVvPwwTWBwg"));
  }

  @Test
  public void testPriority2() {
    String s = "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL";
    var b = new BackpackWithCompartment(s);
    assertEquals(38, b.getPriority());
  }

  @Test
  public void testPriority() {
    String s = "vJrwpWtwJgWrhcsFMMfFFhFp";
    var b = new BackpackWithCompartment(s);
    assertEquals(16, b.getPriority());
  }

  @Test
  public void testPriorityUpperCase() {
    String s = "PmmdzqPrVvPwwTWBwg";
    var b = new BackpackWithCompartment(s);
    assertEquals(42, b.getPriority());
  }
}
