package com.adventofcode.year2022;

import com.google.common.collect.Streams;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.javatuples.Pair;

public class Day13 {

    public static final String ROW_DELIMITER = "\n";

    private static boolean isNumber(String s) {
        s = s.trim();
        return !s.startsWith("[") && !s.endsWith("]");
    };

    private static boolean isArray(String s) {
        s = s.trim();
        return s.startsWith("[") && s.endsWith("]");
    };



    public static int part1(String instructions) {
        List<Integer> noIntervention = new ArrayList<>();
        var packets = instructions.split(ROW_DELIMITER+ROW_DELIMITER);
        IntStream.range(0, packets.length).mapToObj(i -> new Pair<>(i + 1, packets[i]))
            .forEach(p -> {
                var left = p.getValue1().split(ROW_DELIMITER)[0];
                var right = p.getValue1().split(ROW_DELIMITER)[1];
                if (isCorrect(left, right, true)){
                    noIntervention.add(p.getValue0());
                }
            });

        return noIntervention.stream().mapToInt(Integer::intValue).sum();
    }

    static boolean isCorrect(String left, String right) {
        return isCorrect(left, right, false);
    }

  static boolean isCorrect(String left, String right, boolean isOuter) {
    if ("[]".equals(left)) {
      return true;
    } else if ("[]".equals(right)) {
      return false;
    } else if (isNumber(left) && isNumber(right)) {
      int leftInt = Integer.parseInt(left.replaceAll("[^0-9]", ""));
      int rightInt = Integer.parseInt(right.replaceAll("[^0-9]", ""));
      return rightInt >= leftInt;
    } else if (isArray(left) && isNumber(right)) {
      return isCorrect(left, String.format("[%s]", right));
    } else if (isNumber(left) && isArray(right)) {
      return isCorrect(String.format("[%s]", left), right);
    } else if (isArray(left) && isArray(right)) {
      var leftL = parseStringArrays(left.substring(1, left.length() - 1));
      var rightL = parseStringArrays(right.substring(1, right.length() - 1));

      List<Boolean> x =
          Streams.zip(leftL.stream(), rightL.stream(), Pair::new)
              .map(comp -> isCorrect(comp.getValue0(), comp.getValue1()))
              .toList();

      if (x.stream().anyMatch(b -> !b)) {
        return false;
      }
      if (isOuter) {
        return rightL.size() >= leftL.size();
      }
      return true;
    }
    throw new RuntimeException("what dis? " + left + " | " + right);
}

    static List<String> parseStringArrays(String x) {
        StringBuilder stringBuilder = new StringBuilder();
        int isInside = 0;
        ArrayList<String> result = new ArrayList<>();
        for (String l : x.split("")){
            if ("[".equals(l)){
                isInside += 1;
                stringBuilder.append(l);
            } else if ("]".equals(l)){
                isInside = isInside -1;
                stringBuilder.append(l);
            } else if (",".equals(l) && isInside == 0 && stringBuilder.length() > 0){
                result.add(stringBuilder.toString());
                stringBuilder = new StringBuilder();
            } else if (isInside > 0){
                stringBuilder.append(l);
            } else if (!",".equals(l)){
                stringBuilder.append(l);
            }
        }
        if (stringBuilder.length() > 0){
            result.add(stringBuilder.toString());
        }
        return result;
    }

    public static int part2(String instructions) {
        return 3;
    }

    public static void main(String[] args){
        try {
            InputStream i = Day13.class.getClassLoader().getResourceAsStream("2022/day13.txt");
            String instructions = new String(i.readAllBytes());
            System.out.println("Part1: " + part1(instructions));
            System.out.println("Part2: " + part2(instructions));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
