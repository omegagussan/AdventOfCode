package com.adventofcode.utils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Function;

public class GraphUtils {
    public static void BFS(GraphNode root, Consumer<GraphNode> visitConsumer, Function<GraphNode, Boolean> stopCondition) {
        Queue<GraphNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            GraphNode node = queue.poll();
            visitConsumer.accept(node);
            if (stopCondition.apply(node)) {
                break;
            }
            queue.addAll(node.children());
        }
    }
}
