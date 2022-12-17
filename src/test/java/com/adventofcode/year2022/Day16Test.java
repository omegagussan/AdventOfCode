package com.adventofcode.year2022;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.adventofcode.year2022.Day16.ValveNode;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.javatuples.Pair;
import org.junit.Test;

public class Day16Test {

    public static final String TEST_INSTRUCTION = """
        Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
        Valve BB has flow rate=13; tunnels lead to valves CC, AA
        Valve CC has flow rate=2; tunnels lead to valves DD, BB
        Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
        Valve EE has flow rate=3; tunnels lead to valves FF, DD
        Valve FF has flow rate=0; tunnels lead to valves EE, GG
        Valve GG has flow rate=0; tunnels lead to valves FF, HH
        Valve HH has flow rate=22; tunnel leads to valve GG
        Valve II has flow rate=0; tunnels lead to valves AA, JJ
        Valve JJ has flow rate=21; tunnel leads to valve II
                """;

    @Test
    public void testInput(){
        var expected = Day16.parseInput(
            TEST_INSTRUCTION).toList();
        assertEquals(new ValveNode("AA", 0, List.of("DD", "II", "BB")), expected.get(0));
        assertEquals(new ValveNode("JJ", 21, List.of("II")), expected.get(expected.size() -1));
    }

    @Test
    public void part1(){
        var expected = Day16.part1(
            TEST_INSTRUCTION);
        assertEquals(1651, expected);
    }

    @Test
    public void part2(){
        var expected = Day16.part2(
            TEST_INSTRUCTION);
        assertEquals(1707, expected);
    }

    @Test
    public void part1DistanceMap(){
        Day16.parsedInput = Day16.parseInput(TEST_INSTRUCTION).collect(
            Collectors.toMap(ValveNode::id, t -> t));

        var result = Day16.getDistances();
        Map<String, Integer> expected = Map.of("DD", 1, "BB", 1, "HH", 5, "JJ", 2, "CC", 2, "EE", 2);
        assertEquals(expected, result.get("AA"));
        //check all nodes here are non null
    }

    @Test
    public void part1DistanceMap2(){
        Day16.parsedInput = Day16.parseInput(TEST_INSTRUCTION).collect(
            Collectors.toMap(ValveNode::id, t -> t));

        var result = Day16.getDistances();
        Map<String, Integer> expected = Map.of("BB", 6, "DD", 4, "JJ", 7, "CC", 5, "EE", 3);
        assertEquals(expected, result.get("HH"));
    }

    @Test
    public void part1DistanceMap3(){
        Day16.parsedInput = Day16.parseInput(TEST_INSTRUCTION).collect(
            Collectors.toMap(ValveNode::id, t -> t));

        var result = Day16.getDistances();
        Map<String, Integer> expected = Map.of("BB", 3, "DD", 1, "HH", 3, "JJ", 4, "CC", 2);
        assertEquals(expected, result.get("EE"));
    }

    @Test
    public void part1DistanceFromAB(){
        Day16.parsedInput = Day16.parseInput(TEST_INSTRUCTION).collect(
            Collectors.toMap(ValveNode::id, t -> t));
        var m = Day16.initializeMap();
        var expected = Day16.distanceFromAB("AA", "DD", m, Set.of());
        assertEquals(1, expected);
    }

    @Test
    public void part1DistanceFromAB2(){
        Day16.parsedInput = Day16.parseInput(TEST_INSTRUCTION).collect(
            Collectors.toMap(ValveNode::id, t -> t));
        var m = Day16.initializeMap();
        var expected = Day16.distanceFromAB("AA", "CC", m, Set.of());
        assertEquals(2, expected);
    }

    @Test
    public void part1BestMove(){
        Day16.parsedInput = Day16.parseInput(TEST_INSTRUCTION).collect(
            Collectors.toMap(ValveNode::id, t -> t));
        Day16.distances = Day16.getDistances();

        var expected = Day16.bestMove("AA", Set.of(), 1);
        assertEquals(new Pair<>(0, List.of("AA")), expected);
    }

    @Test
    public void part1BestMove2(){
        Day16.parsedInput = Day16.parseInput(TEST_INSTRUCTION).collect(
            Collectors.toMap(ValveNode::id, t -> t));
        Day16.distances = Day16.getDistances();

        var expected = Day16.bestMove("AA", Set.of(), 3);
        assertEquals(new Pair<>(20, List.of("DD", "AA")), expected);
    }

    @Test
    public void part1BestMove3(){
        Day16.parsedInput = Day16.parseInput(TEST_INSTRUCTION).collect(
            Collectors.toMap(ValveNode::id, t -> t));
        Day16.distances = Day16.getDistances();


        var expected = Day16.bestMove("EE", Set.of(), 2);
        assertEquals(new Pair<>(6, List.of("EE")), expected);
    }

    @Test
    public void part1BestMove4(){
        Day16.parsedInput = Day16.parseInput(TEST_INSTRUCTION).collect(
            Collectors.toMap(ValveNode::id, t -> t));
        var distances = Day16.getDistances();
        Day16.distances = Day16.getDistances();


        var expected = Day16.bestMove("EE", Set.of(), 3);
        assertEquals(new Pair<>(29, List.of("DD", "EE")), expected);
    }
}

