package ch.mikeko.adventofcode2022.three;

import ch.mikeko.adventofcode2022.common.InputParser;

import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Program {
    public static void main(String[] args) {
        var prioritySum = 0;

        try (Stream<String> lines = InputParser.getInputByLine(3)) {
            var allLines = lines.collect(Collectors.toList());

            for (var line : allLines) {
                var lineHalfLength = line.length() / 2;
                var firstHalf = line.substring(0, lineHalfLength);
                var secondHalf = line.substring(lineHalfLength);

                var commonCharacter = getCommonCharacter(firstHalf, secondHalf);
                if (commonCharacter < 97) {
                    // Uppercase
                    prioritySum += getUppercasePriority(commonCharacter);
                } else {
                    // Lowercase
                    prioritySum += getLowercasePriority(commonCharacter);
                }
            }

            System.out.printf("Part one result: %s%n", prioritySum);
        }
    }

    private static Character getCommonCharacter(String firstHalf, String secondHalf) {
        var firstSet = new TreeSet<Character>();
        for (char character : firstHalf.toCharArray()) {
            firstSet.add(character);
        }

        var secondSet = new TreeSet<Character>();
        for (char character : secondHalf.toCharArray()) {
            secondSet.add(character);
        }

        firstSet.retainAll(secondSet);

        return firstSet.stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("Couldn't find a matching string between '%s' and '%s'", firstHalf, secondHalf)));
    }

    private static int getLowercasePriority(char value) {
        // 'a' = 97 -> 1
        return ((int) value) - 96;
    }

    private static int getUppercasePriority(char value) {
        // 'A' = 65 -> 27
        return ((int) value) - 38;
    }
}
