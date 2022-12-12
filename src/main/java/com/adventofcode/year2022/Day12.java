package com.adventofcode.year2022;

import com.adventofcode.utils.ArrayUtilz;
import com.adventofcode.utils.Point;
import com.adventofcode.utils.StringMatrixParser;
import java.io.InputStream;
import java.lang.annotation.IncompleteAnnotationException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class Day12 {

    public static HashMap<Point, Integer> BFS(Point start, Integer[][] grid) {
        var distances = new HashMap<Point, Integer>();
        var visited = new HashSet<Point>();
        Queue<Point> queue = new LinkedList<>();
        queue.add(start);
        distances.put(start, 0);

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            // Check all the surrounding cells
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0) continue; // Skip the current cell
                    if (i == 1 && j == 1 ) continue; // No diagonal leaps!
                    if (i == -1 && j == -1 ) continue; // No diagonal leaps!
                    if (i == 1 && j == -1 ) continue; // No diagonal leaps!
                    if (i == -1 && j == 1 ) continue; // No diagonal leaps!

                    int x = current.i() + i;
                    int y = current.j() + j;
                    if (x == 2 && y == 5){
                        System.out.println("here");
                    }
                    var candidate = new Point(x, y);
                    boolean isWithinGrid = x >= 0 && x < grid.length && y >= 0 && y < grid[0].length;
                    if (!isWithinGrid){
                        continue;
                    }
                    boolean isClimbable = grid[x][y] - grid[current.i()][current.j()] <= 1;
                    if (isClimbable){
                        var newCandidateValue = distances.get(current) + 1;
                        if (newCandidateValue < distances.getOrDefault(candidate, Integer.MAX_VALUE))
                            distances.put(candidate, newCandidateValue);
                        if (!visited.contains(candidate)) {
                            visited.add(candidate);
                            queue.add(candidate);
                        }
                    }
                }
            }
        }
        return distances;
    }

    public static int part1(String instructions) {
        var matrix = StringMatrixParser.parse(instructions, "\n", "");
        var costMatrix = StringMatrixParser.applyGeneric(matrix, Integer.class, (e) -> {
            if (Objects.equals(e, "S")){
                e = "a";
            } else if (Objects.equals(e, "E")){
                e = "z";
            }
            return e.chars().findFirst().getAsInt() - 96;
        });
        var start = ArrayUtilz.find(matrix, "S");
        var end = ArrayUtilz.find(matrix, "E");

        var costs = BFS(start, costMatrix);
        System.out.println(costs);
        return costs.get(end);
    }

    public static int part2(String instructions) {
        return 2;
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
