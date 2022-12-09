package com.adventofcode.utils;

public record Point(Integer i, Integer j){
  public <T> T getValue(T[][] matrix){
    return matrix[i][j];
  }

  public static Vector compare(Point a, Point b){
    return new Vector(a.i() - b.i(), a.j() - b.j());
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
}
