package com.adventofcode.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GraphNodeTest {

    @Test
    void equalsReturnsTrueForSameNode() {
        GraphNode node1 = new GraphNode(1);
        GraphNode node2 = new GraphNode(1);
        assertEquals(node1, node2);
    }

    @Test
    void equalsReturnsFalseForDifferentValues() {
        GraphNode node1 = new GraphNode(1);
        GraphNode node2 = new GraphNode(2);
        assertNotEquals(node1, node2);
    }

    @Test
    void equalsReturnsFalseForDifferentNames() {
        GraphNode node1 = new GraphNode("A", 1);
        GraphNode node2 = new GraphNode("B", 1);
        assertNotEquals(node1, node2);
    }

    @Test
    void equalsReturnsTrueForSameStructure() {
        GraphNode node1 = new GraphNode(1);
        GraphNode child1 = new GraphNode(2);
        node1.addChild(child1);

        GraphNode node2 = new GraphNode(1);
        GraphNode child2 = new GraphNode(2);
        node2.addChild(child2);

        assertEquals(node1, node2);
    }

    @Test
    void equalsReturnsFalseForDifferentStructure() {
        GraphNode node1 = new GraphNode(1);
        GraphNode child1 = new GraphNode(2);
        node1.addChild(child1);

        GraphNode node2 = new GraphNode(1);
        GraphNode child2 = new GraphNode(3);
        node2.addChild(child2);

        assertNotEquals(node1, node2);
    }

    @Test
    void equalsReturnsFalseForDifferentNumberOfChildren() {
        GraphNode node1 = new GraphNode(1);
        GraphNode child1 = new GraphNode(2);
        node1.addChild(child1);

        GraphNode node2 = new GraphNode(1);

        assertNotEquals(node1, node2);
    }
}