package ch.mikeko.adventofcode2022.day12;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final char letter;
    private int weight;
    private List<Node> validNeighbors = new ArrayList<>();
    private boolean visited;

    public Node(char letter) {
        this.letter = letter;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public List<Node> getValidNeighbors() {
        return validNeighbors;
    }

    public void setValidNeighbors(List<Node> validNeighbors) {
        this.validNeighbors = validNeighbors;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    @Override
    public String toString() {
        return String.format("Node{%s}", letter);
    }
}
