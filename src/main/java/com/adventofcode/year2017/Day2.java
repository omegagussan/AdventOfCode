package com.adventofcode.year2017;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day2 {
    public static void main(String[] args) {
        try {
            InputStream i = Day2.class.getClassLoader().getResourceAsStream("2017/day2.txt");
            Scanner s = new Scanner(i).useDelimiter(System.lineSeparator());
            List<String> instructions = new ArrayList<>();
            while (s.hasNextLine()) {
                instructions.add(s.next());
            }
            System.out.println(part1(instructions));
        } catch (Exception e) {
            System.err.println("Something went poorly");
            e.printStackTrace();

        }
    }

    public static int part1(List<String> instructions) {
        var sum = 0;
        for (var line : instructions) {
            sum += parseLine(line);
        }
        return sum;
    }

    static int parseLine(String line) {
        var numbers = Arrays.stream(line.split("\t")).map(String::trim).map(Integer::parseInt).toList();
        var sorted = numbers.stream().sorted().toList();
        return sorted.get(sorted.size() - 1) - sorted.get(0);
    }
}

