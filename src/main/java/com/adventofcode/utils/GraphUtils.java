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

    public static GraphNode BFS(GraphNode root, Function<GraphNode, Boolean> stopCondition) {
        return BFS(root, stopCondition, null);
    }

    public static void DFS(GraphNode root, Consumer<GraphNode> visitConsumer, Function<GraphNode, Boolean> stopCondition) {
        visitConsumer.accept(root);
        if (stopCondition.apply(root)) {
            return;
        }
        for (GraphNode child : root.children()) {
            DFS(child, visitConsumer, stopCondition);
        }
    }
}
