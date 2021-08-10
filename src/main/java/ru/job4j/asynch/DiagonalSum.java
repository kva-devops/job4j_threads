package ru.job4j.asynch;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class DiagonalSum {
    public static int[] async(int[][] matrix) throws ExecutionException, InterruptedException {
        int n = matrix.length;
        int[] sums = new int[2 * n];
        Map<Integer, CompletableFuture<Integer>> futures = new HashMap<>();
        futures.put(0, getTask(matrix, 0, n - 1, n - 1));
        for (int k = 1; k <= n; k++) {
            futures.put(k, getTask(matrix, 0, k - 1, k - 1));
            if (k < n) {
                futures.put(2 * n - k, getTask(matrix, n - k, n - 1, n - 1));
            }
        }
        for (Integer key : futures.keySet()) {
            sums[key] = futures.get(key).get();
        }
        return sums;
    }

    public static CompletableFuture<Integer> getTask(
            int[][] data, int startRow, int endRow, int startCol) {
        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            int col = startCol;
            for (int i = startRow; i <= endRow; i++) {
                sum += data[i][col];
                col--;
            }
            return sum;
        });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[][] matrix = new int[2][3];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                matrix[i][j] = i * j + 1;
            }
        }
        int[] result = DiagonalSum.async(matrix);
        for (int j : result) {
            System.out.println(j);
        }
    }
}
