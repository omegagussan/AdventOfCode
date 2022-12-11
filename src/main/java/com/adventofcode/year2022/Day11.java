
package com.adventofcode.year2022;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
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
    ){

    }

    @NotNull
    static Integer getTarget(String m, String matcher) {
        return Arrays.stream(m.split(ROW_DELIMITER))
            .filter(s1 -> s1.contains(String.format("If %s: throw to monkey ", matcher)))
            .map(s1 -> Integer.parseInt(s1.replaceAll("[^0-9]", "")))
            .findFirst().get();
    }

  private static List<Monkey> getMonkeyList(String instructions, LambdaFactory lambdaFactory) {
    AtomicInteger i = new AtomicInteger();

    var state = Arrays.stream(instructions.split(ROW_DELIMITER+ROW_DELIMITER)).map(m -> {
      var items = Arrays.stream(m.split(ROW_DELIMITER))
          .filter(s -> s.contains("Starting items:"))
          .map(s -> s.substring(18))
          .flatMap(s -> Arrays.stream(s.split(",")))
          .map(String::trim)
          .mapToInt(Integer::valueOf)
          .boxed()
          .toList();

      var operation = Arrays.stream(m.split(ROW_DELIMITER))
          .filter(s -> s.contains("Operation:"))
          .map(s -> s.substring(12))
          .map(s -> s.trim().replace("old", "x").replace("=", "->").replace("new", "(x)"))
          .map(s -> {
            try {
              System.out.println("op:" + s);
              return lambdaFactory.createLambda(
                  s, new TypeReference<Function<Integer, Integer>>(){});
            } catch (LambdaCreationException e) {
              throw new RuntimeException(e);
            }
          }).findFirst().get();

      var test = Arrays.stream(m.split(ROW_DELIMITER))
          .filter(s -> s.contains("Test:"))
          .map(s -> s.substring(8).trim())
          .map(s -> "(x) -> x " + s.replace("divisible by", "%") + " == 0")
          .map(s -> {
            try {
              System.out.println("test:" + s);
              return lambdaFactory.createLambda(
                  s, new TypeReference<Function<Integer, Boolean>>(){});
            } catch (LambdaCreationException e) {
              throw new RuntimeException(e);
            }
          })
          .findFirst().get();
      var trueTarget = getTarget(m, "true");
      var falseTarget = getTarget(m, "false");
      return new Monkey(i.getAndIncrement(), new ArrayList<>(items), operation, test, trueTarget, falseTarget);
    }).toList();
    return state;
  }

  public static int part1(String instructions) throws IOException {
    LambdaFactory lambdaFactory = LambdaFactory.get();
    List<Monkey> state = getMonkeyList(instructions,
        lambdaFactory);

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

    LambdaFactory lambdaFactory = LambdaFactory.get();
    List<Monkey> state = getMonkeyList(instructions,
        lambdaFactory);

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
