package com.adventofcode.utils;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class GraphUtils {
    public static GraphNode BFS(GraphNode root, Function<GraphNode, Boolean> stopCondition, Consumer<GraphNode> visitConsumer) {
        Queue<GraphNode> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            GraphNode node = queue.poll();
            if (visited.contains(node.id().get())) {
                continue;
            }
            if (visitConsumer != null) {
                visitConsumer.accept(node);
            }
            visited.add(node.id().get());
            if (stopCondition.apply(node)) {
                return node;
            }
            queue.addAll(node.children());
        }
        return null;
    }

    public static List<GraphNode> lineage(GraphNode node) {
        List<GraphNode> lineage = new ArrayList<>();
        GraphNode current = node;
        while (current != null) {
            lineage.add(current);
            current = current.parent().get();
        }
        return lineage;
    }


    public static GraphNode BFS(GraphNode root, Function<GraphNode, Boolean> stopCondition) {
        return BFS(root, stopCondition, null);
    }

    public static GraphNode DFS(GraphNode root, Consumer<GraphNode> visitConsumer, Function<GraphNode, Boolean> stopCondition) {
        List<String> visited = new ArrayList<>();
        return DFSRecursivePart(root, visitConsumer, stopCondition, visited);
    }

    private static GraphNode DFSRecursivePart(GraphNode curr, Consumer<GraphNode> visitConsumer, Function<GraphNode, Boolean> stopCondition, List<String> visited) {
        visited.add(curr.id().get());
        visitConsumer.accept(curr);
        if (stopCondition.apply(curr)) {
            return curr;
        }
        for (GraphNode child : curr.children()) {
            if (!visited.contains(child.id().get())) {
                DFSRecursivePart(child, visitConsumer, stopCondition, visited);
            }
        }
        return null;
    }

    public static GraphNode Dijkstra(GraphNode root, Function<GraphNode, Integer> weightFunction,  Function<GraphNode, Boolean> stopCondition) {
        PriorityQueue<GraphNode> queue = new PriorityQueue<>(Comparator.comparingInt(weightFunction::apply));
        Set<String> visited = new HashSet<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            GraphNode node = queue.poll();
            if (stopCondition.apply(node)) {
                return node;
            }
            if (visited.contains(node.id().get())) {
                continue;
            }
            visited.add(node.id().get());
            queue.addAll(node.children());
        }
        return null;
    }
}
