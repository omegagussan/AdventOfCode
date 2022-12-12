package com.adventofcode.year2022;

import com.adventofcode.utils.ArrayUtilz;
import com.adventofcode.utils.Point;
import com.adventofcode.utils.StringMatrixParser;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class Day12 {

    public static final String ROW_DELIMITER = "\n";

    public static HashMap<Point, Integer> BFS(Point start, Integer[][] grid) {
        var distances = new HashMap<Point, Integer>();
        var visited = new HashSet<Point>();
        Queue<Point> queue = new LinkedList<>();
        queue.add(start);
        distances.put(start, 0);

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            Point.getAdjacent(current).stream().filter(p -> Point.compare(p, current).magnitude() < 2)
                .filter(c -> Point.isWithinGrid(grid, c))
                .forEach(candidate -> {
                    boolean isClimbable = grid[candidate.i()][candidate.j()] - grid[current.i()][current.j()] <= 1;
                    if (isClimbable){
                        var newCandidateValue = distances.get(current) + 1;
                        if (newCandidateValue < distances.getOrDefault(candidate, Integer.MAX_VALUE))
                            distances.put(candidate, newCandidateValue);
                        if (!visited.contains(candidate)) {
                            visited.add(candidate);
                            queue.add(candidate);
                        }
                    }
            });
        }
        return distances;
    }

    private static Integer[][] parseCostMatrix(String[][] matrix) {
        return StringMatrixParser.applyGeneric(matrix, Integer.class, (e) -> {
            if (Objects.equals(e, "S")){
                e = "a";
            } else if (Objects.equals(e, "E")){
                e = "z";
            }
            return e.chars().findFirst().getAsInt() - 96;
        });
    }

    public static int part1(String instructions) {
        var matrix = StringMatrixParser.parse(instructions, ROW_DELIMITER, "");
        Integer[][] constraintMatrix = parseCostMatrix(matrix);
        var start = ArrayUtilz.findFirst(matrix, "S");
        var end = ArrayUtilz.findFirst(matrix, "E");

        return BFS(start, constraintMatrix).get(end);
    }

    public static int part2(String instructions) {
        var matrix = StringMatrixParser.parse(instructions, ROW_DELIMITER, "");
        Integer[][] constraintMatrix = parseCostMatrix(matrix);
        var starts = ArrayUtilz.findAll(matrix, "a");
        var end = ArrayUtilz.findFirst(matrix, "E");

        return starts.stream()
            .map(s -> BFS(s, constraintMatrix).getOrDefault(end, Integer.MAX_VALUE))
            .mapToInt(Integer::intValue)
            .min().getAsInt();
    }

    public static void main(String[] args){
        try {
            InputStream i = Day12.class.getClassLoader().getResourceAsStream("2022/day12.txt");
            String instructions = new String(i.readAllBytes());
            System.out.println("Part1: " + part1(instructions));
            System.out.println("Part2: " + part2(instructions));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
