package com.adventofcode.year2022;

import com.adventofcode.utils.Point;
import com.adventofcode.utils.Vector;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class Day9 {
    public static final String ROW_DELIMITER = "\n";

    static boolean isTouching(Vector v){
        return Math.abs(v.i()) == 1 || Math.abs(v.j()) == 1;
    }

    private static void oneStepForHead(List<Point> knots, String instruction) {
        switch (instruction.substring(0, 1)) {
            case "U" -> knots.set(0, knots.get(0).move(1, 0));
            case "D" -> knots.set(0, knots.get(0).move(-1, 0));
            case "R" -> knots.set(0, knots.get(0).move(0, 1));
            case "L" -> knots.set(0, knots.get(0).move(0, -1));
        }
    }

    private static void updateTailAndTailVisited(List<Point> knots, HashSet<Point> taiVisited) {
        for (int j = 1; j < knots.size(); j++){
            knots.set(j, followHead(knots.get(j-1), knots.get(j)));
        }
        taiVisited.add(knots.get(knots.size()-1));
    }

    private static Point followHead(Point head, Point tail) {
        var delta = Point.compare(head, tail);
        if (delta.magnitude() > 4){ //diagonal
            tail = new Point(tail.i() + (int) Math.signum(delta.i()),
                tail.j() + (int) Math.signum(delta.j()));
        } else if (!isTouching(delta)) {
            tail = tail.move(delta);
        }
        return tail;
    }

    @NotNull
    private static HashSet<Point> getTailVisited(String instructions, List<Point> knots) {
        var taiVisited = new HashSet<Point>();

        Arrays.stream(instructions.split(ROW_DELIMITER)).forEach(instruction -> {
            int value = Integer.parseInt(instruction.replaceAll("[^0-9]", ""));
            for (int i = 0; i < value; i++){
                updateTailAndTailVisited(knots, taiVisited);
                oneStepForHead(knots, instruction);
                updateTailAndTailVisited(knots, taiVisited);
            }
        });
        updateTailAndTailVisited(knots, taiVisited);
        return taiVisited;
    }

    public static int part1(String instructions) {
        List<Point> knots = new ArrayList<>(Collections.nCopies(2, new Point(0, 0)));
        HashSet<Point> taiVisited = getTailVisited(instructions, knots);
        return taiVisited.size();
    }

    public static int part2(String instructions) {
        final List<Point> knots = new ArrayList<>(Collections.nCopies(10, new Point(0, 0)));
        HashSet<Point> tailVisited = getTailVisited(instructions, knots);
        return tailVisited.size();
    }

    public static void main(String[] args){
        try {
            InputStream i = Day9.class.getClassLoader().getResourceAsStream("2022/day9.txt");
            String instructions = new String(i.readAllBytes());
            System.out.println("Part1: " + part1(instructions));
            System.out.println("Part2: " + part2(instructions));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
