package ch.mikeko.adventofcode2022.day17;

import ch.mikeko.adventofcode2022.common.Coordinate;

import java.util.HashSet;
import java.util.List;

public class CycleCheck {
    private final int shapesDropped;
    private final int shapeIndex;
    private final int x;
    private final int height;

    public CycleCheck(int shapesDropped, int shapeIndex, int x, int height) {
        this.shapesDropped = shapesDropped;
        this.shapeIndex = shapeIndex;
        this.x = x;
        this.height = height;
    }

    public int getShapeIndex() {
        return shapeIndex;
    }

    public int getX() {
        return x;
    }

    public int getHeight() {
        return height;
    }

    public int getShapesDropped() {
        return shapesDropped;
    }
}
