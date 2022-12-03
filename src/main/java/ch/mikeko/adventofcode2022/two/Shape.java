package ch.mikeko.adventofcode2022.two;

import java.util.Map;

public abstract class Shape {
    abstract int getShapeScore();
    abstract Class<? extends Shape> getWinningShapeType();
    abstract Class<? extends Shape> getLosingShapeType();

    public int accept(Shape otherShape) {
        if (getWinningShapeType() == otherShape.getClass()) {
            return 6;
        }

        if (getLosingShapeType() == otherShape.getClass()) {
            return 0;
        }

        return 3;
    }
}
