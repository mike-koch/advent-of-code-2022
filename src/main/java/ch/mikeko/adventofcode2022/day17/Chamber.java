package ch.mikeko.adventofcode2022.day17;

import ch.mikeko.adventofcode2022.common.Coordinate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Chamber {
    public static final int WIDTH = 7;
    private final List<Coordinate> blockedSpaces = new ArrayList<>();

    public List<Coordinate> getBlockedSpaces() {
        return blockedSpaces;
    }

    public boolean isSpaceAvailable(Coordinate coordinate) {
        return !blockedSpaces.contains(coordinate);
    }

    public long getTopOfChamber() {
        if (blockedSpaces.isEmpty()) {
            return 0;
        }

        return blockedSpaces.stream()
                .mapToLong(Coordinate::getY)
                .max()
                .orElse(-1L) + 1;
    }

    @SuppressWarnings("unused")
    public String getChamberState() {
        StringBuilder output = new StringBuilder();
        var first = true;
        for (var y = getTopOfChamber(); y >= 0; y--) {
            if (!first) {
                output.append("|\n");
            }
            first = false;
            output.append("|");
            for (var x = 0; x < WIDTH; x++) {
                var finalY = y;
                var finalX = x;
                output.append(blockedSpaces.stream()
                        .anyMatch(coordinate -> coordinate.getX() == finalX && coordinate.getY() == finalY) ?
                        '#' :
                        '.');
            }
        }
        output.append("|\n+-------+");

        return output.toString();
    }
}
