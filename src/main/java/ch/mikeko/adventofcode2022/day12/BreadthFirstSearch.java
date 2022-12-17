package ch.mikeko.adventofcode2022.day12;

import java.util.*;

public class BreadthFirstSearch {
    public Map<Node, Integer> traverse(Node start) {
        var distances = new HashMap<Node, Integer>();
        distances.put(start, 0);
        var queue = new ArrayDeque<Node>();
        queue.add(start);

        List<Node> visitedNodes = new ArrayList<>();
        while (!queue.isEmpty()) {
            var current = queue.poll();
            int distance = distances.get(current);

            if (!visitedNodes.contains(current)) {
                visitedNodes.add(current);

                for (var node : current.getValidNeighbors()) {
                    if (!distances.containsKey(node)) {
                        distances.put(node, distance + 1);
                        queue.add(node);
                    }
                }
            }
        }

        return distances;
    }
}
