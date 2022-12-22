package ch.mikeko.adventofcode2022.day16;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class DisjointPair {
    private final Map.Entry<Set<String>, Integer> left;
    private final Map.Entry<Set<String>, Integer> right;

    public DisjointPair(Map.Entry<Set<String>, Integer> left, Map.Entry<Set<String>, Integer> right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DisjointPair that = (DisjointPair) o;
        return (Objects.equals(left, that.left) && Objects.equals(right, that.right)) ||
                (Objects.equals(left, that.right) && Objects.equals(right, that.left));
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    public int getCombinedPressure() {
        return left.getValue() + right.getValue();
    }
}
