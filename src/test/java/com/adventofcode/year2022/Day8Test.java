package com.adventofcode.year2022;
import static org.junit.Assert.assertEquals;

import com.adventofcode.utils.StringMatrixParser;
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
  public void getScore(){
    var matrix = StringMatrixParser.parse("30373\n" + "25512\n" + "65332\n" + "33549\n" + "35390", "\n", "");
    var intMatrix = StringMatrixParser.applyGeneric(matrix, Integer.class, Integer::valueOf);
    var transposedMatrix = StringMatrixParser.transposeGeneric(intMatrix, Integer.class);

    assertEquals("message", 0, Day8.getScore(intMatrix, transposedMatrix, 0, 0));
  }
  @Test
  public void getScore2(){
    var matrix = StringMatrixParser.parse("30373\n" + "25512\n" + "65332\n" + "33549\n" + "35390", "\n", "");
    var intMatrix = StringMatrixParser.applyGeneric(matrix, Integer.class, Integer::valueOf);
    var transposedMatrix = StringMatrixParser.transposeGeneric(intMatrix, Integer.class);

    assertEquals("message", 4, Day8.getScore(intMatrix, transposedMatrix, 1, 2));
  }

  @Test
  public void getScore3(){
    var matrix = StringMatrixParser.parse("30373\n" + "25512\n" + "65332\n" + "33549\n" + "35390", "\n", "");
    var intMatrix = StringMatrixParser.applyGeneric(matrix, Integer.class, Integer::valueOf);
    var transposedMatrix = StringMatrixParser.transposeGeneric(intMatrix, Integer.class);

    assertEquals("message", 8, Day8.getScore(intMatrix, transposedMatrix, 3, 2));
  }


  @Test
  public void getScore4(){
    var matrix = StringMatrixParser.parse("""
        11111
        11111
        11111
        11111
        11111""", "\n", "");
    var intMatrix = StringMatrixParser.applyGeneric(matrix, Integer.class, Integer::valueOf);
    var transposedMatrix = StringMatrixParser.transposeGeneric(intMatrix, Integer.class);

    assertEquals("message", 1, Day8.getScore(intMatrix, transposedMatrix, 2, 2));
  }

  @Test
  public void getScore5(){
    var matrix = StringMatrixParser.parse("""
        11111
        11111
        11211
        11111
        11111""", "\n", "");
    var intMatrix = StringMatrixParser.applyGeneric(matrix, Integer.class, Integer::valueOf);
    var transposedMatrix = StringMatrixParser.transposeGeneric(intMatrix, Integer.class);

    assertEquals("message", 16, Day8.getScore(intMatrix, transposedMatrix, 2, 2));
  }

  @Test
  public void getScore6(){
    var matrix = StringMatrixParser.parse("""
        22222
        21112
        21212
        21112
        22222""", "\n", "");
    var intMatrix = StringMatrixParser.applyGeneric(matrix, Integer.class, Integer::valueOf);
    var transposedMatrix = StringMatrixParser.transposeGeneric(intMatrix, Integer.class);

    assertEquals("message", 16, Day8.getScore(intMatrix, transposedMatrix, 2, 2));
  }

  @Test
  public void getScore7(){
    var matrix = StringMatrixParser.parse("""
        1111111
        1222221
        1211121
        1212121
        1211121
        1222221
        1111111""", "\n", "");
    var intMatrix = StringMatrixParser.applyGeneric(matrix, Integer.class, Integer::valueOf);
    var transposedMatrix = StringMatrixParser.transposeGeneric(intMatrix, Integer.class);

    assertEquals("message", 16, Day8.getScore(intMatrix, transposedMatrix, 3, 3));
  }

  @Test
  public void getScore8(){
    var matrix = StringMatrixParser.parse("""
        1111111
        1111111
        1111111
        1112111
        1111111
        1111111
        1111111""", "\n", "");
    var intMatrix = StringMatrixParser.applyGeneric(matrix, Integer.class, Integer::valueOf);
    var transposedMatrix = StringMatrixParser.transposeGeneric(intMatrix, Integer.class);

    assertEquals("message", 81, Day8.getScore(intMatrix, transposedMatrix, 3, 3));
  }


  @Test
  public void isSmallerEnd(){
    assertEquals(Integer.valueOf("2"), Day8.isSmallerUntil(new Integer[]{7, 1}, 8));
  }
  @Test
  public void isSmallerEquals(){
    assertEquals(Integer.valueOf("2"), Day8.isSmallerUntil(new Integer[]{1,2,1}, 2));
  }
}
