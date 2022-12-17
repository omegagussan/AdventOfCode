package com.adventofcode.year2022;

import com.google.common.collect.Streams;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;


public class Day16 {

    record ValveNode(String id, Integer value, List<String> children){}

    public static final String ROW_DELIMITER = "\n";
    public static Map<String, ValveNode> parsedInput;

    @NotNull
    static Pair<Integer, List<String>> bestMove(String currentValve, Set<String> openValves, Integer time, Map<String, Map<String, Integer>> distances){
        if (time < 1){
            return new Pair<>(0, Collections.emptyList());
        }
        var valveOpen = parsedInput.get(currentValve).value() * time;
        var candidates = distances.get(currentValve).entrySet().stream()
            .filter(entry -> !openValves.contains(entry.getKey())).map(entry -> {
                var q = Streams.concat(openValves.stream(), Stream.of(entry.getKey()))
                    .collect(Collectors.toSet());
                return bestMove(entry.getKey(), q, time - entry.getValue() - 1, distances);
            }).toList();
        if (candidates.size() > 0 ){
            var moveValue = candidates.stream().max(Comparator.comparingInt(Pair::getValue0)).get();
            return new Pair<>(valveOpen + moveValue.getValue0(), Streams.concat(moveValue.getValue1().stream(), Stream.of(currentValve)).toList());
        }
        return new Pair<>(valveOpen, List.of(currentValve));
    }

    @NotNull
    static Stream<ValveNode> parseInput(String instructions) {
        Matcher m = Pattern.compile("Valve (.*) has flow rate=(.*); tunnel(.*)")
            .matcher("");
        return Arrays.stream(instructions.split(ROW_DELIMITER)).map(row -> {
            m.reset(row).matches();
            var tmp = m.group(3).startsWith("s ") ? m.group(3).substring(17) : m.group(3).substring(15);
            return new ValveNode(m.group(1), Integer.parseInt(m.group(2)), Arrays.stream(tmp.split(",")).map(
                String::trim).toList());
        });
    }

    static int distanceFromAB(String a, String b, Map<String, Map<String, Integer>> d, Set<String> visited){
        if (a.equals(b)){return 0;};
        if (d.get(b).containsKey(a)){
            return d.get(b).get(a);
        }
        return parsedInput.get(a).children().stream()
            .filter(x -> !visited.contains(x))
            .map(x -> distanceFromAB(x, b, d, Streams.concat(visited.stream(), Stream.of(x)).collect(Collectors.toSet())))
            .mapToInt(Integer::valueOf).min().orElse(Integer.MAX_VALUE/2) + 1;
    }

    static Map<String, Map<String, Integer>> getDistances(){
        Map<String, Map<String, Integer>> distances = initializeMap();
        distances.keySet().forEach(a -> distances.keySet().forEach(b ->{
            if (!a.equals(b)){
                distances.get(a).put(b, distanceFromAB(a, b, distances, new HashSet<>()));
            }
        }));

        distances.forEach((key, value) -> {
            Map<String, Integer> map = distances.get(key).entrySet().stream()
                .filter(entry -> parsedInput.get(entry.getKey()).value() > 0).collect(
                    Collectors.toMap(Entry::getKey, Entry::getValue));
            distances.put(key, map);
        });
        return distances;
    }

    @NotNull
    static Map<String, Map<String, Integer>> initializeMap() {
        Map<String, Map<String, Integer>> distances = new HashMap<>();
        parsedInput.forEach((key, value) -> {
            var v = value.children().stream().map(child -> new Pair<>(
                child, 1)).collect(Collectors.toMap(Pair::getValue0, Pair::getValue1));
            distances.put(key, v);
        });
        return distances;
    }


    public static int part2(String instructions) {
        return 2;
    }

    public static int part1(String instruction) {
        var leafs = parseInput(instruction).collect(Collectors.toMap(t -> t.id, t -> t));
        parsedInput = leafs;

        var distances = getDistances();
        var best = distances.get("AA").keySet().stream()
            .filter(integer -> parsedInput.get(integer).value > 0)
            .map(
                integer -> bestMove(integer, Set.of(integer), 29 - distances.get("AA").get(integer),
                    distances)).max(Comparator.comparingInt(Pair::getValue0)).get();
        System.out.println(best.getValue1());
        return best.getValue0();
    }


    public static void main(String[] args){
        try {
            InputStream i = Day16.class.getClassLoader().getResourceAsStream("2022/day16.txt");
            String instructions = new String(i.readAllBytes());
            System.out.println("Part1: " + part1(instructions));
            System.out.println("Part2: " + part2(instructions));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
