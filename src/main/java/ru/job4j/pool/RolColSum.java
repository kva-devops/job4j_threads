package ru.job4j.pool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
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
            Sums[] arraySums = new Sums[matrix.length * matrix[0].length];
            int buffSumRow = 0;
            int buffSumCol = 0;
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    buffSumRow += matrix[i][j];
                    if (arraySums[matrix[i].length * i + j] != null) {
                        arraySums[matrix[i].length * i + j].setColSum(arraySums[matrix[i].length * i + j].getColSum() + matrix[i][j]);
                    } else {
                        arraySums[matrix[i].length * i + j] = new Sums();
                        arraySums[matrix[i].length * i + j].setColSum(buffSumCol + matrix[i][j]);
                        if (i > 0 && arraySums[j] != null) {
                            arraySums[j].setColSum(arraySums[j].getColSum() + arraySums[matrix[i].length * i + j].getColSum());
                            arraySums[matrix[i].length * i + j].setColSum(arraySums[j].getColSum());
                        }
                    }
                }
                if (arraySums[i] != null) {
                    for (int k = 0; k < matrix[i].length; k++) {
                        arraySums[matrix[i].length * i + k].setRowSum(buffSumRow);
                    }
                    buffSumRow = 0;
                }
            }
            return arraySums;
        }

        public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
            Sums[] result = new Sums[matrix.length * matrix[0].length];
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    result[matrix[i].length * i + j] = getTask(matrix, i, i + 1, j).get();
                }
            }
            return result;
        }

        public static CompletableFuture<Sums> getTask(int[][] data, int startRow, int endRow, int startCol) {
            return CompletableFuture.supplyAsync(() -> {
                Sums result = new Sums();
                int buffSumCol = 0;
                int buffSumRow = 0;
                for (int i = startRow; i < endRow; i++) {
                    for (int j = 0; j < data[i].length; j++) {
                        buffSumRow += data[i][j];
                    }
                    result.setRowSum(buffSumRow);
                }
                for (int i = 0; i < data.length; i++) {
                    for (int j = startCol; j < startCol + 1; j++) {
                        buffSumCol += data[i][j];
                    }
                }
                result.setColSum(buffSumCol);
                return result;
            });
        }

        public static void main(String[] args) throws ExecutionException, InterruptedException {
            int[][] matrix = new int[2][2];
            int count = 1;
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    matrix[i][j] = count++;
                }
            }
            long before = System.currentTimeMillis();
            Sums[] resultSeq = sum(matrix);
            long after = System.currentTimeMillis();
            long result = after - before;
            System.out.println("Time seq: " + result);

            before = System.currentTimeMillis();
            Sums[] resultAsync = asyncSum(matrix);
            after = System.currentTimeMillis();
            result = after - before;
            System.out.println("Time async: " + result);

            System.out.println("Seq execute:");
            for (Sums elem : resultSeq) {
                System.out.println("****************");
                System.out.println(elem.getColSum());
                System.out.println(elem.getRowSum());
            }

            System.out.println("Async execute:");
            for (Sums elem : resultAsync) {
                System.out.println("****************");
                System.out.println(elem.getColSum());
                System.out.println(elem.getRowSum());
            }
        }
    }
}
