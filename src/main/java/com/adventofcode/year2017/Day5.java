package com.adventofcode.year2017;

import java.io.InputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day5 {
    public static void main(String[] args) {
        try {
            InputStream i = Day5.class.getClassLoader().getResourceAsStream("2017/day5.txt");
            Scanner s = new Scanner(i).useDelimiter(System.lineSeparator());
            List<Integer> instructions = new ArrayList<>();
            while (s.hasNextLine()) {
                instructions.add(Integer.parseInt(s.next()));
            }
            System.out.println(part1(instructions));
            System.out.println(part2(instructions));
        } catch (Exception e) {
            System.err.println("Something went poorly");
            e.printStackTrace();
        }
    }

    public static int part1(List<Integer> sequence){
        int index = 0;
        int steps = 0;
        while (index < sequence.size()) {
            int jump = sequence.get(index);
            sequence.set(index, jump + 1);
            index += jump;
            steps++;
        }
        return steps;
    }

    public static int part2(List<Integer> sequence){
        int index = 0;
        int steps = 0;

        while (index < sequence.size()) {
            steps++;
            int jump = sequence.get(index);
            int increase = jump > 2 ? -1 : 1;
            sequence.set(index, jump + increase);
            index += jump;
        }
        return steps;
    }
}

//63
//413 low