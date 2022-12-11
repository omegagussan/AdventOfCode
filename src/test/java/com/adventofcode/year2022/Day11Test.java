package com.adventofcode.year2022;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigInteger;
import java.util.function.Function;
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
            testData));
    }

    @Test
    public void testOperatorTestSquare() {
        Function<Integer, Integer> squareFunction = Day11.parseOperation(
            testData.split("\n\n")[2]);
        assertEquals(Integer.valueOf("0"), squareFunction.apply(0));
        assertEquals(Integer.valueOf("4"), squareFunction.apply(2));
    }

    @Test
    public void testOperatorTestMultiplication() {
    Function<Integer, Integer> squareFunction = Day11.parseOperation(testData.split("\n\n")[0]);
        assertEquals(Integer.valueOf("0"), squareFunction.apply(0));
        assertEquals(Integer.valueOf("19"), squareFunction.apply(1));
    }

    @Test
    public void testOperatorTestAddition() {
    Function<Integer, Integer> squareFunction = Day11.parseOperation(testData.split("\n\n")[1]);
        assertEquals(Integer.valueOf("6"), squareFunction.apply(0));
        assertEquals(Integer.valueOf("7"), squareFunction.apply(1));
    }
    @Test
    public void testExample2() throws IOException {
        assertEquals(
            new BigInteger("2713310158"),
            Day11.part2(
                testData));
    }
}

