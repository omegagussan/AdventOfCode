package com.adventofcode.utils;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
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

        Set<String> visited = new HashSet<>();
        Function<GraphNode, Boolean> exitCondition = node -> false;
        Consumer<GraphNode> visitConsumer = node -> {
            visited.add(node.id().get());
        };
        var node = GraphUtils.BFS(root, exitCondition, visitConsumer);

        assertEquals(Set.of("A", "B", "C"), visited);
        assertEquals(null, node);
    }

    @Test
    public void testBFSStopsAtCondition() {
        GraphNode root = new GraphNode("A", 1);
        GraphNode child1 = new GraphNode("B", 2);
        GraphNode child2 = new GraphNode("C", 3);
        root.addChild(child1);
        root.addChild(child2);

        Function<GraphNode, Boolean> exitCondition = node -> node.id().get().equals("B");
        var node = GraphUtils.BFS(root, exitCondition);

        assertEquals(child1, node);
    }
}