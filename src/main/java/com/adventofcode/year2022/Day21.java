package com.adventofcode.year2022;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.NotImplementedException;
import org.javatuples.Triplet;
import org.jetbrains.annotations.NotNull;

public class Day21 {

  public static final String ROW_DELIMITER = "\n";
  public static final long MONKEY_VALUE_REPLACEMENT = Long.MIN_VALUE;
  public static final String HUMAN_IN_THE_LOOP = "humn";
  public static final String ROOT = "root";

  record Monkey(String name, String expression, Optional<Long> value) {
    public String getReplacementExpression() {
      if (this.name().equals(HUMAN_IN_THE_LOOP)) {
        return "x";
      }
      return this.value().get().equals(MONKEY_VALUE_REPLACEMENT)
          ? expression
          : value.get().toString();
    }
  }

  @NotNull
  static Stream<Monkey> parseInput(String instructions) {
    return Arrays.stream(instructions.split(ROW_DELIMITER))
        .map(
            row -> {
              var arr = row.split(":");
              String trimedExpression = arr[1].trim();
              Optional<Long> value =
                  Optional.of(trimedExpression)
                      .filter(str -> str.trim().matches("\\d+"))
                      .map(Long::parseLong);
              return new Monkey(arr[0], trimedExpression, value);
            });
  }

  public static Optional<Long> maths(Long A, Long B, String expression) {
    if (expression.contains("+")) {
      return Optional.of(A + B);
    } else if (expression.contains("*")) {
      return Optional.of(Math.multiplyExact(A, B));
    } else if (expression.contains("/")) {
      return Optional.of(A / B);
    } else if (expression.contains("-")) {
      return Optional.of(A - B);
    }
    throw new NotImplementedException(expression);
  }

  public static Long oppositeMath(long HL, String expression) {
    long v = Long.parseLong(expression.replaceAll("[^0-9]", ""));
    if (expression.contains("+")) {
      return Math.addExact(HL, -v);
    } else if (expression.contains("*")) {
      return HL / v;
    } else if (expression.startsWith("/")) {
      return Math.multiplyExact(HL, v);
    } else if (expression.contains("/")) {
      return v / HL;
    } else if (expression.startsWith("-")) {
      return Math.addExact(HL, v);
    } else if (expression.contains("-")) {
      return Math.addExact(-HL, v);
    }
    throw new NotImplementedException(expression);
  }

  public static String buildReplacementExpression(String A, String B, String expression) {
    if (expression.contains("+")) {
      return "(" + A + "+" + B + ")";
    } else if (expression.contains("*")) {
      return "(" + A + "*" + B + ")";
    } else if (expression.contains("/")) {
      return "(" + A + "/" + B + ")";
    } else if (expression.contains("-")) {
      return "(" + A + "-" + B + ")";
    }
    throw new NotImplementedException(expression);
  }

  static Long solveForX(String equation) {
    Matcher matchLeft = Pattern.compile("^\\((.*)\\)(.*)$").matcher("");
    Matcher matchRight = Pattern.compile("^([0-9]*.)\\((.*)\\)$").matcher("");

    var split = equation.split("=");
    String LeftHandSide = split[0].substring(1, split[0].length() - 1);
    long RightHandSide = Long.parseLong(split[1]);

    matchLeft.reset(LeftHandSide).matches();
    matchRight.reset(LeftHandSide).matches();
    while (matchLeft.matches() || matchRight.matches()) {
      if (matchLeft.matches()) {
        LeftHandSide = matchLeft.group(1);
        RightHandSide = oppositeMath(RightHandSide, matchLeft.group(2));
      } else {
        LeftHandSide = matchRight.group(2);
        RightHandSide = oppositeMath(RightHandSide, matchRight.group(1));
      }
      matchLeft.reset(LeftHandSide).matches();
      matchRight.reset(LeftHandSide).matches();
    }
    // obs we only support operations to the form 3/x=k or 3-x=k for the last entry
    return oppositeMath(RightHandSide, LeftHandSide);
  }

