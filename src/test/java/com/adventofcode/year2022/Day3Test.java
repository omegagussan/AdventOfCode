package com.adventofcode.year2022;
import static org.junit.Assert.assertEquals;

import com.adventofcode.year2022.Day3.BackpackWithCompartment;
import org.junit.Test;

public class Day3Test {
  @Test
  public void testExample() {
    assertEquals(
        157,
        Day3.part1(
            """
                vJrwpWtwJgWrhcsFMMfFFhFp
                jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
                PmmdzqPrVvPwwTWBwg
                wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
                ttgJtRGJQctTZtZT
                CrZsJsPPZsGzwwsLwLmpwMDw
                """));
  }

  @Test
  public void testPart2() {
    assertEquals(
        70,
        Day3.part2(
            """
                vJrwpWtwJgWrhcsFMMfFFhFp
                jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
                PmmdzqPrVvPwwTWBwg
                wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
                ttgJtRGJQctTZtZT
                CrZsJsPPZsGzwwsLwLmpwMDw"""));
  }

  @Test
  public void testPart2Big() {
    assertEquals(
        18,
        Day3.part2(
            """
                vJrwpWtwJgWrhcsFMMfFFhFp
                jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
                PmmdzqPrVvPwwTWBwg"""));
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
