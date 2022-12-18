package com.adventofcode.year2022;

import com.google.common.collect.Collections2;
import com.google.common.collect.Streams;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.javatuples.Triplet;
import org.jetbrains.annotations.NotNull;

public class Day18 {

  public static final String ROW_DELIMITER = "\n";
  public static final List<Triplet<Integer, Integer, Integer>> DIRECTIONS =
      List.of(
          new Triplet<>(1, 0, 0),
          new Triplet<>(-1, 0, 0),
          new Triplet<>(0, 1, 0),
          new Triplet<>(0, -1, 0),
          new Triplet<>(0, 0, 1),
          new Triplet<>(0, 0, -1));

    @NotNull
    private static Set<Triplet<Integer, Integer, Integer>> parseInput(String instructions) {
        return Arrays.stream(instructions.split(ROW_DELIMITER))
            .map(
                row -> {
                    var idx = row.split(",");
                    assert idx.length == 3;
                    return new Triplet<>(
                        Integer.valueOf(idx[0]), Integer.valueOf(idx[1]), Integer.valueOf(idx[2]));
                })
            .collect(Collectors.toSet());
    }

    static Set<Triplet<Integer, Integer, Integer>> getAdjacentBoxes(Triplet<Integer, Integer, Integer> box) {
    return DIRECTIONS.stream()
        .map(d -> new Triplet<>(
                    d.getValue0() + box.getValue0(),
                    d.getValue1() + box.getValue1(),
                    d.getValue2() + box.getValue2())
        )
        .collect(Collectors.toSet());
  }

    private static long getTotalSurfaceOfBoxes(Set<Triplet<Integer, Integer, Integer>> boxes) {
        return boxes.stream()
            .map(curr -> getAdjacentBoxes(curr)
                .stream().filter(p -> !boxes.contains(p)).count()
            )
            .mapToLong(Long::longValue)
            .sum();
    }

    public static long part2(String instructions) {
        var cubes = parseInput(instructions);

        var min = getLimit(cubes, BinaryOperator::minBy, -1);
        var max = getLimit(cubes, BinaryOperator::maxBy, 1);

        HashSet<Triplet<Integer, Integer, Integer>> outside = getBiggerBox(cubes, max, min);

        var sideLengths = IntStream.rangeClosed(0, 2)
            .map(i -> ((int) max.getValue(i) - (int) min.getValue(i) + 1))
            .boxed().toList();
        int outsideSurface = Collections2.permutations(sideLengths).stream()
            .map(integers -> integers.stream()
                .limit(2)
                .reduce(1, (a, b) -> a * b))
            .mapToInt(Integer::intValue).sum();
        long totalSurfaceOfBox = getTotalSurfaceOfBoxes(outside);
        return totalSurfaceOfBox - outsideSurface;
    }

    @NotNull
    private static HashSet<Triplet<Integer, Integer, Integer>> getBiggerBox(
        Set<Triplet<Integer, Integer, Integer>> cubes,
        Triplet<Integer, Integer, Integer> max,
        Triplet<Integer, Integer, Integer> min
    ) {
        var outside = new HashSet<>(List.of(max, min));
        HashSet<Triplet<Integer, Integer, Integer>> old = new HashSet<>();
        while (old.size() != outside.size()) {
            old = outside;
            var copyForFinalLookup = outside;
            outside =
                Streams.concat(outside.stream()
                        .flatMap(t -> Stream.concat(Stream.of(t), getAdjacentBoxes(t).stream()))
                        .filter(t -> !(copyForFinalLookup.contains(t) || cubes.contains(t)))
                        .filter(checkWithinMinMaxRange(max, min)), outside.stream())
                    .collect(Collectors.toCollection(HashSet::new));
        }
        return outside;
    }

    @NotNull
    private static Predicate<Triplet<Integer, Integer, Integer>> checkWithinMinMaxRange(
        Triplet<Integer, Integer, Integer> max, Triplet<Integer, Integer, Integer> min) {
        return t -> {
            for (int i = 0; i < 3; i++) {
                int value = (int) t.getValue(i);
                if ((int) min.getValue(i) > value ||
                    value > (int) max.getValue(i)) {
                    return false;
                }
            }
            return true;
        };
    }

    @NotNull
    private static List<Triplet<Integer, Integer, Integer>> getMaxMin(
        Set<Triplet<Integer, Integer, Integer>> cubes) {
        var max = IntStream.rangeClosed(0, 2)
            .map(i -> cubes.stream().map(t -> (int) t.getValue(i)).max(Integer::compareTo).get()).toArray();

        var min = IntStream.rangeClosed(0, 2)
            .map(i -> cubes.stream().map(t -> (int) t.getValue(i)).min(Integer::compareTo).get()).toArray();

        return List.of(
            new Triplet<>(max[0] + 1, max[1] + 1, max[2] + 1),
            new Triplet<>(min[0] -1 , min[1] -1 , min[2] -1)
        );
    }

    @NotNull
    private static Triplet<Integer, Integer, Integer> getLimit(
        Set<Triplet<Integer, Integer, Integer>> cubes,
        Function<Comparator<Integer>, BinaryOperator<Integer>> reducingFn,
        int offset
        ) {
        var arr = IntStream.rangeClosed(0, 2)
            .map(i -> cubes.stream().map(t -> (int) t.getValue(i)).reduce(reducingFn.apply(Integer::compareTo)).get()).toArray();

        return new Triplet<>(arr[0] + offset, arr[1] + offset, arr[2] + offset);
    }

    public static long part1(String instructions) {
        return getTotalSurfaceOfBoxes(parseInput(instructions));
    }

    public static void main(String[] args) {
    try {
      InputStream i = Day18.class.getClassLoader().getResourceAsStream("2022/day18.txt");
      String instructions = new String(i.readAllBytes());
      System.out.println("Part1: " + part1(instructions));
      System.out.println("Part2: " + part2(instructions));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
