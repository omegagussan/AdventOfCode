package com.adventofcode.year2015;

import com.adventofcode.utils.ArrayUtilz;

import java.io.InputStream;
import java.util.*;

public class Day9 {
    public static void main(String[] args){
        try {
            InputStream i = Day7.class.getClassLoader().getResourceAsStream("2015/day9.txt");
            Scanner s = new Scanner(i).useDelimiter(System.lineSeparator());
            List<String> instructions = new ArrayList<>();
            while (s.hasNextLine()){
                instructions.add(s.next());
            }
            System.out.println(part1(instructions));
            System.out.println(part2(instructions));
        } catch (Exception e){
            System.err.println("Something went poorly");
            e.printStackTrace();
        }
    }

    record Key(String from, String to) {
    }

    public static int part1(List<String> instructions) {
        List<String> destinationsWithDuplicates = instructions.stream().flatMap(s -> Arrays.stream(s.split(" = ")[0].split(" to "))).toList();
        Set<String> destinationsSet = new HashSet<>(destinationsWithDuplicates);
        Map<Key, Integer> distances = new HashMap<>();
        instructions.forEach(s ->{
            String[] parts = s.split(" = ");
            String[] cities = parts[0].split(" to ");
            distances.put(new Key(cities[0], cities[1]), Integer.parseInt(parts[1]));
            distances.put(new Key(cities[1], cities[0]), Integer.parseInt(parts[1]));
        });
        return ArrayUtilz.permutations(destinationsSet.stream().toList()).stream()
                .map(destinations -> {
                    int sum = 0;
                    for (int i = 0; i < destinations.size() - 1; i++) {
                        var k = new Key(destinations.get(i), destinations.get(i + 1));
                        sum += distances.get(k);
                    }
                    return sum;
                }).min(Comparator.naturalOrder()).orElseThrow();
    }

    public static int part2(List<String> instructions) {
        List<String> destinationsWithDuplicates = instructions.stream().flatMap(s -> Arrays.stream(s.split(" = ")[0].split(" to "))).toList();
        Set<String> destinationsSet = new HashSet<>(destinationsWithDuplicates);
        Map<Key, Integer> distances = new HashMap<>();
        instructions.forEach(s ->{
            String[] parts = s.split(" = ");
            String[] cities = parts[0].split(" to ");
            distances.put(new Key(cities[0], cities[1]), Integer.parseInt(parts[1]));
            distances.put(new Key(cities[1], cities[0]), Integer.parseInt(parts[1]));
        });
        return ArrayUtilz.permutations(destinationsSet.stream().toList()).stream()
                .map(destinations -> {
                    int sum = 0;
                    for (int i = 0; i < destinations.size() - 1; i++) {
                        var k = new Key(destinations.get(i), destinations.get(i + 1));
                        sum += distances.get(k);
                    }
                    return sum;
                }).max(Comparator.naturalOrder()).orElseThrow();
    }
}
