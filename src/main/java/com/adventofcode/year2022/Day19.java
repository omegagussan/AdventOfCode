package com.adventofcode.year2022;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;

public class Day19 {

  public static final String ROW_DELIMITER = "\n";

  public static final int TIME = 24;

  @NotNull
  static Stream<List<Integer>> parseInput(String instructions) {
    Matcher m =
        Pattern.compile(
                "Blueprint (.*): Each ore robot costs (.*) ore. Each clay robot costs (.*) ore. Each obsidian robot costs (.*) ore and (.*) clay. Each geode robot costs (.*) ore and (.*) obsidian.")
            .matcher("");
    return Arrays.stream(instructions.split(ROW_DELIMITER))
        .map(
            row -> {
              m.reset(row).matches();
              return IntStream.rangeClosed(1, 7)
                  .mapToObj(i -> Integer.parseInt(m.group(i)))
                  .toList();
            });
  }

  public static long part2(String instructions) {
    return 2;
  }

  public static long part1(String instructions, Integer time) {
    var blueprints = parseInput(instructions).toList();
    return blueprints.stream()
        .map(
            bp -> {
              var robotsAndResources =
                  new ArrayList<>(
                      List.of(
                          1, 0, 0, 0, 0, 0, 0, 0)); // ore clay obsidian geode | ore clay obsidian geode
              HashMap<Pair<List<Integer>, Integer>, Integer> cache = new HashMap<>();
              var max = maxGeodes(bp, robotsAndResources, time, cache);
              return max * bp.get(0);
            })
        .mapToInt(Integer::intValue)
        .sum();
  }

  static Integer maxGeodes(
      List<Integer> bp,
      List<Integer> oldGen,
      Integer time,
      HashMap<Pair<List<Integer>, Integer>, Integer> cache) {
    if (time == 0) {
      return 0;
    }
    var key = new Pair<>(oldGen, time);
    if (cache.containsKey(key)) {
      return cache.get(key);
    }
    AtomicInteger res = new AtomicInteger();
    nextEpochCandidates(bp, oldGen, time).stream()
        .map(
            newGen ->
                new Pair<>(produce(oldGen, newGen),
                    !Objects.equals(newGen.get(7), newGen.get(7))))
        .forEach(
            integers -> {
              var x = maxGeodes(bp, integers.getValue0(), time - 1, cache);
              var curr = integers.getValue1() ? integers.getValue0().get(3) -1 : integers.getValue0().get(3);
              res.set(Math.max(x + curr, res.get()));
            });
    int rez = res.get();
    cache.put(key, rez);
    return rez;
  }

  @NotNull
  private static List<Integer> produce(
      List<Integer> oldRobots,
      List<Integer> newRobots
  ) {
    return List.of(
        newRobots.get(0),
        newRobots.get(1),
        newRobots.get(2),
        newRobots.get(3),
        newRobots.get(4) + oldRobots.get(0),
        newRobots.get(5) + oldRobots.get(1),
        newRobots.get(6) + oldRobots.get(2),
        newRobots.get(7) + oldRobots.get(3));
  }
  //    var v= tmp.stream()
  //        .map(state -> {
  //              List<Integer> resourcesWithout = state.subList(3, 6);
  //              var key = Joiner.on(",").join(resourcesWithout);
  //              if (cache.containsKey(key)){
  //                var w = cache.get(key).stream()
  //                    .map(l -> Streams.zip(state.stream(), l.stream(),
  // Integer::sum).toList()).toList();
  //                return w.stream().map(ztate -> new Pair<>(ztate, time-1));
  //              } else {
  //                var vv = extracted(bp, state, time - 1, cache, maxCosts);
  //                var diff = vv.stream().map(pair -> Streams.zip(pair.getValue0().stream(),
  //                    state.stream().map(integer -> -integer), Integer::sum).toList()).toList();
  //                cache.put(key, diff);
  //                return vv.stream();
  //              }
  //        })

  @NotNull
  static List<List<Integer>> nextEpochCandidates(
      List<Integer> bp, List<Integer> state, int timeLeft) {
    List<List<Integer>> candidates = new ArrayList<>();
    // geode
    Integer ore = state.get(4);
    Integer clay = state.get(5);
    Integer obsidian = state.get(6);

    Integer geodeOreRequirement = bp.get(5);
    Integer geodeObsidianRequirement = bp.get(6);
    Integer ObsidianOreRequirement = bp.get(3);
    Integer ObsidanClayRequirement = bp.get(4);
    if (ore >= geodeOreRequirement && obsidian >= geodeObsidianRequirement) {
      var a =
          List.of(
              state.get(0),
              state.get(1),
              state.get(2),
              state.get(3) + 1,
              ore - geodeOreRequirement,
              clay,
              obsidian - geodeObsidianRequirement,
              state.get(7));
      return List.of(a);
    }
    if (ore >= ObsidianOreRequirement && clay >= ObsidanClayRequirement) {
      var a =
          List.of(
              state.get(0),
              state.get(1),
              state.get(2) + 1,
              state.get(3),
              ore - ObsidianOreRequirement,
              clay - ObsidanClayRequirement,
              obsidian,
              state.get(7));
      return List.of(a);
    }
    // ore -- if it can justify cost
    if (ore >= bp.get(1) && timeLeft > bp.get(1)) {
      // if (ore >= bp.get(1)){
      var a =
          List.of(
              state.get(0) + 1,
              state.get(1),
              state.get(2),
              state.get(3),
              ore - bp.get(1),
              clay,
              obsidian,
              state.get(7));
      candidates.add(a);
    }
    // clay
    Integer clayRobotCost = bp.get(2);
    if (ore >= clayRobotCost) {
      var a =
          List.of(
              state.get(0),
              state.get(1) + 1,
              state.get(2),
              state.get(3),
              ore - clayRobotCost,
              clay,
              obsidian,
              state.get(7));
      candidates.add(a);
    }
    candidates.add(state);
    return candidates;
  }

  public static void main(String[] args) {
    try {
      InputStream i = Day19.class.getClassLoader().getResourceAsStream("2022/day19.txt");
      String instructions = new String(i.readAllBytes());
      System.out.println("Part1: " + part1(instructions, TIME));
      System.out.println("Part2: " + part2(instructions));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
