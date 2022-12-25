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

  static long fromSNAFU(String string){
    long res = 0;
    long mult = 0;
    Stack<String> aa = Arrays.stream(string.split("")).collect(Collectors.toCollection(Stack::new));
    while(aa.size() > 0){
      var elem = aa.pop();
      long a = (long) Math.pow(BASE, mult);
      switch (elem) {
        case "=" -> {
          res += Math.multiplyExact(-2L , a);
        }
        case "-" -> {
          res += -a;
        }
        case "1" -> {
          res += a;
        }
        case "2" -> {
          res += Math.multiplyExact(2L , a);
        }
      }
      mult += 1;
    }
    return res;
  }


  static String toSNAFU(long decimal){
    var a = new ArrayList<Long>();
    while (decimal > 0){
      a.add(decimal % BASE);
      decimal = Math.floorDiv(decimal, BASE);
    }
    a.add(0L);
    Collections.reverse(a);
    while (a.stream().anyMatch(integer -> integer > 2)){
      IntStream.range(0, a.size()).forEach(i -> {
        if (a.get(i) > 2){
          a.set(i-1, a.get(i-1) + 1);
          a.set(i, a.get(i) - 5);
        }
      });
    }
    String str = a.stream().map(Day25::encode).reduce("", (x, y) -> x + y);
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

  public static Integer part2(String instructions){
    return 1;
  }

  public static void main(String[] args) {
    try {
      InputStream i = Day25.class.getClassLoader().getResourceAsStream("2022/day25.txt");
      String instructions = new String(i.readAllBytes());
      System.out.println("Part1: " + part1(instructions));
      System.out.println("Part2: " + part2(instructions));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
