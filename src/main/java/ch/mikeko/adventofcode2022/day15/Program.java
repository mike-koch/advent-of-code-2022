package ch.mikeko.adventofcode2022.day15;

import ch.mikeko.adventofcode2022.common.InputParser;
import ch.mikeko.adventofcode2022.common.InputType;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Program {
    public static void main(String[] args) {
        try (Stream<String> lines = InputParser.getInputByLine(15, InputType.PUZZLE_INPUT)) {
            var allLines = lines.collect(Collectors.toList());
            List<Sensor> sensors = new ArrayList<>();

            for (var line : allLines) {
                // Ends up being 2,18,-2,15
                line = line.replaceAll("Sensor at x=", "")
                        .replaceAll(" y=", "")
                        .replaceAll(": closest beacon is at x=", ",");
                var splitLine = line.split(",");
                sensors.add(new Sensor(
                        Integer.parseInt(splitLine[0]),
                        Integer.parseInt(splitLine[1]),
                        Integer.parseInt(splitLine[2]),
                        Integer.parseInt(splitLine[3]))
                );
            }

            Set<Coordinate> allNonBeaconPositions = new LinkedHashSet<>();
            sensors.forEach(sensor -> allNonBeaconPositions.addAll(sensor.getPlacesWhereThereCannotBeABeaconInRow(2_000_000)));
            removePossibilitiesThatActuallyHaveABeacon(allNonBeaconPositions, sensors);

            System.out.printf("Part one result: %s%n", allNonBeaconPositions.size());
        }
    }

    private static void removePossibilitiesThatActuallyHaveABeacon(Set<Coordinate> allNonBeaconPositions, List<Sensor> sensors) {
        List<Coordinate> coordinatesToRemove = new ArrayList<>();
        for (var coordinate : allNonBeaconPositions) {
            if (sensors.stream().anyMatch(sensor -> sensor.getClosestBeacon().equals(coordinate))) {
                coordinatesToRemove.add(coordinate);
            }
        }

        coordinatesToRemove.forEach(allNonBeaconPositions::remove);
    }
}
