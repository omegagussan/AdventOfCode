package com.adventofcode.year2022;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day4 {

    public static final String NEWLINE = "\n";

    public static int part1(String instructions) {
        return (int) Arrays.stream(instructions.split(NEWLINE)).filter(s -> {
            var ranges = s.split(",");
            var first = Arrays.stream(ranges[0].split("-")).mapToInt(Integer::valueOf).toArray();
            var second = Arrays.stream(ranges[1].split("-")).mapToInt(Integer::valueOf).toArray();
            if (first[1] - first[0] >= second[1] - second[0]){ //first is larger
                return isContained(first, second);
            }
            return isContained(second, first);
        }).count();
    }

    private static boolean isContained(int[] larger, int[] smaller) {
        return larger[1] >= smaller[1] && larger[0] <= smaller[0];
    }

    public static int part2(String instructions) {
        return (int) Arrays.stream(instructions.split(NEWLINE)).filter(s -> {
            var ranges = s.split(",");
            var first = Arrays.stream(ranges[0].split("-")).mapToInt(Integer::valueOf).toArray();
            var second = Arrays.stream(ranges[1].split("-")).mapToInt(Integer::valueOf).toArray();
            Set<Integer> secondSet = IntStream.range(second[0], second[1] + 1).boxed().collect(Collectors.toSet());
            Set<Integer> firstSet = IntStream.range(first[0], first[1] + 1).boxed().collect(Collectors.toSet());
            secondSet.retainAll(firstSet); //union
            return secondSet.size() > 0;
        }).count();
    }

    public static void main(String[] args){
        try {
            InputStream i = Day4.class.getClassLoader().getResourceAsStream("2022/day4.txt");
            String instructions = new String(i.readAllBytes());
            System.out.println("Part1: " + part1(instructions));
            System.out.println("Part2: " + part2(instructions));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
