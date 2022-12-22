package ch.mikeko.adventofcode2022.day16;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Valve {
    private final String name;
    private final int matrixIndex;
    private int flowRate = -1;
    private final List<String> neighbors = new ArrayList<>();


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Valve valve = (Valve) o;
        return Objects.equals(name, valve.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
