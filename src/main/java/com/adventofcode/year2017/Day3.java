package com.adventofcode.year2017;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Day3 {
    public static void main(String[] args) {
        try {
            System.out.println(part1(361527));
        } catch (Exception e) {
            System.err.println("Something went poorly");
            e.printStackTrace();

        }
    }

    public static int part1(int source) {
        if (source == 1) {
            return 0;
        }
       int i = 1;
       while(i * i < source) {
           i += 2;
       }
        int axis = (i - 1) / 2;
        int last = i - 2;
        int stepsToAxis = Math.abs((source - last * last) % axis);
        return axis + stepsToAxis;
    }

}

