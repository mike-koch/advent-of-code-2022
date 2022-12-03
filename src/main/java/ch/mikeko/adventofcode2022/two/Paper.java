package ch.mikeko.adventofcode2022.two;

import java.util.Map;

public class Paper extends Shape {
    @Override
    int getShapeScore() {
        return 2;
    }

    @Override
    Map<Class<? extends Shape>, Integer> getScores() {
        return Map.of(
                Rock.class, 6,
                Paper.class, 3,
                Scissors.class, 0
        );
    }
}
