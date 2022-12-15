package ch.mikeko.adventofcode2022.day05;

import ch.mikeko.adventofcode2022.common.InputParser;
import ch.mikeko.adventofcode2022.common.InputType;

import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PartTwo {
    private static final Map<Integer, Stack<String>> STACKS = Map.of(
        1, new Stack<>(),
        2, new Stack<>(),
        3, new Stack<>(),
        4, new Stack<>(),
        5, new Stack<>(),
        6, new Stack<>(),
        7, new Stack<>(),
        8, new Stack<>(),
        9, new Stack<>()
    );
    public static void main(String[] args) {
        populateStacks();

        try (Stream<String> lines = InputParser.getInputByLine(5, InputType.PUZZLE_INPUT)) {
            var allLines = lines.collect(Collectors.toList());

            for (var line : allLines) {
                // Reformat to make things easier
                var operations = line
                        .replace("move ", "")
                        .replace(" from ", ",")
                        .replace(" to ", ",")
                        .split(",");

                var numberOfCratesToMove = Integer.parseInt(operations[0]);
                var sourceStack = STACKS.get(Integer.parseInt(operations[1]));
                var destinationStack = STACKS.get(Integer.parseInt(operations[2]));
                var movingStack = new Stack<String>();
                for (int i = 0; i < numberOfCratesToMove; i++) {
                    movingStack.push(sourceStack.pop());
                }
                while (!movingStack.empty()) {
                    destinationStack.push(movingStack.pop());
                }
            }

            // Fetch top of each stack
            var results = new StringBuilder();
            for (int i = 1; i <= 9; i++) {
                results.append(STACKS.get(i).peek());
            }
            System.out.printf("Result for part two: %s%n", results);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private static void populateStacks() {
        // 1
        STACKS.get(1).push("D");
        STACKS.get(1).push("L");
        STACKS.get(1).push("J");
        STACKS.get(1).push("R");
        STACKS.get(1).push("V");
        STACKS.get(1).push("G");
        STACKS.get(1).push("F");
        // 2
        STACKS.get(2).push("T");
        STACKS.get(2).push("P");
        STACKS.get(2).push("M");
        STACKS.get(2).push("B");
        STACKS.get(2).push("V");
        STACKS.get(2).push("H");
        STACKS.get(2).push("J");
        STACKS.get(2).push("S");
        // 3
        STACKS.get(3).push("V");
        STACKS.get(3).push("H");
        STACKS.get(3).push("M");
        STACKS.get(3).push("F");
        STACKS.get(3).push("D");
        STACKS.get(3).push("G");
        STACKS.get(3).push("P");
        STACKS.get(3).push("C");
        // 4
        STACKS.get(4).push("M");
        STACKS.get(4).push("D");
        STACKS.get(4).push("P");
        STACKS.get(4).push("N");
        STACKS.get(4).push("G");
        STACKS.get(4).push("Q");
        // 5
        STACKS.get(5).push("J");
        STACKS.get(5).push("L");
        STACKS.get(5).push("H");
        STACKS.get(5).push("N");
        STACKS.get(5).push("F");
        // 6
        STACKS.get(6).push("N");
        STACKS.get(6).push("F");
        STACKS.get(6).push("V");
        STACKS.get(6).push("Q");
        STACKS.get(6).push("D");
        STACKS.get(6).push("G");
        STACKS.get(6).push("T");
        STACKS.get(6).push("Z");
        // 7
        STACKS.get(7).push("F");
        STACKS.get(7).push("D");
        STACKS.get(7).push("B");
        STACKS.get(7).push("L");
        // 8
        STACKS.get(8).push("M");
        STACKS.get(8).push("J");
        STACKS.get(8).push("B");
        STACKS.get(8).push("S");
        STACKS.get(8).push("V");
        STACKS.get(8).push("D");
        STACKS.get(8).push("N");
        // 9
        STACKS.get(9).push("G");
        STACKS.get(9).push("L");
        STACKS.get(9).push("D");
    }
}
