package ch.mikeko.adventofcode2022.day16;

// Adapted from https://www.programiz.com/dsa/floyd-warshall-algorithm
public class FloydWarshall {
    public static final int INF = 9999;
    private final int[][] matrix;

    public FloydWarshall(int[][] matrix) {
        this.matrix = matrix;
    }

    public int[][] doFloydWarshall() {
        int vertexCount = this.matrix.length;
        int[][] finishedMatrix = new int[vertexCount][vertexCount];
        int i, j, k;

        for (i = 0; i < vertexCount; i++)
            for (j = 0; j < vertexCount; j++)
                finishedMatrix[i][j] = matrix[i][j];

        // Adding vertices individually
        for (k = 0; k < vertexCount; k++) {
            for (i = 0; i < vertexCount; i++) {
                for (j = 0; j < vertexCount; j++) {
                    if (finishedMatrix[i][k] + finishedMatrix[k][j] < finishedMatrix[i][j])
                        finishedMatrix[i][j] = finishedMatrix[i][k] + finishedMatrix[k][j];
                }
            }
        }

        return finishedMatrix;
    }
}
