package ch.mikeko.adventofcode2022.day16;

import java.util.ArrayList;
import java.util.List;

public class Valve {
    private final String name;
    private final int matrixIndex;
    private int flowRate = -1;
    private List<String> neighbors = new ArrayList<>();


    public Valve(String name, int matrixIndex) {
        this.name = name;
        this.matrixIndex = matrixIndex;
    }

    public String getName() {
        return name;
    }

    public int getMatrixIndex() {
        return matrixIndex;
    }

    public void setFlowRate(int flowRate) {
        this.flowRate = flowRate;
    }

    public int getFlowRate() {
        return flowRate;
    }

    public void addNeighbor(String neighbor) {
        neighbors.add(neighbor);
    }

    public List<String> getNeighbors() {
        return neighbors;
    }
}
