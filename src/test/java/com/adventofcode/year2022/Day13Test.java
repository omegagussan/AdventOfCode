package com.adventofcode.year2022;
import static org.junit.Assert.assertEquals;

import com.google.common.collect.Streams;
import java.util.Arrays;
import java.util.List;
import org.javatuples.Pair;
import org.junit.Test;

public class Day13Test {

    @Test
    public void test1(){
        assertEquals(2, Day13.isCorrect("[1,1,3,1,1]", "[1,1,5,1,1]"));
    }

    @Test
    public void test2(){
        assertEquals(2, Day13.isCorrect("[[1],[2,3,4]]", "[[1],4]"));
    }

    @Test
    public void test3(){
        assertEquals(-1, Day13.isCorrect("[9]", "[[8,7,6]]"));
    }

    @Test
    public void test4(){
        assertEquals(1, Day13.isCorrect("[[4,4],4,4]", "[[4,4],4,4,4]"));
    }

    @Test
    public void test5(){
        assertEquals(-1, Day13.isCorrect("[7,7,7,7]", "[7,7,7]"));
    }

    @Test
    public void test6(){
        assertEquals(1, Day13.isCorrect("[]", "[3]"));
    }

    @Test
    public void test7(){
        assertEquals(-1, Day13.isCorrect("[[[]]]", "[[]]"));
    }

    @Test
    public void test8(){
        assertEquals(-7, Day13.isCorrect("[1,[2,[3,[4,[5,6,7]]]],8,9]", "[1,[2,[3,[4,[5,6,0]]]],8,9]"));
    }

    @Test
    public void testZip() {
    var run =
        Streams.zip(
                Arrays.stream(new String[] {"a", "b"}),
                Arrays.stream(new String[] {"1", "2", "3"}),
                Pair::new)
            .toList();
    assertEquals(List.of(new Pair("a", "1"), new Pair("b", "2")), run);
    }

    @Test
    public void testParseStringArrays() {
        assertEquals(List.of("[1]", "[2,3,4]"), Day13.parseStringArrays("[1],[2,3,4]"));
    }

    @Test
    public void testParseStringArrays2() {
        assertEquals(List.of("[1]", "4"), Day13.parseStringArrays("[1],4"));
    }

    @Test
    public void testParseStringArrays3() {
        assertEquals(List.of("1", "[2,[3,[4,[5,6,7]]]]", "8", "9"), Day13.parseStringArrays("1,[2,[3,[4,[5,6,7]]]],8,9"));
    }

    @Test
    public void testExample(){
    assertEquals(
        13,
        Day13.part1(
            """
        [1,1,3,1,1]
        [1,1,5,1,1]
    
        [[1],[2,3,4]]
        [[1],4]
    
        [9]
        [[8,7,6]]
    
        [[4,4],4,4]
        [[4,4],4,4,4]
    
        [7,7,7,7]
        [7,7,7]
    
        []
        [3]
    
        [[[]]]
        [[]]
    
        [1,[2,[3,[4,[5,6,7]]]],8,9]
        [1,[2,[3,[4,[5,6,0]]]],8,9]
        """));
    }

    @Test
    public void testExample2(){
        assertEquals(
            140,
            Day13.part2(
                """
            [1,1,3,1,1]
            [1,1,5,1,1]
        
            [[1],[2,3,4]]
            [[1],4]
        
            [9]
            [[8,7,6]]
        
            [[4,4],4,4]
            [[4,4],4,4,4]
        
            [7,7,7,7]
            [7,7,7]
        
            []
            [3]
        
            [[[]]]
            [[]]
        
            [1,[2,[3,[4,[5,6,7]]]],8,9]
            [1,[2,[3,[4,[5,6,0]]]],8,9]
            """));
    }

}

