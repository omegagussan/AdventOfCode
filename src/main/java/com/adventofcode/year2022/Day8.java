package com.adventofcode.year2022;

import com.adventofcode.utils.StringMatrixParser;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.lang3.ArrayUtils;

public class Day8 {
    public record Coord(Integer i, Integer j){
        <T> T getValue(T[][] matrix){
            return matrix[i][j];
        }
    }

    public static Integer isSmaller(Integer[] arr, Integer elem){
        if (arr.length == 0){
            return 0;
        }
        for (int i=0; i < arr.length; i++){
            if (!(elem > arr[i])){
                return i + 1;
            }
        }
        return arr.length;
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
        var visibleHashSet = new HashSet<>(visible);

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
        var matrix = StringMatrixParser.parse(instructions, "\n", "");
        var intMatrix = StringMatrixParser.applyGeneric(matrix, Integer.class, Integer::valueOf);
        var transposedMatrix = StringMatrixParser.transposeGeneric(intMatrix, Integer.class);

        var topScore = 0;
        for (int i=0; i < intMatrix.length; i++){
            for (int j=0; j < intMatrix[0].length; j++){
                int score = getScore(intMatrix, transposedMatrix, i, j);
                if (score > topScore){
                    topScore = score;
                }
            }
        }
        return topScore;
    }

    public static int getScore(Integer[][] intMatrix, Integer[][] transposedMatrix, int i, int j) {
        var left = reverse(ArrayUtils.subarray(intMatrix[i], 0, j));
        var right = ArrayUtils.subarray(intMatrix[i], j+1, intMatrix.length + 1);
        var up = reverse(ArrayUtils.subarray(transposedMatrix[j], 0, i));
        var down = ArrayUtils.subarray(transposedMatrix[j], i+1, transposedMatrix.length + 1);

        var val = intMatrix[i][j];
        Integer ls = isSmaller(left, val);
        Integer rs = isSmaller(right, val);
        Integer us = isSmaller(up, val);
        Integer ds = isSmaller(down, val);
        return ls * rs * us * ds;
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
