package ch.mikeko.adventofcode2022.two;

import ch.mikeko.adventofcode2022.common.InputParser;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Program {
    public static void main(String[] args) {
        int totalScore = 0;

        try (Stream<String> lines = InputParser.getInputByLine(2)) {
            var allLines = lines.collect(Collectors.toList());
            // Sample line: B Z
            for (var line : allLines) {
                var shapes = line.split(" ");

                var myShape = getShapeForSymbol(shapes[1]);
                totalScore += myShape.getShapeScore();
                totalScore += myShape.accept(getShapeForSymbol(shapes[0]));
            }
        }

        System.out.printf("Total score: %s%n", totalScore);
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
}
