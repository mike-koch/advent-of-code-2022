package ch.mikeko.adventofcode2022.two;

import java.util.Map;

public class Scissors extends Shape {
    @Override
    int getShapeScore() {
        return 3;
    }

    @Override
    Map<Class<? extends Shape>, Integer> getScores() {
        return Map.of(
                Rock.class, 0,
                Paper.class, 6,
                Scissors.class, 3
        );
    }
}
