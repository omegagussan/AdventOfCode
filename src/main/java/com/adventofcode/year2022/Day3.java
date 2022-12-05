package com.adventofcode.year2022;

import com.google.common.collect.Lists;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.ArrayUtils;

public class Day3 {

    static class BackpackWithCompartment {
        Set<String> pocket1;
        Set<String> pocket2;
        BackpackWithCompartment(String backpackString){
            var arr = backpackString.split("");
            var len = arr.length;
            var pocket1 = ArrayUtils.subarray(arr, 0, len / 2);
            var pocket2 = ArrayUtils.subarray(arr, len / 2, len);
            this.pocket1 = new HashSet<>(List.of(pocket1));
            this.pocket2 = new HashSet<>(List.of(pocket2));
        }

        List<String> getCommonUnique(){ //no order
            var tmp = new HashSet<>(this.pocket1);
            tmp.retainAll(pocket2); //union
            return tmp.stream().toList();
        }

        int getPriority(){
            var r =this.getCommonUnique();
            return getPriorityFromList(r);
        }
    }

    private static int getPriorityFromList(List<String> l) {
        return l.stream().map(s -> {
            var original = (int) s.charAt(0);
            if (original < 91) {
                return (original - 64 + 26);
            } else if (original > 96) {
                return original - 96;
            } else {
                throw new IllegalStateException("no good!");
            }
        }).mapToInt(Integer::intValue).sum();
    }

    public static int part1(String instructions) {
        var t = Arrays.stream(instructions.split("\n"))
            .map(BackpackWithCompartment::new)
            .map(BackpackWithCompartment::getPriority)
            .toList();
        return t.stream().mapToInt(Integer::intValue).sum();

    }

    public static int part2(String instructions) {
        var t = Arrays.stream(instructions.split("\n")).toList();
        var groups = Lists.partition(t, 3);
        return groups.stream().map(group -> {
            var bagsWithoutCompartments = group.stream().map(s -> List.of(s.split(""))).toList();
            var commonNonUniqueItemsAmongBags = bagsWithoutCompartments.get(0).stream()
                .filter(bagsWithoutCompartments.get(1)::contains)
                .filter(bagsWithoutCompartments.get(2)::contains).toList();
            return getPriorityFromList(List.of(commonNonUniqueItemsAmongBags.get(0))); //because assignment says so. Get just first hack instead of filter for unique.
        }).mapToInt(Integer::intValue).sum();
    }

    public static void main(String[] args){
        try {
            InputStream i = Day3.class.getClassLoader().getResourceAsStream("2022/day3.txt");
            String instructions = new String(i.readAllBytes());
            System.out.println("Part1: " + part1(instructions));
            System.out.println("Part2: " + part2(instructions));
        } catch (Exception e){
            System.err.println("Something went poorly: " + e.getCause());
        }
    }
}
