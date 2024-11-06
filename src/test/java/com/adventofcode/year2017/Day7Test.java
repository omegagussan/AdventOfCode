package com.adventofcode.year2017;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class Day7Test {
    @Test
    public void testParseDiscs() {
        List<String> given = List.of("pbga (66)", "xhth (57)", "ebii (61)", "havc (66)", "ktlj (57)", "fwft (72) -> ktlj, cntj, xhth", "qoyq (66)", "padx (45) -> pbga, havc, qoyq", "tknk (41) -> ugml, padx, fwft", "jptl (61)", "ugml (68) -> gyxo, ebii, jptl", "gyxo (61)", "cntj (57)");
        assertEquals("tknk", Day7.parseDiscs(given).name());
    }

    @Test
    public void testBalanceDisc() {
        List<String> given = List.of("pbga (66)", "xhth (57)", "ebii (61)", "havc (66)", "ktlj (57)", "fwft (72) -> ktlj, cntj, xhth", "qoyq (66)", "padx (45) -> pbga, havc, qoyq", "tknk (41) -> ugml, padx, fwft", "jptl (61)", "ugml (68) -> gyxo, ebii, jptl", "gyxo (61)", "cntj (57)");
        assertEquals(60, Day7.balanceDisc(Day7.parseDiscs(given)));
    }

    @Test
    public void testBalanceDisc1() {
        List<String> given = List.of("fwft (72) -> ktlj, cntj, xhth", "ktlj (57)", "cntj (56)", "xhth (57)");
        assertEquals(57, Day7.balanceDisc(Day7.parseDiscs(given)));
    }
}
