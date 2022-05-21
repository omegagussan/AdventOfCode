
package com.adventofcode.year2015;

import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class Day7Test {
    @Test
    public void testToAndFromInt(){
        int val = 0;
        var underTest = Day7.Byte16Number.fromInt(val);
        assertEquals(underTest.toInt(), val);
    }

    @Test
    public void testFromInt2(){
        int val = 65535;
        var underTest = Day7.Byte16Number.fromInt(val);
        assertEquals(underTest.state, List.of(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1));
    }

    @Test
    public void testFrom3(){
        int val = 65535;
        var underTest = Day7.Byte16Number.fromInt(val);
        assertEquals(underTest.toInt(), val);
    }


    @Test
    public void testByte16NumberFromToInt(){
        int val = 10;
        var underTest = Day7.Byte16Number.fromInt(val);
        assertEquals(underTest.toInt(), val);
    }

    @Test
    public void testByte16NumberFromToInt2(){
        int val = 1337;
        var underTest = Day7.Byte16Number.fromInt(val);
        assertEquals(underTest.toInt(), val);
    }

    @Test
    public void testSimple(){
        Map<String, Integer> expected = Map.of(
                "x", 123,
                "y", 456
        );
        List<String> given = List.of(
                "123 -> x",
                "456 -> y"
        );
        var state = Day7.getState(given);
        var intState = state.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toInt()));
        assertEquals(expected, intState);
    }

    @Test
    public void testAnd(){
        Map<String, Integer> expected = Map.of(
                "x", 1,
                "y", 2,
                "d", 0
        );
        List<String> given = List.of(
                "1 -> x",
                "2 -> y",
                "x AND y -> d"
        );
        var state = Day7.getState(given);
        var intState = state.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toInt()));
        assertEquals(intState, expected);
    }

    @Test
    public void testAnd2(){
        Map<String, Integer> expected = Map.of(
                "x", 1,
                "y", 3,
                "d", 1
        );
        List<String> given = List.of(
                "1 -> x",
                "3 -> y",
                "x AND y -> d"
        );
        var state = Day7.getState(given);
        var intState = state.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toInt()));
        assertEquals(intState, expected);
    }

    @Test
    public void testOr(){
        Map<String, Integer> expected = Map.of(
                "x", 1,
                "y", 2,
                "d", 3
        );
        List<String> given = List.of(
                "1 -> x",
                "2 -> y",
                "x OR y -> d"
        );
        var state = Day7.getState(given);
        var intState = state.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toInt()));
        assertEquals(expected, intState);
    }

    @Test
    public void testLshift(){
        Map<String, Integer> expected = Map.of(
                "x", 1,
                "f", 4
        );
        List<String> given = List.of(
                "1 -> x",
                "x LSHIFT 2 -> f"
                );
        var state = Day7.getState(given);
        var intState = state.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toInt()));
        assertEquals(intState, expected);
    }

    @Test
    public void testRshift(){
        Map<String, Integer> expected = Map.of(
                "x", 4,
                "f", 1
        );
        List<String> given = List.of(
                "4 -> x",
                "x RSHIFT 2 -> f"
        );
        var state = Day7.getState(given);
        var intState = state.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toInt()));
        assertEquals(intState, expected);
    }

    @Test
    public void testNot(){
        Map<String, Integer> expected = Map.of(
                "x", 0,
                "f", 65535
        );
        List<String> given = List.of(
                "0 -> x",
                "NOT X -> f"
        );
        var state = Day7.getState(given);
        var intState = state.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toInt()));
        assertEquals(intState, expected);
    }

    @Test
    public void testNot2(){
        Map<String, Integer> expected = Map.of(
                "x", 65535,
                "f", 0
        );
        List<String> given = List.of(
                "65535 -> x",
                "NOT x -> f"
        );
        var state = Day7.getState(given);
        var intState = state.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toInt()));
        assertEquals(intState, expected);
    }

    @Test
    public void testCase(){
        Map<String, Integer> expected = Map.of(
                "d", 72,
                "e", 507,
                "f", 492,
                "g", 114,
                "h", 65412,
                "i", 65079,
                "x", 123,
                "y", 456
        );
        List<String> given = List.of(
                "123 -> x",
                "456 -> y",
                "x AND y -> d",
                "x OR y -> e",
                "x LSHIFT 2 -> f",
                "y RSHIFT 2 -> g",
                "NOT x -> h",
                "NOT y -> i");
        var state = Day7.getState(given);
        var intState = state.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toInt()));
        assertEquals(expected, intState);
    }


}
