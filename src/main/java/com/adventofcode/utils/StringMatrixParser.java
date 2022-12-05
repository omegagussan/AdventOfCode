package com.adventofcode.utils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.IntStream;

public class StringMatrixParser {
  public static String[][] parse(String arg, String rowDelimiter, String columnDelimiter) {
    return Arrays.stream(arg.split(rowDelimiter))
        .map(r -> parseRow(columnDelimiter, r))
        .toArray(String[][]::new);
  }

  static String[] parseRow(String columnDelimiter, String r) {
    return r.split(columnDelimiter);
  }

  static <T> T[][] transposeGeneric(T[][] input, Class<T> clz){
    T[][] target = (T[][]) Array.newInstance(clz, input[0].length, input.length);
    IntStream.range(0, input[0].length)
        .forEach(i -> IntStream.range(0, input.length).forEach(j -> target[i][j] = input[j][i]));
    return target;
  }

}
