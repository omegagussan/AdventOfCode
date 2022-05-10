package com.adventofcode.year2015;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import com.google.common.base.Stopwatch;

public class Day4 {


    static int part1(String input, int candidateInit, String breakCondition) {
        int threadpoolSize = 100;
        ExecutorService pool = Executors.newFixedThreadPool(threadpoolSize);


        int candidate = candidateInit;
        while (true){
            int finalCandidate = candidate;
            List<CompletableFuture<String>> futures = new ArrayList<>();
            for (int i = 0; i < threadpoolSize; i++){
                int finalI = i;
                var f = CompletableFuture.supplyAsync(() -> {
                    try {
                        return computeHash(input, finalCandidate + finalI);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    return null;
                }, pool);
                futures.add(f);
            }
            List<String> hashes = futures.stream().map(CompletableFuture::join).toList();
            if (hashes.stream().anyMatch(s -> s.startsWith(breakCondition))){break;}
            candidate+= threadpoolSize;
        }
        return candidate;
    }

    private static String computeHash(String input, int candidate) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] bytesOfMessage = (input + candidate).getBytes(StandardCharsets.UTF_8);
        return byteArrayToString(md.digest(bytesOfMessage));
    }

    public static int part1(String input) {
        return part1(input, 1, "00000");
    }

    public static int part2(String input) {
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
            System.out.println("Part1: " + part1(input));
            System.out.println("Part2: " + part2(input));
        } catch (Exception e){
            e.printStackTrace();
            System.err.println("Something went poorly: " + e.getCause());
        }
    }
}
