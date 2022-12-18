package ch.mikeko.adventofcode2022.day15;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Sensor {
    private final Coordinate sensorLocation;
    private final Coordinate closestBeacon;

    public Sensor(int x, int y, int closestBeaconX, int closestBeaconY) {
        sensorLocation = new Coordinate(x, y);
        closestBeacon = new Coordinate(closestBeaconX, closestBeaconY);
    }

    public Coordinate getSensorLocation() {
        return sensorLocation;
    }

    public Coordinate getClosestBeacon() {
        return closestBeacon;
    }

    public int getManhattanDistance() {
        return Math.abs(sensorLocation.getX() - closestBeacon.getX()) +
                Math.abs(sensorLocation.getY() - closestBeacon.getY());
    }

    public Set<Coordinate> getPlacesWhereThereCannotBeABeaconInRow(int row) {
        Set<Coordinate> coordinates = new LinkedHashSet<>();
        var possibleColumnDeviations = getManhattanDistance() - Math.abs(sensorLocation.getY() - row);
        for (var i = 0; i <= possibleColumnDeviations; i++) {
            coordinates.add(new Coordinate(sensorLocation.getX() - i, row));
            coordinates.add(new Coordinate(sensorLocation.getX() + i, row));
        }

        return coordinates;
    }
}
