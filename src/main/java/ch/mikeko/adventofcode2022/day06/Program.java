package ch.mikeko.adventofcode2022.day06;

import ch.mikeko.adventofcode2022.common.InputParser;
import ch.mikeko.adventofcode2022.common.InputType;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Program {
    public static void main(String[] args) {
        try (Stream<String> lines = InputParser.getInputByLine(6, InputType.PUZZLE_INPUT)) {
            var allLines = lines.collect(Collectors.toList());
            var datastreamBuffer = allLines.get(0);

            var foundStartOfPacket = false;
            var foundStartOfMessage = false;

            for (var i = 0; i < datastreamBuffer.length() && (!foundStartOfMessage || !foundStartOfPacket); i++) {
                var chars = new ArrayList<>();
                for (var j = 0; j < 14; j++) {
                    chars.add(datastreamBuffer.charAt(i + j));
                }
                var startOfPacketChars = chars.subList(0, 4);

                if (!foundStartOfPacket && new HashSet<>(startOfPacketChars).size() == startOfPacketChars.size()) {
                    System.out.println("Part one answer: " + (i + 4));
                    foundStartOfPacket = true;
                }
                if (!foundStartOfMessage && new HashSet<>(chars).size() == chars.size()) {
                    System.out.println("Part two answer: " + (i + 14));
                    foundStartOfMessage = true;
                }
            }
        }
    }
}
