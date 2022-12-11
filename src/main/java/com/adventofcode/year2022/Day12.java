package com.adventofcode.year2022;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;

public class Day12 {

    public static final String ELF_DELIMITER = "\n\n";
    public static final String ROW_DELIMITER = "\n";

    public static int part1(String instructions) {
        var elfLevel = Arrays.stream(instructions.split(ELF_DELIMITER));
        var elfSums = elfLevel.map(Day12::getElfSum);
        return elfSums.max(Comparator.naturalOrder()).get();
    }

    private static int getElfSum(String s) {
        return Arrays.stream(s.split(ROW_DELIMITER)).mapToInt(Integer::valueOf).sum();
    }

    public static int part2(String instructions) {
        var elfLevel = Arrays.stream(instructions.split(ELF_DELIMITER));
        var elfSumsSorted = elfLevel.map(Day12::getElfSum).sorted(Comparator.reverseOrder());
        return elfSumsSorted.limit(3).mapToInt(Integer::valueOf).sum();
    }

    public static void main(String[] args){
        try {
            InputStream i = Day12.class.getClassLoader().getResourceAsStream("2022/day1.txt");
            String instructions = new String(i.readAllBytes());
            System.out.println("Part1: " + part1(instructions));
            System.out.println("Part2: " + part2(instructions));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
