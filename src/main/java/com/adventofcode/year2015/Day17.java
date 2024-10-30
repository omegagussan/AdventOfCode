package com.adventofcode.year2015;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import static com.adventofcode.utils.ArrayUtilz.combinatorics;

public class Day17 {
    public static void main(String[] args) {
        try {
            InputStream i = Day17.class.getClassLoader().getResourceAsStream("2015/day17.txt");
            Scanner s = new Scanner(i).useDelimiter(System.lineSeparator());
            List<Integer> instructions = new ArrayList<>();
            while (s.hasNextLine()) {
                instructions.add(Integer.valueOf(s.next()));
            }
            System.out.println(part1(instructions, 150));
        } catch (Exception e) {
            System.err.println("Something went poorly");
            e.printStackTrace();
        }
    }

    public static int part1(List<Integer> instructions, int target) {
        Map<Integer, Long> ocurrances = instructions.stream().collect(Collectors.groupingBy(integer -> integer, Collectors.counting()));
        int j = 0;
        var c = combinatorics(instructions);
        for (List<Integer> l : c) {
            if (l.stream().mapToInt(Integer::intValue).sum() == target) {
                Long reduce = l.stream().map(i -> {
                    long totalOccurances = ocurrances.get(i);
                    long occurancesInSolution = l.stream().filter(integer -> integer.longValue() == i).count();
                    return totalOccurances + 1 - occurancesInSolution;
                }).reduce(1L, (a, b) -> a * b);
                j += reduce;
            }
        }
        return j;
    }
}
