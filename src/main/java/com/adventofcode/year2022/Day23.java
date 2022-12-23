package com.adventofcode.year2022;

import com.adventofcode.utils.Point;
import com.adventofcode.utils.StringMatrixParser;
import com.google.common.collect.Streams;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;

public class Day23 {

  public static final String ROW_DELIMITER = "\n";

  public static long part2(String instructions) {
    return 2;
  }

  public static Point elfMove(Map<Point, String> elfs, Pair<Point, List<Point>> pair, int i) {
    var elf = pair.getValue0();
    var neighbourhood = pair.getValue1();
    List<Point> free = neighbourhood.stream().filter(point -> !elfs.containsKey(point)).toList();
    if (free.size() == 8) {
      return elf;
    }
    Supplier<Optional<Point>> a = () -> {
      if (free.stream().filter(point1 -> point1.i() == elf.i() - 1).count() == 3) {
        return Optional.of(new Point(elf.i() - 1, elf.j()));
      }
      return Optional.empty();
    };
    Supplier<Optional<Point>> b = () -> {
      if (free.stream().filter(point1 -> point1.i() == elf.i() + 1).count() == 3) {
        return Optional.of(new Point(elf.i() + 1, elf.j()));
      }
      return Optional.empty();
    };
    Supplier<Optional<Point>> c = () -> {
      if (free.stream().filter(point1 -> point1.j() == elf.j() + 1).count() == 3) {
        return Optional.of(new Point(elf.i(), elf.j() + 1));
      }
      return Optional.empty();
    };
    Supplier<Optional<Point>> d = () -> {
      if (free.stream().filter(point -> point.j() == elf.j() - 1).count() == 3) {
        return Optional.of(new Point(elf.i(), elf.j() - 1));
      }
      return Optional.empty();
    };
    var ops = new ArrayList<>(List.of(a, b, d, c));
    var newOrder = Streams.concat(ops.stream().skip(i % 4), ops.stream()).limit(4).toList();
    Optional<Point> first = newOrder.stream()
        .map(Supplier::get)
        .filter(Optional::isPresent)
        .map(Optional::get).findFirst();
    return first.orElse(elf);
  }

  public static long part1(String instructions, int rounds) {
    var elfMatrix = StringMatrixParser.parse(instructions, ROW_DELIMITER, "");
    Map<Point, String> elfs = extracted(rounds, elfMatrix);
    var jStats =
        elfs.keySet().stream().map(Point::j).collect(Collectors.summarizingInt(Integer::intValue));
    var iStats =
        elfs.keySet().stream().map(Point::i).collect(Collectors.summarizingInt(Integer::intValue));
    var occupiedSquares = elfs.size();

    return (long) (jStats.getMax() - jStats.getMin() + 1) * (iStats.getMax() - iStats.getMin() + 1)
        - occupiedSquares;
  }

  @NotNull
  static Map<Point, String> extracted(int rounds, String[][] elfMatrix) {
    Map<Point, String> elfs = getInitState(elfMatrix);
    var lastRoundELfs =
        elfs.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    final var totalElfs = elfs.size();
    for (int r = 0; r < rounds; r++) {
      int finalR = r;
      var elfDestinations =
          elfs.entrySet().stream()
              .map(
                  entry ->
                      new Pair<>(
                          new Pair<>(
                              entry.getKey(),
                              Point.getAdjacent(entry.getKey()).stream()
                                  .filter(ap -> !elfs.containsKey(ap))
                                  .toList()),
                          entry.getValue()))
              .map(pair -> new Pair<>(elfMove(elfs, pair.getValue0(), finalR), pair.getValue1()))
              .toList();
      Map<Point, List<Pair<Point, String>>> destinationCounts =
          elfDestinations.stream().collect(Collectors.groupingBy(Pair::getValue0, Collectors.toList()));

      Map<String, Point> finalLastRoundELfs = lastRoundELfs;
      destinationCounts.entrySet().stream()
          .filter(e -> e.getValue().size() == 1)
          .forEach(
              kv -> {
                var elfName = kv.getValue().get(0).getValue1();
                var elfPoint = kv.getValue().get(0).getValue0();
                var oldElfCord = finalLastRoundELfs.get(elfName);
                elfs.remove(oldElfCord);
                elfs.put(elfPoint, elfName);
              });
      lastRoundELfs =
          elfs.entrySet().stream()
              .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
      assert elfs.size() == totalElfs;
      assert lastRoundELfs.size() == totalElfs;
    }
    return elfs;
  }

  @NotNull
  static Map<Point, String> getInitState(String[][] elfMatrix) {
    var elfs =
        Arrays.stream(
                StringMatrixParser.applyGenericPoseAware(
                    elfMatrix,
                    Point.class,
                    (s, p) -> "#".equals(s) ? new Point(p.getValue0(), p.getValue1()) : null))
            .flatMap(Arrays::stream)
            .filter(Objects::nonNull)
            .collect(
                Collectors.toMap(point -> point, point -> java.util.UUID.randomUUID().toString()));
    return elfs;
  }


  public static String draw(Map<Point, String> elfs) {
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
