package com.adventofcode.year2022;

import java.io.InputStream;
import java.sql.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Day5 {

    public static final String NEWLINE = "\n";

    record ParsedShift(int source, int target, int argument){}

    private static ParsedShift getParsedShift(String s) {
        var importantNumbers = Arrays.stream(
            s.replaceAll("[^0-9]", ",").replaceAll(",+", ",").substring(1).split(",")).mapToInt(Integer::valueOf).boxed().toList();
        return new ParsedShift(importantNumbers.get(1), importantNumbers.get(2), importantNumbers.get(0));
    }

    private static String solution(String instructions, Map<Integer, ArrayDeque<String>> state, boolean isPart2) {
        var stacksAndShifts = instructions.split(NEWLINE + NEWLINE);
        Arrays.stream(stacksAndShifts[1].split(NEWLINE)).forEach(s -> {
            ParsedShift p = getParsedShift(s);
            List<String> tmp = new ArrayList<>(IntStream.range(0, p.argument).mapToObj(ignored -> state.get(p.source).pop()).toList());
            if (isPart2){Collections.reverse(tmp);}
            tmp.forEach(w -> state.get(p.target).addFirst(w));
        });
        return String.join("", state.keySet().stream().sorted().map(integer -> state.get(integer).getFirst()).toList());
    }

    public static String part1(String instructions, Map<Integer, ArrayDeque<String>> state) {
        return solution(instructions, state, false);
    }

    public static String part2(String instructions, Map<Integer, ArrayDeque<String>> state) {
        return solution(instructions, state, true);
    }

    public static void main(String[] args){
        try {
            InputStream i = Day5.class.getClassLoader().getResourceAsStream("2022/day5.txt");

            String instructions = new String(i.readAllBytes());
            System.out.println("Part1: " + part1(instructions, getManuallyCraftedInputState()));
            System.out.println("Part2: " + part2(instructions, getManuallyCraftedInputState()));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static Map<Integer, ArrayDeque<String>> getManuallyCraftedInputState() {
        return Map.of(
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
    }
}
