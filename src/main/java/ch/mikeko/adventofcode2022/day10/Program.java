package ch.mikeko.adventofcode2022.day10;

import ch.mikeko.adventofcode2022.common.InputParser;
import ch.mikeko.adventofcode2022.common.InputType;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Program {
    public static void main(String[] args) {
        try (Stream<String> lines = InputParser.getInputByLine(10, InputType.PUZZLE_INPUT)) {
            var allLines = lines.collect(Collectors.toList());
            var cpu = new Cpu();

            for (var line : allLines) {
                var splitLine = line.split(" ");
                if ("noop".equals(splitLine[0])) {
                    cpu.noop();
                } else if ("addx".equals(splitLine[0])) {
                    cpu.addx(Integer.parseInt(splitLine[1]));
                } else {
                    throw new IllegalArgumentException("Found invalid instruction: " + splitLine[0]);
                }
            }

            System.out.printf("Part one result: %s%n", cpu.getSignalStrengths().values().stream().mapToInt(x -> x).sum());
        }
    }
}
