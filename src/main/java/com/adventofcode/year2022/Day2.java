package com.adventofcode.year2022;

import java.io.InputStream;
import java.util.Arrays;
import java.util.function.Function;

public class Day2 {
    public static final String ROW_DELIMITER = "\n";

    private record Round(String their, String yours){
        int getDiff(){
            return  this.yours.chars().findFirst().getAsInt() - (this.their.chars().findFirst().getAsInt() + 23);
        }
    }

    private static Function<Round, Integer> yourSignScore() {
        return round -> switch (round.yours) {
            case "X" -> 1;
            case "Y" -> 2;
            case "Z" -> 3;
            case default -> throw new RuntimeException("Not a valid sign");
        };
    }

    private static Function<String, Round> asRound() {
        return s -> {
            var a = s.split(" ");
            return new Round(a[0], a[a.length - 1]);
        };
    }

    private static Function<Round, Integer> getRoundScore() {
        return round -> {
            switch (round.getDiff()) {
                case 0 -> {
                    return 3;
                }
                case 1, -2 -> {
                    return 6;
                }
                case 2, -1 -> {
                    return 0;
                }
                case default -> throw new RuntimeException("Not a valid diff from pair");
            }
        };
    }
    private static Function<Round, Round> getSignFromOpponent() {
        return round -> {
            switch (round.yours) {
                case "X" -> {
                    return new Round(round.their, howToLoose(round.their));
                }
                case "Y" -> {
                    return new Round(round.their, howToDraw(round.their));
                }
                case "Z" -> {
                    return new Round(round.their, howToWin(round.their));
                }
                case default -> throw new RuntimeException("Not a valid opponents sign");
            }
        };
    }

    static public String howToWin(String argumentSign){
        return switch (argumentSign){
            case "A" -> "Y";
            case "B" -> "Z";
            case "C" -> "X";
            default -> throw new IllegalStateException("Unexpected value: " + argumentSign);
        };
    }

    static public String howToLoose(String argumentSign){
        return switch (argumentSign){
            case "A" -> "Z";
            case "B" -> "X";
            case "C" -> "Y";
            default -> throw new IllegalStateException("Unexpected value: " + argumentSign);
        };
    }

    static public String howToDraw(String argumentSign){
        return switch (argumentSign){
            case "A" -> "X";
            case "B" -> "Y";
            case "C" -> "Z";
            default -> throw new IllegalStateException("Unexpected value: " + argumentSign);
        };
    }

    private static Function<Round, Integer> usingElfKnowledge() {
        return round -> switch (round.yours) {
            case "X" -> 0;
            case "Y" -> 3;
            case "Z" -> 6;
            case default -> throw new RuntimeException("Not a valid sign from you");
        };
    }

    public static int part1(String instructions) {
        var rounds = Arrays.stream(instructions.split(ROW_DELIMITER)).toList();
        var signScore = rounds.stream().map(asRound()).map(yourSignScore()).mapToInt(Integer::valueOf).sum();

        var winScore = rounds.stream().map(asRound()).map(getRoundScore()).mapToInt(Integer::valueOf).sum();
        return winScore + signScore;
    }

    public static int part2(String instructions) {
        var rounds = Arrays.stream(instructions.split(ROW_DELIMITER)).toList();
        var winScore = rounds.stream().map(asRound()).map(usingElfKnowledge()).mapToInt(Integer::valueOf).sum();

        var signState = rounds.stream().map(asRound()).map(getSignFromOpponent()).toList();
        var signScore = signState.stream().map(yourSignScore()).mapToInt(Integer::valueOf).sum();
        return winScore + signScore;
    }

    public static void main(String[] args){
        try {
            InputStream i = Day2.class.getClassLoader().getResourceAsStream("2022/day2.txt");
            String instructions = new String(i.readAllBytes());
            System.out.println("Part1: " + part1(instructions));
            System.out.println("Part2: " + part2(instructions));
        } catch (Exception e){
            System.err.println("Something went poorly: " + e.getCause());
        }
    }
}
