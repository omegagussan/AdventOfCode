package com.adventofcode.year2022;

import static java.util.stream.Collectors.toList;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ArrayUtils;

public class Day3 {

    static class Backpack{
        List<String> pocket1;
        List<String> pocket2;
        Backpack(String backpackString){
            var arr = backpackString.split("");
            var len = arr.length;
            var pocket1 = ArrayUtils.subarray(arr, 0, (int) (len / 2));
            var pocket2 = ArrayUtils.subarray(arr, (int) (len / 2), len);
            this.pocket1 = new ArrayList<>(List.of(pocket1));
            this.pocket2 = List.of(pocket2);
        }

        List<String> getCommonUnique(){
           //return Streams.zip(pocket1.stream(), pocket2.stream(), Pair::of).filter(p -> p.getLeft().equals(p.getRight())).map(
                //Pair::getLeft).toList();
          return this.pocket2.stream().filter(caseSensitiveChar -> this.pocket1.contains(caseSensitiveChar)).collect(
              Collectors.toSet()).stream().toList();
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
            .map(Backpack::new)
            .map(Backpack::getPriority)
            .toList();
        return t.stream().mapToInt(Integer::intValue).sum();

    }

    public static  <T> Set<T> intersection(List<T>... list) {
        Set<T> result = Sets.newHashSet(list[0]);
        for (List<T> numbers : list) {
            result = Sets.intersection(result, Sets.newHashSet(numbers));
        }
        return result;
    }

    public static int part2(String instructions) {
        var t = Arrays.stream(instructions.split("\n")).toList();
        var groups = Lists.partition(t, 3);
        return groups.stream().map(group -> {
            var bags = group.stream().map(s -> List.of(s.split(""))).collect(Collectors.toList());
            var retain = bags.get(0).stream()
                .filter(bags.get(1)::contains).filter(bags.get(2)::contains).collect(toList());
            return getPriorityFromList(List.of(retain.get(0)));
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
