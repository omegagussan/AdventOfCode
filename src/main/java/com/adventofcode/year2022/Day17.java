package com.adventofcode.year2022;

import com.adventofcode.utils.Point;
import com.adventofcode.utils.StringMatrixParser;
import com.adventofcode.utils.Vector;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.jetbrains.annotations.NotNull;

public class Day17 {

  public static final String ROW_DELIMITER = "\n";
  public static final int TUNNEL_WIDTH = 6;
  public static final String SHAPES_STRING =
      """
        ####

        .#.
        ###
        .#.

        ..#
        ..#
        ###

        #
        #
        #
        #

        ##
        ##
        """;
  public static final List<Set<Point>> SHAPES =
      Arrays.stream(SHAPES_STRING.split(ROW_DELIMITER + ROW_DELIMITER))
          .map(s -> StringMatrixParser.parse(s, ROW_DELIMITER, ""))
          .map(
              z ->
                  StringMatrixParser.applyGenericPoseAware(
                      z,
                      Point.class,
                      (s, p) ->
                          "#".equals(s)
                              ? new Point(p.getValue1(), z[0].length - 1 - p.getValue0())
                              : null))
          .map(
              arrArr ->
                  Arrays.stream(arrArr)
                      .flatMap(Arrays::stream)
                      .filter(Objects::nonNull)
                      .collect(Collectors.toSet()))
          .toList();
  static Map<Integer, Integer> SHAPE_OFFSET = Map.of(0, -3, 1, 0, 2, 0, 3, 3, 4, 0);

  public static List<Set<Point>> PROCESSED_SHAPES =
      IntStream.rangeClosed(0, SHAPES.size() - 1)
          .mapToObj(
              i ->
                  SHAPES.get(i).stream()
                      .map(point -> point.move(0, SHAPE_OFFSET.get(i)))
                      .collect(Collectors.toSet()))
          .toList();

  public static Set<Point> moveIfSpace(Set<Point> rock, Vector movement, Set<Point> occupied) {
    var candidate =
        rock.stream()
            .map(point -> point.moveArbitrary(movement))
            .filter(point -> 0 <= point.i() && point.i() <= TUNNEL_WIDTH)
            .filter(point -> 0 <= point.j())
            .collect(Collectors.toSet());

    var isCollisionFree = candidate.stream().noneMatch(occupied::contains);
    return (candidate.size() == rock.size() && isCollisionFree) ? candidate : rock;
  }

  public static void draw(Set<Point> current, Set<Point> constraints, String label) {
    var highestPoint = current.stream().map(Point::j).mapToInt(Integer::intValue).max().orElse(-1);
    var highestPoint2 =
        constraints.stream().map(Point::j).mapToInt(Integer::intValue).max().orElse(-1);
    var range =
        new ArrayList<>(
            IntStream.rangeClosed(0, Math.max(highestPoint, highestPoint2))
                .boxed()
                .toList());
    System.out.println(label);
    Collections.reverse(range);
    range.forEach(
        j -> {
          var sb = new StringBuilder();
          IntStream.rangeClosed(0, TUNNEL_WIDTH)
              .forEach(
                  i -> {
                    var c = new Point(i, j);
                    if (current.contains(c)) {
                      sb.append("@");
                    } else if (constraints.contains(c)) {
                      sb.append("#");
                    } else {
                      sb.append(".");
                    }
                  });
          System.out.println(sb.toString());
        });
    System.out.println("");
    System.out.println("");
    System.out.println("");
  }

  public static int part1(String instructions) {
    var instructionsList = Arrays.stream(instructions.split("")).toList();
    var tower = new HashSet<Point>();
    AtomicInteger wind = new AtomicInteger();
    IntStream.rangeClosed(0, 2021)
        .boxed()
        .forEach(
            blockNumber -> {
              int shapeIdx = blockNumber % SHAPES.size();
              Set<Point> shape =
                  getShape(
                      tower.stream().map(Point::j).mapToInt(Integer::intValue).max().orElse(-1),
                      shapeIdx);
              while (true) {
                int movementX =
                    ">".equals(
                                instructionsList.get(
                                    wind.getAndIncrement() % instructionsList.size()))
                        ? 1
                        : -1;
                var horizontal = moveIfSpace(shape, new Vector(movementX, 0), tower);
                var vertical = moveIfSpace(horizontal, new Vector(0, -1), tower);

                if (horizontal == vertical) {
                  shape = horizontal;
                  break;
                }
                shape = vertical;
              }
              tower.addAll(shape);
            });
    return tower.stream().map(Point::j).mapToInt(Integer::intValue).max().getAsInt() + 1; //zero indexed
  }

  @NotNull
  static Set<Point> getShape(int towerHighestPoint, int shapeIdx) {
    var shape =
        new ArrayList<>(PROCESSED_SHAPES.get(shapeIdx))
            .stream()
                .map(point -> point.move(2, +towerHighestPoint + 4))
                .collect(Collectors.toSet());
    return shape;
  }

  public static int part2(String instructions) {
    return 2;
  }

  public static void main(String[] args) {
    try {
      InputStream i = Day17.class.getClassLoader().getResourceAsStream("2022/day17.txt");
      String instructions = new String(i.readAllBytes());
      System.out.println("Part1: " + part1(instructions));
      System.out.println("Part2: " + part2(instructions));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
