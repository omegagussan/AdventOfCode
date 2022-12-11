package com.adventofcode.year2015;

import com.google.common.collect.Streams;

import java.io.InputStream;
import java.util.*;

public class Day3 {
    public static final String NORTH = "^";
    public static final String SOUTH = "v";
    public static final String EAST = ">";
    public static final String WEST = "<";

    record Pair(Integer horizontal, Integer vertical){};

    public static int part1(String instructions) {
        Map<Pair, Integer> counts = getCountsMap(Arrays.stream(instructions.split("")).toList());
        return counts.values().size();
    }

    private static Map<Pair, Integer> getCountsMap(List<String> instructions) {
        Map<Pair, Integer> counts = new HashMap<>();
        int horizontal = 0;
        int vertical = 0; //down
        incrementPosition(counts, horizontal, vertical);
        for (String i: instructions){
            switch (i) {
                case NORTH -> vertical += -1;
                case SOUTH -> vertical += 1;
                case EAST -> horizontal += 1;
                case WEST -> horizontal += -1;
                default -> throw new IllegalArgumentException("that's not a direction!");
            }
            incrementPosition(counts, horizontal, vertical);
        }
        return counts;
    }

    public static int part2(String instructions){
        List<String> even = getEvenOrOdd(instructions, 0);
        List<String> odd = getEvenOrOdd(instructions, 1);
        var santaMap = getCountsMap(even);
        var roboMap = getCountsMap(odd);
        santaMap.putAll(roboMap); //will overwrite duplicate visited by both.
        // Does not matter as we only care about unique.
        return santaMap.values().size();
    }

    private static List<String> getEvenOrOdd(String instructions, int remainder) {
        return Streams.mapWithIndex(Arrays.stream(instructions.split("")), AbstractMap.SimpleImmutableEntry::new)
                .filter(e -> e.getValue() % 2 == remainder)
                .map(AbstractMap.SimpleImmutableEntry::getKey)
                .toList();
    }

    private static void incrementPosition(Map<Pair, Integer> counts, int horizontal, int vertical) {
        counts.merge(new Pair(horizontal, vertical), 1, Integer::sum);
    }

    public static void main(String[] args){
        try {
            InputStream i = Day3.class.getClassLoader().getResourceAsStream("2015/day3.txt");
            String instructions = new String(i.readAllBytes());
            System.out.println("Part1: " + part1(instructions));
            System.out.println("Part2: " + part2(instructions));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
