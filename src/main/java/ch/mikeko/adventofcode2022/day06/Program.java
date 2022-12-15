package ch.mikeko.adventofcode2022.day06;

import ch.mikeko.adventofcode2022.common.InputParser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Program {
    public static void main(String[] args) {
        try (Stream<String> lines = InputParser.getInputByLine(6)) {
            var allLines = lines.collect(Collectors.toList());
            var datastreamBuffer = allLines.get(0);

            for (var i = 0; i < datastreamBuffer.length(); i++) {
                var chars = Arrays.asList(datastreamBuffer.charAt(i),
                        datastreamBuffer.charAt(i + 1),
                        datastreamBuffer.charAt(i + 2),
                        datastreamBuffer.charAt(i + 3));
                if (new HashSet<>(chars).size() == chars.size()) {
                    System.out.println("Part one answer: " + (i + 4));
                    break;
                }
            }
        }
    }
}
