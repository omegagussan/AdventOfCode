package com.adventofcode.utils;

import java.lang.reflect.Array;

public class ArrayUtilz {
  public static <T, E> T[] reverse(T[] arr, Class<E> clazz)
  {
    T[] result = (T[]) Array.newInstance(clazz, arr.length);
    for (int i = 0; i < arr.length; i++) {
      result[arr.length - i -1] = arr[i];
    }
    return result;
  }

  public static <T> Point find(T[][] arr, T elem)
  {
    for (int i = 0 ; i < arr.length; i++){
      for(int j = 0 ; j < arr[0].length ; j++) {
        if (elem.equals(arr[i][j])) {
          return new Point(i, j);
        }
      }
    }
    return null;
  }
}
