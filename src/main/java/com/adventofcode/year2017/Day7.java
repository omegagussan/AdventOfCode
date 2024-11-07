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
            System.out.println(balanceDisc(x).get());
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
    public static Optional<Integer> balanceDisc(Disc d) {
        List<Integer> childrenTotalWeights = d.children.stream().map(Disc::totalWeight).toList();
        var possibleUnique = findUnique(childrenTotalWeights);

        Optional<Integer> i = possibleUnique.flatMap((unique) -> {
            int index = childrenTotalWeights.indexOf(unique);
            return balanceDisc(d.children.get(index));
        });
        if (i.isPresent()) {
            return i;
        }

        return possibleUnique.map((unique) -> {
            int index = childrenTotalWeights.indexOf(unique);
            List<Integer> childrenWeights = d.children.stream().map(Disc::weight).toList();
            return findCommon(childrenTotalWeights) - childrenTotalWeights.get(index) + childrenWeights.get(index);
        });
    }

    public static Disc parseDisc(ArrayList<String> parts){
        String name = parts.remove(0);
        String intPart = parts.remove(0);
        int weight = Integer.parseInt(intPart.substring(1, intPart.length() - 1));
        return new Disc(name, weight, new ArrayList<>());
    }

    public static Disc parseDiscs(List<String> discs) {
        //this is an optimization
        List<String> sortd = discs.stream().sorted(Comparator.comparingInt(String::length)).toList();
        var remaining = new ArrayList<>(sortd);
        Disc disc = null;
        Map<String, Disc> d = new HashMap<>(Map.of());
        outer:
        while (!remaining.isEmpty()) {
            var curr = remaining.remove(0);
            ArrayList<String> s = new ArrayList<>(List.of(curr.split(" ")));
            disc = parseDisc(s);
            if (d.containsKey(disc.name)) {
                continue;
            }
            if (!s.isEmpty()) {
                List<String> list = Arrays.stream(curr.split(" -> ")[1].split(", ")).toList();
                for (String child : list) {
                    Disc e = d.get(child);
                    if (e == null) {
                        remaining.add(curr);
                        continue outer;
                    }
                    disc.children.add(e);
                }
            }
            d.put(disc.name, disc);
        }
        return disc;
    }
}
