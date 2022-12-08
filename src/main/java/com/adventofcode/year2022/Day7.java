package com.adventofcode.year2022;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;
import org.jetbrains.annotations.NotNull;

public class Day7 {

    public static final String NEWLINE = "\n";
    public static final String DIR_PATTERN = "dir ";
    public static final String COMMAND = "$";
    public static final String ROOT = "/";


    public static Integer getSize(Map<String, Map<String, Integer>> tree, Map<String, Integer> currNode, Integer old){
        if (currNode == null){return old;}
        return currNode.entrySet().stream()
            .map(e -> e.getValue() != null ? e.getValue() : getSize(tree, tree.get(e.getKey()), 0))
            .mapToInt(Integer::intValue).sum() + old;
    }

    public static List<Integer> getDirectorySizeList(Map<String, Map<String, Integer>> tree, Map<String, Integer> currNode, List<Integer> res){
        if (currNode == null){return res;}
        res.addAll(currNode.entrySet().stream()
            .map(e -> e.getValue() != null ? null: getSize(tree, tree.get(e.getKey()), 0)) //null values as they are not dir
            .filter(Objects::nonNull).toList()); //and filter them away
        return res;
    }

    protected static Map<String, Map<String, Integer>> getTree(String instructions) {
        var path = new Stack<String>();
        Map<String, Map<String, Integer>> tree = new HashMap<>();

        var rows = Arrays.stream(instructions.split(NEWLINE)).toList();

        int pos = 0;
        Map<String, Integer> ongoingLs = null;
        while (pos < rows.size()){
            var rowString = rows.get(pos);
            if(ongoingLs != null && !rowString.startsWith(COMMAND)){
                if (rowString.contains(DIR_PATTERN)){
                    var t = rowString.split(DIR_PATTERN);
                    var dir  = t[t.length -1];
                    ongoingLs.put(key(path, dir), null); //put value null for dirs to lookup in root
                } else {
                    var t = rowString.split(" ");
                    ongoingLs.put(t[1], Integer.valueOf(t[0]));
                }
                pos++;
                continue;
            }
            if (ongoingLs != null){
                tree.put(key(path, ""), new HashMap<>(ongoingLs));
                ongoingLs = null;
            }
            if (rowString.startsWith(COMMAND + " ls")){
                ongoingLs = new HashMap<>();
            } else if (rowString.startsWith("$ cd")){
                switch (rowString) {
                    case COMMAND + " cd .." -> path.pop();
                    case COMMAND + " cd /" -> {path = new Stack<>(); path.add(ROOT);}
                    case default -> path.add(rowString.substring(5));
                }
            }
            pos++;
        }
        if (ongoingLs != null){
            tree.put(key(path, ""), new HashMap<>(ongoingLs));
        }
        return tree;
    }

    private static @NotNull String key(List<String> path, String dir) {
        return String.join(ROOT, path) + (dir.isEmpty() ? "" : (ROOT + dir));
    }
    public static int part1(String instructions) {
        var maxSizeDir = 100000;
        Map<String, Map<String, Integer>> tree = getTree(instructions);
        return tree.values().stream().map(stringIntegerMap -> getSize(tree, stringIntegerMap, 0)).mapToInt(Integer::intValue).filter(i -> i <= maxSizeDir).sum();
    }

    public static int part2(String instructions) {
        int freeSpaceTarget = 30000000;
        int spaceTotal = 70000000;
        Map<String, Map<String, Integer>> tree = getTree(instructions);
        Integer treeSize = getSize(tree, tree.get(ROOT), 0);
        int unusedSpace = spaceTotal - treeSize;
        int spaceTarget = freeSpaceTarget - unusedSpace;
        var dirSizes = tree.entrySet().stream().flatMap(e -> getDirectorySizeList(tree, tree.get(e.getKey()), new ArrayList<>()).stream()).toList();
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
