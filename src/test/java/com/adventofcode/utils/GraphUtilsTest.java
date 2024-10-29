package com.adventofcode.utils;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertNull(node);
    }

    @Test
    public void testLineageReturnsAllNodes() {
        GraphNode root = new GraphNode("A", 1);
        GraphNode child1 = new GraphNode("B", 2);
        GraphNode child2 = new GraphNode("C", 3);
        root.addChild(child1);
        root.addChild(child2);

        var lineage = GraphUtils.lineage(child1);

        assertEquals(List.of(child1, root), lineage);
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

    @org.junit.jupiter.api.Test
    void DFSVisitsAllNodes() {
        GraphNode root = new GraphNode("A", 1);
        GraphNode child1 = new GraphNode("B", 2);
        GraphNode child2 = new GraphNode("C", 3);
        root.addChild(child1);
        root.addChild(child2);

        Set<String> visited = new HashSet<>();
        Consumer<GraphNode> visitConsumer = node -> visited.add(node.id().get());
        Function<GraphNode, Boolean> stopCondition = node -> false;
        GraphUtils.DFS(root, visitConsumer, stopCondition);

        assertEquals(Set.of("A", "B", "C"), visited);
    }

    @org.junit.jupiter.api.Test
    void DFSStopsAtCondition() {
        GraphNode root = new GraphNode("A", 1);
        GraphNode child1 = new GraphNode("B", 2);
        GraphNode child2 = new GraphNode("C", 3);
        root.addChild(child1);
        root.addChild(child2);

        Set<String> visited = new HashSet<>();
        Consumer<GraphNode> visitConsumer = node -> visited.add(node.id().get());
        Function<GraphNode, Boolean> stopCondition = node -> node.id().get().equals("C");
        GraphUtils.DFS(root, visitConsumer, stopCondition);

        assertEquals(Set.of("A", "B", "C"), visited);
    }
}