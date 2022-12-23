package com.adventofcode.utils;

import java.util.List;
import java.util.stream.IntStream;
import org.apache.commons.lang3.NotImplementedException;
import org.javatuples.Pair;

public record Vector(Integer i, Integer j){
  public int magnitude(){
    return Math.addExact(Math.multiplyExact(i,i), Math.multiplyExact(j,j));
  }

  public Vector byScalar(Integer s){
    return new Vector(this.i * s, this.j * s);
  }

  public Pair<Integer, Integer> toPair(){
    return new Pair<>(this.i, this.j);
  }

  public Vector turn(String direction){
    if (direction.equals("R")){
      return new Vector(-this.j, this.i);
    } else if (direction.equals("L")){
      return new Vector(this.j, -this.i);
    }
    return this;
  }

  public List<Point> consistsOf(Point start){
    if (this.i != 0 && this.j != 0){
      throw new NotImplementedException("Sorry");
    } else if (this.i != 0){
      return IntStream.range(0, Math.abs(i) + 1)
          .mapToObj(i1 -> start.move((int) Math.signum(this.i) * i1, 0)).toList();
    } else if (this.j != 0){
      return IntStream.range(0, Math.abs(j) + 1)
          .mapToObj(j1 -> start.move(0, (int) Math.signum(this.j) * j1)).toList();
    }
    return List.of();
  }

  public List<Point> consistsOf(Point start, int wrap){
    if (this.i != 0 && this.j != 0){
      throw new NotImplementedException("Sorry");
    } else if (this.i != 0){
      return IntStream.range(0, Math.abs(i) + 1)
          .mapToObj(i1 -> start.move(((int) Math.signum(this.i) * i1) % wrap, 0)).toList();
    } else if (this.j != 0){
      return IntStream.range(0, Math.abs(j) + 1)
          .mapToObj(j1 -> start.move(0, ((int) Math.signum(this.j) * j1) % wrap)).toList();
    }
    return List.of();
  }
}
