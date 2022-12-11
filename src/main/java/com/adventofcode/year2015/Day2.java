package com.adventofcode.year2015;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day2 {

    record Gift(int height, int width, int length){
        int wrapperSize(){
            int side1 = length * width;
            int side2 = width * height;
            int side3 = height * length;
            return 2 * side1 + 2 * side2 + 2 * side3 + smallest(List.of(side1, side2, side3));
        }

        int ribbonSize(){
            List<Integer> sides = List.of(length, width, height);
            int sideSum = sides.stream().reduce(1, (integer, integer2) -> integer*integer2);
            int smallest = 2 * smallest(sides);
            int secondSmallest = 2 * smallest(sides, 1);

            return smallest + secondSmallest + sideSum;
        }

        int smallest(List<Integer> candidates, int smallestIndex){
            List<Integer> tmp = new ArrayList<>(candidates);
           tmp.sort(Integer::compareTo);
           return tmp.get(smallestIndex);
        }

        int smallest(List<Integer> candidates){
            return smallest(candidates, 0);
        }
    }

    public static int part1(List<String> instructions) {
        int sum = 0;
        for (String i: instructions){
            List<Integer> parts = Arrays.stream(i.split("x")).map(Integer::parseInt).toList();
            Gift p = new Gift(parts.get(0), parts.get(1), parts.get(2));
            sum += p.wrapperSize();
        }
        return sum;
    }

    public static int part2(List<String> instructions) {
        int sum = 0;
        for (String i: instructions){
            List<Integer> parts = Arrays.stream(i.split("x")).map(Integer::parseInt).toList();
            Gift p = new Gift(parts.get(0), parts.get(1), parts.get(2));
            sum += p.ribbonSize();
        }
        return sum;
    }


    public static void main(String[] args){
        try {
            InputStream i = Day2.class.getClassLoader().getResourceAsStream("2015/day2.txt");
            Scanner s = new Scanner(i).useDelimiter(System.lineSeparator());
            List<String> instructions = new ArrayList<>();
            while (s.hasNextLine()){
                instructions.add(s.next());
            }
            System.out.println("Part1: " + part1(instructions));
            System.out.println("Part2: " + part2(instructions));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
