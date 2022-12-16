package ch.mikeko.adventofcode2022.day08;

import ch.mikeko.adventofcode2022.common.InputParser;
import ch.mikeko.adventofcode2022.common.InputType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Program {
    public static void main(String[] args) {
        try (Stream<String> lines = InputParser.getInputByLine(8, InputType.PUZZLE_INPUT)) {
            var allLines = lines.collect(Collectors.toList());
            var treeMapping = initializeTreeMapping(allLines);

            System.out.printf("Part one answer: %s%n", getVisibleTrees(treeMapping));
            System.out.printf("Part two answer: %s%n", getBestScenicScore(treeMapping));
        }
    }

    //region Part One
    private static int[][] initializeTreeMapping(List<String> allLines) {
        var treeMapping = new int[allLines.size()][allLines.size()];
        for (var i = 0; i < allLines.size(); i++) {
            var line = allLines.get(i);
            for (var j = 0; j < line.toCharArray().length; j++) {
                treeMapping[i][j] = Integer.parseInt(String.valueOf(line.charAt(j)));
            }
        }

        return treeMapping;
    }

    private static int getVisibleTrees(int[][] treeMapping) {
        var runningTotal = 0;
        for (var row = 0; row < treeMapping.length; row++) {
            for (var column = 0; column < treeMapping[row].length; column++) {
                // If the tree is on the edge, it's always visible
                if (row == 0 || row == treeMapping.length - 1 || column == 0 || column == treeMapping[row].length - 1) {
                    runningTotal++;
                    continue;
                }

                var tree = treeMapping[row][column];
                var tallestInRow = getTallestTreesInRow(treeMapping, row, column);
                var tallestInColumn = getTallestTreesInColumn(treeMapping, row, column);

                if (tree > tallestInRow.get(0) || tree > tallestInRow.get(1) ||
                    tree > tallestInColumn.get(0) || tree > tallestInColumn.get(1)) {
                    runningTotal++;
                }
            }
        }

        return runningTotal;
    }

    private static List<Integer> getTallestTreesInRow(int[][] treeMapping, int row, int currentTreeIndex) {
        var tallestLeftTree = Integer.MIN_VALUE;
        var tallestRightTree = Integer.MIN_VALUE;
        for (var column = 0; column < treeMapping[row].length; column++) {
            if (column == currentTreeIndex) {
                // Don't count the tree we're looking at
                continue;
            }

            if (column < currentTreeIndex) {
                tallestLeftTree = Math.max(tallestLeftTree, treeMapping[row][column]);
            } else {
                tallestRightTree = Math.max(tallestRightTree, treeMapping[row][column]);
            }
        }

        return Arrays.asList(tallestLeftTree, tallestRightTree);
    }

    private static List<Integer> getTallestTreesInColumn(int[][] treeMapping, int currentTreeIndex, int column) {
        var tallestTopTree = Integer.MIN_VALUE;
        var tallestBottomTree = Integer.MIN_VALUE;
        for (var row = 0; row < treeMapping.length; row++) {
            if (row == currentTreeIndex) {
                // Don't count the tree we're looking at
                continue;
            }

            if (row < currentTreeIndex) {
                tallestTopTree = Math.max(tallestTopTree, treeMapping[row][column]);
            } else {
                tallestBottomTree = Math.max(tallestBottomTree, treeMapping[row][column]);
            }
        }

        return Arrays.asList(tallestTopTree, tallestBottomTree);
    }
    //endregion

    //region Part Two
    private static int getBestScenicScore(int[][] treeMapping) {
        var bestScenicScore = 0;
        for (var row = 0; row < treeMapping.length; row++) {
            for (var column = 0; column < treeMapping.length; column++) {
                bestScenicScore = Math.max(bestScenicScore, getScenicScoreForTree(treeMapping, row, column));
            }
        }
        return bestScenicScore;
    }

    private static int getScenicScoreForTree(int[][] treeMapping, int currentRow, int currentColumn) {
        var topScore = getScenicScoreForDirection(treeMapping, currentRow, currentColumn, Direction.UP);
        var bottomScore = getScenicScoreForDirection(treeMapping, currentRow, currentColumn, Direction.DOWN);
        var leftScore = getScenicScoreForDirection(treeMapping, currentRow, currentColumn, Direction.LEFT);
        var rightScore = getScenicScoreForDirection(treeMapping, currentRow, currentColumn, Direction.RIGHT);

        return topScore * bottomScore * leftScore * rightScore;
    }

    private static int getScenicScoreForDirection(int[][] treeMapping, int currentRow, int currentColumn, Direction direction) {

        if ((direction == Direction.UP && currentRow == 0) ||
                (direction == Direction.DOWN && currentRow == treeMapping.length - 1) ||
                (direction == Direction.LEFT && currentColumn == 0) ||
                (direction == Direction.RIGHT && currentColumn == treeMapping[currentRow].length - 1)) {
            return 0;
        }

        return direction.accept(treeMapping, currentRow, currentColumn);
    }
    //endregion
}
