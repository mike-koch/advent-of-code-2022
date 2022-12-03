package ch.mikeko.adventofcode2022.two;

import java.util.Map;

public class Rock extends Shape {
    @Override
    int getShapeScore() {
        return 1;
    }

    @Override
    Map<Class<? extends Shape>, Integer> getScores() {
        return Map.of(
                Rock.class, 3,
                Paper.class, 0,
                Scissors.class, 6
        );
    }
}
