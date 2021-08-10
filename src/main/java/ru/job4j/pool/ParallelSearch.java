package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {

    private final T[] array;

    private final T object;

    private final int from;

    private final int to;

    public ParallelSearch(T[] array, T object, int from, int to) {
        this.array = array;
        this.object = object;
        this.from = from;
        this.to = to;
    }


    @Override
    protected Integer compute() {
        if (to - from < 10 && to > from) {
            return findIndex(from, to);
        }
        int mid = (from + to) / 2;
        ParallelSearch<T> leftSearch = new ParallelSearch<>(array, object, from, mid);
        ParallelSearch<T> rightSearch = new ParallelSearch<>(array, object, mid + 1, to);
        leftSearch.fork();
        rightSearch.fork();
        Integer right = rightSearch.join();
        Integer left = leftSearch.join();
        return (left > right) ? left : right;
    }

    private int findIndex(int from, int to) {
        int rsl = -1;
        for (int i = from; i <= to; i++) {
            if (array[i].equals(object)) {
                rsl = i;
            }
        }
        return rsl;
    }

    public static int search(Object[] array, Object object) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelSearch<>(array, object, 0, array.length - 1));
    }

    public static void main(String[] args) {
        String[] array = new String[100];
        for (int i = 0; i < 100; i++) {
            array[i] = String.format("String No%s", i);
        }
        String object = "String No59";
        int result = ParallelSearch.search(array, object);
        System.out.println(result);
    }
}
