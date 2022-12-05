package ch.mikeko.adventofcode2022.day03;

import ch.mikeko.adventofcode2022.common.InputParser;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Program {
    public static void main(String[] args) {
        var partOnePrioritySum = 0;
        var partTwoPrioritySum = 0;

        try (Stream<String> lines = InputParser.getInputByLine(3)) {
            var allLines = lines.collect(Collectors.toList());

            var partTwoCounter = 1;
            List<String> partTwoSublists = new ArrayList<>();
            for (var line : allLines) {
                //region Part 1 - common item in each list
                var lineHalfLength = line.length() / 2;
                var firstHalf = line.substring(0, lineHalfLength);
                var secondHalf = line.substring(lineHalfLength);

                var commonCharacter = getCommonCharacter(firstHalf, secondHalf);
                partOnePrioritySum += getPriority(commonCharacter);
                //endregion
                //region Part 2 - common char in each group of 3
                partTwoSublists.add(line);
                if (partTwoCounter++ % 3 == 0) {
                    commonCharacter = getCommonCharacter(partTwoSublists.toArray(new String[] {}));
                    partTwoPrioritySum += getPriority(commonCharacter);
                    partTwoSublists.clear();
                }
                //endregion
            }

            System.out.printf("Part one result: %s%n", partOnePrioritySum);
            System.out.printf("Part two result: %s%n", partTwoPrioritySum);
        }
    }

    private static Character getCommonCharacter(String... strings) {
        List<TreeSet<Character>> sets = new ArrayList<>();
        for (String aString : strings) {
            var set = new TreeSet<Character>();
            for (char character : aString.toCharArray()) {
                set.add(character);
            }

            sets.add(set);
        }

        for (int i = 1; i < sets.size(); i++) {
            sets.get(0).retainAll(sets.get(i));
        }

        return sets.get(0).stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Couldn't find a matching string"));
    }

    private static int getPriority(char value) {
        if (value < 97) {
            // 'A' = 65 -> 27
            return ((int) value) - 38;
        }

        // 'a' = 97 -> 1
        return ((int) value) - 96;
    }
}
