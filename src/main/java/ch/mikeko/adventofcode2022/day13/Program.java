package ch.mikeko.adventofcode2022.day13;

import ch.mikeko.adventofcode2022.common.InputParser;
import ch.mikeko.adventofcode2022.common.InputType;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Program {
    public static void main(String[] args) {
        try (Stream<String> lines = InputParser.getInputByLine(13, InputType.PUZZLE_INPUT)) {
            var allLines = lines.collect(Collectors.toList());

            List<Integer> indicesInOrder = new ArrayList<>();
            for (var i = 0; i < allLines.size(); i += 3) {
                var first = parseLine(allLines.get(i));
                var second = parseLine(allLines.get(i + 1));

                var sortedStatus = pairIsInOrder(first, second);
                if (sortedStatus == Status.UNKNOWN) {
                    throw new IllegalStateException(String.format("Unknown sort result: %s vs %s", allLines.get(i), allLines.get(i + 1)));
                }
                if (sortedStatus == Status.ORDERED) {
                    indicesInOrder.add(i / 3 + 1);
                }
            }

            System.out.printf("Part one result: %s%n", indicesInOrder.stream().mapToInt(x -> x).sum());
        }
    }

    private static List<Object> parseLine(String line) {
        var sb = new StringBuilder(line);
        line = sb.deleteCharAt(0).deleteCharAt(sb.length() - 1).toString();

        var startArrayIndices = new Stack<Integer>();
        List<Object> entities = new ArrayList<>();
        StringBuilder value = new StringBuilder();
        for (var i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '[') {
                startArrayIndices.push(i);
            } else if (line.charAt(i) == ']') {
                if (startArrayIndices.size() == 1) {
                    entities.add(parseLine(line.substring(startArrayIndices.pop(), i + 1)));
                    i++;
                } else {
                    startArrayIndices.pop();
                }

                continue;
            }

            // Don't grab anything that isn't meant for this array
            if (!startArrayIndices.empty()) {
                continue;
            }

            if (line.charAt(i) == ',') {
                entities.add(Integer.parseInt(value.toString()));
                value = new StringBuilder();
            } else {
                value.append(line.charAt(i));
            }
        }
        if (!"".equals(value.toString())) {
            entities.add(Integer.parseInt(value.toString()));
        }

        return entities;
    }

    @SuppressWarnings("unchecked")
    private static Status pairIsInOrder(List<Object> firstList, List<Object> secondList) {
        for (var i = 0; i < Math.max(firstList.size(), secondList.size()); i++) {
            var first = safeGet(firstList, i);
            var second = safeGet(secondList, i);

            // Left side out of items = ordered, right side out of items = unordered
            if (first == null) {
                return Status.ORDERED;
            }
            if (second == null) {
                return Status.UNORDERED;
            }

            if (first instanceof Integer && second instanceof Integer) {
                if ((int)first < (int)second) {
                    return Status.ORDERED;
                }
                if ((int)first > (int)second) {
                    return Status.UNORDERED;
                }
                //-- Equal? keep going
            } else if ((first instanceof Integer && second instanceof List) || (first instanceof List && second instanceof Integer)) {
                //-- Mixed types. Convert the int to a list and compare lists
                var actualFirst = first instanceof Integer ? List.of(first) : (List<Object>) first;
                var actualSecond = second instanceof Integer ? List.of(second) : (List<Object>) second;

                var innerResult = pairIsInOrder(actualFirst, actualSecond);
                if (innerResult != Status.UNKNOWN) {
                    return innerResult;
                }
            } else if (first instanceof List && second instanceof List) {
                //-- Both lists. Check list contents
                var innerResult = pairIsInOrder((List<Object>) first, (List<Object>) second);
                if (innerResult != Status.UNKNOWN) {
                    return innerResult;
                }
            }
        }

        // If two lists are identical, we don't know yet
        return Status.UNKNOWN;
    }

    private static Object safeGet(List<Object> list, int index) {
        try {
            return list.get(index);
        } catch (IndexOutOfBoundsException ignored) {
            return null;
        }
    }
}
