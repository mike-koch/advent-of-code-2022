package ch.mikeko.adventofcode2022.day02;

import ch.mikeko.adventofcode2022.common.InputParser;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Program {
    public static void main(String[] args) {
        int partOneTotalScore = 0;
        int partTwoTotalScore = 0;

        try (Stream<String> lines = InputParser.getInputByLine(2)) {
            var allLines = lines.collect(Collectors.toList());
            // Sample line: B Z
            for (var line : allLines) {
                var symbols = line.split(" ");
                var opponentShape = getShapeForSymbol(symbols[0]);


                // Part one
                var myShape = getShapeForSymbol(symbols[1]);
                partOneTotalScore += myShape.getShapeScore();
                partOneTotalScore += myShape.accept(opponentShape);

                // Part two
                var shapeForOutcome = getShapeForOutcome(opponentShape, symbols[1]);
                partTwoTotalScore += shapeForOutcome.getShapeScore();
                partTwoTotalScore += shapeForOutcome.accept(opponentShape);
            }
        }

        System.out.printf("Part one total score: %s%n", partOneTotalScore);
        System.out.printf("Part two total score: %s%n", partTwoTotalScore);
    }

    private static Shape getShapeForSymbol(String symbol) {
        if (Arrays.asList("A", "X").contains(symbol)) {
            return new Rock();
        }
        if (Arrays.asList("B", "Y").contains(symbol)) {
            return new Paper();
        }
        if (Arrays.asList("C", "Z").contains(symbol)) {
            return new Scissors();
        }

        throw new RuntimeException(String.format("Unhandled symbol: '%s'", symbol));
    }

    private static Shape getShapeForOutcome(Shape opponentShape, String desiredOutcome) {
        Class<? extends Shape> shapeToCreate;
        if ("X".equals(desiredOutcome)) {
            // Loss
            shapeToCreate = opponentShape.getWinningShapeType();
        } else if ("Y".equals(desiredOutcome)) {
            shapeToCreate = opponentShape.getClass();
        } else if ("Z".equals(desiredOutcome)) {
            shapeToCreate = opponentShape.getLosingShapeType();
        } else {
            throw new RuntimeException(String.format("Unhandled symbol: '%s'", desiredOutcome));
        }

        try {
            return shapeToCreate.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
