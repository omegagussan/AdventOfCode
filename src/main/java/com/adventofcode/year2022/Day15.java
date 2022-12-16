package com.adventofcode.year2022;

import com.adventofcode.utils.Point;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;

public class Day15 {

  public static final String ROW_DELIMITER = "\n";

  public static int part2(String instructions) {
    return 2;
  }

  public static int part1(String instruction, int lineToSearch) {
    var sensorBeaconPairs = parseInput(instruction).toList();
    var beacons =
        sensorBeaconPairs.stream()
            .map(l -> new Point(l.get(2), l.get(3)))
            .collect(Collectors.toSet());
    var sensors =
        sensorBeaconPairs.stream()
            .map(l -> new Point(l.get(0), l.get(1)))
            .collect(Collectors.toSet());

    var sensorDistancePairs =
        sensorBeaconPairs.stream()
            .map(
                row -> {
                  Integer manhattanDistance =
                      Math.addExact(
                          Math.abs(row.get(0) - row.get(2)), Math.abs(row.get(1) - row.get(3)));
                  return new Pair<>(new Point(row.get(0), row.get(1)), manhattanDistance);
                })
            .toList();

    int to =
        sensorDistancePairs.stream()
            .mapToInt(x -> x.getValue0().j() + x.getValue1())
            .max()
            .getAsInt();
    int from =
        sensorDistancePairs.stream()
            .mapToInt(x -> x.getValue0().j() - x.getValue1())
            .min()
            .getAsInt();

    var possibleAdditionalBeacons = new HashSet<Point>();
    IntStream.rangeClosed(from, to)
        .forEach(
            i -> {
              var pt = new Point(i, lineToSearch);
              var rangeEligability =
                  sensorDistancePairs.stream().map(p -> new Pair<>(Math.addExact(
                          Math.abs(p.getValue0().i() - pt.i()),
                          Math.abs(p.getValue0().j() - pt.j())), p.getValue1()))
                      .allMatch(p2 -> p2.getValue0() > p2.getValue1());
              if (!rangeEligability || beacons.contains(pt) || sensors.contains(pt)) {
                possibleAdditionalBeacons.add(pt);
              }
            });
    return possibleAdditionalBeacons.size() - (int) beacons.stream().filter(point -> point.j() == lineToSearch).count();
  }

  @NotNull
  static Stream<List<Integer>> parseInput(String instructions) {
    Matcher m =
        Pattern.compile("Sensor at x=(.*), y=(.*): closest beacon is at x=(.*), y=(.*)")
            .matcher("");
    return Arrays.stream(instructions.split(ROW_DELIMITER))
        .map(
            row -> {
              m.reset(row).matches();
              return IntStream.rangeClosed(1, 4)
                  .mapToObj(i -> Integer.parseInt(m.group(i)))
                  .toList();
            });
  }

  public static void main(String[] args) {
    try {
      InputStream i = Day15.class.getClassLoader().getResourceAsStream("2022/day15.txt");
      String instructions = new String(i.readAllBytes());
      System.out.println("Part1: " + part1(instructions, 2000000));
      System.out.println("Part2: " + part2(instructions));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
