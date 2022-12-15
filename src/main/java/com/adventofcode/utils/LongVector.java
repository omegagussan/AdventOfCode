package com.adventofcode.utils;

public record LongVector(Long i, Long j){
  public long magnitude(){
    return i*i + j*j;
  }
}
