package com.adventofcode.year2015;

import com.google.common.collect.Streams;

import java.io.InputStream;
import java.util.*;
import java.util.stream.IntStream;

public class Day7 {
    public static Byte16Number add(Byte16Number first, Byte16Number second) {
        return Byte16Number.fromInt(first.toInt() + second.toInt());
    }

    static class Byte16Number{
        public static final int CAPACITY = 16;
        List<Integer> state;

        private Byte16Number(List<Integer> state){
            this.state = state;
        }
        public Byte16Number lshift(int number){
            var stateCopy = new ArrayList<>(state);
            Collections.rotate(stateCopy, number);
            return new Byte16Number(stateCopy);
        }
        public Byte16Number rshift(int number){
            var stateCopy = new ArrayList<>(state);
            Collections.rotate(stateCopy, -number);
            return new Byte16Number(stateCopy);
        }

        public int toInt(){
            return IntStream.range(0, state.size())
                    .filter(value -> state.get(value) > 0)
                    .reduce(0, (sum, element) -> (int) (sum + Math.pow(2, element)));
        }

        public Byte16Number bitwiseComplement(){
            var complementState = this.state.stream().map(integer -> integer == 1 ? 0 : 1).toList();
            return new Byte16Number(complementState);
        }

        public static Byte16Number fromInt(int number){
            int tmp = number;
            List<Integer> state = new ArrayList<>(Collections.nCopies(CAPACITY, 0));
            for (int i=CAPACITY-1; i > -1; i--){
                int elem = (int) Math.pow(2, i);
                if (tmp >= elem){
                    state.set(i, 1);
                    tmp = tmp - elem;
                }
            }
            return new Byte16Number(state);
        }

    }
    public static final String AND = "AND";
    public static final String OR = "OR";
    public static final String LSHIFT = "LSHIFT";
    public static final String RSHIFT = "RSHIFT";
    public static final String NOT = "NOT";
    public static final List<String> OPERATIONS = List.of(AND, OR, LSHIFT, RSHIFT, NOT);

    public static boolean isAlpha(String name) {
        return name.matches("[a-zA-Z]+");
    }

    public static int part1(List<String> instructions){
        HashMap<String, Byte16Number> state = getState(instructions);
        System.out.println(state);
        return getOrDefault(state, "a").toInt();
    }
    static HashMap<String, Byte16Number> getState(List<String> instructions) {
        HashMap<String, Byte16Number> state = new HashMap<>();
        for (String i: instructions){
            String[] parts = i.split(" -> ");
            String target = parts[1].trim();
            String expression = parts[0].trim();
            if (OPERATIONS.stream().noneMatch(expression::contains)){
                if (isAlpha(expression)){
                    var source = getOrDefault(state, expression);
                    state.put(target, source);
                } else {
                    state.put(target, Byte16Number.fromInt(Integer.parseInt(expression)));
                }
            } else if (expression.contains(RSHIFT)) {
                String shiftTarget = expression.split(RSHIFT)[0].trim();
                int offset = Integer.parseInt(expression.split(RSHIFT)[1].trim());
                var old = getOrDefault(state, shiftTarget);
                state.put(target, old.rshift(offset));
            } else if (expression.contains(LSHIFT)) {
                String shiftTarget = expression.split(LSHIFT)[0].trim();
                int offset = Integer.parseInt(expression.split(LSHIFT)[1].trim());
                var old = getOrDefault(state, shiftTarget);
                state.put(target, old.lshift(offset));
            } else if (expression.contains(AND)) {
                String[] shiftTargets = expression.split(AND);
                var a = getOrDefault(state, shiftTargets[0].trim());
                var b = getOrDefault(state, shiftTargets[1].trim());
                var novelState= Streams.zip(a.state.stream(), b.state.stream(), (x,y) ->  x > 0 && y > 0 ? 1 : 0).toList();
                state.put(target, new Byte16Number(novelState));
            } else if (expression.contains(NOT)){
                String source = expression.split(" ")[1];
                state.put(target, getOrDefault(state, source).bitwiseComplement());
            } else if (expression.contains(OR)){
                String[] sources = expression.split(OR);
                var a = getOrDefault(state, sources[0].trim());
                var b = getOrDefault(state, sources[1].trim());
                var novelState= Streams.zip(a.state.stream(), b.state.stream(), (x,y) ->  x > 0 || y > 0 ? 1 : 0).toList();
                state.put(target, new Byte16Number(novelState));}
            else {
                throw new IllegalArgumentException("not implemented yet");
            }
        }
        return state;
    }

    private static Byte16Number getOrDefault(HashMap<String, Byte16Number> state, String shiftTargets) {
        return state.getOrDefault(shiftTargets, Byte16Number.fromInt(0));
    }

    public static void main(String[] args){
        try {
            InputStream i = Day7.class.getClassLoader().getResourceAsStream("day7.txt");
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
