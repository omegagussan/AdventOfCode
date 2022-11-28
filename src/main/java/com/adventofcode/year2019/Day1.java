package com.adventofcode.year2019;

import java.io.InputStream;
import java.util.Arrays;

public class Day1{

    static int fuelConsumption(Integer mass){
        return (int) (Math.floor(mass /3.0 ) -2);
    }

    static int fuelConsumptionRecursive(Integer mass){
        int next = (int) (Math.floor(mass / 3.0) - 2);
        if (next < 0){
            return 0;
        }
        return next + fuelConsumptionRecursive(next);
    }


    //sum of all lines
    public static int part1(String instructions) {
        return Arrays.stream(instructions.split(System.lineSeparator())).map(Integer::valueOf).map(
            Day1::fuelConsumption).mapToInt(Integer::valueOf).sum();
    }

    //when enter basement
    public static int part2(String instructions) {
        return Arrays.stream(instructions.split(System.lineSeparator())).map(Integer::valueOf).map(
            Day1::fuelConsumptionRecursive).mapToInt(Integer::valueOf).sum();
    }

    public static void main(String[] args){
        try {
            InputStream i = Day1.class.getClassLoader().getResourceAsStream("2019/day1.txt");
            String instructions = new String(i.readAllBytes());
            System.out.println("Part1: " + part1(instructions));
            System.out.println("Part2: " + part2(instructions));
        } catch (Exception e){
            System.err.println("Something went poorly: " + e.getCause());
        }
    }
}
