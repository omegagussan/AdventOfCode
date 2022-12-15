package com.adventofcode.year2022;

import com.adventofcode.utils.Point;
import java.io.InputStream;
import java.util.Arrays;
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

    public static int part1(String instruction, int lookFor) {
        var input = parseInput(instruction).toList();
        var xes = input.stream()
            .flatMap(pair -> Stream.of(pair.getValue0(), pair.getValue1()))
            .map(Point::i).toList();
        var minX = xes.stream().mapToInt(Integer::intValue).min().getAsInt();
        var maxX = xes.stream().mapToInt(Integer::intValue).max().getAsInt();

        var comparators = input.stream().map(pair -> {
            var d = Point.compareBig(pair.getValue0(), pair.getValue1()).magnitude();
            return new Pair<>(pair.getValue0(), d);
        }).toList();

        var beacons = input.stream().map(Pair::getValue1).collect(Collectors.toSet());
        var outcomes = IntStream.rangeClosed(minX, maxX)
            .mapToObj(i -> new Point(i, lookFor))
            .filter(candidate -> {
                if (beacons.contains(candidate)){
                    return false;
                }
                var distanceMap = comparators.stream()
                    .map(pair -> new Pair<>(pair.getValue0(), Point.compareBig(candidate, pair.getValue0()).magnitude()))
                    .collect(Collectors.toMap(Pair::getValue0, Pair::getValue1));

                    boolean b = comparators.stream()
                    .allMatch(pair -> pair.getValue1() >= distanceMap.get(pair.getValue0()));
                return b;
        }).toList();
        return outcomes.size();

    }

    @NotNull
    static Stream<Pair<Point, Point>> parseInput(String instructions) {
        return Arrays.stream(instructions.split(ROW_DELIMITER)).map(row -> {
            int sensorX = Integer.parseInt(row.split(",")[0].replaceAll("[^0-9-]", ""));
            int sensorY = Integer.parseInt(
                row.split(":")[0].split(",")[1].replaceAll("[^0-9-]", ""));

            int beaconX = Integer.parseInt(
                row.split(":")[1].split(",")[0].replaceAll("[^0-9-]", ""));
            int beaconY = Integer.parseInt(
                row.split(":")[1].split(",")[1].replaceAll("[^0-9-]", ""));

            Point sensor = new Point(sensorX, sensorY);
            Point beacon = new Point(beaconX, beaconY);
            return new Pair<>(sensor, beacon);
        });
    }

    public static void main(String[] args){
        try {
            InputStream i = Day15.class.getClassLoader().getResourceAsStream("2022/day15.txt");
            String instructions = new String(i.readAllBytes());
            System.out.println("Part1: " + part1(instructions,2000000));
            System.out.println("Part2: " + part2(instructions));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
