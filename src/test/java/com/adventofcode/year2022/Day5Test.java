package com.adventofcode.year2022;
import static org.junit.Assert.assertEquals;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class Day5Test {
  @Test
  public void testExample() {
    var state = Map.of(1, new ArrayDeque<>(List.of("N", "Z")), 2, new ArrayDeque<>(List.of("D", "C", "M")), 3, new ArrayDeque<>(List.of("P")));
    assertEquals(
        "CMZ",
        Day5.part1(
            """
                    [D]   \s
                [N] [C]   \s
                [Z] [M] [P]
                 1   2   3\s

                move 1 from 2 to 1
                move 3 from 1 to 3
                move 2 from 2 to 1
                move 1 from 1 to 2
                """, state
        ));
  }

  @Test
  public void testExample2() {
    var state = Map.of(1, new ArrayDeque<>(List.of("N", "Z")), 2, new ArrayDeque<>(List.of("D", "C", "M")), 3, new ArrayDeque<>(List.of("P")));
    assertEquals(
        "MCD",
        Day5.part2(
            """
                    [D]   \s
                [N] [C]   \s
                [Z] [M] [P]
                 1   2   3\s

                move 1 from 2 to 1
                move 3 from 1 to 3
                move 2 from 2 to 1
                move 1 from 1 to 2
                """, state
        ));
  }
}
