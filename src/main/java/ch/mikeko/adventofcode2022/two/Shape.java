package ch.mikeko.adventofcode2022.two;

import java.util.Map;

public abstract class Shape {
    abstract int getShapeScore();
    abstract Map<Class<? extends Shape>, Integer> getScores();

    public int accept(Shape otherShape) {
        return getScores().getOrDefault(otherShape.getClass(), Integer.MIN_VALUE);
    }
}
