package com.adventofcode.year2022;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.Test;

public class Day20Test {

    public static final String TEST_INSTRUCTION = """
    1
    2
    -3
    3
    -2
    0
    4
  """;
    @Test
    public void testPart1(){
        assertEquals(3, Day20.part1(TEST_INSTRUCTION));
    }

    @Test
    public void testGetAnswerList(){
        var given = List.of(1, 2, -3, 4, 0, 3, -2);
        var result = Day20.getAnswer(given);
        List<Integer> expected = List.of(4, - 3, 2);
        assertEquals(3, result.size());
        assertEquals(expected, result);
    }

//    @Test
//    public void testNoMove(){
//        var originalOrderValues = Arrays.stream(TEST_INSTRUCTION.split(ROW_DELIMITER))
//            .map(s -> Integer.valueOf(s.trim()))
//            .toList();
//        var originalOrder = IntStream.range(0,originalOrderValues.size())
//            .mapToObj(z -> new Pair<>(z, originalOrderValues.get(z))).toList();
//        Deque<Pair<Integer, Integer>> res = Day20.extracted(0, originalOrder);
//        assertEquals(List.of(1, 2, -3, 3, -2, 0, 4), res.stream().map(Pair::getValue1).toList());
//    }
//
//    @Test
//    public void testOneMove(){
//        var originalOrderValues = Arrays.stream(TEST_INSTRUCTION.split(ROW_DELIMITER))
//            .map(s -> Integer.valueOf(s.trim()))
//            .toList();
//        var originalOrder = IntStream.range(0,originalOrderValues.size())
//            .mapToObj(z -> new Pair<>(z, originalOrderValues.get(z))).toList();
//        Deque<Pair<Integer, Integer>> res = Day20.extracted(1, originalOrder);
//        assertEquals(List.of(2, 1, -3, 3, -2, 0, 4), res.stream().map(Pair::getValue1).toList());
//    }
//
//    @Test
//    public void testTwoMove(){
//        var originalOrderValues = Arrays.stream(TEST_INSTRUCTION.split(ROW_DELIMITER))
//            .map(s -> Integer.valueOf(s.trim()))
//            .toList();
//        var originalOrder = IntStream.range(0,originalOrderValues.size())
//            .mapToObj(z -> new Pair<>(z, originalOrderValues.get(z))).toList();
//        Deque<Pair<Integer, Integer>> res = Day20.extracted(2, originalOrder);
//        assertEquals(List.of(1, -3, 2, 3, -2, 0, 4), res.stream().map(Pair::getValue1).toList());
//    }
//
//    @Test
//    public void testThreeMove(){
//        var originalOrderValues = Arrays.stream(TEST_INSTRUCTION.split(ROW_DELIMITER))
//            .map(s -> Integer.valueOf(s.trim()))
//            .toList();
//        var originalOrder = IntStream.range(0,originalOrderValues.size())
//            .mapToObj(z -> new Pair<>(z, originalOrderValues.get(z))).toList();
//        Deque<Pair<Integer, Integer>> res = Day20.extracted(3, originalOrder);
//        assertEquals(List.of(1, 2, 3, -2, -3, 0, 4), res.stream().map(Pair::getValue1).toList());
//    }
//
//    @Test
//    public void testFourMove(){
//        var originalOrderValues = Arrays.stream(TEST_INSTRUCTION.split(ROW_DELIMITER))
//            .map(s -> Integer.valueOf(s.trim()))
//            .toList();
//        var originalOrder = IntStream.range(0,originalOrderValues.size())
//            .mapToObj(z -> new Pair<>(z, originalOrderValues.get(z))).toList();
//        Deque<Pair<Integer, Integer>> res = Day20.extracted(4, originalOrder);
//        assertEquals(List.of(1, 2, -2, -3, 0, 3, 4), res.stream().map(Pair::getValue1).toList());
//    }
//
//    @Test
//    public void testFiveMove(){
//        var originalOrderValues = Arrays.stream(TEST_INSTRUCTION.split(ROW_DELIMITER))
//            .map(s -> Integer.valueOf(s.trim()))
//            .toList();
//        var originalOrder = IntStream.range(0,originalOrderValues.size())
//            .mapToObj(z -> new Pair<>(z, originalOrderValues.get(z))).toList();
//        Deque<Pair<Integer, Integer>> res = Day20.extracted(5, originalOrder);
//        assertEquals(List.of(1, 2, -3, 0, 3, 4, -2), res.stream().map(Pair::getValue1).toList());
//    }
//
//    @Test
//    public void testSixMove(){
//        var originalOrderValues = Arrays.stream(TEST_INSTRUCTION.split(ROW_DELIMITER))
//            .map(s -> Integer.valueOf(s.trim()))
//            .toList();
//        var originalOrder = IntStream.range(0,originalOrderValues.size())
//            .mapToObj(z -> new Pair<>(z, originalOrderValues.get(z))).toList();
//        Deque<Pair<Integer, Integer>> res = Day20.extracted(6, originalOrder);
//        assertEquals(List.of(1, 2, -3, 0, 3, 4, -2), res.stream().map(Pair::getValue1).toList());
//    }
//
//    @Test
//    public void testSevenMove(){
//        var originalOrderValues = Arrays.stream(TEST_INSTRUCTION.split(ROW_DELIMITER))
//            .map(s -> Integer.valueOf(s.trim()))
//            .toList();
//        var originalOrder = IntStream.range(0,originalOrderValues.size())
//            .mapToObj(z -> new Pair<>(z, originalOrderValues.get(z))).toList();
//        Deque<Pair<Integer, Integer>> res = Day20.extracted(7, originalOrder);
//        assertEquals(List.of(1, 2, -3, 4, 0, 3, -2), res.stream().map(Pair::getValue1).toList());
//    }
//
//    @Test
//    public void testMoveNegative(){
//        var originalOrderValues = List.of(0,0,0,-1,0,0,0);
//        var originalOrder = IntStream.range(0,originalOrderValues.size())
//            .mapToObj(z -> new Pair<>(z, originalOrderValues.get(z))).toList();
//        Deque<Pair<Integer, Integer>> res = Day20.extracted(7, originalOrder);
//        assertEquals(List.of(0,0,-1,0,0,0,0), res.stream().map(Pair::getValue1).toList());
//    }
}

