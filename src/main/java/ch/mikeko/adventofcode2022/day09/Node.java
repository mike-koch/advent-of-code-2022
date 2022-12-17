package ch.mikeko.adventofcode2022.day09;

import java.util.*;

public class Node {
    private int x;
    private int y;
    private final Set<Node> visitedNodes = new LinkedHashSet<>();

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void moveTo(int x, int y) {
        visitedNodes.add(new Node(this.x, this.y));
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getVisitedNodeCount() {
        if (!visitedNodes.contains(this)) {
            return visitedNodes.size() + 1;
        }

        return visitedNodes.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x && y == node.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
