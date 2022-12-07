package com.adventofcode.year2022;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class Day6 {
    public static int part1(String instructions) {
        return solution(instructions, 4);
    }

    public static int part2(String instructions) {
        return solution(instructions, 14);
    }

    record Pair(Integer startOfSet, Set setOfChar){
        int endOfSet(int messageLength){
            return this.startOfSet + messageLength;
        }
    }

    private static int solution(String instructions, int messageLength) {
        return IntStream.range(0, instructions.length()- messageLength + 1)
            .mapToObj(i -> new Pair(i, setOfSubset(instructions, messageLength, i)))
            .filter(p -> p.setOfChar.size() == messageLength).findFirst().orElseThrow(IllegalStateException::new).endOfSet(messageLength);
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
