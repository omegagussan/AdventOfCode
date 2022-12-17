package com.adventofcode.utils;

public record LongPoint(Long i, Long j){
  public LongPoint moveArbitrary(Vector delta){
    return new LongPoint(this.i() + delta.i(), this.j() + delta.j());
  }

  public LongPoint move(Long x, Long y){
    return new LongPoint(this.i() + x, this.j() + y);
  }
}
