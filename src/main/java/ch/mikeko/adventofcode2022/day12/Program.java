package ch.mikeko.adventofcode2022.day12;

import ch.mikeko.adventofcode2022.common.InputParser;
import ch.mikeko.adventofcode2022.common.InputType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Program {
    private static Node start;
    private static Node end;

    private static final List<Node> PART_TWO_POTENTIAL_STARTS = new ArrayList<>();

    public static void main(String[] args) {
        var bfs = new BreadthFirstSearch();

        try (Stream<String> lines = InputParser.getInputByLine(12, InputType.PUZZLE_INPUT)) {
            var allLines = lines.collect(Collectors.toList());
            Node[][] graph = initNodes(allLines);
            populateValidNeighbors(graph);

            //region Part One
            var visitCount = bfs.traverse(start);
            System.out.printf("Part one answer: %s%n", visitCount.get(end));
            //endregion

            //region Part Two
            var partTwoVisitCount = Integer.MAX_VALUE;
            for (var node : PART_TWO_POTENTIAL_STARTS) {
                Map<Node, Integer> traversalResult = bfs.traverse(node);
                partTwoVisitCount = Math.min(partTwoVisitCount, traversalResult.getOrDefault(end, Integer.MAX_VALUE));
            }
            System.out.printf("Part two answer: %s%n", partTwoVisitCount);
            //endregion
        }
    }

    private static Node[][] initNodes(List<String> allLines) {
        Node[][] graph = null;
        boolean first = true;
        for (var i = 0; i < allLines.size(); i++) {
            var line = allLines.get(i);
            if (first) {
                graph = new Node[allLines.size()][line.length()];
                first = false;
            }

            for (var j = 0; j < line.length(); j++) {
                var letter = line.charAt(j);
                var node = new Node(letter);
                node.setWeight(getWeight(letter));
                graph[i][j] = node;

                if (letter == 'S') {
                    start = node;
                } else if (letter == 'E') {
                    end = node;
                }

                if (node.getWeight() == 1) {
                    PART_TWO_POTENTIAL_STARTS.add(node);
                }
            }
        }

        return graph;
    }

    private static int getWeight(char character) {
        if (character == 'S') {
            character = 'a';
        }
        if (character == 'E') {
            character = 'z';
        }

        // a = 97
        return (int) character - 96;
    }

    private static void populateValidNeighbors(Node[][] graph) {
        for (var i = 0; i < graph.length; i++) {
            for (var j = 0; j < graph[i].length; j++) {
                var node = graph[i][j];
                potentiallyAddNeighbor(graph, node, i, j - 1);
                potentiallyAddNeighbor(graph, node, i, j + 1);
                potentiallyAddNeighbor(graph, node, i - 1, j);
                potentiallyAddNeighbor(graph, node, i + 1, j);
            }
        }
    }

    private static void potentiallyAddNeighbor(Node[][] graph, Node current, int i, int j) {
        try {
            var neighbor = graph[i][j];
            if (neighbor.getWeight() - current.getWeight() < 2) {
                current.getValidNeighbors().add(neighbor);
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {
            //-- no-op
        }
    }
}
