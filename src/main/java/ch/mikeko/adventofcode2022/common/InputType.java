package ch.mikeko.adventofcode2022.common;

public enum InputType {
    EXAMPLE("example.txt"),
    PUZZLE_INPUT("input.txt");

    private final String fileName;
    InputType(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
