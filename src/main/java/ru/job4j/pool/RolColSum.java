package ru.job4j.pool;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1)
    @Warmup(iterations = 5)
    @Measurement(iterations = 5)
    public void init() throws ExecutionException, InterruptedException {
        RolColSum.Sums.asyncSum(RolColSum.Sums.initMatrix(10000));
        //RolColSum.Sums.sum(RolColSum.Sums.initMatrix(10000));
    }

    public static class Sums {

        private int rowSum;

        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        public static Sums[] sum(int[][] matrix) {
            Sums[] arraySums = new Sums[matrix.length];
            int buffSumRow = 0;
            int buffSumCol = 0;
            for (int i = 0; i < arraySums.length; i++) {
                arraySums[i] = new Sums();
                for (int j = 0; j < matrix.length; j++) {
                    buffSumRow += matrix[i][j];
                }
                arraySums[i].setRowSum(buffSumRow);
                buffSumRow = 0;
                for (int k = 0; k < matrix.length; k++) {
                    buffSumCol += matrix[k][i];
                }
                arraySums[i].setColSum(buffSumCol);
                buffSumCol = 0;
            }
            return arraySums;
        }

        public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
            Sums[] result = new Sums[matrix.length];
            for (int i = 0; i < matrix.length; i++) {
                result[i] = getTask(matrix, i).get();
            }
            return result;
        }

        public static CompletableFuture<Sums> getTask(int[][] data, int row) {
            return CompletableFuture.supplyAsync(() -> {
                Sums result = new Sums();
                int buffSumCol = 0;
                int buffSumRow = 0;
                for (int i = 0; i < data.length; i++) {
                    buffSumRow += data[row][i];
                    buffSumCol += data[i][row];
                }
                result.setRowSum(buffSumRow);
                result.setColSum(buffSumCol);
                return result;
            });
        }

        public static int[][] initMatrix(int size) {
            int[][] result = new int[size][size];
            int count = 1;
            for (int i = 0; i < result.length; i++) {
                for (int j = 0; j < result[i].length; j++) {
                    result[i][j] = count++;
                }
            }
            return result;
        }

        public static void main(String[] args) throws ExecutionException, InterruptedException {
            int[][] matrix = new int[2][2];
            int count = 1;
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    matrix[i][j] = count++;
                }
            }
            Sums[] resultSeq = sum(matrix);
            Sums[] resultAsync = asyncSum(matrix);
            System.out.println("Seq execute:");
            for (Sums elem : resultSeq) {
                System.out.println("****************");
                System.out.println(elem.getRowSum());
                System.out.println(elem.getColSum());
            }
            System.out.println("Async execute:");
            for (Sums elem : resultAsync) {
                System.out.println("****************");
                System.out.println(elem.getRowSum());
                System.out.println(elem.getColSum());
            }
        }
    }
}
