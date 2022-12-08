package com.adventofcode.year2022;

import static com.adventofcode.utils.ArrayUtilz.reverse;
import static com.adventofcode.utils.ArrayUtilz.slizeRow;

import com.adventofcode.utils.StringMatrixParser;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

public class Day8 {
    public record Coord(Integer i, Integer j){
        <T> T getValue(T[][] matrix){
            return matrix[i][j];
        }
    }

    public static Integer isSmallerUntil(Integer[] arr, Integer elem){
        if (arr.length == 0){return 0;}
        for (int i=0; i < arr.length; i++){
            if (!(elem > arr[i])){
                return i + 1;
            }
        }
        return arr.length;
    }

    public static boolean leavesMap(Integer[] arr, Integer comp){
        return Arrays.stream(arr).filter(elem -> elem >= comp).findAny().isEmpty();
    }

    @NotNull
    private static HashSet<Coord> getHashSet(String[][] matrix) {
        var visible = List.of(
        IntStream.range(0, matrix.length).mapToObj(value -> new Coord(value, 0)).toList(),
        IntStream.range(0, matrix.length).mapToObj(value -> new Coord(value, matrix[0].length-1)).toList(),
        IntStream.range(0, matrix[0].length).mapToObj(value -> new Coord(0, value)).toList(),
        IntStream.range(0, matrix[0].length).mapToObj(value -> new Coord(matrix.length-1, value)).toList()
        ).stream().flatMap(coords -> coords.stream()).collect(Collectors.toSet());
        var visibleHashSet = new HashSet<>(visible);
        return visibleHashSet;
    }

    public static int getScore(Integer[][] intMatrix, Integer[][] transposedMatrix, int i, int j) {
        var currentValue = intMatrix[i][j];
        Integer ls = isSmallerUntil(reverse(slizeRow(intMatrix, i, 0, j), Integer.class), currentValue);
        Integer rs = isSmallerUntil(slizeRow(intMatrix, i, j+1, intMatrix.length + 1), currentValue);
        Integer us = isSmallerUntil(reverse(slizeRow(transposedMatrix, j, 0, i), Integer.class), currentValue);
        Integer ds = isSmallerUntil(slizeRow(transposedMatrix, j, i+1, transposedMatrix.length + 1), currentValue);
        return ls * rs * us * ds;
    }

    public static int part1(String instructions) {
        var matrix = StringMatrixParser.parse(instructions, "\n", "");
        var intMatrix = StringMatrixParser.applyGeneric(matrix, Integer.class, Integer::valueOf);
        var transposedMatrix = StringMatrixParser.transposeGeneric(intMatrix, Integer.class);

        HashSet<Coord> visibleHashSet = getHashSet(matrix);

        for (int i=1; i < intMatrix.length -1; i++){
            for (int j=1; j < intMatrix[0].length -1; j++){
                var elem= new Coord(i, j);
                var los = slizeRow(intMatrix, i, 0, j);
                var los2 = reverse(slizeRow(intMatrix, i, j +1 , intMatrix.length), Integer.class);
                var los3 = slizeRow(transposedMatrix, j, 0, i);
                var los4 = reverse(slizeRow(transposedMatrix, j, i +1 , transposedMatrix.length), Integer.class);
                if (leavesMap(los, elem.getValue(intMatrix)) ||
                    leavesMap(los2, elem.getValue(intMatrix)) ||
                    leavesMap(los3, elem.getValue(intMatrix)) ||
                    leavesMap(los4, elem.getValue(intMatrix))
                ) {visibleHashSet.add(elem);}
            }
        }
        return visibleHashSet.size();
    }

    public static int part2(String instructions) {
        var matrix = StringMatrixParser.parse(instructions, "\n", "");
        var intMatrix = StringMatrixParser.applyGeneric(matrix, Integer.class, Integer::valueOf);
        var transposedMatrix = StringMatrixParser.transposeGeneric(intMatrix, Integer.class);

        return IntStream.range(0, intMatrix.length).boxed()
            .flatMap(i -> IntStream.range(0, intMatrix[0].length)
                .mapToObj(j -> new Coord(i, j)))
            .map(c -> getScore(intMatrix, transposedMatrix, c.i, c.j))
            .mapToInt(Integer::intValue).max().getAsInt();
    }

    public static void main(String[] args){
        try {
            InputStream i = Day8.class.getClassLoader().getResourceAsStream("2022/day8.txt");
            String instructions = new String(i.readAllBytes());
            System.out.println("Part1: " + part1(instructions));
            System.out.println("Part2: " + part2(instructions));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
