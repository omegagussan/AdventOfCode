package com.adventofcode.year2022;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Day9 {
    public record Coord(Integer i, Integer j){
        <T> T getValue(T[][] matrix){
            return matrix[i][j];
        }

        Coord move(Integer i, Integer j){
            return new Coord(this.i + i, this.j + j);
        }

        Coord move(Coord delta){
            if (delta.i * delta.i > delta.j * delta.j){
                int unitI = (int) (delta.i / Math.sqrt(delta.i * delta.i));
                return new Coord(this.i + unitI, this.j);
            }
            int unitJ = (int) (delta.j / Math.sqrt(delta.j * delta.j));
            return new Coord(this.i, this.j + unitJ);
        }

        Coord moveDiagonal(Coord delta){
            int unitJ = (int) (delta.j / Math.sqrt(delta.j * delta.j));
            int unitI = (int) (delta.i / Math.sqrt(delta.i * delta.i));
            return new Coord(this.i + unitI, this.j + unitJ);
        }

        boolean isTouching(){
            return Math.abs(i) == 1 || Math.abs(j) == 1;
        }

        int magnitude(){
            return i*i + j*j;
        }

        static Coord compare(Coord a, Coord b){
            return new Coord(a.i - b.i, a.j - b.j);
        }


    }
    public static final String ROW_DELIMITER = "\n";

    public static int part2(String instructions) {
        final List<Coord> knots = new ArrayList<>(Collections.nCopies(10, new Coord(0, 0)));
        var taiVisited = new HashSet<Coord>();

        Arrays.stream(instructions.split(ROW_DELIMITER)).forEach(a -> {
            int value = Integer.parseInt(a.replaceAll("[^0-9]", ""));
            for (int i = 0; i < value; i++){
                for (int j = 1; j < 10; j++){
                    knots.set(j, followHeadSpecial(knots.get(j-1), knots.get(j)));
                }
                taiVisited.add(knots.get(9));
                switch (a.substring(0, 1)) {
                    case "U" -> knots.set(0, knots.get(0).move(1, 0));
                    case "D" -> knots.set(0, knots.get(0).move(-1, 0));
                    case "R" -> knots.set(0, knots.get(0).move(0, 1));
                    case "L" -> knots.set(0, knots.get(0).move(0, -1));
                };
                for (int j = 1; j < 10; j++){
                    knots.set(j, followHeadSpecial(knots.get(j-1), knots.get(j)));
                }
                taiVisited.add(knots.get(9));
            }
        });
        for (int j = 1; j < 10; j++){
            knots.set(j, followHeadSpecial(knots.get(j-1), knots.get(j)));
        }
        taiVisited.add(knots.get(9));
        return taiVisited.size();
    }

    public static int part1(String instructions) {
        final Coord[] head = {new Coord(0, 0)};
        final Coord[] tail = {new Coord(0, 0)};
        var taiVisited = new HashSet<Coord>();

        Arrays.stream(instructions.split(ROW_DELIMITER)).forEach(a -> {
            int value = Integer.parseInt(a.replaceAll("[^0-9]", ""));
            for (int i = 0; i < value; i++){
                followHead(head, tail, taiVisited);
                switch (a.substring(0, 1)) {
                    case "U" -> head[0] = head[0].move(1, 0);
                    case "D" -> head[0] = head[0].move(-1, 0);
                    case "R" -> head[0] = head[0].move(0, 1);
                    case "L" -> head[0] = head[0].move(0, -1);
                };
                followHead(head, tail, taiVisited);
            }
        });
        followHead(head, tail, taiVisited);

        return taiVisited.size();
    }

    private static void followHead(Coord[] head, Coord[] tail, HashSet<Coord> taiVisited) {
        var delta = Coord.compare(head[0], tail[0]);
        if (delta.magnitude() > 4){ //diagonal
            tail[0] = tail[0].moveDiagonal(delta);
            taiVisited.add(tail[0]);
        } else if (!delta.isTouching()) {
            tail[0] = tail[0].move(delta);
            taiVisited.add(tail[0]);
        }
    }

    private static Coord followHeadSpecial(Coord head, Coord tail) {
        var delta = Coord.compare(head, tail);
        if (delta.magnitude() > 4){ //diagonal
            tail = tail.moveDiagonal(delta);
        } else if (!delta.isTouching()) {
            tail = tail.move(delta);
        }
        return tail;
    }

    public static void main(String[] args){
        try {
            InputStream i = Day9.class.getClassLoader().getResourceAsStream("2022/day9.txt");
            String instructions = new String(i.readAllBytes());
            System.out.println("Part1: " + part1(instructions));
            System.out.println("Part2: " + part2(instructions));
        } catch (Exception e){
            System.err.println("Something went poorly: " + e.getCause());
        }
    }
}
