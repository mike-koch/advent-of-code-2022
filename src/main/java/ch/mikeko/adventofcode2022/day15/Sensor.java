package ch.mikeko.adventofcode2022.day15;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Sensor {
    private final Coordinate sensorLocation;
    private final Coordinate closestBeacon;

    public Sensor(long x, long y, long closestBeaconX, long closestBeaconY) {
        sensorLocation = new Coordinate(x, y);
        closestBeacon = new Coordinate(closestBeaconX, closestBeaconY);
    }

    public Coordinate getSensorLocation() {
        return sensorLocation;
    }

    public Coordinate getClosestBeacon() {
        return closestBeacon;
    }

    public long getManhattanDistance() {
        return Math.abs(sensorLocation.getX() - closestBeacon.getX()) +
                Math.abs(sensorLocation.getY() - closestBeacon.getY());
    }

    public Set<Coordinate> getPlacesWhereThereCannotBeABeaconInRow(int row) {
        Set<Coordinate> coordinates = new HashSet<>();
        var possibleColumnDeviations = getManhattanDistance() - Math.abs(sensorLocation.getY() - row);
        for (var i = 0; i <= possibleColumnDeviations; i++) {
            coordinates.add(new Coordinate(sensorLocation.getX() - i, row));
            coordinates.add(new Coordinate(sensorLocation.getX() + i, row));
        }

        return coordinates;
    }

    public Set<Coordinate> getCoordinatesJustOutsideManhattanSquare() {
        var distance = getManhattanDistance() + 1;

        Set<Coordinate> coordinates = new HashSet<>();
        for (var x = 0; x <= distance; x++) {
            coordinates.add(new Coordinate(sensorLocation.getX() - x, sensorLocation.getY() - distance - x));
            coordinates.add(new Coordinate(sensorLocation.getX() - x, sensorLocation.getY() + distance - x));
            coordinates.add(new Coordinate(sensorLocation.getX() + x, sensorLocation.getY() - distance - x));
            coordinates.add(new Coordinate(sensorLocation.getX() + x, sensorLocation.getY() + distance - x));
        }

        return coordinates.stream()
                .filter(x -> x.getX() >=0 && x.getX() <= 4_000_000 && x.getY() >= 0 && x.getY() <= 4_000_000)
                .collect(Collectors.toSet());
    }

    private void addCoordinateIfNotInManhattanSquare(Set<Coordinate> coordinates, long x, long y) {
        var coord = new Coordinate(x, y);
        if (!isCoordinateInManhattanSquare(coord)) {
            coordinates.add(coord);
        }
    }

    public boolean isCoordinateInManhattanSquare(Coordinate coordinate) {
        var manhattanDistanceToCoordinate = Math.abs(sensorLocation.getX() - coordinate.getX()) +
                Math.abs(sensorLocation.getY() - coordinate.getY());

        return manhattanDistanceToCoordinate <= getManhattanDistance();
    }
}
