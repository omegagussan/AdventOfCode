package com.adventofcode.year2022;

import java.io.InputStream;
import java.util.Arrays;

public class Day2 {
    public static final String ROW_DELIMITER = "\n";

    public static int part1(String instructions) {
        return Arrays.stream(instructions.split(ROW_DELIMITER)).toList().stream().map(row -> switch (row){
        case "A X" -> 3 + 1;
        case "A Y" -> 6 + 2;
        case "A Z" -> 0 + 3;

        case "B X" -> 0 + 1;
        case "B Y" -> 3 + 2;
        case "B Z" -> 6 + 3;

        case "C X" -> 6 + 1;
        case "C Y" -> 0 + 2;
        case "C Z" -> 3 + 3;
        default -> throw new IllegalStateException("This cant happen!");
    }).mapToInt(Integer::valueOf).sum();
    }

    public static int part2(String instructions) {
        return Arrays.stream(instructions.split(ROW_DELIMITER)).toList().stream().map(row -> switch (row){
            case "A X" -> 0 + 3;
            case "A Y" -> 3 + 1;
            case "A Z" -> 6 + 2;

            case "B X" -> 0 + 1;
            case "B Y" -> 3 + 2;
            case "B Z" -> 6 + 3;

            case "C X" -> 0 + 2;
            case "C Y" -> 3 + 3;
            case "C Z" -> 6 + 1;
            default -> throw new IllegalStateException("This cant happen!");
        }).mapToInt(Integer::valueOf).sum();
    }

    public static void main(String[] args){
        try {
            InputStream i = Day2.class.getClassLoader().getResourceAsStream("2022/day2.txt");
            String instructions = new String(i.readAllBytes());
            System.out.println("Part1: " + part1(instructions));
            System.out.println("Part2: " + part2(instructions));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
