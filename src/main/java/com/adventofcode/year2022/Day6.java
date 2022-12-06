package com.adventofcode.year2022;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day6 {

    public static final String NEWLINE = "\n";

    public static int part1(String instructions) {
        AtomicReference<Integer> res = new AtomicReference<>(0);
        AtomicReference<Boolean> done = new AtomicReference<>(false);
        Arrays.stream(instructions.split(NEWLINE))
            .forEach(r -> IntStream.range(0, r.length()-3)
                .mapToObj(i -> new HashSet(Arrays.stream(r.substring(i, i + 4).split("")).toList()))
                .forEach(set -> {
                if (set.size() == 4){
                    done.set(true);
                } else if (!done.get()){
                    res.getAndSet(res.get() + 1);
                }
            }));
        return res.get() + 4;
    }

    public static String part2(String instructions) {
        return "b";
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
