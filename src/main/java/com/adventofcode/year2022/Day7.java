package com.adventofcode.year2022;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Day7 {
    public static int part1(String instructions) {
        Map<String, Map<String, Integer>> tree = getTree(instructions);
        return tree.entrySet().stream().map(e -> getSize(tree, e.getValue())).mapToInt(Integer::intValue).filter(i -> i <= 100000).sum();
    }

    public static Integer getSize(Map<String, Map<String, Integer>> tree, Map<String, Integer> currNode, Integer curr){
        if (currNode == null){
            return curr;
        }
        return currNode.entrySet().stream().map(e -> {
            if (e.getValue() == null){
                return getSize(tree, tree.get(e.getKey()));
            }
            return e.getValue();
        }).mapToInt(Integer::intValue).sum() + curr;
    }

    public static List<Integer> getSizeList(Map<String, Map<String, Integer>> tree, Map<String, Integer> currNode, List<Integer> curr){
        if (currNode == null){
            return curr;
        }
        curr.addAll(currNode.entrySet().stream().map(e -> {
            if (e.getValue() == null){
                return getSize(tree, tree.get(e.getKey()));
            }
            return null;
        }).filter(Objects::nonNull).toList());
        return curr;
    }

    public static Integer getSize(Map<String, Map<String, Integer>> tree, Map<String, Integer> currNode){
        return getSize(tree, currNode, 0);
    }

    protected static Map<String, Map<String, Integer>> getTree(String instructions) {
        String path = "";
        Map<String, Map<String, Integer>> tree = new HashMap<>();
        var rows = Arrays.stream(instructions.split("\n")).toList();

        int pos = 0;
        Map<String, Integer> lsOngoing = null;
        while (pos < rows.size()){
            var r = rows.get(pos);
            if(lsOngoing != null && !r.startsWith("$")){
                if (r.contains("dir")){
                    var t = r.split("dir ");
                    var dir  = t[t.length -1];
                    lsOngoing.put((path + "/" + dir).replaceAll("//", "/"), null);
                } else {
                    var t = r.split(" ");
                    lsOngoing.put(t[1], Integer.valueOf(t[0]));
                }
                pos++;
                continue;
            }
            if (lsOngoing != null){
                tree.put(path, new HashMap<>(lsOngoing));
                lsOngoing = null;
            }
            if (r.startsWith("$ ls")){
                lsOngoing = new HashMap<>();
            } else if (r.startsWith("$ cd")){
                switch (r) {
                    case "$ cd .." -> {
                        var cutIdx = path.lastIndexOf("/");
                        path = path.substring(0, cutIdx);
                    }
                    case "$ cd /" -> path = "/";
                    case default -> path = (path + "/" +  r.substring(5)).replaceAll("//", "/");
                }
            }
            pos++;
        }
        if (lsOngoing != null){
            tree.put(path, new HashMap<>(lsOngoing));
        }
        return tree;
    }

    public static int part2(String instructions) {
        int freeSpaceTarget = 30000000;
        int spaceTotal = 70000000;
        Map<String, Map<String, Integer>> tree = getTree(instructions);
        Integer treeSize = getSize(tree, tree.get("/"));
        int unusedSpace = spaceTotal - treeSize;
        int spaceTarget = freeSpaceTarget - unusedSpace;
        var dirSizes = tree.entrySet().stream().flatMap(e -> getSizeList(tree, tree.get(e.getKey()), new ArrayList<>()).stream()).toList();
        return dirSizes.stream().filter(i -> i >= spaceTarget).sorted().findFirst().get();
    }

    public static void main(String[] args){
        try {
            InputStream i = Day7.class.getClassLoader().getResourceAsStream("2022/day7.txt");
            String instructions = new String(i.readAllBytes());
            System.out.println("Part1: " + part1(instructions));
            System.out.println("Part2: " + part2(instructions));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
