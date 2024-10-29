package com.adventofcode.utils;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArrayUtilz {
    public static <T, E> T[] reverse(T[] arr, Class<E> clazz) {
        T[] result = (T[]) Array.newInstance(clazz, arr.length);
        for (int i = 0; i < arr.length; i++) {
            result[arr.length - i - 1] = arr[i];
        }
        return result;
    }

    public static <T> Point findFirst(T[][] arr, T elem) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (elem.equals(arr[i][j])) {
                    return new Point(i, j);
                }
            }
        }
        return null;
    }

    public static <T> List<Point> findAll(T[][] arr, T elem) {
        var result = new ArrayList<Point>();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (elem.equals(arr[i][j])) {
                    result.add(new Point(i, j));
                }
            }
        }
        return result;
    }

    public static <T> List<List<T>> permutations(List<T> given) {
        if (given.size() == 1) {
            return List.of(given);
        }
        List<List<T>> result = new ArrayList<>();
        for (int i = 0; i < given.size(); i++) {
            T first = given.get(i);
            List<T> rest = new ArrayList<>(given);
            rest.remove(i);
            List<List<T>> perms = permutations(rest);
            for (List<T> perm : perms) {
                List<T> newPerm = new ArrayList<>();
                newPerm.add(first);
                newPerm.addAll(perm);
                result.add(newPerm);
            }
        }
        return result;
    }
}
