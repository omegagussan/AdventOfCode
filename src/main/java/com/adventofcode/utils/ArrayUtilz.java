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
}
