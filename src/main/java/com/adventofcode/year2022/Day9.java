package com.adventofcode.year2022;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Day9 {
    public static final String ROW_DELIMITER = "\n";
    public record Coord(Integer i, Integer j){

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

    private static void oneStepForHead(List<Coord> knots, String instruction) {
        switch (instruction.substring(0, 1)) {
            case "U" -> knots.set(0, knots.get(0).move(1, 0));
            case "D" -> knots.set(0, knots.get(0).move(-1, 0));
            case "R" -> knots.set(0, knots.get(0).move(0, 1));
            case "L" -> knots.set(0, knots.get(0).move(0, -1));
        }
        ;
    }

    private static void updateCoordsAndTailVisited(List<Coord> knots, HashSet<Coord> taiVisited) {
        for (int j = 1; j < 10; j++){
            knots.set(j, followHead(knots.get(j-1), knots.get(j)));
        }
        taiVisited.add(knots.get(9));
    }

    private static void updateTailAndTailVisited(List<Coord> knots, HashSet<Coord> taiVisited) {
        knots.set(1, followHead(knots.get(0), knots.get(1)));
        taiVisited.add(knots.get(1));
    }

    private static Coord followHead(Coord head, Coord tail) {
        var delta = Coord.compare(head, tail);
        if (delta.magnitude() > 4){ //diagonal
            tail = tail.moveDiagonal(delta);
        } else if (!delta.isTouching()) {
            tail = tail.move(delta);
        }
        return tail;
    }

    public static int part1(String instructions) {
        List<Coord> knots = new ArrayList<>(Collections.nCopies(2, new Coord(0, 0)));
        var taiVisited = new HashSet<Coord>();

        Arrays.stream(instructions.split(ROW_DELIMITER)).forEach(instruction -> {
            int value = Integer.parseInt(instruction.replaceAll("[^0-9]", ""));
            for (int i = 0; i < value; i++){
                updateTailAndTailVisited(knots, taiVisited);
                oneStepForHead(knots, instruction);
                updateTailAndTailVisited(knots, taiVisited);
            }
        });
        updateTailAndTailVisited(knots, taiVisited);

        return taiVisited.size();
    }

    public static int part2(String instructions) {
        final List<Coord> knots = new ArrayList<>(Collections.nCopies(10, new Coord(0, 0)));
        var tailVisited = new HashSet<Coord>();

        Arrays.stream(instructions.split(ROW_DELIMITER)).forEach(instruction -> {
            int numberOfSteps = Integer.parseInt(instruction.replaceAll("[^0-9]", ""));
            for (int i = 0; i < numberOfSteps; i++){
                updateCoordsAndTailVisited(knots, tailVisited);
                oneStepForHead(knots, instruction);
                updateCoordsAndTailVisited(knots, tailVisited);
            }
        });
        updateCoordsAndTailVisited(knots, tailVisited);
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
