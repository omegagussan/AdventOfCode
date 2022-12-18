package com.adventofcode.year2022;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.collect.Streams;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.javatuples.Triplet;

public class Day18 {

  public static final String ROW_DELIMITER = "\n";
  public static final List<Triplet<Integer, Integer, Integer>> DIRECTIONS =
      List.of(
          new Triplet<>(1, 0, 0),
          new Triplet<>(-1, 0, 0),
          new Triplet(0, 1, 0),
          new Triplet<>(0, -1, 0),
          new Triplet(0, 0, 1),
          new Triplet(0, 0, -1));

  public static long part1(String instructions) {
    var cubes =
        Arrays.stream(instructions.split(ROW_DELIMITER))
            .map(
                row -> {
                  var idx = row.split(",");
                  assert idx.length == 3;
                  return new Triplet<>(
                      Integer.valueOf(idx[0]), Integer.valueOf(idx[1]), Integer.valueOf(idx[2]));
                })
            .collect(Collectors.toSet());

    return cubes.stream()
        .map(curr -> getPermutations(curr).stream().filter(p -> !cubes.contains(p)).count())
        .mapToLong(Long::longValue)
        .sum();
  }

  static Set<Triplet<Integer, Integer, Integer>> getPermutations(
      Triplet<Integer, Integer, Integer> cube) {
    return DIRECTIONS.stream()
        .map(
            d ->
                new Triplet<>(
                    d.getValue0() + cube.getValue0(),
                    d.getValue1() + cube.getValue1(),
                    d.getValue2() + cube.getValue2()))
        .collect(Collectors.toSet());
  }

  public static long part2(String instructions) {
    var cubes =
        Arrays.stream(instructions.split(ROW_DELIMITER))
            .map(
                row -> {
                  var idx = row.split(",");
                  assert idx.length == 3;
                  return new Triplet<>(
                      Integer.valueOf(idx[0]), Integer.valueOf(idx[1]), Integer.valueOf(idx[2]));
                })
            .collect(Collectors.toSet());

    var maxX = (int) cubes.stream().map(Triplet::getValue0).max(Integer::compareTo).get();
    var maxY = (int)  cubes.stream().map(Triplet::getValue1).max(Integer::compareTo).get();
    var maxZ = (int) cubes.stream().map(Triplet::getValue2).max(Integer::compareTo).get();
    var max = List.of(maxX + 1, maxY + 1, maxZ + 1);

  var minX = (int) cubes.stream().map(Triplet::getValue0).min(Integer::compareTo).get();
  var minY = (int)  cubes.stream().map(Triplet::getValue1).min(Integer::compareTo).get();
  var minZ = (int) cubes.stream().map(Triplet::getValue2).min(Integer::compareTo).get();
  var min = List.of(minX -1, minY -1, minZ -1);

    HashSet<Triplet<Integer, Integer, Integer>> outside =
        new HashSet<>(
            List.of(
                new Triplet<>(maxX, maxY, maxZ),
                new Triplet<>(minX, minY, minZ),
                new Triplet<>(maxX, 0, 0),
                new Triplet<>(0, maxY, 0),
                new Triplet<>(0, 0, maxZ)));
    HashSet<Triplet<Integer, Integer, Integer>> old = new HashSet<>();
    while (old.size() != outside.size()) {
      old = outside;
        HashSet<Triplet<Integer, Integer, Integer>> finalOutside1 = outside;
        outside =
            Streams.concat(outside.stream()
              .flatMap(t -> Stream.concat(Stream.of(t), getPermutations(t).stream()))
              .filter(t -> !(finalOutside1.contains(t) || cubes.contains(t)))
              .filter(
                  t -> {
                    for (int i = 0; i < 3; i++) {
                      int value = (int) t.getValue(i);
                      if (min.get(i) > value || value > max.get(i)) {
                        return false;
                      }
                    }
                    return true;
                  }), outside.stream())
              .collect(Collectors.toCollection(HashSet::new));
    }

      HashSet<Triplet<Integer, Integer, Integer>> finalOutside = outside;
      int outsideSurface = 2 * ((maxX-minX + 3) * (maxY-minY + 3)) + 2 * ((maxY-minY + 3)  * (maxZ-minZ + 3)) + 2 * ((maxX-minX + 3)  * (maxZ-minZ + 3));
      long innerOuterSurface = outside.stream()
          .map(
              curr ->
                  getPermutations(curr).stream().filter(p -> !finalOutside.contains(p)).count())
          .mapToLong(Long::longValue)
          .sum();
      return innerOuterSurface - outsideSurface;
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
