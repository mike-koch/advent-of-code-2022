package ch.mikeko.adventofcode2022.day08;

public enum Direction {
    UP((treeMapping, currentRow, currentColumn) -> {
        var score = 0;
        for (var row = currentRow - 1; row >= 0; row--) {
            score++;

            if (treeMapping[currentRow][currentColumn] <= treeMapping[row][currentColumn]) {
                break;
            }
        }

        return score;
    }),
    DOWN((treeMapping, currentRow, currentColumn) -> {
        var score = 0;
        for (var row = currentRow + 1; row < treeMapping.length; row++) {
            score++;

            if (treeMapping[currentRow][currentColumn] <= treeMapping[row][currentColumn]) {
                break;
            }
        }

        return score;
    }),
    LEFT((treeMapping, currentRow, currentColumn) -> {
        var score = 0;
        for (var column = currentColumn - 1; column >= 0; column--) {
            score++;

            if (treeMapping[currentRow][currentColumn] <= treeMapping[currentRow][column]) {
                break;
            }
        }

        return score;
    }),
    RIGHT((treeMapping, currentRow, currentColumn) -> {
        var score = 0;
        for (var column = currentColumn + 1; column < treeMapping[currentRow].length; column++) {
            score++;

            if (treeMapping[currentRow][currentColumn] <= treeMapping[currentRow][column]) {
                break;
            }
        }

        return score;
    });

    private final SceneCalculator sceneCalculator;

    Direction(SceneCalculator sceneCalculator) {
        this.sceneCalculator = sceneCalculator;
    }

    public int accept(int[][] treeMapping, int currentRow, int currentColumn) {
        return this.sceneCalculator.calculateScenicScore(treeMapping, currentRow, currentColumn);
    }

    @FunctionalInterface
    private interface SceneCalculator {
        int calculateScenicScore(int[][] treeMapping, int currentRow, int currentColumn);
    }
}
