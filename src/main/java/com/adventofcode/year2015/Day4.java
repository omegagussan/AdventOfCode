package com.adventofcode.year2015;

import java.nio.charset.StandardCharsets;
import java.security.*;
import com.google.common.base.Stopwatch;

public class Day4 {


    static int part1(String input, int candidateInit, String breakCondition) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");

        int candidate = candidateInit;
        while (true){
            byte[] bytesOfMessage = (input + candidate).getBytes(StandardCharsets.UTF_8);
            String hexDecimalHash = byteArrayToString(md.digest(bytesOfMessage));
            if (hexDecimalHash.startsWith(breakCondition)){break;}
            candidate++;
        }
        return candidate;
    }

    public static int part1(String input) throws NoSuchAlgorithmException {
        return part1(input, 1, "00000");
    }

    public static int part2(String input) throws NoSuchAlgorithmException {
        Stopwatch timer = Stopwatch.createStarted();
        int answer = part1(input, 1, "000000");
        System.out.println("Part2 took: " + timer.stop());
        return answer;
    }

    private static String byteArrayToString(byte[] byteArray){
        StringBuilder builder = new StringBuilder();
        for (byte b : byteArray) {
            builder.append(String.format("%02X ", b).strip());
        }
        return String.join("", builder);
    }

    public static void main(String[] args){
        try {
            String input = "yzbqklnj";
            //System.out.println("Part1: " + part1(input));
            System.out.println("Part2: " + part2(input));
        } catch (Exception e){
            System.err.println("Something went poorly: " + e.getCause());
        }
    }
}
