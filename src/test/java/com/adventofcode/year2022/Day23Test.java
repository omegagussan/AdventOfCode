package com.adventofcode.year2022;

import static org.junit.Assert.assertEquals;

import com.adventofcode.utils.Point;
import com.adventofcode.utils.StringMatrixParser;
import java.util.Map;
import java.util.Optional;
import org.junit.Test;

public class Day23Test {

    String INSTRUCTIONS = """
        ..............
        ..............
        .......#......
        .....###.#....
        ...#...#.#....
        ....#...##....
        ...#.###......
        ...##.#.##....
        ....#..#......
        ..............
        ..............
        ..............
        """;

    String SMALL = """
        .....
        ..##.
        ..#..
        .....
        ..##.
        .....
        """;

    @Test
    public void testPart1(){
        assertEquals(110L, Day23.part1(INSTRUCTIONS, 10));
    }

    @Test
    public void testDraw(){
        var elfMatrix = StringMatrixParser.parse(INSTRUCTIONS, Day23.ROW_DELIMITER, "");
        var state = Day23.getInitState(elfMatrix);
        Day23.draw(state);
    }

    @Test
    public void testDrawSmall(){
        var elfMatrix = StringMatrixParser.parse(SMALL, Day23.ROW_DELIMITER, "");
        var state = Day23.getInitState(elfMatrix);
        var result = Day23.draw(state);
        var expected = """
            ##
            #.
            ..
            ##
            """;
        assertEquals(expected, result);
    }

    @Test
    public void extractedOneRound(){
        var elfMatrix = StringMatrixParser.parse(INSTRUCTIONS, Day23.ROW_DELIMITER, "");
        Map<Point, String> given = Day23.extracted(1, elfMatrix);
        var result = Day23.draw(given);
        var expected = """
            .....#...
            ...#...#.
            .#..#.#..
            .....#..#
            ..#.#.##.
            #..#.#...
            #.#.#.##.
            .........
            ..#..#...
            """;
        assertEquals(expected, result);
    }

    @Test
    public void extractedTwoRound(){
        var elfMatrix = StringMatrixParser.parse(INSTRUCTIONS, Day23.ROW_DELIMITER, "");
        Map<Point, String> given = Day23.extracted(2, elfMatrix);
        var result = Day23.draw(given);
        var expected = """
            ......#....
            ...#.....#.
            ..#..#.#...
            ......#...#
            ..#..#.#...
            #...#.#.#..
            ...........
            .#.#.#.##..
            ...#..#....
            """;
        assertEquals(expected, result);
    }

    @Test
    public void extractedFourRound(){
        var elfMatrix = StringMatrixParser.parse(INSTRUCTIONS, Day23.ROW_DELIMITER, "");
        Map<Point, String> given = Day23.extracted(4, elfMatrix);
        var result = Day23.draw(given);
        var expected = """
            ......#....
            .....#....#
            .#...##....
            ..#.....#.#
            ........#..
            #...###..#.
            .#......#..
            ...##....#.
            ...#.......
            ......#....
            """;
        assertEquals(expected, result);
    }

    @Test
    public void extracted5Round(){
        var elfMatrix = StringMatrixParser.parse(INSTRUCTIONS, Day23.ROW_DELIMITER, "");
        Map<Point, String> given = Day23.extracted(5, elfMatrix);
        var result = Day23.draw(given);
        var expected = """
           ......#....
           ...........
           .#..#.....#
           ........#..
           .....##...#
           #.#.####...
           ..........#
           ...##..#...
           .#.........
           .........#.
           ...#..#....
           """;
        assertEquals(expected, result);
    }

    @Test
    public void extracted10Round(){
        var elfMatrix = StringMatrixParser.parse(INSTRUCTIONS, Day23.ROW_DELIMITER, "");
        Map<Point, String> given = Day23.extracted(10, elfMatrix);
        var result = Day23.draw(given);
        var expected = """
        ......#.....
        ..........#.
        .#.#..#.....
        .....#......
        ..#.....#..#
        #......##...
        ....##......
        .#........#.
        ...#.#..#...
        ............
        ...#..#..#..
            """;
        assertEquals(expected, result);
    }

    @Test
    public void extractedSmallOneRound(){
        var elfMatrix = StringMatrixParser.parse(SMALL, Day23.ROW_DELIMITER, "");
        Map<Point, String> given = Day23.extracted(1, elfMatrix);
        var result = Day23.draw(given);
        var expected = """
            ##
            ..
            #.
            .#
            #.
            """;
        assertEquals(expected, result);
    }

    @Test
    public void extractedSmallThreeRound(){
        var elfMatrix = StringMatrixParser.parse(SMALL, Day23.ROW_DELIMITER, "");
        Map<Point, String> given = Day23.extracted(3, elfMatrix);
        var result = Day23.draw(given);
        var expected = """
            ..#..
            ....#
            #....
            ....#
            .....
            ..#..
            """;
        assertEquals(expected, result);
    }

}

