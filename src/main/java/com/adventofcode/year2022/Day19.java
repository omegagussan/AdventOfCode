package com.adventofcode.year2022;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;

public class Day19 {

  public static final String ROW_DELIMITER = "\n";

  public static final int TIME = 24;
  public static int geodeHeuristic = Integer.MIN_VALUE;

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
            bluePrintRow -> {
              // robots: {ore clay obsidian geode} | resources: {ore clay obsidian geode}
              var robotsAndResources =
                  List.of(1, 0, 0, 0, 0, 0, 0, 0);
              HashMap<Pair<List<Integer>, Integer>, Integer> cache = new HashMap<>();
              geodeHeuristic = Integer.MIN_VALUE;
              return maxGeodes(bluePrintRow, robotsAndResources, time, cache) * bluePrintRow.get(0);
            })
        .mapToInt(Integer::intValue)
        .sum();
  }

  static Integer maxGeodes(
      List<Integer> bp,
      List<Integer> oldGenRnR, // RobotsAndResources
      Integer time,
      HashMap<Pair<List<Integer>, Integer>, Integer> cache) {

    Integer currentOreRobots = oldGenRnR.get(0);
    Integer currentClayRobots = oldGenRnR.get(1);
    Integer currentObsidianRobots = oldGenRnR.get(2);
    Integer currentGeodeRobots = oldGenRnR.get(3);

    Integer ore = oldGenRnR.get(4);
    Integer clay = oldGenRnR.get(5);
    Integer obsidian = oldGenRnR.get(6);
    Integer geodes = oldGenRnR.get(7);

    if (time == 0) {
      geodeHeuristic = Math.max(geodeHeuristic, geodes);
      return geodes;
    }
    var key = new Pair<>(oldGenRnR, time);
    if (cache.containsKey(key)) {
      return cache.get(key);
    }

    //pruning
    if(getMaxGeodesPossible(time, geodes, currentGeodeRobots) < geodeHeuristic){return geodes;}


    Integer oreOreRequirement = bp.get(1);
    Integer clayRobotOreRequirement = bp.get(2);
    Integer obsidianOreRequirement = bp.get(3);
    Integer obsidianClayRequirement = bp.get(4);
    Integer geodeOreRequirement = bp.get(5);
    Integer geodeObsidianRequirement = bp.get(6);

    int epochMaxGeodes = 0;
    // geode - always craft when can
    if (ore >= geodeOreRequirement && obsidian >= geodeObsidianRequirement) {
      var a =
          List.of(
              currentOreRobots,
              currentClayRobots,
              currentObsidianRobots,
              currentGeodeRobots + 1,
              ore - geodeOreRequirement,
              clay,
              obsidian - geodeObsidianRequirement,
              geodes);
      epochMaxGeodes = Math.max(epochMaxGeodes, maxGeodes(bp, produce(oldGenRnR, a), time - 1, cache));
    } else {
      //we can only craft one geode bot per turn  =>  we never need more than geodeObsidianRequirement obsidian bots
      if (geodeObsidianRequirement > currentObsidianRobots && ore >= obsidianOreRequirement && clay >= obsidianClayRequirement) {
        var a =
            List.of(
                currentOreRobots,
                currentClayRobots,
                currentObsidianRobots + 1,
                currentGeodeRobots,
                ore - obsidianOreRequirement,
                clay - obsidianClayRequirement,
                obsidian,
                geodes);
        epochMaxGeodes = Math.max(epochMaxGeodes, maxGeodes(bp, produce(oldGenRnR, a), time - 1, cache));
      }

      // ore -- if it can justify cost
      Integer biggestOreCost = Collections.max(
          List.of(geodeOreRequirement, obsidianOreRequirement, clayRobotOreRequirement));
      if (ore >= oreOreRequirement && biggestOreCost > currentOreRobots){
        var a =
            List.of(
                currentOreRobots + 1,
                currentClayRobots,
                currentObsidianRobots,
                currentGeodeRobots,
                ore - oreOreRequirement,
                clay,
                obsidian,
                geodes);
        epochMaxGeodes = Math.max(epochMaxGeodes, maxGeodes(bp, produce(oldGenRnR, a), time - 1, cache));
      }

      //we can only craft one geode bot per turn  =>  we never need more than geodeObsidianRequirement obsidian bots
      if (currentClayRobots < obsidianClayRequirement && ore >= clayRobotOreRequirement) {
        var a =
            List.of(
                currentOreRobots,
                currentClayRobots + 1,
                currentObsidianRobots,
                currentGeodeRobots,
                currentGeodeRobots,
                ore - clayRobotOreRequirement,
                clay,
                obsidian,
                geodes);
        epochMaxGeodes = Math.max(epochMaxGeodes, maxGeodes(bp, produce(oldGenRnR, a), time - 1, cache));
      }

      // do nothing
      epochMaxGeodes = Math.max(epochMaxGeodes, maxGeodes(bp, produce(oldGenRnR, oldGenRnR), time - 1, cache));
    }

    cache.put(key, epochMaxGeodes);
    return epochMaxGeodes;
  }

  private static int getMaxGeodesPossible(Integer time, Integer geodes, Integer currentGeodeRobots) {
    int maxGeodesPossible = geodes;
    for(int i = 0; i < time; i++){
      maxGeodesPossible += currentGeodeRobots + i;
    }
    return maxGeodesPossible;
  }

  @NotNull
  private static List<Integer> produce(List<Integer> oldRobots, List<Integer> newRobots) {
    return List.of(
        newRobots.get(0),
        newRobots.get(1),
        newRobots.get(2),
        newRobots.get(3),
        newRobots.get(4) + oldRobots.get(0),
        newRobots.get(5) + oldRobots.get(1),
        newRobots.get(6) + oldRobots.get(2),
        newRobots.get(7) + oldRobots.get(3)
    );
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
