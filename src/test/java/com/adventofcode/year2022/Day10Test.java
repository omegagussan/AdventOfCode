package com.adventofcode.year2022;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Day10Test {
    @Test
    public void testExample(){
    assertEquals(
        13140,
        Day10.part1(
            """
                addx 15
                addx -11
                addx 6
                addx -3
                addx 5
                addx -1
                addx -8
                addx 13
                addx 4
                noop
                addx -1
                addx 5
                addx -1
                addx 5
                addx -1
                addx 5
                addx -1
                addx 5
                addx -1
                addx -35
                addx 1
                addx 24
                addx -19
                addx 1
                addx 16
                addx -11
                noop
                noop
                addx 21
                addx -15
                noop
                noop
                addx -3
                addx 9
                addx 1
                addx -3
                addx 8
                addx 1
                addx 5
                noop
                noop
                noop
                noop
                noop
                addx -36
                noop
                addx 1
                addx 7
                noop
                noop
                noop
                addx 2
                addx 6
                noop
                noop
                noop
                noop
                noop
                addx 1
                noop
                noop
                addx 7
                addx 1
                noop
                addx -13
                addx 13
                addx 7
                noop
                addx 1
                addx -33
                noop
                noop
                noop
                addx 2
                noop
                noop
                noop
                addx 8
                noop
                addx -1
                addx 2
                addx 1
                noop
                addx 17
                addx -9
                addx 1
                addx 1
                addx -3
                addx 11
                noop
                noop
                addx 1
                noop
                addx 1
                noop
                noop
                addx -13
                addx -19
                addx 1
                addx 3
                addx 26
                addx -30
                addx 12
                addx -1
                addx 3
                addx 1
                noop
                noop
                noop
                addx -9
                addx 18
                addx 1
                addx 2
                noop
                noop
                addx 9
                noop
                noop
                noop
                addx -1
                addx 2
                addx -37
                addx 1
                addx 3
                noop
                addx 15
                addx -21
                addx 22
                addx -6
                addx 1
                noop
                addx 2
                addx 1
                noop
                addx -10
                noop
                noop
                addx 20
                addx 1
                addx 2
                addx 2
                addx -6
                addx -11
                noop
                noop
                noop"""));
    }

    @Test
    public void testExample2(){
        assertEquals(

            """
                ##..##..##..##..##..##..##..##..##..##..
                ###...###...###...###...###...###...###.
                ####....####....####....####....####....
                #####.....#####.....#####.....#####.....
                ######......######......######......####
                #######.......#######.......#######.....
                """,
            Day10.part2(
                "addx 15\n"
                    + "addx -11\n"
                    + "addx 6\n"
                    + "addx -3\n"
                    + "addx 5\n"
                    + "addx -1\n"
                    + "addx -8\n"
                    + "addx 13\n"
                    + "addx 4\n"
                    + "noop\n"
                    + "addx -1\n"
                    + "addx 5\n"
                    + "addx -1\n"
                    + "addx 5\n"
                    + "addx -1\n"
                    + "addx 5\n"
                    + "addx -1\n"
                    + "addx 5\n"
                    + "addx -1\n"
                    + "addx -35\n"
                    + "addx 1\n"
                    + "addx 24\n"
                    + "addx -19\n"
                    + "addx 1\n"
                    + "addx 16\n"
                    + "addx -11\n"
                    + "noop\n"
                    + "noop\n"
                    + "addx 21\n"
                    + "addx -15\n"
                    + "noop\n"
                    + "noop\n"
                    + "addx -3\n"
                    + "addx 9\n"
                    + "addx 1\n"
                    + "addx -3\n"
                    + "addx 8\n"
                    + "addx 1\n"
                    + "addx 5\n"
                    + "noop\n"
                    + "noop\n"
                    + "noop\n"
                    + "noop\n"
                    + "noop\n"
                    + "addx -36\n"
                    + "noop\n"
                    + "addx 1\n"
                    + "addx 7\n"
                    + "noop\n"
                    + "noop\n"
                    + "noop\n"
                    + "addx 2\n"
                    + "addx 6\n"
                    + "noop\n"
                    + "noop\n"
                    + "noop\n"
                    + "noop\n"
                    + "noop\n"
                    + "addx 1\n"
                    + "noop\n"
                    + "noop\n"
                    + "addx 7\n"
                    + "addx 1\n"
                    + "noop\n"
                    + "addx -13\n"
                    + "addx 13\n"
                    + "addx 7\n"
                    + "noop\n"
                    + "addx 1\n"
                    + "addx -33\n"
                    + "noop\n"
                    + "noop\n"
                    + "noop\n"
                    + "addx 2\n"
                    + "noop\n"
                    + "noop\n"
                    + "noop\n"
                    + "addx 8\n"
                    + "noop\n"
                    + "addx -1\n"
                    + "addx 2\n"
                    + "addx 1\n"
                    + "noop\n"
                    + "addx 17\n"
                    + "addx -9\n"
                    + "addx 1\n"
                    + "addx 1\n"
                    + "addx -3\n"
                    + "addx 11\n"
                    + "noop\n"
                    + "noop\n"
                    + "addx 1\n"
                    + "noop\n"
                    + "addx 1\n"
                    + "noop\n"
                    + "noop\n"
                    + "addx -13\n"
                    + "addx -19\n"
                    + "addx 1\n"
                    + "addx 3\n"
                    + "addx 26\n"
                    + "addx -30\n"
                    + "addx 12\n"
                    + "addx -1\n"
                    + "addx 3\n"
                    + "addx 1\n"
                    + "noop\n"
                    + "noop\n"
                    + "noop\n"
                    + "addx -9\n"
                    + "addx 18\n"
                    + "addx 1\n"
                    + "addx 2\n"
                    + "noop\n"
                    + "noop\n"
                    + "addx 9\n"
                    + "noop\n"
                    + "noop\n"
                    + "noop\n"
                    + "addx -1\n"
                    + "addx 2\n"
                    + "addx -37\n"
                    + "addx 1\n"
                    + "addx 3\n"
                    + "noop\n"
                    + "addx 15\n"
                    + "addx -21\n"
                    + "addx 22\n"
                    + "addx -6\n"
                    + "addx 1\n"
                    + "noop\n"
                    + "addx 2\n"
                    + "addx 1\n"
                    + "noop\n"
                    + "addx -10\n"
                    + "noop\n"
                    + "noop\n"
                    + "addx 20\n"
                    + "addx 1\n"
                    + "addx 2\n"
                    + "addx 2\n"
                    + "addx -6\n"
                    + "addx -11\n"
                    + "noop\n"
                    + "noop\n"
                    + "noop"));
    }
}

