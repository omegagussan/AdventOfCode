package com.adventofcode.year2022;

import com.adventofcode.utils.StringMatrixParser;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;
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

    public static boolean isStriklyIncreasing(Integer[] arr){
        for (int i=1; i < arr.length; i++){
            boolean b = !(arr[i] > arr[i - 1]);
            if (b){
                return false;
            }
        }
        return true;
    }

    public static boolean customCheck(Integer[] arr, Integer comp){
        return Arrays.stream(arr).filter(elem -> elem >= comp).findAny().isEmpty();
    }

    public static Integer[] reverse(Integer arr[])
    {
        Integer[] b = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) {
            b[arr.length - i -1] = arr[i];
        }
        return b;
    }

    public static int part1(String instructions) {
        var matrix = StringMatrixParser.parse(instructions, "\n", "");
        var intMatrix = StringMatrixParser.applyGeneric(matrix, Integer.class, Integer::valueOf);
        var transposedMatrix = StringMatrixParser.transposeGeneric(intMatrix, Integer.class);

        var visible = List.of(
        IntStream.range(0, matrix.length).mapToObj(value -> new Coord(value, 0)).toList(),
        IntStream.range(0, matrix.length).mapToObj(value -> new Coord(value, matrix[0].length-1)).toList(),
        IntStream.range(0, matrix[0].length).mapToObj(value -> new Coord(0, value)).toList(),
        IntStream.range(0, matrix[0].length).mapToObj(value -> new Coord(matrix.length-1, value)).toList()
        ).stream().flatMap(coords -> coords.stream()).collect(Collectors.toSet());
//            Arrays.stream(ArrayUtils.subarray(matrix[0], 0, matrix[0].length)).toList(),
//            Arrays.stream(ArrayUtils.subarray(matrix[matrix.length-1], 0, matrix[0].length)).toList(),
//            Arrays.stream(ArrayUtils.subarray(transposedMatrix[transposedMatrix.length-1], 0, transposedMatrix[0].length)).toList(),
//            Arrays.stream(ArrayUtils.subarray(transposedMatrix[transposedMatrix.length-1], 0, transposedMatrix[0].length)).toList()
//            ).stream().flatMap(list -> list..stream()).collect(Collectors.toSet());
        var visibleHashSet = new HashSet<>(visible);
        System.out.println(visibleHashSet.size());

        for (int i=1; i < intMatrix.length -1; i++){
            for (int j=1; j < intMatrix[0].length -1; j++){
                var elem= new Coord(i, j);
                var los = ArrayUtils.subarray(intMatrix[i], 0, j);
        var los2 = reverse(ArrayUtils.subarray(intMatrix[i], j +1 , intMatrix.length));
                var los3 = ArrayUtils.subarray(transposedMatrix[j], 0, i);
                var los4 = reverse(ArrayUtils.subarray(transposedMatrix[j], i +1 , transposedMatrix.length));

                if (customCheck(los, elem.getValue(intMatrix)) || customCheck(los2, elem.getValue(intMatrix)) || customCheck(los3, elem.getValue(intMatrix)) || customCheck(los4, elem.getValue(intMatrix))){
                    visibleHashSet.add(elem);
                }
            }
        }
        return visibleHashSet.size();
    }

    public static int part2(String instructions) {
        return 3;
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