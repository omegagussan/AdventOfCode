package com.adventofcode.year2022;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Day21Test {
    @Test
    public void testSolveForX(){
        assertEquals(Long.valueOf(9), Day21.solveForX("(3+x)=12"));
    }

    @Test
    public void testSolveForX2(){
        assertEquals(Long.valueOf(-9), Day21.solveForX("(3-x)=12"));
    }

    @Test
    public void testSolveForXNotCommutation(){
        assertEquals(Long.valueOf(-12), Day21.solveForX("((3-x)-3)=12"));
    }

    @Test
    public void testSolveForXNotCommutation3(){
        assertEquals(Long.valueOf(12), Day21.solveForX("(3-(3-x))=12"));
    }

    @Test
    public void testSolveForX3(){
        assertEquals(Long.valueOf(3), Day21.solveForX("(36/(1*x))=12"));
    }

    @Test
    public void testSolveForXNotCommutation2(){
        assertEquals(Long.valueOf(2), Day21.solveForX("(24/x)=12"));
    }

    @Test
    public void testSolveForX4(){
        assertEquals(Long.valueOf(4), Day21.solveForX("(3*x)=12"));
    }

    @Test
    public void testSolveForXNested(){
        assertEquals(Long.valueOf(3), Day21.solveForX("(3+(3*x))=12"));
    }

    @Test
    public void testSolveForXNestedNested(){
        assertEquals(Long.valueOf(-1), Day21.solveForX("(12+(3+(3*x)))=12"));
    }
}

