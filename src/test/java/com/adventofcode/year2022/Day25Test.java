package com.adventofcode.year2022;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Day25Test {

  String INSTRUCTIONS =
      """
        1=-0-2
        12111
        2=0=
        21
        2=01
        111
        20012
        112
        1=-1=
        1-12
        12
        1=
        122""";

      @Test
      public void testParseSNAFUSum() {
        assertEquals(4890, Day25.parseSNAFUSum(INSTRUCTIONS));
      }

    @Test
    public void testPart1() {
        assertEquals("2=-1=0", Day25.part1(INSTRUCTIONS));
    }

    @Test
    public void fromSNAFU0() {
        assertEquals(0, Day25.fromSNAFU("0"));
    }
    @Test
    public void fromSNAFU1() {
        assertEquals(1, Day25.fromSNAFU("1"));
    }
    @Test
    public void fromSNAFU3() {
        assertEquals(3, Day25.fromSNAFU("1="));
    }
    @Test
    public void fromSNAFU5() {
        assertEquals(5, Day25.fromSNAFU("10"));
    }
    @Test
    public void fromSNAFU9() {
        assertEquals(9, Day25.fromSNAFU("2-"));
    }

    @Test
    public void toSNAFU0() {
        assertEquals("0", Day25.toSNAFU(0));
    }
    @Test
    public void toSNAFU1() {
        assertEquals("1", Day25.toSNAFU(1));
    }
    @Test
    public void toSNAFU4() {
        assertEquals("1-", Day25.toSNAFU(4));
    }
    @Test
    public void toSNAFU11() {
        assertEquals("21", Day25.toSNAFU(11));
    }
    @Test
    public void toSNAFU1747() {
        assertEquals("1=-0-2", Day25.toSNAFU(1747));
    }
}