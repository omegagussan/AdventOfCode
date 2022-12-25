package com.adventofcode.year2022;

import com.adventofcode.utils.Point;
import com.adventofcode.utils.StringMatrixParser;
import com.adventofcode.utils.Vector;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.jetbrains.annotations.NotNull;

public class Day22 {

  public static final String ROW_DELIMITER = "\n";
  static public final String WITH_DELIMITER = "(?<=%1s)";


  public static long part2(String instructions) {
    return 2;
  }

  static int score(Vector v){
    if (v.equals(new Vector(1,0))){
      return 0;
    } else if (v.equals(new Vector(0,-1))){
      return 1;
    } else if (v.equals(new Vector(-1,0))){
      return 2;
    }else if (v.equals(new Vector(0,1))){
      return 3;
    }
    throw new IllegalArgumentException("what this? " + v);
  };

  public static long part1(String instructions) {
    String board = instructions.split(ROW_DELIMITER+ROW_DELIMITER)[0];
    var boardMatrix = StringMatrixParser.parse(board, ROW_DELIMITER, "");
    var paddedBoardMatrix = StringMatrixParser.parse(board, ROW_DELIMITER, "", "Z");
    var transposedBoard = StringMatrixParser.transposeString(paddedBoardMatrix);
    var paddedTransposedBoard = StringMatrixParser.transposeGeneric(paddedBoardMatrix, String.class);

    var rowConstraints = getConstraints(paddedBoardMatrix);
    var colConstraints = getConstraints(paddedTransposedBoard);

    var pose = new Point(rowConstraints.get(0).getValue0(), 0);
    var direction = new Vector(1, 0);

    String path = instructions.split(ROW_DELIMITER+ROW_DELIMITER)[1];
    var steps = path.split(String.format(WITH_DELIMITER, "[A-Z]"));
    for (String step: steps){
      var sliceOfBoardToStep = direction.j() == 0 ? boardMatrix[pose.j()] : transposedBoard[pose.i()];
      var pathOfStepsWithoutWrapAround = direction.byScalar(Integer.parseInt(step.substring(0, step.length() -1)))
          .consistsOf(pose);

      Point old = pathOfStepsWithoutWrapAround.get(0);
      for (Point point : pathOfStepsWithoutWrapAround){
        var constraits = direction.j() == 0 ? rowConstraints.get(point.j()) : colConstraints.get(point.i());
        int dimensions = direction.j() == 0 ? point.i() : point.j();
        int candidateIdx = (dimensions - constraits.getValue0()) % constraits.getValue2();
        assert (candidateIdx + constraits.getValue0()) <= constraits.getValue1();
        assert (candidateIdx + constraits.getValue0()) >= constraits.getValue0();

        if (Objects.equals(sliceOfBoardToStep[candidateIdx], "#")){
          break;
        }
        old = direction.j() == 0 ? new Point(candidateIdx + constraits.getValue0(), point.j()) : new Point(point.i(), candidateIdx + constraits.getValue0());
      }
      pose = old;
      direction = direction.turn(step.substring(step.length()-1));
      System.out.println(direction);
      System.out.println(pose);
      System.out.println(" ");
    }
    return (pose.j() + 1) * 1000 + 4 * (pose.i() + 1) + score(direction);
  }

  @NotNull
  private static List<Triplet<Integer, Integer, Integer>> getConstraints(String[][] matrix) {
    return Arrays.stream(matrix).map(arr -> {
      var from = IntStream.range(0, arr.length)
          .mapToObj(i -> new Pair<>(i, arr[i]))
          .filter(p -> !p.getValue1().equals("Z"))
          .findFirst().get().getValue0();
      var to = from + arr.length - 1;
      return new Triplet<>(from, to, to -from);
    }).toList();
  }

  public static void main(String[] args) {
    try {
      InputStream i = Day22.class.getClassLoader().getResourceAsStream("2022/day22.txt");
      String instructions = new String(i.readAllBytes());
      System.out.println("Part1: " + part1(instructions));
      System.out.println("Part2: " + part2(instructions));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
