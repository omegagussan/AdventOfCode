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
    interface Operations {
        void on(int xc, int yx);
        void off(int xc, int yx);
        void toggle(int xc, int yx);
    }

    record Pair(int x, int y){}
    static class GridContainer{
        private final Grid g1;
        private final Grid2 g2;


        GridContainer(Grid g1) {
            this.g1 = g1;
            this.g2 = null;
        }

        GridContainer(Grid2 g2) {
            this.g1 = null;
            this.g2 = g2;
        }

        public Object getGrid(){
            return this.g1 == null ? this.g2 : this.g1;
        }
    }
    record Grid(Map<Pair, Integer> state) implements Operations{
        public void on(int xc, int yx){
            var p = new Pair(xc, yx);
            this.state.put(p, 1);
        }

        public void off(int xc, int yx){
            var p = new Pair(xc, yx);
            this.state.put(p, 0);
        }

        public void toggle(int xc, int yx){
            var p = new Pair(xc, yx);
            var curr = this.state.getOrDefault(p, 0); //assume missing are off
            this.state.put(p, curr == 0 ? 1 : 0);
        }

        int count(){
            return this.state.values().stream().reduce(Integer::sum).orElse(0);
        }
    }

    record Grid2(Map<Pair, Integer> state) implements Operations{
        public void on(int xc, int yx){
            var p = new Pair(xc, yx);
            var curr = this.state.getOrDefault(p, 0); //assume missing are off
            this.state.put(p, curr + 1);
        }

        public void off(int xc, int yx){
            var p = new Pair(xc, yx);
            var curr = this.state.getOrDefault(p, 0); //assume missing are off
            this.state.put(p, Math.max(curr - 1, 0));
        }

        public void toggle(int xc, int yx){
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
        followInstructions(instructions, new GridContainer(grid));
        return grid.count();
    }

    public static int part2(List<String> instructions) {
        var grid = new Grid2(new HashMap<>());
        followInstructions(instructions, new GridContainer(grid));
        return grid.count();
    }

    private static void followInstructions(List<String> instructions, GridContainer gridContainer) {
        var grid = gridContainer.getGrid();

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
                        case TOGGLE -> ((Operations) grid).toggle(x, y);
                        case OFF -> ((Operations) grid).off(x, y);
                        case ON -> ((Operations) grid).on(x, y);
                        default -> throw new IllegalArgumentException("This is not good!");
                    }
                }
            }
        }
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
