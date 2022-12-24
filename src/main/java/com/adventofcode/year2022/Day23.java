package com.adventofcode.year2022;

import com.adventofcode.utils.Point;
import com.adventofcode.utils.StringMatrixParser;
import com.google.common.collect.Streams;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;

public class Day23 {

  public static final String ROW_DELIMITER = "\n";

  private final static BiFunction<List<Point>,Point, Optional<Point>> MOVE_NORTH = (points, current) -> {
    if (points.stream().filter(point1 -> point1.i() == current.i() - 1).count() == 3) {
      return Optional.of(new Point(current.i() - 1, current.j()));
    }
    return Optional.empty();
  };
  private final static BiFunction<List<Point>, Point, Optional<Point>> MOVE_SOUTH = (points, current) -> {
    if (points.stream().filter(point1 -> point1.i() == current.i() + 1).count() == 3) {
      return Optional.of(new Point(current.i() + 1, current.j()));
    }
    return Optional.empty();
  };
  private final static BiFunction<List<Point>, Point, Optional<Point>> MOVE_WEST = (points, current) -> {
    if (points.stream().filter(point -> point.j() == current.j() - 1).count() == 3) {
      return Optional.of(new Point(current.i(), current.j() - 1));
    }
    return Optional.empty();
  };
  private final static BiFunction<List<Point>, Point, Optional<Point>> MOVE_EAST = (points, current) -> {
    if (points.stream().filter(point1 -> point1.j() == current.j() + 1).count() == 3) {
      return Optional.of(new Point(current.i(), current.j() + 1));
    }
    return Optional.empty();
  };

  private final static List<BiFunction<List<Point>, Point, Optional<Point>>> ELF_MOVES = List.of(MOVE_NORTH, MOVE_SOUTH, MOVE_WEST, MOVE_EAST);


  public static Point elfMove(Pair<Point, List<Point>> pair, int i) {
    var elf = pair.getValue0();
    var neighbourhood = pair.getValue1();
    if (neighbourhood.size() == 8) {
      return elf;
    }
    var newOrder = Streams.concat(ELF_MOVES.stream().skip(i % 4), ELF_MOVES.stream()).limit(4).toList();
    return newOrder.stream()
        .map(f -> f.apply(neighbourhood, elf))
        .filter(Optional::isPresent)
        .map(Optional::get).findFirst().orElse(elf);
  }
  @NotNull
  static Map<Point, String> doIterationUntilRounds(int rounds, String[][] elfMatrix) {
    Map<Point, String> elfs = getInitState(elfMatrix);
    var lastRoundELfs = elfs.entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    for (int r = 0; r < rounds; r++) {
      doOneIteration(elfs, lastRoundELfs, r);
      lastRoundELfs = elfs.entrySet().stream()
              .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }
    return elfs;
  }

  @NotNull
  static int doIterationUntilStable(String[][] elfMatrix) {
    Map<Point, String> elfs = getInitState(elfMatrix);
    var lastRoundELfs = elfs.entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    int rounds = 0;
    while(true){
      doOneIteration(elfs, lastRoundELfs, rounds);
      var currentElfs = elfs.entrySet().stream()
              .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
      if (currentElfs.equals(lastRoundELfs)){
        break;
      }
      lastRoundELfs = currentElfs;
      rounds ++;
    }
    return rounds;
  }

  private static void doOneIteration(
      Map<Point, String> elfs, Map<String, Point> lastRoundELfs, int itterationNumber) {
    elfs.entrySet().stream()
        .map(
            entry ->
                new Pair<>(
                    new Pair<>(
                        entry.getKey(),
                        getFreePoints(Point.getAdjacent(entry.getKey()), elfs)
                    ),
                    entry.getValue()
                )
        )
        .map(
            pair -> new Pair<>(
                elfMove(pair.getValue0(), itterationNumber),
                pair.getValue1()
            )
        )
        .collect(Collectors.groupingBy(Pair::getValue0, Collectors.toList()))
        .entrySet()
        .stream()
        .filter(e -> e.getValue().size() == 1)
        .forEach(
            kv -> {
              var elfName = kv.getValue().get(0).getValue1();
              var elfPoint = kv.getValue().get(0).getValue0();
              var oldElfCord = lastRoundELfs.get(elfName);
              elfs.remove(oldElfCord);
              elfs.put(elfPoint, elfName);
            });
  }

  @NotNull
  private static List<Point> getFreePoints(List<Point> entry, Map<Point, String> elfs) {
    return entry.stream()
        .filter(ap -> !elfs.containsKey(ap))
        .toList();
  }

  @NotNull
  static Map<Point, String> getInitState(String[][] elfMatrix) {
    return Arrays.stream(
                StringMatrixParser.applyGenericPoseAware(
                    elfMatrix,
                    Point.class,
                    (s, p) -> "#".equals(s) ? new Point(p.getValue0(), p.getValue1()) : null))
            .flatMap(Arrays::stream)
            .filter(Objects::nonNull)
            .collect(
                Collectors.toMap(point -> point, point -> java.util.UUID.randomUUID().toString()));
  }

  static String draw(Map<Point, String> elfs) {
    StringBuilder res = new StringBuilder();
    var jStats =
        elfs.keySet().stream().map(Point::j).collect(Collectors.summarizingInt(Integer::intValue));
    var iStats =
        elfs.keySet().stream().map(Point::i).collect(Collectors.summarizingInt(Integer::intValue));
    for (int j = iStats.getMin(); j < iStats.getMax() + 1; j++) {
        var sb = new StringBuilder();
        for (int i = jStats.getMin(); i < jStats.getMax() + 1; i++){
            var c = new Point(j, i);
            if (elfs.containsKey(c)) {
                sb.append("#");
            } else {
                sb.append(".");
            }
        };
        res.append(sb).append("\n");
    }
    System.out.println(res);
    return res.toString();
  }

  public static long part2(String instructions) {
    var elfMatrix = StringMatrixParser.parse(instructions, ROW_DELIMITER, "");
    return doIterationUntilStable(elfMatrix) + 1;
  }

  public static long part1(String instructions, int rounds) {
    var elfMatrix = StringMatrixParser.parse(instructions, ROW_DELIMITER, "");
    Map<Point, String> elfs = doIterationUntilRounds(rounds, elfMatrix);
    var jStats =
        elfs.keySet().stream().map(Point::j).collect(Collectors.summarizingInt(Integer::intValue));
    var iStats =
        elfs.keySet().stream().map(Point::i).collect(Collectors.summarizingInt(Integer::intValue));
    var occupiedSquares = elfs.size();

    return (long) (jStats.getMax() - jStats.getMin() + 1) * (iStats.getMax() - iStats.getMin() + 1)
        - occupiedSquares;
  }

  public static void main(String[] args) {
    try {
      InputStream i = Day23.class.getClassLoader().getResourceAsStream("2022/day23.txt");
      String instructions = new String(i.readAllBytes());
      System.out.println("Part1: " + part1(instructions, 10));
      System.out.println("Part2: " + part2(instructions));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
