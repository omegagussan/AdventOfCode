package com.adventofcode.year2015;

import com.google.common.collect.Streams;

import java.io.InputStream;
import java.util.*;

public class Day5 {
    public static final Set<String> VOWELS = new HashSet<>(List.of("a", "e", "i", "o", "u"));
    public static final Set<String> BANNED = new HashSet<>(List.of("ab", "cd", "pq", "xy"));

    static boolean hasAdjacentTwinCharacter(String[] instructions){
        String previous = "";
        for (String i : instructions){
            if (i.equals(previous))return true;
            previous = i;
        }
        return false;
    }
    static boolean isNice(String instruction){
        String[] instructionArray = instruction.split("");
        return BANNED.stream().noneMatch(instruction::contains) &&
                (Arrays.stream(instructionArray).filter(VOWELS::contains).count() > 2) &&
                hasAdjacentTwinCharacter(instructionArray);
    }

    private static String[] getEvenOrOdd(String[] instructions, int remainder) {
        return Streams.mapWithIndex(Arrays.stream(instructions), AbstractMap.SimpleImmutableEntry::new)
                .filter(e -> e.getValue() % 2 == remainder)
                .map(AbstractMap.SimpleImmutableEntry::getKey).toArray(String[]::new);
    }

    //0, 1, 2...i-1, <i, i+1>, i+2, i+3...
    static boolean hasTwinPairsNotAdjacent(String instruction){
        for (int i=0; i < instruction.length() -2; i++){
            String pair = instruction.substring(i, i + 2);
            String left = instruction.substring(0, Math.max(i, 0));
            String right = instruction.substring(Math.min(i + 2, instruction.length() - 1));
            if (left.contains(pair) || right.contains(pair)) return true;
        }
        return false;
    }

    static boolean isNiceImproved(String instruction){
        String[] instructionArray = instruction.split("");
        String[] evenArr = getEvenOrOdd(instructionArray, 0);
        String[] oddArr = getEvenOrOdd(instructionArray, 1);
        boolean oneLetterBetween = hasAdjacentTwinCharacter(evenArr) || hasAdjacentTwinCharacter(oddArr);
        return oneLetterBetween && hasTwinPairsNotAdjacent(instruction);
    }
    public static int part1(List<String> instructions) {
        return (int) instructions.stream().filter(Day5::isNice).count();
    }

    public static int part2(List<String> instructions) {
        return (int) instructions.stream().filter(Day5::isNiceImproved).count();
    }

    public static void main(String[] args){
        try {
            InputStream i = Day5.class.getClassLoader().getResourceAsStream("2015/day5.txt");
            Scanner s = new Scanner(i).useDelimiter(System.lineSeparator());
            List<String> instructions = new ArrayList<>();
            while (s.hasNextLine()){
                instructions.add(s.next());
            }
            System.out.println("Part1: " + part1(instructions));
            System.out.println("Part2: " + part2(instructions));
        } catch (Exception e){
            System.err.println("Something went poorly");
            e.printStackTrace();
        }
    }
}
