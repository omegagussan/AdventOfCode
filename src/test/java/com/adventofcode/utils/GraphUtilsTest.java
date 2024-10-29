package com.adventofcode.utils;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class GraphUtilsTest extends TestCase {

    @Test
    public void testBFSVisitsAllNodes() {
        GraphNode root = new GraphNode("A", 1);
        GraphNode child1 = new GraphNode("B", 2);
        GraphNode child2 = new GraphNode("C", 3);
        root.addChild(child1);
        root.addChild(child2);

        Set<String> visitedNodes = new HashSet<>();
        Consumer<GraphNode> graphNodeConsumer = node -> visitedNodes.add(node.id().get());
        Function<GraphNode, Boolean> exitCondition = node -> visitedNodes.contains("C");
        GraphUtils.BFS(root, graphNodeConsumer, exitCondition);

        var expected = Set.of("A", "B", "C");

        assertEquals(expected, visitedNodes);
    }

    @Test
    public void testBFSStopsAtCondition() {
        GraphNode root = new GraphNode("A", 1);
        GraphNode child1 = new GraphNode("B", 2);
        GraphNode child2 = new GraphNode("C", 3);
        root.addChild(child1);
        root.addChild(child2);

        Set<String> visitedNodes = new HashSet<>();
        Consumer<GraphNode> graphNodeConsumer = node -> visitedNodes.add(node.id().get());
        Function<GraphNode, Boolean> exitCondition = node -> node.id().get().equals("B");
        GraphUtils.BFS(root, graphNodeConsumer, exitCondition);

        var expected = Set.of("A", "B");

        assertEquals(expected, visitedNodes);
    }
}