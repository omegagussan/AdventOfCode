package com.adventofcode.year2022;

import com.adventofcode.utils.LongPoint;
import com.adventofcode.utils.Point;
import com.adventofcode.utils.StringMatrixParser;
import com.adventofcode.utils.Vector;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;

public class Day17 {

  public static final String ROW_DELIMITER = "\n";
  public static final int TUNNEL_WIDTH = 6;
  public static final String SHAPES_STRING =
      """
        ####

        .#.
        ###
        .#.

        ..#
        ..#
        ###

        #
        #
        #
        #

        ##
        ##
        """;
  public static final List<Set<Point>> SHAPES =
      Arrays.stream(SHAPES_STRING.split(ROW_DELIMITER + ROW_DELIMITER))
          .map(s -> StringMatrixParser.parse(s, ROW_DELIMITER, ""))
          .map(
              z ->
                  StringMatrixParser.applyGenericPoseAware(
                      z,
                      Point.class,
                      (s, p) ->
                          "#".equals(s)
                              ? new Point(p.getValue1(), z[0].length - 1 - p.getValue0())
                              : null))
          .map(
              arrArr ->
                  Arrays.stream(arrArr)
                      .flatMap(Arrays::stream)
                      .filter(Objects::nonNull)
                      .collect(Collectors.toSet()))
          .toList();
    public static final long ELEPHANT_ARE_IMPRESSED_LIMIT = 1000000000000L;
    static Map<Integer, Integer> SHAPE_OFFSET = Map.of(0, -3, 1, 0, 2, 0, 3, 3, 4, 0);

  public static List<Set<Point>> PROCESSED_SHAPES =
      IntStream.rangeClosed(0, SHAPES.size() - 1)
          .mapToObj(
              i ->
                  SHAPES.get(i).stream()
                      .map(point -> point.move(0, SHAPE_OFFSET.get(i)))
                      .collect(Collectors.toSet()))
          .toList();

    public static Set<LongPoint> moveIfSpace(Set<LongPoint> rock, Vector movement, Set<LongPoint> occupied) {
        var candidate =
            rock.stream()
                .map(point -> point.moveArbitrary(movement))
                .filter(point -> 0 <= point.i() && point.i() <= TUNNEL_WIDTH)
                .filter(point -> 0 <= point.j())
                .collect(Collectors.toSet());

        var isCollisionFree = candidate.stream().noneMatch(occupied::contains);
        return (candidate.size() == rock.size() && isCollisionFree) ? candidate : rock;
    }

//  public static void draw(Set<Point> current, Set<Point> constraints, Integer highestPoint, String label) {
//    System.out.println(label);
//    for (int j = highestPoint; j > -1; j--){
//        var sb = new StringBuilder();
//        for (int i = 0; i <= TUNNEL_WIDTH; i++){
//            var c = new Point(i, j);
//            if (current.contains(c)) {
//                sb.append("@");
//            } else if (constraints.contains(c)) {
//                sb.append("#");
//            } else {
//                sb.append(".");
//            }
//        };
//        System.out.println(sb);
//    }
//    System.out.println("");
//    System.out.println("");
//    System.out.println("");
//  }

    @NotNull
    static Set<LongPoint> getShapeLong(Long towerHighestPoint, int shapeIdx) {
        var shape =
            new ArrayList<>(PROCESSED_SHAPES.get(shapeIdx))
                .stream()
                .map(intPoint -> new LongPoint(Long.valueOf(intPoint.i()), Long.valueOf(intPoint.j())))
                .map(point -> point.move(2L, +towerHighestPoint + 4))
                .collect(Collectors.toSet());
        return shape;
    }

    public static long part(String instructions, Long duration) {
        var instructionsList = Arrays.stream(instructions.split("")).toList();
        var tower = new HashSet<LongPoint>();
        var cache = new HashMap<String, Pair<Long, Long>>();

        int windex = 0; //wind index
        long count = 0;
        long highest = -1;
        while (count < duration){
            int shapeIdx = (int) (count % SHAPES.size());
            Set<LongPoint> shape = getShapeLong(highest, shapeIdx);

            var shapeWindKey = windex + "/" + shapeIdx;
            if (cache.containsKey(shapeWindKey)){
                var period = count - cache.get(shapeWindKey).getValue0(); //count
                if (count % period == duration % period){
                    Long heightBefore = cache.get(shapeWindKey).getValue1();
                    return heightBefore + Math.multiplyExact((highest +1) - heightBefore, Math.floorDiv((duration-count), period) + 1);
                }
            } else {
                cache.put(shapeWindKey, new Pair<>(count, highest + 1));
            }


            while (true) {
                int movementX =
                    ">".equals(
                        instructionsList.get(
                            windex))
                        ? 1
                        : -1;
                windex += 1;
                windex = windex % instructionsList.size();
                var horizontal = moveIfSpace(shape, new Vector(movementX, 0), tower);
                var vertical = moveIfSpace(horizontal, new Vector(0, -1), tower);

                if (horizontal == vertical) {
                    shape = horizontal;
                    break;
                }
                shape = vertical;
            }
            var highestPointInShape = shape.stream().map(LongPoint::j).mapToLong(Long::longValue).max().getAsLong();
            highest = Math.max(highest, highestPointInShape);
            tower.addAll(shape);
            count += 1;
        }
        return highest +1;
    }

  public static void main(String[] args) {
    try {
      InputStream i = Day17.class.getClassLoader().getResourceAsStream("2022/day17.txt");
      String instructions = new String(i.readAllBytes());
        System.out.println("Part1: " + part(instructions, 2022L));
        System.out.println("Part2: " + part(instructions, ELEPHANT_ARE_IMPRESSED_LIMIT));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
