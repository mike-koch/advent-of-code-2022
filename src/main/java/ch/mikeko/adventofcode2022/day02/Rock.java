package ch.mikeko.adventofcode2022.day02;

public class Rock extends Shape {
    @Override
    int getShapeScore() {
        return 1;
    }

    @Override
    Class<? extends Shape> getWinningShapeType() {
        return Scissors.class;
    }

    @Override
    Class<? extends Shape> getLosingShapeType() {
        return Paper.class;
    }
}
