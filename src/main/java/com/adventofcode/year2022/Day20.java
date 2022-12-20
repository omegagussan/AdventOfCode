package com.adventofcode.year2022;

import java.io.InputStream;
import java.util.Arrays;
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
    Deque<Pair<Integer, Integer>> state = extracted(originalOrder.size(), originalOrder);
    return getAnswer(state.stream().map(Pair::getValue1).toList()).stream().mapToInt(Integer::intValue).sum();
  }

  static List<Integer> getAnswer(List<Integer> lst) {
    int idx = lst.indexOf(0);
    List<Integer> lll = Stream.of(1000, 2000, 3000)
        .map(v -> v + idx)
        .map(v -> v % lst.size())
        .map(lst::get).mapToInt(Integer::intValue).boxed().toList();
    return lll;
  }

  @NotNull
  static Deque<Pair<Integer, Integer>> extracted(Integer steps,
      List<Pair<Integer, Integer>> originalOrder) {
    Deque<Pair<Integer, Integer>> state = new LinkedList<>(originalOrder);
    int length = originalOrder.size();
    for (int i = 0; i < steps; i++){
      int firstShift = 0; //is always smaller then length
      while (state.peekFirst().getValue0() != i){
        state.addLast(state.pollFirst());
        firstShift ++;
      }
      var target = state.poll();

      Integer secondShift = target.getValue1() % length;
      for (int j=0; j<Math.abs(secondShift); j++){
        if (secondShift < 0){
          state.addFirst(state.pollLast());
        } else {
          state.addLast(state.pollFirst());
        }
      }
      state.addFirst(target);

      Integer effectiveShift = (firstShift + secondShift) > length ? (firstShift + secondShift +1) : (firstShift + secondShift);
      //effectiveShift = (firstShift + secondShift) % length;
      effectiveShift = effectiveShift <= 0 ? effectiveShift + length - 1 : effectiveShift;
      for (int j=0; j<Math.abs(effectiveShift); j++){
          state.addFirst(state.pollLast());
      }
      assert state.size() == length;
    }
    return state;
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
