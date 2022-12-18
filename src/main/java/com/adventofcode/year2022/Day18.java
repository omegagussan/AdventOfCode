package com.adventofcode.year2022;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.javatuples.Triplet;

public class Day18 {

  public static final String ROW_DELIMITER = "\n";
  public static final List<Triplet<Integer, Integer, Integer>> DIRECTIONS = List.of(new Triplet<>(1,0,0), new Triplet<>(-1, 0, 0), new Triplet(0, 1, 0), new Triplet<>(0, -1, 0), new Triplet(0, 0, 1), new Triplet(0, 0, -1));

  public static long part1(String instructions){
      var cubes = Arrays.stream(instructions.split(ROW_DELIMITER)).map(row -> {
          var idx = row.split(",");
          assert idx.length == 3;
          return new Triplet<>(Integer.valueOf(idx[0]), Integer.valueOf(idx[1]), Integer.valueOf(idx[2]));
      }).collect(Collectors.toSet());

      return cubes.stream()
          .map(curr -> getPermutations(curr).stream()
              .filter(p -> !cubes.contains(p)).count()
          ).mapToLong(Long::longValue).sum();
  }

    static Set<Triplet<Integer, Integer, Integer>> getPermutations(
        Triplet<Integer, Integer, Integer> cube) {
        return DIRECTIONS.stream().map(d -> new Triplet<>(d.getValue0() + cube.getValue0(), d.getValue1() + cube.getValue1(), d.getValue2() + cube.getValue2())).collect(
            Collectors.toSet());
    }


    public static int part2(String instructions){
        return 24;
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
