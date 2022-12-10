package com.adventofcode.year2022;
import static com.adventofcode.year2022.Day7.getSize;
import static com.adventofcode.year2022.Day7.getTree;
import static org.junit.Assert.assertEquals;

import java.util.Map;
import org.junit.Test;

public class Day7Test {

  public static final String INSTRUCTIONS = """
      $ cd /
      $ ls
      dir a
      14848514 b.txt
      8504156 c.dat
      dir d
      $ cd a
      $ ls
      dir e
      29116 f
      2557 g
      62596 h.lst
      $ cd e
      $ ls
      584 i
      $ cd ..
      $ cd ..
      $ cd d
      $ ls
      4060174 j
      8033020 d.log
      5626152 d.ext
      7214296 k""";

  @Test
  public void testExample() {
    assertEquals(
        95437,
        Day7.part1(
            INSTRUCTIONS));
  }


  @Test
  public void testTreeSize(){
    Map<String, Map<String, Integer>> tree = getTree(INSTRUCTIONS);
    assertEquals(Integer.valueOf("48381165"), getSize(tree, tree.get("/"), 0));
  }

  @Test
  public void testExample2() {
    assertEquals(
        24933642,
        Day7.part2(
            INSTRUCTIONS));
  }


}
