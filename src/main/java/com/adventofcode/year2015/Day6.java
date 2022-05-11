package com.adventofcode.year2015;

import java.io.InputStream;
import java.util.*;

public class Day6 {

    enum BulbOperationState {
        TOGGLE,
        OFF,
        ON;

        static BulbOperationState fromString(String s){
            var t = s.replaceAll("turn ", "").trim().toUpperCase();
            return BulbOperationState.valueOf(t);
        }
    }

    record Pair(int x, int y){}
    record Grid(Map<Pair, Integer> state){
        void on(int xc, int yx){
            var p = new Pair(xc, yx);
            this.state.put(p, 1);
        }

        void off(int xc, int yx){
            var p = new Pair(xc, yx);
            this.state.put(p, 0);
        }

        void toggle(int xc, int yx){
            var p = new Pair(xc, yx);
            var curr = this.state.getOrDefault(p, 0); //assume missing are off
            this.state.put(p, curr == 0 ? 1 : 0);
        }

        int count(){
            return this.state.values().stream().reduce(Integer::sum).orElse(0);
        }
    }

    record Grid2(Map<Pair, Integer> state){
        void on(int xc, int yx){
            var p = new Pair(xc, yx);
            var curr = this.state.getOrDefault(p, 0); //assume missing are off
            this.state.put(p, curr + 1);
        }

        void off(int xc, int yx){
            var p = new Pair(xc, yx);
            var curr = this.state.getOrDefault(p, 0); //assume missing are off
            this.state.put(p, Math.max(curr - 1, 0));
        }

        void toggle(int xc, int yx){
            var p = new Pair(xc, yx);
            var curr = this.state.getOrDefault(p, 0); //assume missing are off
            this.state.put(p, curr + 2);
        }

        int count(){
            return this.state.values().stream().reduce(Integer::sum).orElse(0);
        }
    }


    public static int part1(List<String> instructions) {
        var grid = new Grid(new HashMap<>());
        for (String i : instructions){
            String operationString = i.split("\\d+", 2)[0];
            BulbOperationState operation = BulbOperationState.fromString(operationString);
            String numberString = i.replaceAll("[^, 0-9]", "").replaceAll("\\s{2,}", " ").trim();
            int xFrom = Integer.parseInt(numberString.split(" ")[0].split(",")[0]);
            int xTo = Integer.parseInt(numberString.split(" ")[1].split(",")[0]);
            int yFrom = Integer.parseInt(numberString.split(" ")[0].split(",")[1]);
            int yTo = Integer.parseInt(numberString.split(" ")[1].split(",")[1]);
            for (int x=xFrom; x <= xTo; x++){
                for (int y=yFrom; y <= yTo; y++){
                    switch (operation) {
                        case TOGGLE -> grid.toggle(x, y);
                        case OFF -> grid.off(x, y);
                        case ON -> grid.on(x, y);
                        default -> throw new IllegalArgumentException("This is not good!");
                    }
                }
            }
        }
        return grid.count();
    }

    public static int part2(List<String> instructions) {
        var grid = new Grid2(new HashMap<>());
        for (String i : instructions){
            String operationString = i.split("\\d+", 2)[0];
            BulbOperationState operation = BulbOperationState.fromString(operationString);
            String numberString = i.replaceAll("[^, 0-9]", "").replaceAll("\\s{2,}", " ").trim();
            int xFrom = Integer.parseInt(numberString.split(" ")[0].split(",")[0]);
            int xTo = Integer.parseInt(numberString.split(" ")[1].split(",")[0]);
            int yFrom = Integer.parseInt(numberString.split(" ")[0].split(",")[1]);
            int yTo = Integer.parseInt(numberString.split(" ")[1].split(",")[1]);
            for (int x=xFrom; x <= xTo; x++){
                for (int y=yFrom; y <= yTo; y++){
                    switch (operation) {
                        case TOGGLE -> grid.toggle(x, y);
                        case OFF -> grid.off(x, y);
                        case ON -> grid.on(x, y);
                        default -> throw new IllegalArgumentException("This is not good!");
                    }
                }
            }
        }
        return grid.count();
    }

    public static void main(String[] args){
        try {
            InputStream i = Day6.class.getClassLoader().getResourceAsStream("day6.txt");
            Scanner s = new Scanner(i).useDelimiter(System.lineSeparator());
            List<String> instructions = new ArrayList<>();
            while (s.hasNextLine()){
                instructions.add(s.next());
            }
            System.out.println("Part1: " + part1(instructions));
            System.out.println("Part2: " + part2(instructions));
        } catch (Exception e){
            System.err.println("Something went poorly");
            e.printStackTrace();
        }
    }
}
