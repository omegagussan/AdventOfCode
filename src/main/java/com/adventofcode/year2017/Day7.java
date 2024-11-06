package com.adventofcode.year2017;

import java.io.InputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day7 {
    record Disc(String name, int weight, List<Disc> children) {
        public int totalWeight() {
            return weight + children.stream().mapToInt(Disc::totalWeight).sum();
        }

        public List<String> contains() {
            List<String> list = new ArrayList<>();
            list.add(name);
            list.addAll(children.stream().flatMap(d -> d.contains().stream()).toList());
            return list;
        }
    }


    public static void main(String[] args) {
        try {
            InputStream i = Day7.class.getClassLoader().getResourceAsStream("2017/day7.txt");
            Scanner s = new Scanner(i).useDelimiter(System.lineSeparator());
            List<String> instructions = new ArrayList<>();
            while (s.hasNextLine()) {
                instructions.add(s.next());
            }
            Disc x = parseDiscs(instructions);
            System.out.println(x.name);
            System.out.println(balanceDisc(x));
        } catch (Exception e) {
            System.err.println("Something went poorly");
            e.printStackTrace();
        }
    }

    public static Optional<Integer> findUnique(List<Integer> discs) {
        return discs.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream().filter(e -> e.getValue() == 1).findFirst().map(Map.Entry::getKey);
    }
    public static Integer findCommon(List<Integer> discs) {
        return discs.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream().filter(e -> e.getValue() > 1).findFirst().map(Map.Entry::getKey).get();
    }
    public static int balanceDisc(Disc d) {
        List<Integer> childrenTotalWeights = d.children.stream().map(Disc::totalWeight).toList();
        List<Integer> childrenWeights = d.children.stream().map(Disc::weight).toList();
        var possibleUnique = findUnique(childrenTotalWeights);
        if (possibleUnique.isEmpty()) {
            return 0;
        }
        int index = childrenTotalWeights.indexOf(possibleUnique.get());
        int balanced = balanceDisc(d.children.get(index));
        if (balanced != 0) {
            return balanced;
        }
        return findCommon(childrenTotalWeights) - childrenTotalWeights.get(index) + childrenWeights.get(index);
    }

    public static Disc parseDiscs(List<String> discs) {
        Map<String, Disc> d = new HashMap<>(Map.of());
        var i = 0;
        outer:
        while (true) {
            var disc = discs.get(i % discs.size());
            i++;
            String[] parts = disc.split(" ");
            String name = parts[0];
            if (d.containsKey(name)) {
                continue outer;
            }
            int weight = Integer.parseInt(parts[1].substring(1, parts[1].length() - 1));
            List<Disc> children = new ArrayList<>();
            if (parts.length > 2) {
                List<String> list = Arrays.stream(disc.split(" -> ")[1].split(", ")).toList();
                for (String child : list) {
                    Disc e = d.get(child);
                    if (e == null) {
                        continue outer;
                    }
                    children.add(e);
                }
            }
            Disc value = new Disc(name, weight, children);
            d.put(name, value);
            if (value.contains().size() == discs.size()) {
                return value;
            }
        }
    }
}
