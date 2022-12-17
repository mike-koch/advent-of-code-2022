package ch.mikeko.adventofcode2022.day14;

import ch.mikeko.adventofcode2022.common.InputParser;
import ch.mikeko.adventofcode2022.common.InputType;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PartOne {
    public static void main(String[] args) {
        try (Stream<String> lines = InputParser.getInputByLine(14, InputType.PUZZLE_INPUT)) {
            var allLines = lines.collect(Collectors.toList());

            var points = buildPoints(allLines);
            var bottomLeftmostPoint = getBottomLeftmostPoint(points);
            var bottomRightmostPoint = getBottomRightmostPoint(points);

            var keepGoing = true;
            var count = 0;
            do {
                keepGoing = addSand(points, bottomLeftmostPoint, bottomRightmostPoint);
                if (keepGoing) {
                    count++;
                }
            } while (keepGoing);

            System.out.printf("Part one result: %s%n", count);
        }
    }

    private static Set<Point> buildPoints(List<String> allLines) {
        Set<Point> points = new LinkedHashSet<>();
        for (var line : allLines) {
            var directions = line.split(" -> ");

            var lastX = Integer.MIN_VALUE;
            var lastY = Integer.MIN_VALUE;
            for (var direction : directions) {
                var splitDirection = direction.split(",");
                var x = Integer.parseInt(splitDirection[0]);
                var y = Integer.parseInt(splitDirection[1]);

                if (lastX == Integer.MIN_VALUE && lastY == Integer.MIN_VALUE) {
                    // First point, so just create one
                    points.add(new Point(x, y));
                } else {
                    for (var i = Math.min(lastX, x); i <= Math.max(lastX, x); i++) {
                        // Plot horizontal points
                        points.add(new Point(i, lastY));
                    }
                    for (var i = Math.min(lastY, y); i <= Math.max(lastY, y); i++) {
                        // Plot vertical points
                        points.add(new Point(lastX, i));
                    }
                }

                lastX = x;
                lastY = y;
            }
        }

        return points;
    }

    private static Point getBottomLeftmostPoint(Set<Point> points) {
        return points.stream().min((o1, o2) -> {
            if (o1.getY() > o2.getY()) {
                return -1;
            }
            if (o1.getY() < o2.getY()) {
                return 1;
            }
            if (o1.getX() < o2.getX()) {
                return -1;
            }

            return o1.getX() > o2.getX() ? 1 : 0;
        }).orElseThrow(() -> new IllegalStateException("Should not be able to reach here"));
    }

    private static Point getBottomRightmostPoint(Set<Point> points) {
        return points.stream().min((o1, o2) -> {
            if (o1.getY() > o2.getY()) {
                return -1;
            }
            if (o1.getY() < o2.getY()) {
                return 1;
            }
            if (o1.getX() > o2.getX()) {
                return -1;
            }

            return o1.getX() < o2.getX() ? 1 : 0;
        }).orElseThrow(() -> new IllegalStateException("Should not be able to reach here"));
    }

    private static boolean addSand(Set<Point> points, Point bottomLeftPoint, Point bottomRightPoint) {
        var sand = new Point(500, 0);
        sand.setSymbol("o");
        var canMove = true;
        while (canMove) {
            // 1. Can the point go down a spot? If so, do it.
            if (!points.contains(new Point(sand.getX(), sand.getY() + 1))) {
                // 1a. Have we entered the abyss? If so, stop everything.
                if (bottomLeftPoint.getY() < sand.getY() || bottomRightPoint.getY() < sand.getY()) {
                    return false;
                }

                sand.setY(sand.getY() + 1);
                continue;
            }
            // 2. Can the point go down and to the left a spot? If so, do it.
            if (!points.contains(new Point(sand.getX() - 1, sand.getY() + 1))) {
                sand.setX(sand.getX() - 1);
                sand.setY(sand.getY() + 1);
                continue;
            }

            // 3. Can the point go down and to the right a spot? If so, do it.
            if (!points.contains(new Point(sand.getX() + 1, sand.getY() + 1))) {
                sand.setX(sand.getX() + 1);
                sand.setY(sand.getY() + 1);
                continue;
            }

            // 4. Otherwise we're at a stopping point.
            canMove = false;
        }
        points.add(sand);
        return true;
    }
}
