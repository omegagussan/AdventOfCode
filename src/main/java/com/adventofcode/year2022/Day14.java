package com.adventofcode.year2022;

import com.adventofcode.utils.Point;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;


public class Day14 {

    public static final String ROW_DELIMITER = "\n";

    @NotNull
    static List<Pair<Point, Point>> getLineSegments(String instructions) {
        return Arrays.stream(instructions.split(ROW_DELIMITER))
            .map(row -> {
                var points = Arrays.stream(row.split(" -> ")).map(s -> {
                    var v = s.split(",");
                    int v1 = Integer.parseInt(v[0].replaceAll("[^0-9]", ""));
                    int v2 = Integer.parseInt(v[1].replaceAll("[^0-9]", ""));
                    return new Point(v1, v2);
                }).toList();
                return IntStream.range(0, points.size() - 1)
                    .mapToObj(i -> new Pair<>(points.get(i), points.get(i + 1))).toList();
            }).flatMap(Collection::stream).toList();
    }

    public static int part1(String instructions) {
        var lineSegments = getLineSegments(instructions);
        Set<Point> constraints = getConstrains(lineSegments);
        var spawnPoint = new Point(500, 0);
        var abyssPoint = lineSegments.stream().flatMap(p -> List.of(p.getValue0(), p.getValue1()).stream()).max(
            Comparator.comparingInt(Point::j)).get().j();
        ArrayList<Point> sand = new ArrayList<>(List.of(spawnPoint));
        Set<Point> sandAtRest = new HashSet<>();

    while (sand.stream().noneMatch(point -> point.j() > abyssPoint)){
        var newSand =
            sand.stream()
                .map(
                    current -> {
                        var downCandidate = current.move(0, 1);
                        var downLeftCandidate = current.move(-1, 1);
                        var downRightCandidate = current.move(1, 1);
                        Optional<Point> possibleMove =
                            Stream.of(downCandidate, downLeftCandidate, downRightCandidate) //OBS! Order matters
                                .filter(c -> !constraints.contains(c) && !sandAtRest.contains(c))
                                .findFirst();
                        if (possibleMove.isEmpty()) {
                            sandAtRest.add(current);
                        }
                        return possibleMove;
                    })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        sand = new ArrayList<>(newSand);
        sand.add(spawnPoint);
    }

    return sandAtRest.size();
    }

    @NotNull
    static Set<Point> getConstrains(List<Pair<Point, Point>> lineSegments) {
        return lineSegments.stream()
            .flatMap(pair -> Point.compare(pair.getValue0(), pair.getValue1()).consistsOf(pair.getValue1()).stream())
            .collect(Collectors.toSet());
    }

    public static int part2(String instructions) {
        var lineSegments = getLineSegments(instructions);
        Set<Point> constraints = getConstrains(lineSegments);
        var spawnPoint = new Point(500, 0);
        var floorHeight = lineSegments.stream().flatMap(p -> List.of(p.getValue0(), p.getValue1()).stream()).max(
            Comparator.comparingInt(Point::j)).get().j() + 2;
        ArrayList<Point> sand = new ArrayList<>(List.of(spawnPoint));
        Set<Point> sandAtRest = new HashSet<>();

        while (!sandAtRest.contains(spawnPoint)){
            var newSand =
                sand.stream()
                    .map(
                        current -> {
                            Optional<Point> possibleMove =
                                Point.getAdjacent(current).stream().filter(point -> point.j() > current.j()) //OBS! Order matters
                                    .filter(c -> !constraints.contains(c) && !sandAtRest.contains(c) && c.j() != floorHeight)
                                    .findFirst();
                            if (possibleMove.isEmpty()) {
                                sandAtRest.add(current);
                            }
                            return possibleMove;
                        })
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
            sand = new ArrayList<>(newSand);
            sand.add(spawnPoint);
        }

        return sandAtRest.size();
    }

    public static void main(String[] args){
        try {
            InputStream i = Day14.class.getClassLoader().getResourceAsStream("2022/day14.txt");
            String instructions = new String(i.readAllBytes());
            System.out.println("Part1: " + part1(instructions));
            System.out.println("Part2: " + part2(instructions));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