  @NotNull
  private static Monkey getRootAnswer(Map<String, Monkey> monkeys, Consumer<Triplet<Monkey, Monkey, Monkey>> fun) {
    while (monkeys.get(ROOT).value().isEmpty()) {
      monkeys.values().stream()
          .filter(monkey -> monkey.value().isEmpty())
          .forEach(
              m -> {
                var termA = m.expression().substring(0, 4);
                var termB = m.expression().substring(m.expression().length() - 4);
                var monkeyA = monkeys.get(termA);
                var monkeyB = monkeys.get(termB);
                fun.accept(new Triplet<>(monkeyA, monkeyB, m));
              });
    }
    return monkeys.get(ROOT);
  }

  public static long part2(String instructions) {
    var monkeys =
        parseInput(instructions).collect(Collectors.toMap(monkey -> monkey.name, monkey -> monkey));

    var root = monkeys.get(ROOT);
    monkeys.put(
        root.name(),
        new Monkey(root.name(), root.expression().replace("+", "="), Optional.empty()));

    var humn = monkeys.get(HUMAN_IN_THE_LOOP);
    monkeys.put(
        humn.name(),
        new Monkey(humn.name(), humn.expression(), Optional.of(MONKEY_VALUE_REPLACEMENT)));

    Set<String> expressionMonkeys = new HashSet<>(List.of(humn.name()));

    Consumer<Triplet<Monkey, Monkey, Monkey>> fun = (triplet) -> {
      Monkey monkeyA = triplet.getValue0();
      Monkey monkeyB = triplet.getValue1();
      Monkey m = triplet.getValue2();
      if (m.name().equals(ROOT)
          && monkeyA.value().isPresent()
          && monkeyB.value().isPresent()) {
        var s =
            monkeyA.getReplacementExpression() + "=" + monkeyB.getReplacementExpression();
        monkeys.put(
            m.name(), new Monkey(m.name(), s, Optional.of(MONKEY_VALUE_REPLACEMENT)));
        expressionMonkeys.add(m.name());
      } else if ((expressionMonkeys.contains(monkeyA.name())
          && monkeyB.value().isPresent())
          || (expressionMonkeys.contains(monkeyB.name())
          && monkeyA.value().isPresent())) {
        var e =
            buildReplacementExpression(
                monkeyA.getReplacementExpression(),
                monkeyB.getReplacementExpression(),
                m.expression());
        monkeys.put(
            m.name(), new Monkey(m.name(), e, Optional.of(MONKEY_VALUE_REPLACEMENT)));
        expressionMonkeys.add(m.name());
      } else if (monkeyA.value().isPresent() && monkeyB.value().isPresent()) {
        var value = maths(monkeyA.value().get(), monkeyB.value().get(), m.expression());
        monkeys.put(m.name(), new Monkey(m.name(), m.expression(), value));
      }
    };

    return solveForX(getRootAnswer(monkeys, fun).expression());
  }

  public static long part1(String instructions) {
    var monkeys =
        parseInput(instructions).collect(Collectors.toMap(monkey -> monkey.name, monkey -> monkey));

    Consumer<Triplet<Monkey, Monkey, Monkey>> fun = (triplet) -> {
      Monkey monkeyA = triplet.getValue0();
      Monkey monkeyB = triplet.getValue1();
      Monkey m = triplet.getValue2();
      if (monkeyA.value().isPresent() && monkeyB.value().isPresent()) {
        var value = maths(monkeyA.value().get(), monkeyB.value().get(), m.expression());
        monkeys.put(m.name(), new Monkey(m.name(), m.expression(), value));
      }
    };
    return getRootAnswer(monkeys, fun).value().get();
  }

  public static void main(String[] args) {
    try {
      InputStream i = Day21.class.getClassLoader().getResourceAsStream("2022/day21.txt");
      String instructions = new String(i.readAllBytes());
      System.out.println("Part1: " + part1(instructions));
      System.out.println("Part2: " + part2(instructions));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
