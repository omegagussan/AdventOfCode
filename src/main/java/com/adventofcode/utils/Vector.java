package com.adventofcode.utils;

import java.util.List;
import java.util.stream.IntStream;
import org.apache.commons.lang3.NotImplementedException;

public record Vector(Integer i, Integer j){
  public int magnitude(){
    return i*i + j*j;
  }

  public List<Point> consistsOf(Point start){
    if (this.i != 0 && this.j != 0){
      throw new NotImplementedException("Sorry");
    } else if (this.i != 0){
      List<Point> points = IntStream.range(0, Math.abs(i) + 1)
          .mapToObj(i -> start.move((int) Math.signum(this.i) * i, 0)).toList();
      return points;
    } else if (this.j != 0){
      List<Point> points = IntStream.range(0, Math.abs(j) + 1)
          .mapToObj(j -> start.move(0, (int) Math.signum(this.j) * j)).toList();
      return points;
    }
    return List.of();
  }
}
