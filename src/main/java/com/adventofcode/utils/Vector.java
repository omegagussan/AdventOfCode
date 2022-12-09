package com.adventofcode.utils;

public record Vector(Integer i, Integer j){
  public int magnitude(){
    return i*i + j*j;
  }
}
