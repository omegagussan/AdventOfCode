package com.adventofcode.utils;
import static org.junit.Assert.assertEquals;

import java.util.function.Function;
import org.junit.Test;

public class StringMatrixParserTest {
  @Test
  public void testParse() {
    var expected = new String[][]{{"1", "2", "3"}, {"4", "5", "6"}, {"7", "8", "9"}};
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
  public void testTransposeGeneric(){
    var expected = new int[][]{{1, 4, 7}, {2, 5, 8}, {3, 6, 9}};
    var result = StringMatrixParser.transposeGeneric(new Integer[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}, Integer.class);
    assertEquals(expected, result);
  }

  @Test
  public void testTransposeGenericHard(){
    var expected = new String[][]{{"1", "4"}, {"2", "5"}, {"3", "6"}};
    var result = StringMatrixParser.transposeGeneric(new String[][]{{"1", "2", "3"}, {"4", "5", "6"}}, String.class);
    assertEquals(expected, result);
  }

  @Test
  public void testApplyGenericSameType(){
    var expected = new String[][]{{"11", "12"}, {"13", "14"}};
    Function<String, String> fun = (s -> "1" + s);
    var result = StringMatrixParser.applyGeneric(new String[][]{{"1", "2"}, {"3", "4"}}, String.class, fun);
    assertEquals(expected, result);
  }

  @Test
  public void testApplyGenericSameTypeNonQuadratic(){
    var expected = new String[][]{{"11", "12", "13"}, {"14", "15", "16"}};
    Function<String, String> fun = (s -> "1" + s);
    var result = StringMatrixParser.applyGeneric(new String[][]{{"1", "2", "3"}, {"4", "5", "6"}}, String.class, fun);
    assertEquals(expected, result);
  }

  @Test
  public void testApplyGenericTypeSwap(){
    var expected = new int[][]{{1, 2}, {3,4}};
    Function<String, Integer> fun = (Integer::valueOf);
    var result = StringMatrixParser.applyGeneric(new String[][]{{"1", "2"}, {"3", "4"}}, Integer.class, fun);
    assertEquals(expected, result);
  }
}