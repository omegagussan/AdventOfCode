package com.adventofcode.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public record GraphNode(
    AtomicReference<GraphNode> parent,
    Optional<String> id,
    Optional<Integer> value,
    ArrayList<GraphNode> children) {

    public void addChild(GraphNode child) {
        child.setParent(this);
        this.children.add(child);
    }

    public void setParent(GraphNode parent) {
        this.parent.set(parent);
    }

    public GraphNode(String name, Integer value) {
        this(new AtomicReference<>(), Optional.of(name), Optional.of(value), new ArrayList<>(List.of()));
    }

    public GraphNode(Integer value) {
        this(new AtomicReference<>(), Optional.empty(), Optional.of(value), new ArrayList<>(List.of()));
    }


    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof GraphNode other)) return false;
        if (!this.id.map(v -> v.equals(other.id.orElse(null))).orElseGet(other.id::isEmpty)){
            return false;
        }
        if (!this.value.map(v -> v.equals(other.value.orElse(null))).orElseGet(other.value::isEmpty)){
            return false;
        }
        for (int i = 0; i < this.children.size(); i++) {
            HashSet<GraphNode> otherChildren = new HashSet<>(other.children);
            if (!otherChildren.contains(this.children.get(i))) {
                return false;
            }
        }
        return true;
    }
}
