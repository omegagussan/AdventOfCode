package com.adventofcode.utils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.javatuples.Pair;
public class StringMatrixParser {
  public static String[][] parse(String arg, String rowDelimiter, String columnDelimiter) {
    return Arrays.stream(arg.split(rowDelimiter))
        .map(r -> Arrays.stream(parseRow(columnDelimiter, r)).filter(s -> !s.isBlank()).toArray(String[]::new))
        .toArray(String[][]::new);
  }

  public static String[][] parse(String arg, String rowDelimiter, String columnDelimiter, String replacement) {
    return Arrays.stream(arg.split(rowDelimiter))
        .map(r -> Arrays.stream(parseRow(columnDelimiter, r)).map(s -> s.isBlank() ? replacement : s).toArray(String[]::new))
        .toArray(String[][]::new);
  }


  static String[] parseRow(String columnDelimiter, String r) {
    return r.split(columnDelimiter);
  }

  public static <T> T[][] transposeGeneric(T[][] input, Class<T> clz){
    T[][] target = (T[][]) Array.newInstance(clz, input[0].length, input.length);
    IntStream.range(0, input[0].length)
        .forEach(i -> IntStream.range(0, input.length).forEach(j -> target[i][j] = input[j][i]));
    return target;
  }

//  public static String[][] transposeString(String [][] input){
//    var a = String.join((CharSequence) "", IntStream.range(0, input.length)
//        .mapToObj(i -> Stream.of(input).map(r -> r.length > i ? r[i] : null)
//            .filter(Objects::nonNull).toList()).toList());
//    while (a.startsWith("#")){
//      a = a.substring(1);
//    }
//    return IntStream.range(0, input.length)
//        .mapToObj(i -> Stream.of(input).map(r -> r.length > i ? r[i] : null)
//            .filter(Objects::nonNull).toArray(String[]::new))
//        .toArray(String[][]::new);
//  }


  public static <T, R> R[][] applyGeneric(T[][] input, Class<R> clz, Function<T,R> fun){
    R[][] target = (R[][]) Array.newInstance(clz, input.length, input[0].length);
    IntStream.range(0, input.length)
        .forEach(i -> IntStream.range(0, input[0].length).forEach(j -> target[i][j] = fun.apply(input[i][j])));
    return target;
  }

  public static <T, R> R[][] applyGenericPoseAware(T[][] input, Class<R> clz, BiFunction<T, Pair<Integer, Integer>, R> fun){
    R[][] target = (R[][]) Array.newInstance(clz, input.length, input[0].length);
    IntStream.range(0, input.length)
        .forEach(i -> IntStream.range(0, input[0].length).forEach(j -> target[i][j] = fun.apply(input[i][j], new Pair<>(i, j))));
    return target;
  }

}
