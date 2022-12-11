
package com.adventofcode.year2022;
import com.google.common.collect.Collections2;
import java.io.IOException;
import java.io.InputStream;
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

    public static int part1(String instructions) throws IOException {
      LambdaFactory lambdaFactory = LambdaFactory.get();
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
                .map(s -> s.substring(13))
                .map(s -> s.replace("old", "x").replace("=", "->").replace("new", "(x)"))
                .map(s -> {
                    try {
                        return lambdaFactory.createLambda(
                            s, new TypeReference<Function<Integer, Integer>>(){});
                    } catch (LambdaCreationException e) {
                        throw new RuntimeException(e);
                    }
                }).findFirst().get();

            var test = Arrays.stream(m.split(ROW_DELIMITER))
                .filter(s -> s.contains("Test:"))
                .map(s -> s.substring(7))
                .map(s -> "(x) -> x" + s.replace("divisible by", "%") + " == 0")
                .map(s -> {
                    try {
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

      var inspections = new ArrayList<>(Collections.nCopies(state.size(), 0));

      for (int round = 0; round < 20; round ++){
          for (Monkey m : state){
              var itemCopy = new ArrayList<>(m.items);
              for (Integer item: itemCopy){
                  inspections.set(m.id, inspections.get(m.id) + 1);
                  m.items.remove(Integer.valueOf(item));
                  item = m.op.apply(item);
                  item = (int) Math.floor(item / 3);
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

    @NotNull
    private static Integer getTarget(String m, String matcher) {
        return Arrays.stream(m.split(ROW_DELIMITER))
            .filter(s1 -> s1.contains(String.format("If %s: throw to monkey ", matcher)))
            .map(s1 -> Integer.parseInt(s1.replaceAll("[^0-9]", "")))
            .findFirst().get();
    }

    public static String part2(String instructions){
        return "";
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
