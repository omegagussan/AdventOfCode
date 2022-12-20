package com.adventofcode.year2022;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;

public class Day20 {

  public static final String ROW_DELIMITER = "\n";

  public static int part1(String instructions){
    var originalOrderValues = Arrays.stream(instructions.split(ROW_DELIMITER))
        .map(s -> Integer.valueOf(s.trim()))
        .toList();
    var originalOrder = IntStream.range(0, originalOrderValues.size())
        .mapToObj(z -> new Pair<>(z, originalOrderValues.get(z))).toList();
    var state = extracted(originalOrder.size(), originalOrder);
    return getAnswer(state).stream().mapToInt(Integer::intValue).sum();
  }

  static List<Integer> getAnswer(List<Integer> lst) {
    int idx = lst.indexOf(0);
    List<Integer> tmp = Stream.of(1000, 2000, 3000)
        .map(v -> v + idx)
        .map(v -> v % lst.size())
        .map(lst::get).mapToInt(Integer::intValue).boxed().toList();
    return tmp;
  }

  @NotNull
  static List<Integer> extracted(Integer steps,
      List<Pair<Integer, Integer>> originalOrder) {
    ArrayList<Pair<Integer, Integer>> state = new ArrayList<>(originalOrder);
    for (int i = 0; i < steps; i++){
      while (state.get(0).getValue0() != i){
        Collections.rotate(state, -1);
      }
      var target = state.remove(0);
      Integer rotate = target.getValue1();
      Collections.rotate(state, -rotate);
      state.add(0, target);
      Collections.rotate(state, rotate);
    }
    return state.stream().map(Pair::getValue1).toList();
  }

  public static int part2(String instructions){
    return 2;
  }

  public static void main(String[] args) {
    try {
      InputStream i = Day20.class.getClassLoader().getResourceAsStream("2022/day20.txt");
      String instructions = new String(i.readAllBytes());
      System.out.println("Part1: " + part1(instructions));
      System.out.println("Part2: " + part2(instructions));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
