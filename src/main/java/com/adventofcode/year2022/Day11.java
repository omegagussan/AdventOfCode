
package com.adventofcode.year2022;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;
import pl.joegreen.lambdaFromString.LambdaCreationException;
import pl.joegreen.lambdaFromString.LambdaFactory;
import pl.joegreen.lambdaFromString.TypeReference;

public class Day11 {
    public static final String ROW_DELIMITER = "\n";

    record Monkey(
        Integer id,
        List<Integer> items,
        Function<Integer, Integer> op,
        Function<Integer, Boolean> test,
        Integer trueTarget,
        Integer falseTarget
    ){}

  @NotNull
  static Integer getTarget(String m, String matcher) {
      return Arrays.stream(m.split(ROW_DELIMITER))
          .filter(s1 -> s1.contains(String.format("If %s: throw to monkey ", matcher)))
          .map(s1 -> Integer.parseInt(s1.replaceAll("[^0-9]", "")))
          .findFirst().get();
  }

  @NotNull
  static Function<Integer, Integer> parseOperation(String instructions) {
    LambdaFactory lambdaFactory = LambdaFactory.get();

    return Arrays.stream(instructions.split(ROW_DELIMITER))
        .filter(s -> s.contains("Operation:"))
        .map(s -> s.substring(12))
        .map(s -> s.trim().replace("old", "x").replace("=", "->").replace("new", "(x)"))
        .map(s -> {
          try {
            return lambdaFactory.createLambda(s, new TypeReference<Function<Integer, Integer>>() {});
          } catch (LambdaCreationException e) {
            throw new RuntimeException(e);
          }
        }).findFirst().get();
  }

  @NotNull
  static ArrayList<Integer> parseItems(String instructions) {
    return new ArrayList<>(Arrays.stream(instructions.split(ROW_DELIMITER))
        .filter(s -> s.contains("Starting items:"))
        .map(s -> s.substring(18))
        .flatMap(s -> Arrays.stream(s.split(",")))
        .map(String::trim)
        .mapToInt(Integer::valueOf)
        .boxed()
        .toList());
  }

  @NotNull
  static Function<Integer, Boolean> getTestFn(String instructions) {
    LambdaFactory lambdaFactory = LambdaFactory.get();
    return Arrays.stream(instructions.split(ROW_DELIMITER))
        .filter(s -> s.contains("Test:"))
        .map(s -> s.substring(8).trim())
        .map(s -> "(x) -> x " + s.replace("divisible by", "%") + " == 0")
        .map(s -> {
          try {
            return lambdaFactory.createLambda(s, new TypeReference<Function<Integer, Boolean>>() {});
          } catch (LambdaCreationException e) {
            throw new RuntimeException(e);
          }
        })
        .findFirst().get();
  }

  static List<Monkey> getMonkeyList(String instructions) {
    String[] rows = instructions.split(ROW_DELIMITER + ROW_DELIMITER);
    return IntStream.range(0, rows.length).mapToObj(i -> new Pair<>(i, rows[i])).map(pair -> new Monkey(
          pair.getValue0(),
          parseItems(pair.getValue1()),
          parseOperation(pair.getValue1()),
          getTestFn(pair.getValue1()),
          getTarget(pair.getValue1(), "true"),
          getTarget(pair.getValue1(), "false")
      )).toList();
  }

  public static int part1(String instructions) throws IOException {
    List<Monkey> state = getMonkeyList(instructions);

    var inspections = new ArrayList<>(Collections.nCopies(state.size(), 0));

    for (int round = 0; round < 20; round ++){
      for (Monkey m : state){
        var itemCopy = new ArrayList<>(m.items);
        for (Integer item: itemCopy){
          inspections.set(m.id, inspections.get(m.id) + 1);
          m.items.remove(Integer.valueOf(item));
          item = m.op.apply(item);
          item = Math.floorDiv(item, 3);
          if (m.test.apply(item)){
            state.get(m.trueTarget).items.add(item);
          } else {
            state.get(m.falseTarget).items.add(item);
          }
        }
      }
    }
    var sorted = inspections.stream().sorted().toList();
    return sorted.get(inspections.size()-1) * sorted.get(inspections.size()-2);
  }

  public static BigInteger part2(String instructions) throws IOException {
    //int commonMod = 19 * 2 * 13 * 5 * 7 * 11; //prod
    int commonMod = 23 * 13 * 19 * 17; //test

    List<Monkey> state = getMonkeyList(instructions);

    var inspections = new ArrayList<>(Collections.nCopies(state.size(), 0));

    for (int round = 0; round < 20; round ++){
      for (Monkey currentMonkey : state){
        inspections.set(currentMonkey.id, inspections.get(currentMonkey.id) + currentMonkey.items.size());
        var itemCopy = new ArrayList<>(currentMonkey.items);
        for (Integer item: itemCopy){
          currentMonkey.items.remove(item);
          item = currentMonkey.op.apply(item);
          item = item % commonMod;
          if (currentMonkey.test.apply(item)){
            assert !currentMonkey.id.equals(currentMonkey.trueTarget);
            state.get(currentMonkey.trueTarget).items.add(item);
          } else {
            assert !currentMonkey.id.equals(currentMonkey.falseTarget);
            state.get(currentMonkey.falseTarget).items.add(item);
          }
        }
      }
    }
    var sorted = inspections.stream().sorted().toList();
    return new BigInteger(sorted.get(inspections.size()-1).toString()).multiply(new BigInteger(sorted.get(inspections.size()-2).toString()));
  }

  public static void main(String[] args){
        try {
            InputStream i = Day11.class.getClassLoader().getResourceAsStream("2022/day11.txt");
            String instructions = new String(i.readAllBytes());
            System.out.println("Part1: " + part1(instructions));
            System.out.println("Part2: ");
            System.out.println(part2(instructions));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
