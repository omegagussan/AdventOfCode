package com.adventofcode.year2015;

import java.io.InputStream;
import java.util.*;

import static com.google.common.collect.Sets.combinations;

public class Day17 {
    public static void main(String[] args) {
        try {
            InputStream i = Day17.class.getClassLoader().getResourceAsStream("2015/day17.txt");
            Scanner s = new Scanner(i).useDelimiter(System.lineSeparator());
            List<Integer> instructions = new ArrayList<>();
            while (s.hasNextLine()) {
                instructions.add(Integer.valueOf(s.next()));
            }
            System.out.println(part1(instructions, 150));
        } catch (Exception e) {
            System.err.println("Something went poorly");
            e.printStackTrace();
        }
    }

    public static int part1(List<Integer> instructions, int target) {
        combinations(instructions, target, 0, 0, new ArrayList<>());
        System.out.println(instructions);
        return 0;
    }
}
