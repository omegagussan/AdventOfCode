package com.adventofcode.utils;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.function.Function;
import org.junit.Test;

public class VectorTest {
  @Test
  public void testInclusive(){
    var given = new Vector(2,0);
    var expected = List.of(new Point(0,0), new Point(1,0), new Point(2,0));
    assertEquals(expected, given.consistsOf(new Point(0,0)));
  }

  @Test
  public void testStartPoint(){
    var given = new Vector(2,0);
    var expected = List.of(new Point(1,1), new Point(2,1), new Point(3, 1));
    assertEquals(expected, given.consistsOf(new Point(1,1)));
  }

  @Test
  public void testOffset(){
    var given = new Vector(2,0);
    var expected = List.of(new Point(0,1), new Point(1,1), new Point(2,1));
    assertEquals(expected, given.consistsOf(new Point(0,1)));
  }

  @Test
  public void test2NDimension(){
    var given = new Vector(0,2);
    var expected = List.of(new Point(0,1), new Point(0,2), new Point(0,3));
    assertEquals(expected, given.consistsOf(new Point(0,1)));
  }

  @Test
  public void testTurn(){
    var given = new Vector(1,0);
    assertEquals(new Vector(0, 1), given.turn("R"));
  }

  @Test
  public void testTurn2(){
    var given = new Vector(0,-1);
    assertEquals(new Vector(-1, 0), given.turn("L"));
  }
}