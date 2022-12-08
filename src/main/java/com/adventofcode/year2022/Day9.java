package com.adventofcode.year2022;

import java.io.InputStream;

public class Day9 {

    public static final String ROW_DELIMITER = "\n";

    public static int part2(String instructions) {
        return 3;
    }


    public static int part1(String instructions) {
        return 2;
    }

    public static void main(String[] args){
        try {
            InputStream i = Day9.class.getClassLoader().getResourceAsStream("2022/day9.txt");
            String instructions = new String(i.readAllBytes());
            System.out.println("Part1: " + part1(instructions));
            System.out.println("Part2: " + part2(instructions));
        } catch (Exception e){
            System.err.println("Something went poorly: " + e.getCause());
        }
    }
}
