package com.adventofcode.year2019;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;
import one.util.streamex.StreamEx;

public class Day2 {

    public static final int ADD_VALUE = 1;
    public static final int TIMES_VALUE = 2;
    public static final int ABORT_VALUE = 99;
    public static final int TARGET_POS = 3;
    public static final int NUMBER_OF_VALUES_IN_INSTRUCTION = 4;
    public static final int PART_TWO_ANSWER = 19690720;

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

        public ArrayList<Integer> apply(int currPos, ArrayList<Integer> memory)
            throws InterruptedException {
            if (this == ABORT){
                throw new InterruptedException("its time to abort");
            }
            final Integer parameterOne = memory.get(currPos + 1);
            final Integer parameterTwo = memory.get(currPos + 2);
            final Integer parameterThree = memory.get(currPos + TARGET_POS);
            switch (this){
                case ADD -> memory.set(parameterThree, memory.get(parameterOne) + memory.get(parameterTwo));
                case TIMES -> {
                    memory.set(parameterThree, memory.get(parameterOne) * memory.get(parameterTwo));
                }
                default -> throw new IllegalStateException("unknown OP_CODE");
            }
            return memory;
        }
    }

    public static int part1(String instructions) {
        var memory = new ArrayList<>(
            Arrays.stream(instructions.split(",")).map(Integer::valueOf).toList());
        memory = operateOnMemory(memory);
        return memory.get(0);
    }

    static ArrayList<Integer> operateOnMemory(ArrayList<Integer> memory) {
        int instructionPointer = 0;
        try {
            while (true){
                OP_CODE currCode = OP_CODE.fromInt(memory.get(instructionPointer));
                currCode.apply(instructionPointer, memory);

                //lookahead
                instructionPointer += NUMBER_OF_VALUES_IN_INSTRUCTION;
            }
        } catch (InterruptedException ignored){}
        //pos 0 when program ends.
        return memory;
    }

    static <T> UnaryOperator<StreamEx<T>> takeUntil(Predicate<? super T> predicate){
        var last = new AtomicReference<T>();
        Predicate<? super T> wrapped = (Predicate<T>) t -> {
            var outcome = predicate.test(t);
            if (!outcome){
                last.set(t);
            }
            return outcome;
        };
        return (stream -> StreamEx.of(
            stream.takeWhile(wrapped).<List<T>>toListAndThen(ts -> {ts.add(last.get()); return ts;})));
    }




    public static int part2(String instructions) {
        final var memory = new ArrayList<>(
            Arrays.stream(instructions.split(",")).map(Integer::valueOf).toList());
        var argumentPairs = IntStream.range(0, 99)
            .boxed()
            .flatMap(noun -> IntStream.range(0, 99).mapToObj(verb -> new Pair(noun, verb)))
            .toList();
        var output = StreamEx.of(argumentPairs)
            .chain(takeUntil(pair -> {
            var memoryCopy = new ArrayList<>(memory);
            memoryCopy.set(1, pair.noun);
            memoryCopy.set(2, pair.verb);
            memoryCopy = operateOnMemory(memoryCopy);
            return memoryCopy.get(0) != PART_TWO_ANSWER;
        })).reduce((first, second) -> second);
        return 100 * output.get().noun + output.get().verb;
    }

    record Pair(Integer noun, Integer verb){}

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
