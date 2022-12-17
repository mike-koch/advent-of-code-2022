package ch.mikeko.adventofcode2022.day09;

import ch.mikeko.adventofcode2022.common.InputParser;
import ch.mikeko.adventofcode2022.common.InputType;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Program {
    public static void main(String[] args) {
        try (Stream<String> lines = InputParser.getInputByLine(9, InputType.PUZZLE_INPUT)) {
            var head = new Node(0, 0);
            var tails = Arrays.asList(
                    new Node(0, 0),
                    new Node(0, 0),
                    new Node(0, 0),
                    new Node(0, 0),
                    new Node(0, 0),
                    new Node(0, 0),
                    new Node(0, 0),
                    new Node(0, 0),
                    new Node(0, 0)
            );
            var allLines = lines.collect(Collectors.toList());

            for (var line : allLines) {
                var command = line.split(" ");
                var direction = Direction.fromCode(command[0]);
                var amount = Integer.parseInt(command[1]);

                for (var i = 0; i < amount; i++) {
                    direction.moveNode(head);

                    for (var j = 0; j < tails.size(); j++) {
                        if (j == 0) {
                            updateTailPosition(head, tails.get(j));
                        } else {
                            updateTailPosition(tails.get(j - 1), tails.get(j));
                        }
                    }
                }
            }

            System.out.printf("Part one answer: %s%n", tails.get(0).getVisitedNodeCount());
            System.out.printf("Part two answer: %s%n", tails.get(8).getVisitedNodeCount());
        }
    }

    private static void updateTailPosition(Node head, Node tail) {
        // 1. If the tail is within 1 row/column, no need to move
        if (Math.abs(head.getX() - tail.getX()) < 2 && Math.abs(head.getY() - tail.getY()) < 2) {
            return;
        }

        // 2. Tail is on the same row, but 2+ columns away
        if (Math.abs(head.getX() - tail.getX()) > 1 && head.getY() == tail.getY()) {
            if (head.getX() > tail.getX()) {
                tail.moveTo(tail.getX() + 1, tail.getY());
            } else {
                tail.moveTo(tail.getX() - 1, tail.getY());
            }

            return;
        }

        // 3. Tail is on the same column, but 2+ rows away
        if (head.getX() == tail.getX() && Math.abs(head.getY() - tail.getY()) > 1) {
            if (head.getY() > tail.getY()) {
                tail.moveTo(tail.getX(), tail.getY() + 1);
            } else {
                tail.moveTo(tail.getX(), tail.getY() - 1);
            }

            return;
        }

        // 4. Diagonal. Both are 2 away
        var xDelta = head.getX() - tail.getX();
        var yDelta = head.getY() - tail.getY();

        if (Math.abs(xDelta) == 2 && Math.abs(yDelta) == 2) {
            // head X > tail X -> right 1
            // head X < tail X -> left 1
            // head Y > tail Y -> up 1
            // head Y < tail Y -> down 1
            var xMovement = head.getX() > tail.getX() ? 1 : -1;
            var yMovement = head.getY() > tail.getY() ? 1 : -1;

            tail.moveTo(tail.getX() + xMovement, tail.getY() + yMovement);
            return;
        }

        // 5. Diagonal. One direction will be 1 away, the other 2
        if (Math.abs(yDelta) == 2) {
            // Tail is 2 steps away from the head in the Y direction. Align X, adjust Y based on +/- Y
            var yMovement = yDelta < 0 ? 1 : -1;
            tail.moveTo(head.getX(), head.getY() + yMovement);
        } else if (Math.abs(xDelta) == 2) {
            // Tail is 2 steps away from the head in the X direction. Align Y, adjust X based on +/- X
            var xMovement = xDelta < 0 ? 1 : -1;
            tail.moveTo(head.getX() + xMovement, head.getY());
        }
    }
}
