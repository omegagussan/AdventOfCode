package com.adventofcode.year2015;

import com.google.gson.Gson;

import java.io.InputStream;
import java.util.*;

public class Day16 {
    public static String EXPECTATION= """
            {
            "children": 3,
            "cats": 7,
            "samoyeds": 2,
            "pomeranians": 3,
            "akitas": 0,
            "vizslas": 0,
            "goldfish": 5,
            "trees": 3,
            "cars": 2,
            "perfumes": 1
            }
""";
    public static void main(String[] args) {
        try {
            InputStream i = Day16.class.getClassLoader().getResourceAsStream("2015/day16.txt");
            Scanner s = new Scanner(i).useDelimiter(System.lineSeparator());
            List<String> instructions = new ArrayList<>();
            while (s.hasNextLine()) {
                instructions.add(s.next());
            }
            System.out.println(part1(instructions));
            System.out.println(part2(instructions));

        } catch (Exception e) {
            System.err.println("Something went poorly");
            e.printStackTrace();
        }
    }

    public static int part1(List<String> instructions) {
        var expectation = new Gson().fromJson(EXPECTATION, Map.class);
        for (String instruction : instructions) {
            var parts = instruction.split(": ");
            var number = Integer.parseInt(parts[0].split(" ")[1]);
            var items = "\"" + String.join("\": " ,Arrays.stream(parts).skip(1).toList());
            items = items.replaceAll(", ", ", \"");
            items = "{" + items + "}";
            var map = new Gson().fromJson(items, Map.class);
            System.out.println(map);
            if (contains(map, expectation)) {
                return number;
            }
        }
        return 0;
    }

    public static int part2(List<String> instructions) {
        var expectation = new Gson().fromJson(EXPECTATION, Map.class);
        for (String instruction : instructions) {
            var parts = instruction.split(": ");
            var number = Integer.parseInt(parts[0].split(" ")[1]);
            var items = "\"" + String.join("\": " ,Arrays.stream(parts).skip(1).toList());
            items = items.replaceAll(", ", ", \"");
            items = "{" + items + "}";
            var map = new Gson().fromJson(items, Map.class);
            System.out.println(map);
            if (contains2(map, expectation)) {
                return number;
            }
        }
        return 0;
    }

    public static boolean contains(Map m, Map expectation) {
        for (Object entry : m.keySet()) {
            if (expectation.containsKey(entry)) {
               if (!expectation.get(entry).equals(m.get(entry))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean contains2(Map m, Map expectation) {
        for (Object entry : m.keySet()) {
            if (expectation.containsKey(entry)) {
                if (entry.equals("cats") || entry.equals("trees")) {
                    if ((double) expectation.get(entry) >= (double) m.get(entry)) {
                        return false;
                    }
                } else if (entry.equals("pomeranians") || entry.equals("goldfish")) {
                    if ((double) expectation.get(entry) <= (double) m.get(entry)) {
                        return false;
                    }
                } else if (!expectation.get(entry).equals(m.get(entry))) {
                    return false;
                }
            }
        }
        return true;
    }
}

//40