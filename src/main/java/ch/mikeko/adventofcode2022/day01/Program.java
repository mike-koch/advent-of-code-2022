package ch.mikeko.adventofcode2022.day01;

import ch.mikeko.adventofcode2022.common.InputParser;
import ch.mikeko.adventofcode2022.common.InputType;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Program {
    public static void main(String[] args) {
        var list = new ArrayList<Integer>();

        try (Stream<String> lines = InputParser.getInputByLine(1, InputType.PUZZLE_INPUT)) {
            var collectedLines = lines.collect(Collectors.toList());

            var runningTotal = 0;
            for (var line : collectedLines) {
                if ("".equals(line)) {
                    list.add(runningTotal);
                    runningTotal = 0;
                    continue;
                }

                runningTotal += Integer.parseInt(line);
            }
        }

        list.sort((o1, o2) -> o2 - o1);

        System.out.printf("Part one solution: %s%n", list.get(0));

        var topThreeTotal = list.get(0) + list.get(1) + list.get(2);
        System.out.printf("Part two solution: %s%n", topThreeTotal);
    }
}
