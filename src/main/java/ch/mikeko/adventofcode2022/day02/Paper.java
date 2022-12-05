package ch.mikeko.adventofcode2022.day02;

public class Paper extends Shape {
    @Override
    int getShapeScore() {
        return 2;
    }

    @Override
    Class<? extends Shape> getWinningShapeType() {
        return Rock.class;
    }

    @Override
    Class<? extends Shape> getLosingShapeType() {
        return Scissors.class;
    }
}
