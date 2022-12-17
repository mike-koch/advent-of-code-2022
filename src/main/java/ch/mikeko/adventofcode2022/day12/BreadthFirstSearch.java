package ch.mikeko.adventofcode2022.day12;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public class BreadthFirstSearch {
    public Map<Node, Integer> traverse(Node start) {
        var distances = new HashMap<Node, Integer>();
        distances.put(start, 0);
        var queue = new ArrayDeque<Node>();
        queue.add(start);
        while (!queue.isEmpty()) {
            var current = queue.poll();
            int distance = distances.get(current);

            if (!current.isVisited()) {
                current.setVisited(true);

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
