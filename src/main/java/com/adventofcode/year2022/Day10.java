
package com.adventofcode.year2022;

import com.google.common.collect.Lists;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

public class Day10 {
    public static final String ROW_DELIMITER = "\n";

    private static void operateOnMemory(
        String instructions,
        BiConsumer<ArrayList<Integer>, AtomicReference<Integer>> function,
        ArrayList<Integer> memory,
        AtomicReference<Integer> registry
    ) {

        Arrays.stream(instructions.split(ROW_DELIMITER)).forEach(r -> {
            function.accept(memory, registry);
            if (!r.startsWith("noop")) {
                int value = Integer.parseInt(r.replaceAll("[^0-9-]", ""));
                memory.add(registry.get());
                function.accept(memory, registry);
                registry.updateAndGet(v -> v + value);
            }
            memory.add(registry.get());
        });
    }

    private static void operateOnSignalStrengths(
        Set<Integer> signalStrengths,
        ArrayList<Integer> answer,
        ArrayList<Integer> memory,
        AtomicReference<Integer> registry
    ) {
        if (signalStrengths.contains(memory.size()+1)){
            answer.add((memory.size() +1) * registry.get());
        }
    }

    private static void operateOnCRT(ArrayList<String> CRT, AtomicReference<Integer> registry) {
        if (Math.abs(registry.get() % 40 - CRT.size() % 40) < 2){
            CRT.add("#");
        } else {
            CRT.add(".");
        }
    }

    public static int part1(String instructions) {
        var signalStrengths = Set.of(20, 60, 100, 140, 180, 220);
        var answer = new ArrayList<Integer>();
        var memory = new ArrayList<Integer>();
        var registry = new AtomicReference<>(1);

        operateOnMemory(
            instructions,
            (mem, reg) -> operateOnSignalStrengths(signalStrengths, answer, mem, reg),
            memory,
            registry
        );
        return answer.stream().mapToInt(Integer::intValue).sum();
    }

    public static String part2(String instructions) {
        var CRT = new ArrayList<String>();
        var memory = new ArrayList<Integer>();
        var registry = new AtomicReference<>(1);

        operateOnMemory(
            instructions,
            (ignored, reg) -> operateOnCRT(CRT, reg),
            memory,
            registry
        );

        List<String> CRTRows = Lists.partition(CRT, 40).stream()
            .map(strings -> String.join("", strings) + ROW_DELIMITER).toList();
        return String.join("", CRTRows);
    }

    public static void main(String[] args){
        try {
            InputStream i = Day10.class.getClassLoader().getResourceAsStream("2022/day10.txt");
            String instructions = new String(i.readAllBytes());
            System.out.println("Part1: " + part1(instructions));
            System.out.println("Part2: ");
            System.out.println(part2(instructions));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
