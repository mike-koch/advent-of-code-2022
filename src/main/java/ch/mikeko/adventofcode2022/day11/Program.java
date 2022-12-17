package ch.mikeko.adventofcode2022.day11;

import ch.mikeko.adventofcode2022.common.InputParser;
import ch.mikeko.adventofcode2022.common.InputType;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Program {
    // Part one
    /*private static Integer WORRY_DIVISOR = new Integer("3");
    private static int ROUND_COUNT = 20;*/

    // Part two
    private static int WORRY_DIVISOR = 1;
    private static int ROUND_COUNT = 10000;

    public static void main(String[] args) {

        try (Stream<String> lines = InputParser.getInputByLine(11, InputType.PUZZLE_INPUT)) {
            var allLines = lines.collect(Collectors.toList());
            var monkeys = new LinkedHashMap<Integer, Monkey>();

            var modValue = setupMonkeys(allLines, monkeys);

            for (var i = 0; i < ROUND_COUNT; i++) {
                for (var monkey : monkeys.values()) {
                    while (!monkey.getItems().isEmpty()) {
                        var item = monkey.getItems().poll();
                        item = monkey.inspect(item) % modValue / WORRY_DIVISOR;

                        if (item % monkey.getDivisibleByFactor() == 0) {
                            monkeys.get(monkey.getDivisibleTrueRecipient()).pushItem(item);
                        } else {
                            monkeys.get(monkey.getDivisibleFalseRecipient()).pushItem(item);
                        }
                    }
                }
            }

            /*for (var monkey : monkeys.entrySet()) {
                System.out.printf("Monkey %s inspected items %s times.%n", monkey.getKey(), monkey.getValue().getInspectionCount());
            }*/
            var monkeyInspections = monkeys.values().stream()
                    .map(Monkey::getInspectionCount)
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toList());
            System.out.printf("Part one result: %s%n", monkeyInspections.get(0) * monkeyInspections.get(1));
        }
    }

    private static int setupMonkeys(List<String> allLines, HashMap<Integer, Monkey> monkeys) {
        var totalModValue = 0;
        var currentMonkeyIndex = -1;
        for (var line : allLines) {
            // Trim indentation
            line = line.replaceAll("\\s{2}+", "");

            if (line.startsWith("Monkey")) {
                var monkeyIndex = Integer.parseInt(line
                        .replace("Monkey ", "")
                        .replace(":", ""));
                monkeys.put(monkeyIndex, new Monkey());
                currentMonkeyIndex = monkeyIndex;
                continue;
            }

            Monkey monkey = monkeys.get(currentMonkeyIndex);
            if (line.startsWith("Starting items")) {
                var items = Arrays.stream(line.replace("Starting items: ", "")
                        .replace(", ", ",")
                        .split(","))
                        .map(Long::parseUnsignedLong)
                        .collect(Collectors.toList());

                items.forEach(monkey::pushItem);
                continue;
            }

            if (line.startsWith("Operation")) {
                // old * 19
                var operands = line.replace("Operation: new = ", "");
                if ("old * old".equals(operands)) {
                    monkey.setOperation(item -> item * item);
                } else {
                    var splitOperands = operands.split(" ");

                    if ("+".equals(splitOperands[1])) {
                        monkey.setOperation(item -> item + Long.parseUnsignedLong(splitOperands[2]));
                    } else if ("*".equals(splitOperands[1])) {
                        monkey.setOperation(item -> item * Long.parseUnsignedLong(splitOperands[2]));
                    } else {
                        throw new IllegalStateException("Unknown operand " + splitOperands[1]);
                    }
                }
            }

            if (line.startsWith("Test")) {
                var modValue = Long.parseUnsignedLong(line.replace("Test: divisible by ", ""));
                if (totalModValue == 0) {
                    totalModValue = (int)modValue;
                } else {
                    totalModValue *= modValue;
                }

                monkey.setDivisibleByFactor(modValue);
                continue;
            }

            if (line.startsWith("If true")) {
                monkey.setDivisibleTrueRecipient(Integer.parseInt(line.replace("If true: throw to monkey ", "")));
            }

            if (line.startsWith("If false")) {
                monkey.setDivisibleFalseRecipient(Integer.parseInt(line.replace("If false: throw to monkey ", "")));
            }
        }

        return totalModValue;
    }
}
