package com.adventofcode.year2022;

import static com.adventofcode.utils.ArrayUtilz.reverse;
import static com.adventofcode.utils.ArrayUtilz.slizeRow;

import com.adventofcode.utils.StringMatrixParser;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
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
    private static HashSet<Coord> getHashSet(Integer[][] matrix) {
        return Stream.of(
            IntStream.range(0, matrix.length).mapToObj(value -> new Coord(value, 0)).toList(),
            IntStream.range(0, matrix.length)
                .mapToObj(value -> new Coord(value, matrix[0].length - 1)).toList(),
            IntStream.range(0, matrix[0].length).mapToObj(value -> new Coord(0, value)).toList(),
            IntStream.range(0, matrix[0].length)
                .mapToObj(value -> new Coord(matrix.length - 1, value)).toList()
        ).flatMap(Collection::stream).collect(Collectors.toCollection(HashSet::new));
    }

    public static int getScore(Integer[][] intMatrix, Integer[][] transposedMatrix, int i, int j) {
        var currentValue = intMatrix[i][j];
        Integer ls = isSmallerUntil(reverse(slizeRow(intMatrix, i, 0, j), Integer.class), currentValue);
        Integer rs = isSmallerUntil(slizeRow(intMatrix, i, j+1, intMatrix.length + 1), currentValue);
        Integer us = isSmallerUntil(reverse(slizeRow(transposedMatrix, j, 0, i), Integer.class), currentValue);
        Integer ds = isSmallerUntil(slizeRow(transposedMatrix, j, i+1, transposedMatrix.length + 1), currentValue);
        return ls * rs * us * ds;
    }

    public static int part1(Integer[][] intMatrix, Integer[][] transposedMatrix) {
        HashSet<Coord> visibleHashSet = getHashSet(intMatrix);

        IntStream.range(0, intMatrix.length).boxed()
            .flatMap(i -> IntStream.range(0, intMatrix[0].length)
                .mapToObj(j -> new Coord(i, j))).forEach(coord -> {
                var los = slizeRow(intMatrix, coord.i, 0, coord.j);
                var los2 = reverse(slizeRow(intMatrix, coord.i, coord.j +1 , intMatrix.length), Integer.class);
                var los3 = slizeRow(transposedMatrix, coord.j, 0, coord.i);
                var los4 = reverse(slizeRow(transposedMatrix, coord.j, coord.i +1 , transposedMatrix.length), Integer.class);
                if (leavesMap(los, coord.getValue(intMatrix)) ||
                    leavesMap(los2, coord.getValue(intMatrix)) ||
                    leavesMap(los3, coord.getValue(intMatrix)) ||
                    leavesMap(los4, coord.getValue(intMatrix))
                ) {visibleHashSet.add(coord);}
            });

        return visibleHashSet.size();
    }

    public static int part2(Integer[][] intMatrix, Integer[][] transposedMatrix) {
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
            var matrix = StringMatrixParser.parse(instructions, "\n", "");
            var intMatrix = StringMatrixParser.applyGeneric(matrix, Integer.class, Integer::valueOf);
            var transposedMatrix = StringMatrixParser.transposeGeneric(intMatrix, Integer.class);
            System.out.println("Part1: " + part1(intMatrix, transposedMatrix));
            System.out.println("Part2: " + part2(intMatrix, transposedMatrix));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
