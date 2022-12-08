package com.adventofcode.utils;

import java.lang.reflect.Array;
import org.apache.commons.lang3.ArrayUtils;

public class ArrayUtilz {
  public static <T, E> T[] reverse(T[] arr, Class<E> clazz)
  {
    T[] result = (T[]) Array.newInstance(clazz, arr.length);
    for (int i = 0; i < arr.length; i++) {
      result[arr.length - i -1] = arr[i];
    }
    return result;
  }

  public static <T> T[] slizeRow(T[][] matrix, Integer row, Integer fromCol, Integer toCol){
    return ArrayUtils.subarray(matrix[row], fromCol, toCol);
  }
}
