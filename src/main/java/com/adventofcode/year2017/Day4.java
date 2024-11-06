package com.adventofcode.year2017;

import java.io.InputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day4 {
    public static void main(String[] args) {
        try {
            InputStream i = Day2.class.getClassLoader().getResourceAsStream("2017/day4.txt");
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
        return (int) instructions.stream().filter(Day4::isValid).count();
    }

    public static int part2(List<String> instructions) {
        return (int) instructions.stream().filter(Day4::isValid2).count();
    }

    public static boolean isValid(String passPhrase){
        Map<String, Long> phraseDict = Arrays.stream(passPhrase.split(" ")).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return phraseDict.values().stream().allMatch(v -> v == 1);
    }

    public static boolean isValid2(String passPhrase){
        Map<String, Long> phraseDict = Arrays.stream(passPhrase.split(" "))
                .map(Day4::sortString)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return phraseDict.values().stream().allMatch(v -> v == 1);
    }

    public static String sortString(String inputString) {
        char tempArray[] = inputString.toCharArray();
        Arrays.sort(tempArray);
        return new String(tempArray);
    }
}

