package ch.mikeko.adventofcode2022.day04;

import ch.mikeko.adventofcode2022.common.InputParser;

import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Program {
    public static void main(String[] args) {
        var partOneResult = 0;
        var partTwoResult = 0;
        /*
        Truth table determining self-containment or any overlap.
        Values are relative to the start/end of P2, respectively.

        P1 Start | P1 End | Result (Part 1)
        ---------|--------|----------------
        LT       | LT     | false
        LT       | GT     | true
        LT       | EQ     | true
        GT       | LT     | true
        GT       | GT     | false
        GT       | EQ     | true
        EQ       | LT     | true
        EQ       | GT     | true
        EQ       | EQ     | true
         */

        try (Stream<String> lines = InputParser.getInputByLine(4)) {
            var allLines = lines.collect(Collectors.toList());

            for (var line : allLines) {
                var pairs = line.split(",");
                var pairOne = pairs[0].split("-");
                var pairTwo = pairs[1].split("-");

                var pairOneBegin = Integer.parseInt(pairOne[0]);
                var pairOneEnd = Integer.parseInt(pairOne[1]);
                var pairTwoBegin = Integer.parseInt(pairTwo[0]);
                var pairTwoEnd = Integer.parseInt(pairTwo[1]);
                if (!(pairOneBegin < pairTwoBegin && pairOneEnd < pairTwoEnd) &&
                    !(pairOneBegin > pairTwoBegin && pairOneEnd > pairTwoEnd)) {
                    partOneResult++;
                }

                if (doListsOverlap(pairOneBegin, pairOneEnd, pairTwoBegin, pairTwoEnd)) {
                    partTwoResult++;
                }
            }
        }

        System.out.printf("Result for part one: %s%n", partOneResult);
        System.out.printf("Result for part two: %s%n", partTwoResult);
    }

    private static boolean doListsOverlap(int pairOneBegin, int pairOneEnd, int pairTwoBegin, int pairTwoEnd) {
        // 1. Explode into list of possible values
        var pairOneRange = IntStream.range(pairOneBegin, pairOneEnd + 1)
                .boxed()
                .collect(Collectors.toList());
        var pairTwoRange = IntStream.range(pairTwoBegin, pairTwoEnd + 1)
                .boxed()
                .collect(Collectors.toList());

        // 2. Check if the lists intersect
        return pairOneRange.stream().anyMatch(pairTwoRange::contains);
    }
}
