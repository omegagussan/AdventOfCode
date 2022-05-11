package com.adventofcode.year2015;

import com.google.common.collect.Streams;

import java.io.InputStream;
import java.util.*;

public class Day6 {

    public static final String TOGGLE = "toggle";
    public static final String OFF = "off";
    public static final String ON = "on";

    record Pair(int x, int y){}
    record Grid(Map<Pair, Boolean> state){
        void on(int xc, int yx){
            var p = new Pair(xc, yx);
            this.state.put(p, true);
        }

        void off(int xc, int yx){
            var p = new Pair(xc, yx);
            this.state.put(p, false);
        }

        void toggle(int xc, int yx){
            var p = new Pair(xc, yx);
            var curr = this.state.getOrDefault(p, false); //assume missing are off
            this.state.put(p, !curr);
        }

        int count(){
            return (int) this.state.values().stream().filter(aBoolean -> aBoolean).count();
        }
    }

    public static int part1(List<String> instructions) {
        var grid = new Grid(new HashMap<>());
        for (String i : instructions){
            String operation = i.contains(TOGGLE) ? TOGGLE : i.contains(OFF) ? OFF : ON;
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
        } catch (Exception e){
            System.err.println("Something went poorly");
            e.printStackTrace();
        }
    }
}
