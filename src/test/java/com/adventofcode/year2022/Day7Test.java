package com.adventofcode.year2022;
import static com.adventofcode.year2022.Day7.getSize;
import static com.adventofcode.year2022.Day7.getTree;
import static org.junit.Assert.assertEquals;

import java.util.Map;
import org.junit.Test;

public class Day7Test {

  public static final String INSTRUCTIONS = "$ cd /\n"
      + "$ ls\n"
      + "dir a\n"
      + "14848514 b.txt\n"
      + "8504156 c.dat\n"
      + "dir d\n"
      + "$ cd a\n"
      + "$ ls\n"
      + "dir e\n"
      + "29116 f\n"
      + "2557 g\n"
      + "62596 h.lst\n"
      + "$ cd e\n"
      + "$ ls\n"
      + "584 i\n"
      + "$ cd ..\n"
      + "$ cd ..\n"
      + "$ cd d\n"
      + "$ ls\n"
      + "4060174 j\n"
      + "8033020 d.log\n"
      + "5626152 d.ext\n"
      + "7214296 k";

  @Test
  public void testExample() {
    assertEquals(
        "simple case",
        95437,
        Day7.part1(
            INSTRUCTIONS));
  }


  @Test
  public void testTreeSize(){
    Map<String, Map<String, Integer>> tree = getTree(INSTRUCTIONS);
    assertEquals("message", Integer.valueOf("48381165"), getSize(tree, tree.get("/")));
  }

  @Test
  public void testExample2() {
    assertEquals(
        "simple case",
        24933642,
        Day7.part2(
            INSTRUCTIONS));
  }


}
