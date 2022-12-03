package ch.mikeko.adventofcode2022.two;

import java.util.Map;

public class Scissors extends Shape {
    @Override
    int getShapeScore() {
        return 3;
    }

    @Override
    Class<? extends Shape> getWinningShapeType() {
        return Paper.class;
    }

    @Override
    Class<? extends Shape> getLosingShapeType() {
        return Rock.class;
    }
}
