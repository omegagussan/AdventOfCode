package com.adventofcode.year2022;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Day8Test {

  @Test
  public void example(){
    assertEquals("message", 21, Day8.part1("30373\n" + "25512\n" + "65332\n" + "33549\n" + "35390"));
  }

  @Test
  public void striklyIncreasing(){
    assertEquals("strikly", false, Day8.isStriklyIncreasing(new Integer[]{2, 3, 3}));
  }
}
