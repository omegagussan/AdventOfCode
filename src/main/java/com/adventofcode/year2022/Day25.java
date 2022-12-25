package com.adventofcode.year2022;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

public class Day25 {

  public static final String ROW_DELIMITER = "\n";
  public static final long BASE = 5;
  public static final int HIGHEST_TOKEN_VALUE = 2;

  static long fromSNAFU(String string){
    long sum = 0;
    long baseMultiplication = 0;
    Stack<String> stack = Arrays.stream(string.split("")).collect(Collectors.toCollection(Stack::new));
    while(stack.size() > 0){
      var elem = stack.pop();
      long multiBase = (long) Math.pow(BASE, baseMultiplication);
      sum += decode(elem, multiBase);
      baseMultiplication += 1;
    }
    return sum;
  }

  private static long decode(String elem, long factor) {
    return switch (elem) {
      case "=" -> Math.multiplyExact(-2L , factor);
      case "-" -> -factor;
      case "1" -> factor;
      case "2" -> Math.multiplyExact(2L , factor);
      case "0" -> 0;
      default -> throw new IllegalStateException("Unexpected value: " + elem);
    };
  }


  static String toSNAFU(long decimal){
    var state = new ArrayList<Long>();
    while (decimal > 0){
      state.add(decimal % BASE);
      decimal = Math.floorDiv(decimal, BASE);
    }
    state.add(0L);
    Collections.reverse(state);
    while (state.stream().anyMatch(integer -> integer > HIGHEST_TOKEN_VALUE)){
      IntStream.range(0, state.size()).forEach(i -> {
        if (state.get(i) > HIGHEST_TOKEN_VALUE){
          state.set(i-1, state.get(i-1) + 1);
          state.set(i, state.get(i) - BASE);
        }
      });
    }
    String str = state.stream().map(Day25::encode).reduce("", (x, y) -> x + y);
    return !str.equals("0") ? StringUtils.stripStart(str, "0") : "0";
  };

  @NotNull
  private static String encode(long val) {
    return switch (String.valueOf(val)) {
      case "0" -> "0";
      case "1" -> "1";
      case "2" -> "2";
      case "-1" -> "-";
      case "-2" -> "=";
      default -> throw new IllegalStateException("Unexpected value: " + val);
    };
  }

  public static String part1(String instructions){
    var v = parseSNAFUSum(instructions);
    return toSNAFU(v);
  }

  static long parseSNAFUSum(String instructions) {
    return Arrays.stream(instructions.split(ROW_DELIMITER))
        .map(Day25::fromSNAFU).mapToLong(Long::valueOf).sum();
  }

  public static void main(String[] args) {
    try {
      InputStream i = Day25.class.getClassLoader().getResourceAsStream("2022/day25.txt");
      String instructions = new String(i.readAllBytes());
      System.out.println("Part1: " + part1(instructions));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
