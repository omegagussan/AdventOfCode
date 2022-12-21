
package com.adventofcode.year2022;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;

public class Day11 {
    public static final String ROW_DELIMITER = "\n";

    record Monkey(
        Integer id,
        List<Long> items,
        Function<Long, Long> op,
        Function<Long, Boolean> test,
        Integer trueTarget,
        Integer falseTarget
    ){}

  @NotNull
  static Integer getTarget(String m, String matcher) {
      return Arrays.stream(m.split(ROW_DELIMITER))
          .filter(s1 -> s1.contains(String.format("If %s: throw to Monkey ", matcher)))
          .map(s1 -> Integer.parseInt(s1.replaceAll("[^0-9]", "")))
          .findFirst().get();
  }

  @NotNull
  static Function<Long, Long> parseOperation(String instructions) {
    return Arrays.stream(instructions.split(ROW_DELIMITER))
        .filter(s -> s.contains("Operation:"))
        .map(s -> s.substring(12))
        .map(s -> {
          if (s.contains("old * old")){
            return (Function<Long, Long>) x -> x * x;
          }
          var value = Integer.parseInt(s.replaceAll("[^0-9]", ""));
          if (s.contains(" * ")){
            return (Function<Long, Long>) x -> x * value;
          } else {
            return (Function<Long, Long>) x -> x + value;
          }
        })
        .findFirst().get();
  }

  @NotNull
  static ArrayList<Long> parseItems(String instructions) {
    return new ArrayList<>(Arrays.stream(instructions.split(ROW_DELIMITER))
        .filter(s -> s.contains("Starting items:"))
        .map(s -> s.substring(18))
        .flatMap(s -> Arrays.stream(s.split(",")))
        .map(String::trim)
        .map(Long::valueOf)
        .toList());
  }

  @NotNull
  static Function<Long, Boolean> getTestFn(String instructions) {
    return Arrays.stream(instructions.split(ROW_DELIMITER))
        .filter(s -> s.contains("Test:"))
        .map(s -> s.substring(8).trim())
        .map(s -> {
          var value = Integer.parseInt(s.replaceAll("[^0-9]", ""));
          return (Function<Long, Boolean>) x -> x %  value == 0L;
        })
        .findFirst().get();
  }

  static List<Monkey> getMonkeyList(String instructions) {
    String[] rows = instructions.split(ROW_DELIMITER + ROW_DELIMITER);
    return IntStream.range(0, rows.length)
        .mapToObj(i -> new Pair<>(i, rows[i]))
        .map(pair -> new Monkey(
          pair.getValue0(),
          parseItems(pair.getValue1()),
          parseOperation(pair.getValue1()),
          getTestFn(pair.getValue1()),
          getTarget(pair.getValue1(), "true"),
          getTarget(pair.getValue1(), "false")
      )).toList();
  }

  @NotNull
  private static ArrayList<Long> countInspections(Function<Long, Long> worryFun, int rounds,
      List<Monkey> state) {
    var inspections = new ArrayList<>(Collections.nCopies(state.size(), 0L));

    for (int round = 0; round < rounds; round ++){
      for (Monkey m : state){
        inspections.set(m.id, inspections.get(m.id) + m.items.size());

        var itemCopy = new ArrayList<>(m.items);
        for (Long item: itemCopy){
          m.items.remove(item);
          var operatedItem = m.op.apply(item);
          var moddedItem = worryFun.apply(operatedItem);
          var targetIdx = m.test.apply(moddedItem) ? m.trueTarget : m.falseTarget;
          state.get(targetIdx).items.add(moddedItem);
        }
      }
    }
    return inspections;
  }

  public static int part1(String instructions, Function<Long, Long> worryFun, int rounds) {
    List<Monkey> state = getMonkeyList(instructions);
    ArrayList<Long> inspections = countInspections(worryFun, rounds, state);
    var sorted = inspections.stream().sorted().toList();
    return (int) (sorted.get(inspections.size()-1) * sorted.get(inspections.size()-2));
  }

  public static Long part2(String instructions, Function<Long, Long> worryFun, int rounds) {
    List<Monkey> state = getMonkeyList(instructions);
    ArrayList<Long> inspections = countInspections(worryFun, rounds, state);
    var sorted = inspections.stream().sorted().toList();
    return sorted.get(inspections.size()-1) * sorted.get(inspections.size()-2);
  }

  public static void main(String[] args){
        try {
            InputStream i = Day11.class.getClassLoader().getResourceAsStream("2022/day11.txt");
            var l = (long) (19 * 2 * 13 * 5 * 7 * 11 * 17* 3);
              String instructions = new String(i.readAllBytes());
            System.out.println("Part1: " + part1(
                instructions,
                (x) -> (long) Math.floor(x/3),
                20));
            System.out.println("Part2: " + part2(
                instructions,
                (x) -> x % l,
                10000
            ));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
