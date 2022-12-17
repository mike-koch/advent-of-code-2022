package ch.mikeko.adventofcode2022.day09;

import java.util.function.Consumer;

public enum Direction {
    UP(node -> node.moveTo(node.getX(), node.getY() + 1)),
    DOWN(node -> node.moveTo(node.getX(), node.getY() - 1)),
    LEFT(node -> node.moveTo(node.getX() - 1, node.getY())),
    RIGHT(node -> node.moveTo(node.getX() + 1, node.getY()));

    private final Consumer<Node> handler;
    Direction(Consumer<Node> handler) {
        this.handler = handler;
    }

    public void moveNode(Node node) {
        handler.accept(node);
    }

    public static Direction fromCode(String code) {
        switch (code) {
            case "U":
                return UP;
            case "D":
                return DOWN;
            case "L":
                return LEFT;
            case "R":
                return RIGHT;
            default:
                throw new RuntimeException("Found unknown direction code: " + code);
        }
    }
}
