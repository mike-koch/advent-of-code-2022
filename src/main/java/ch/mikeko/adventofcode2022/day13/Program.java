package ch.mikeko.adventofcode2022.day13;

import ch.mikeko.adventofcode2022.common.InputParser;
import ch.mikeko.adventofcode2022.common.InputType;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Program {
    public static void main(String[] args) {
        try (Stream<String> lines = InputParser.getInputByLine(13, InputType.PUZZLE_INPUT)) {
            var allLines = lines.collect(Collectors.toList());
            var allPackets = new ArrayList<Packet>();

            List<Integer> indicesInOrder = new ArrayList<>();
            for (var i = 0; i < allLines.size(); i += 3) {
                var first = parseLine(allLines.get(i));
                first.setTextRepresentation(allLines.get(i));
                var second = parseLine(allLines.get(i + 1));
                second.setTextRepresentation(allLines.get(i + 1));
                allPackets.addAll(Arrays.asList(first, second));

                var sortedStatus = PacketComparer.pairIsInOrder(first, second);
                if (sortedStatus == Status.UNKNOWN) {
                    throw new IllegalStateException(String.format("Unknown sort result: %s vs %s", allLines.get(i), allLines.get(i + 1)));
                }
                if (sortedStatus == Status.ORDERED) {
                    indicesInOrder.add(i / 3 + 1);
                }
            }

            System.out.printf("Part one result: %s%n", indicesInOrder.stream().mapToInt(x -> x).sum());

            // Part 2
            Packet dividerPacketOne = parseLine("[[2]]");
            dividerPacketOne.setTextRepresentation("[[2]]");
            Packet dividerPacketTwo = parseLine("[[6]]");
            dividerPacketTwo.setTextRepresentation("[[6]]");
            allPackets.addAll(Arrays.asList(dividerPacketOne, dividerPacketTwo));

            //noinspection ComparatorMethodParameterNotUsed -- No two packets are equal
            allPackets.sort((o1, o2) -> {
                if (PacketComparer.pairIsInOrder(o1, o2) == Status.ORDERED) {
                    return -1;
                }

                return 1;
            });
            System.out.printf("Part two result: %s%n", (allPackets.indexOf(dividerPacketOne) + 1) * (allPackets.indexOf(dividerPacketTwo) + 1));
        }
    }

    private static Packet parseLine(String line) {
        var sb = new StringBuilder(line);
        line = sb.deleteCharAt(0).deleteCharAt(sb.length() - 1).toString();

        var startArrayIndices = new Stack<Integer>();
        Packet entities = new Packet();
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
}
