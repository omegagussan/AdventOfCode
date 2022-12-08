package com.adventofcode.year2022;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Day8Test {

  @Test
  public void example(){
    assertEquals("message", 21, Day8.part1("30373\n" + "25512\n" + "65332\n" + "33549\n" + "35390"));
  }

  @Test
  public void example2(){
    assertEquals("message", 8, Day8.part2("30373\n" + "25512\n" + "65332\n" + "33549\n" + "35390"));
  }

  @Test
  public void striklyIncreasing(){
    assertEquals("strikly", Integer.valueOf("1"), Day8.isDecreasing(new Integer[]{3, 3, 2}));
  }

  @Test
  public void striklyIncreasing2(){
    assertEquals("strikly", Integer.valueOf("2"), Day8.isDecreasing(new Integer[]{8, 7, 1}));
  }

  @Test
  public void striklyIncreasing3(){
    assertEquals("strikly", Integer.valueOf("2"), Day8.isDecreasing(new Integer[]{10, 7, 8}));
  }
}
