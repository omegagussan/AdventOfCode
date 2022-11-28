package com.adventofcode.year2019;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class Day2 {

    public static final int ADD_VALUE = 1;
    public static final int TIMES_VALUE = 2;
    public static final int ABORT_VALUE = 99;
    public static final int TARGET_POS = 3;
    public static final int NEXT_POS_STEP = 4;

    enum OP_CODE{
        ADD(ADD_VALUE),
        TIMES(TIMES_VALUE),
        ABORT(ABORT_VALUE);
        private final int value;
        OP_CODE(int value){
            this.value = value;
        }

        public static OP_CODE fromInt(int value) {
            switch (value){
                case ADD_VALUE -> {
                    return ADD;
                }
                case TIMES_VALUE -> {
                    return TIMES;
                }
                case ABORT_VALUE -> {
                    return ABORT;
                }
                default -> throw new IllegalStateException("unknown OP_CODE");
            }
        }

        public ArrayList<Integer> apply(int currPos, ArrayList<Integer> state)
            throws InterruptedException {
            if (this == ABORT){
                throw new InterruptedException("its time to abort");
            }
            final Integer termOneIndex = state.get(currPos + 1);
            final Integer termTwoIndex = state.get(currPos + 2);
            final Integer targetIndex = state.get(currPos + TARGET_POS);
            switch (this){
                case ADD -> state.set(targetIndex, state.get(termOneIndex) + state.get(termTwoIndex));
                case TIMES -> {
                    state.set(targetIndex, state.get(termOneIndex) * state.get(termTwoIndex));
                }
                default -> throw new IllegalStateException("unknown OP_CODE");
            }
            return state;
        }
    }

    public static int part1(String instructions) {
        ArrayList<Integer> state = doStateWork(instructions);
        return state.get(0);
    }

    static ArrayList<Integer> doStateWork(String instructions) {
        var state = new ArrayList<>(
            Arrays.stream(instructions.split(",")).map(Integer::valueOf).toList());
        int currPos = 0;
        try {
            while (true){
                OP_CODE currCode = OP_CODE.fromInt(state.get(currPos));
                currCode.apply(currPos, state);

                //lookahead
                currPos += NEXT_POS_STEP;
            }
        } catch (InterruptedException ignored){}
        //pos 0 when program ends.
        return state;
    }

    //when enter basement
    public static int part2(String instructions) {
        return 0;
    }

    public static void main(String[] args){
        try {
            InputStream i = Day2.class.getClassLoader().getResourceAsStream("2019/day2.txt");
            String instructions = new String(i.readAllBytes());
            System.out.println("Part1: " + part1(instructions));
            System.out.println("Part2: " + part2(instructions));
        } catch (Exception e){
            System.err.println("Something went poorly: " + e.getCause());
        }
    }
}
