package com.adventofcode.year2022;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.javatuples.Pair;

public class Day20 {

  public static final String ROW_DELIMITER = "\n";
  public static final Long DECRYPTION_KEY = 811589153L;

  public static long part1(String instructions) {
    var originalOrderValues =
        Arrays.stream(instructions.split(ROW_DELIMITER)).map(s -> Long.valueOf(s.trim())).toList();
    Map<Integer, Long> cache = new HashMap<>();
    var originalOrder =
        IntStream.range(0, originalOrderValues.size())
            .mapToObj(
                z -> {
                  var val = originalOrderValues.get(z);
                  var shorter = val % (originalOrderValues.size() - 1);
                  cache.put(z, val);
                  return new Pair<>(z, shorter);
                })
            .collect(Collectors.toCollection(LinkedList::new));
    var state = extracted(originalOrder.size(), originalOrder);
    var lastState = state.stream().map(Pair::getValue0).map(cache::get).toList();
    return getAnswer(lastState).stream().mapToLong(Long::longValue).sum();
  }

  static List<Long> getAnswer(List<Long> lst) {
    int idx = lst.indexOf(0L);
    return Stream.of(1000, 2000, 3000)
        .map(v -> v + idx)
        .map(v -> v % lst.size())
        .map(lst::get)
        .mapToLong(Long::longValue)
        .boxed()
        .toList();
  }

  static LinkedList<Pair<Integer, Long>> extracted(
      Integer steps, LinkedList<Pair<Integer, Long>> state) {
    for (int i = 0; i < steps; i++) {
      while (state.peekFirst().getValue0() != i) {
        state.addLast(state.pollFirst());
      }
      var target = state.removeFirst();
      //var preRotate = target.getValue1() % (state.size() -1);
      int rotate = Math.toIntExact(target.getValue1());
      for (int j = 0; j < Math.abs(rotate); j++) {
        if (rotate > 0) {
          state.addLast(state.pollFirst());
        } else {
          state.addFirst(state.pollLast());
        }
      }
      state.addFirst(target);
      for (int j = 0; j < Math.abs(rotate); j++) {
        if (rotate > 0) {
          state.addFirst(state.pollLast());
        } else {
          state.addLast(state.pollFirst());
        }
      }
    }
    return state;
  }

  public static long part2(String instructions) {
    var originalOrderValues =
        Arrays.stream(instructions.split(ROW_DELIMITER))
            .map(s -> Long.valueOf(s.trim()))
            .map(l -> Math.multiplyExact(DECRYPTION_KEY, l))
            .toList();
    Map<Integer, Long> cache = new HashMap<>();
    var originalOrder =
        IntStream.range(0, originalOrderValues.size())
            .mapToObj(
                z -> {
                  var val = originalOrderValues.get(z);
                  var shorter = val % (originalOrderValues.size() - 1);
                  cache.put(z, val);
                  return new Pair<>(z, shorter);
                })
            .collect(Collectors.toCollection(LinkedList::new));
    for (int z = 0; z < 10; z++) {
      originalOrder = extracted(originalOrder.size(), originalOrder);
      System.out.println("done with " + z);
      System.out.println(originalOrder.stream().map(Pair::getValue0).map(cache::get).toList());
    }
    var lastState = originalOrder.stream().map(Pair::getValue0).map(cache::get).toList();
    List<Long> longs = getAnswer(lastState);
    return longs.stream().mapToLong(Long::longValue).sum();
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
