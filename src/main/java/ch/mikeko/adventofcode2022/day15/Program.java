package ch.mikeko.adventofcode2022.day15;

import ch.mikeko.adventofcode2022.common.Coordinate;
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
                        Long.parseLong(splitLine[0]),
                        Long.parseLong(splitLine[1]),
                        Long.parseLong(splitLine[2]),
                        Long.parseLong(splitLine[3]))
                );
            }

            Set<Coordinate> allNonBeaconPositions = new LinkedHashSet<>();
            sensors.forEach(sensor -> allNonBeaconPositions.addAll(sensor.getPlacesWhereThereCannotBeABeaconInRow(2_000_000)));
            removePossibilitiesThatActuallyHaveABeacon(allNonBeaconPositions, sensors);

            System.out.printf("Part one result: %s%n", allNonBeaconPositions.size());

            // Part 2 - check if outer perimeter of one sensor is non-existent in another
            // It's slow, but it works :)
            var done = false;
            for (Sensor sensor : sensors) {
                if (done) {
                    break;
                }

                for (Coordinate coordinate : sensor.getCoordinatesJustOutsideManhattanSquare()) {
                    if (sensors.stream().filter(x -> x != sensor).noneMatch(x -> x.isCoordinateInManhattanSquare(coordinate))) {
                        System.out.printf("Part two result: %s%n", (coordinate.getX() * 4_000_000) + coordinate.getY());
                        done = true;
                    }
                }
            }
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
