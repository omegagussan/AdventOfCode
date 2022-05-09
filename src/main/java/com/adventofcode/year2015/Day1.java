package com.adventofcode.year2015;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Day1{

    public static final String UP = "(";
    public static final String DOWN = ")";

    //what floor after all instructions
    public static int part1(String instructions) {
        Map<String, Long> occurrences = Arrays.stream(instructions.split("")).collect(Collectors.groupingBy(s -> s, Collectors.counting()));
        return (int) (occurrences.getOrDefault(UP, 0L)-occurrences.getOrDefault(DOWN, 0L));
    }

    //when enter basement
    public static int part2(String instructions) {
        int floor = 0;
        int instruction = 0;
        for (String i :  instructions.split("")){
            if (floor < 0) break;
            int operation = i.equals(UP) ? 1 : -1;
            floor += operation;
            instruction += 1;
        }

        return instruction;
    }

    public static void main(String[] args){
        try {
            InputStream i = Day1.class.getClassLoader().getResourceAsStream("day1.txt");
            String instructions = new String(i.readAllBytes());
            System.out.println("Part1: " + part1(instructions));
            System.out.println("Part2: " + part2(instructions));
        } catch (Exception e){
            System.err.println("Something went poorly: " + e.getCause());
        }
    }
}
