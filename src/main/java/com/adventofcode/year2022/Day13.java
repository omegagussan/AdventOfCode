package com.adventofcode.year2022;

import com.google.common.collect.Streams;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.javatuples.Pair;

public class Day13 {

  public static final String ROW_DELIMITER = "\n";
  public static final String START_ARRAY = "[";
  public static final String END_ARRAY = "]";
  public static final String ARRAY_DELIMITER = ",";

  private static boolean isNumber(String s) {
    return !s.startsWith(START_ARRAY) && !s.endsWith(END_ARRAY);
  }

  private static boolean isArray(String s) {
    return s.startsWith(START_ARRAY) && s.endsWith(END_ARRAY);
  }

  public static int part1(String instructions) {
    List<Integer> noIntervention = new ArrayList<>();
    var packets = instructions.split(ROW_DELIMITER + ROW_DELIMITER);
    IntStream.range(0, packets.length)
        .mapToObj(i -> new Pair<>(i + 1, packets[i]))
        .forEach(
            p -> {
              var left = p.getValue1().split(ROW_DELIMITER)[0];
              var right = p.getValue1().split(ROW_DELIMITER)[1];
              if (isCorrect(left, right) > 0) {
                noIntervention.add(p.getValue0());
              }
            });

    return noIntervention.stream().mapToInt(Integer::intValue).sum();
  }

  static int isCorrect(String left, String right) {
    if (isNumber(left) && isNumber(right)) {
      int leftInt = Integer.parseInt(left.replaceAll("[^0-9]", ""));
      int rightInt = Integer.parseInt(right.replaceAll("[^0-9]", ""));
      return rightInt - leftInt;
    } else if (isArray(left) && isNumber(right)) {
      return isCorrect(left, String.format("[%s]", right));
    } else if (isNumber(left) && isArray(right)) {
      return isCorrect(String.format("[%s]", left), right);
    } else if (isArray(left) && isArray(right)) {
      var leftL = parseStringArrays(left.substring(1, left.length() - 1));
      var rightL = parseStringArrays(right.substring(1, right.length() - 1));

      Optional<Integer> x =
          Streams.zip(leftL.stream(), rightL.stream(), Pair::new)
              .map(comp -> isCorrect(comp.getValue0(), comp.getValue1()))
              .filter(integer -> integer != 0)
              .findFirst();

      return x.orElseGet(() -> rightL.size() - leftL.size());
    }
    throw new RuntimeException("what is this fool!?");
  }

  static List<String> parseStringArrays(String s) {
    var sb = new StringBuilder();
    int depth = 0;
    ArrayList<String> result = new ArrayList<>();
    for (String l : s.split("")) {
      if (START_ARRAY.equals(l)) {
        depth += 1;
        sb.append(l);
      } else if (END_ARRAY.equals(l)) {
        depth -= 1;
        sb.append(l);
      } else if (ARRAY_DELIMITER.equals(l) && depth == 0 && sb.length() > 0) {
        result.add(sb.toString());
        sb = new StringBuilder();
      } else if (depth > 0) {
        sb.append(l);
      } else if (!ARRAY_DELIMITER.equals(l)) {
        sb.append(l);
      }
    }
    if (sb.length() > 0) {
      result.add(sb.toString());
    }
    return result;
  }

  public static int part2(String instructions) {
    var packets = Arrays.stream(instructions.split(ROW_DELIMITER)).filter(s -> !s.isEmpty());
    String hardCoded1 = "[[2]]";
    String hardCoded2 = "[[6]]";
    var arrayLizt =
        new ArrayList<>(Stream.concat(packets, Stream.of(hardCoded1, hardCoded2)).toList());
    arrayLizt.sort((a, b) -> (int) Math.signum(isCorrect(a, b)));
    Collections.reverse(arrayLizt);

    return (arrayLizt.indexOf(hardCoded1) + 1) * (arrayLizt.indexOf(hardCoded2) + 1);
  }

  public static void main(String[] args) {
    try {
      InputStream i = Day13.class.getClassLoader().getResourceAsStream("2022/day13.txt");
      String instructions = new String(i.readAllBytes());
      System.out.println("Part1: " + part1(instructions));
      System.out.println("Part2: " + part2(instructions));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
