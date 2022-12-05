package com.adventofcode.year2022;

import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Day5 {

    public static final String NEWLINE = "\n";

    public static String part2(String instructions, Map<Integer, ArrayDeque<String>> state) {
        return "3";
    }

    public static String part1(String instructions, Map<Integer, ArrayDeque<String>> state) {
        var stacksAndShifts = instructions.split(NEWLINE + NEWLINE);
        Arrays.stream(stacksAndShifts[1].split(NEWLINE)).forEach(s -> {
            var importantNumbers = Arrays.stream(s.replaceAll("[^0-9]", ",").replaceAll(",+", ",").substring(1).split(",")).mapToInt(Integer::valueOf).boxed().toList();
            var source = importantNumbers.get(1);
            var target = importantNumbers.get(2);
            var argument = importantNumbers.get(0);

            List<String> tmp = new java.util.ArrayList<>();
            for (int i=0; i < argument; i++){
                tmp.add(state.get(source).pop());
            }
            tmp.forEach(w -> {
                state.get(target).addFirst(w);
            });
        });
        return String.join("", state.keySet().stream().sorted().map(integer -> state.get(integer).getFirst()).toList());
    }

    public static void main(String[] args){
        try {
            InputStream i = Day5.class.getClassLoader().getResourceAsStream("2022/day5.txt");
            var state = Map.of(
                1, new ArrayDeque<>(List.of("C", "S", "G", "B")),
                2, new ArrayDeque<>(List.of("G", "V", "N", "J", "H", "W", "M", "T")),
                3, new ArrayDeque<>(List.of("S", "Q", "M")),
                4, new ArrayDeque<>(List.of("M", "N", "W", "T", "L", "S", "B")),
                5, new ArrayDeque<>(List.of("P", "W", "G", "V", "T", "F", "Z", "J")),
                6, new ArrayDeque<>(List.of("S", "H", "Q", "G", "B", "T", "C")),
                7, new ArrayDeque<>(List.of("W", "B", "P", "J", "T")),
                8, new ArrayDeque<>(List.of("M", "Q", "T", "F", "Z", "C", "D", "G")),
                9, new ArrayDeque<>(List.of("F", "P", "B", "H", "S", "N"))
            );

            String instructions = new String(i.readAllBytes());
            System.out.println("Part1: " + part1(instructions, state));
            System.out.println("Part2: " + part2(instructions, state));
        } catch (Exception e){
            System.err.println("Something went poorly: " + e.getCause());
        }
    }
}
