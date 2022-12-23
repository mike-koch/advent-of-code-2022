package ch.mikeko.adventofcode2022.day17;

import ch.mikeko.adventofcode2022.common.Coordinate;
import ch.mikeko.adventofcode2022.common.InputParser;
import ch.mikeko.adventofcode2022.common.InputType;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Program {
    private static final List<Coordinate> SHAPE_ONE = List.of(
            new Coordinate(0, 0),
            new Coordinate(1, 0),
            new Coordinate(2, 0),
            new Coordinate(3, 0)
    );
    private static final List<Coordinate> SHAPE_TWO = List.of(
            new Coordinate(1, 2),
            new Coordinate(0, 1),
            new Coordinate(1, 1),
            new Coordinate(2, 1),
            new Coordinate(1, 0)
    );
    private static final List<Coordinate> SHAPE_THREE = List.of(
            new Coordinate(2, 2),
            new Coordinate(2, 1),
            new Coordinate(0, 0),
            new Coordinate(1, 0),
            new Coordinate(2, 0)
    );
    private static final List<Coordinate> SHAPE_FOUR = List.of(
            new Coordinate(0, 3),
            new Coordinate(0, 2),
            new Coordinate(0, 1),
            new Coordinate(0, 0)
    );
    private static final List<Coordinate> SHAPE_FIVE = List.of(
            new Coordinate(0, 0),
            new Coordinate(0, 1),
            new Coordinate(1, 0),
            new Coordinate(1, 1)
    );
    private static final Map<Integer, List<Coordinate>> ALL_SHAPES = new HashMap<>();
    private static final int CHAMBER_WIDTH = 7;

    public static void main(String[] args) {
        ALL_SHAPES.put(1, SHAPE_ONE);
        ALL_SHAPES.put(2, SHAPE_TWO);
        ALL_SHAPES.put(3, SHAPE_THREE);
        ALL_SHAPES.put(4, SHAPE_FOUR);
        ALL_SHAPES.put(5, SHAPE_FIVE);

        try (Stream<String> lines = InputParser.getInputByLine(17, InputType.PUZZLE_INPUT)) {
            var jetStreamIndex = 0;
            var jetStream = lines.collect(Collectors.toList()).get(0);
            var chamber = new Chamber();

            for (var i = 0; i < 2022; i++) {
                var shape = cloneShape(ALL_SHAPES.get(i % 5 + 1));
                var startingX = 2;
                var startingY = chamber.getTopOfChamber() + 3L;
                shape.forEach(coordinate -> {
                    coordinate.setX(coordinate.getX() + startingX);
                    coordinate.setY(coordinate.getY() + startingY);
                });

                var rockCanMoveDownwards = true;
                while (rockCanMoveDownwards) {
                    var jetStreamDelta = jetStream.charAt(jetStreamIndex++ % jetStream.length()) == '>' ? 1 : -1;
                    shape.forEach(coordinate -> coordinate.setX(coordinate.getX() + jetStreamDelta));

                    if (shape.stream().anyMatch(x -> x.getX() >= CHAMBER_WIDTH || x.getX() < 0) ||
                            shape.stream().anyMatch(coordinate -> !chamber.isSpaceAvailable(coordinate))) {
                        //-- Revert the move as we would be exceeding the chamber or colliding with another rock
                        shape.forEach(coordinate -> coordinate.setX(coordinate.getX() - jetStreamDelta));
                    }

                    //-- Check if we can go down one. If one part of the rock can't move, then we're done.
                    rockCanMoveDownwards = shape.stream().noneMatch(x -> x.getY() == 0L);
                    for (var coordinate : shape) {
                        var coordinateWithNewY = new Coordinate(coordinate.getX(), coordinate.getY() - 1);
                        rockCanMoveDownwards = rockCanMoveDownwards && chamber.isSpaceAvailable(coordinateWithNewY);
                    }

                    if (rockCanMoveDownwards) {
                        // Move all coordinates down one
                        shape.forEach(coordinate -> coordinate.setY(coordinate.getY() - 1));
                    }
                }

                //-- Rock stopped moving; record its coordinates and move onto the next shape.
                chamber.getBlockedSpaces().addAll(shape);
            }

            System.out.printf("Part one result: %s%n", chamber.getTopOfChamber());
        }
    }

    private static List<Coordinate> cloneShape(List<Coordinate> shape) {
        List<Coordinate> cloned = new ArrayList<>();
        for (var coordinate : shape) {
            cloned.add(new Coordinate(coordinate.getX(), coordinate.getY()));
        }

        return cloned;
    }
}