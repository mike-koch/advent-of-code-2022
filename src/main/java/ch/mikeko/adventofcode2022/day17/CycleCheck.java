package ch.mikeko.adventofcode2022.day17;

import ch.mikeko.adventofcode2022.common.Coordinate;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class CycleCheck {
    private final int shapesDropped;
    private final int shapeIndex;
    private final int x;
    private final int height;
    private final String previousTenRows;

    public CycleCheck(int shapesDropped, int shapeIndex, int x, int height, Chamber chamber) {
        this.shapesDropped = shapesDropped;
        this.shapeIndex = shapeIndex;
        this.x = x;
        this.height = height;
        this.previousTenRows = buildPreviousTenRows(chamber, height);
    }

    public static String buildPreviousTenRows(Chamber chamber, int height) {
        var result = new StringBuilder();
        for (int y = 0; y < 1000; y++) {
            var row = "1234567";
            var finalY = height - y;
            if (finalY < 0) {
                return "INVALID";
            }
            var coordinates = chamber.getBlockedSpaces().stream().filter(x -> x.getY() == finalY).collect(Collectors.toList());
            for (var coordinate : coordinates) {
                row = row.replace(String.valueOf(coordinate.getX()), "X");
            }
            result.append(row);
        }

        return result.toString();
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

    public String getPreviousTenRows() {
        return previousTenRows;
    }
}
