package com.adventofcode.utils;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringMatrixParserTest {
  @Test
  public void testParse() {
    String[][] expected = new String[][]{{"1", "2", "3"}, {"4", "5", "6"}, {"7", "8", "9"}};
    assertEquals(expected, StringMatrixParser.parse("""
        1 2 3\s
        4 5 6\s
        7 8 9\s
        """, "\n", " "));
  }

  @Test
  public void testParseRow(){
    assertEquals(new String[]{"a", "b", "c"}, StringMatrixParser.parseRow(" ", "a b c "));
  }

  @Test
  public void transposeGeneric(){
    var expected = new int[][]{{1, 4, 7}, {2, 5, 8}, {3, 6, 9}};
    var result = StringMatrixParser.transposeGeneric(new Integer[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}, Integer.class);
    assertEquals(expected, result);
  }

  @Test
  public void transposeGenericHard(){
    String[][] expected = new String[][]{{"1", "4"}, {"2", "5"}, {"3", "6"}};
    String[][] result = StringMatrixParser.transposeGeneric(new String[][]{{"1", "2", "3"}, {"4", "5", "6"}}, String.class);
    assertEquals(expected, result);
  }
}