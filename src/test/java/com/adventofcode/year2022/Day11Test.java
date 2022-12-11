package com.adventofcode.year2022;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

public class Day11Test {

    public static final String testData = """
        Monkey 0:
          Starting items: 79, 98
          Operation: new = old * 19
          Test: divisible by 23
            If true: throw to monkey 2
            If false: throw to monkey 3

        Monkey 1:
          Starting items: 54, 65, 75, 74
          Operation: new = old + 6
          Test: divisible by 19
            If true: throw to monkey 2
            If false: throw to monkey 0

        Monkey 2:
          Starting items: 79, 60, 97
          Operation: new = old * old
          Test: divisible by 13
            If true: throw to monkey 1
            If false: throw to monkey 3

        Monkey 3:
          Starting items: 74
          Operation: new = old + 3
          Test: divisible by 17
            If true: throw to monkey 0
            If false: throw to monkey 1
        """;

    @Test
    public void testExample() throws IOException {
    assertEquals(
        10605,
        Day11.part1(
            testData, (x) -> (long) Math.floor(x/3), 20));
    }

    @Test
    public void testOperatorTestSquare() {
        Function<Long, Long> squareFunction = Day11.parseOperation(
            testData.split("\n\n")[2]);
        assertEquals(Long.valueOf(0), squareFunction.apply(0L));
        assertEquals(Long.valueOf(4L), squareFunction.apply(2L));
    }

    @Test
    public void testOperatorTestMultiplication() {
    Function<Long, Long> squareFunction = Day11.parseOperation(testData.split("\n\n")[0]);
        assertEquals(Long.valueOf(0L), squareFunction.apply(0L));
        assertEquals(Long.valueOf(19L), squareFunction.apply(1L));
    }

    @Test
    public void testOperatorTestMultiplicationBigNumbers() {
        Function<Long, Long> squareFunction = Day11.parseOperation(testData.split("\n\n")[2]);
        Long l = (long) (19 * 2 * 13 * 5 * 7 * 11 * 17);
        Long outcome = squareFunction.apply(l);
        long expected = l * l;
        assertEquals(Long.valueOf(expected), outcome);
    }

    @Test
    public void testOperatorTestAddition() {
    Function<Long, Long> squareFunction = Day11.parseOperation(testData.split("\n\n")[1]);
        assertEquals(Long.valueOf(6L), squareFunction.apply(0L));
        assertEquals(Long.valueOf(7L), squareFunction.apply(1L));
    }

    @Test
    public void testTestFn() {
        @NotNull Function<Long, Boolean> testFn = Day11.getTestFn(
            testData.split("\n\n")[0]);
        assertTrue(testFn.apply(23L));
        assertFalse(testFn.apply(24L));
    }

    @Test
    public void testTestFn2() {
        @NotNull Function<Long, Boolean> testFn = Day11.getTestFn(
            testData.split("\n\n")[1]);
        assertTrue(testFn.apply(19L));
        assertFalse(testFn.apply(20L));
    }

    @Test
    public void testTarget() {
        Integer falseCase = Day11.getTarget(testData.split("\n\n")[0], "false");
        Integer trueCase = Day11.getTarget(testData.split("\n\n")[0], "true");

        assertEquals(Integer.valueOf("2"), trueCase);
        assertEquals(Integer.valueOf("3"), falseCase);
    }

    @Test
    public void testTarget2() {
        Integer falseCase = Day11.getTarget(testData.split("\n\n")[1], "false");
        Integer trueCase = Day11.getTarget(testData.split("\n\n")[1], "true");

        assertEquals(Integer.valueOf("2"), trueCase);
        assertEquals(Integer.valueOf("0"), falseCase);
    }

    @Test
    public void testExample2() {
        assertEquals(
            Long.valueOf(2713310158L),
            Day11.part2(
                testData, (x) -> x % (23 * 13 * 19 * 17), 10000));
    }
}

