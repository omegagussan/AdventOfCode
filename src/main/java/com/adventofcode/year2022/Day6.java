package com.adventofcode.year2022;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;
import org.javatuples.Pair;

public class Day6 {
    public static int part1(String instructions) {
        return solution(instructions, 4);
    }

    public static int part2(String instructions) {
        return solution(instructions, 14);
    }

    private static int solution(String instructions, int messageLength) {
        return IntStream.range(0, instructions.length()- messageLength + 1)
            .mapToObj(i -> new Pair<>(i, setOfSubset(instructions, messageLength, i)))
            .filter(p -> p.getValue1().size() == messageLength).map(p -> p.getValue0() + messageLength).findFirst().orElseThrow(IllegalStateException::new);
    }

    private static HashSet<String> setOfSubset(String instructions, int messageLength, int i) {
        return new HashSet<>(
            Arrays.stream(instructions.substring(i, i + messageLength).split("")).toList());
    }

    public static void main(String[] args){
        try {
            InputStream i = Day6.class.getClassLoader().getResourceAsStream("2022/day6.txt");
            String instructions = new String(i.readAllBytes());
            System.out.println("Part1: " + part1(instructions));
            System.out.println("Part2: " + part2(instructions));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
