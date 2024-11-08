package com.adventofcode.year2017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day6 {
    String input="10\t3\t15\t10\t5\t15\t5\t15\t9\t2\t5\t8\t5\t2\t3\t6";

    public static void main(String... args) {
        Day6 day6 = new Day6();
        System.out.println(part1(day6.input));
    }

    public static int getMaxIndex(List<Integer> given){
        int max = given.get(0);
        int maxIndex = 0;
        for (int i = 1; i < given.size(); i++) {
            if (given.get(i) > max) {
                max = given.get(i);
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public static int part1(String input) {
        var seen = new java.util.HashSet<String>();
        var banks = new ArrayList<>(Arrays.stream(input.split("\t")).map(Integer::parseInt).toList());
        seen.add(banks.toString());

        int count = 1;
        //while(!seen.contains(banks.toString())){
        while (true){
            int curr = getMaxIndex(banks);
            int fwd = banks.get(curr);
            banks.set(curr, 0);
            for (int j=1; j <= fwd; j++){
                int index = (curr + j) % banks.size();
                banks.set(index, banks.get(index) + 1);
            }
            if (seen.contains(banks.toString())) break;
            seen.add(banks.toString());
            count++;
        }
        return count;
    }
}
