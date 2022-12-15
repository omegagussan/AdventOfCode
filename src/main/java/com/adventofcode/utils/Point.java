package com.adventofcode.utils;

import java.util.List;
import java.util.stream.IntStream;

public record Point(Integer i, Integer j){
  public <T> T getValue(T[][] matrix){
    return matrix[i][j];
  }

  public static Vector compare(Point a, Point b){
    return new Vector(a.i() - b.i(), a.j() - b.j());
  }

  public static LongVector compareBig(Point a, Point b){
    return new LongVector((long) (a.i() - b.i()), (long) (a.j() - b.j()));
  }

  public Point move(int x, int y){
    return new Point(this.i() + x, this.j() + y);
  }

  public Point move(Vector delta){
    if (Math.abs(delta.i()) > Math.abs(delta.j())){
      return new Point(this.i() + (int) Math.signum(delta.i()), this.j());
    }
    return new Point(this.i(), this.j() + (int) Math.signum(delta.j()));
  }

  public static List<Point> getAdjacent(Point p){
    return IntStream.range(-1, 2)
        .mapToObj(i -> IntStream.range(-1, 2)
            .mapToObj(j -> new Point(p.i() + i, p.j() + j)))
        .flatMap(pointStream -> pointStream).filter(c -> c != p).toList();
  }

  public static <T> boolean isWithinGrid(T[][] grid, Point candidate) {
    return candidate.i() >= 0 &&
        candidate.i() < grid.length &&
        candidate.j() >= 0 &&
        candidate.j() < grid[0].length;
  }
}
