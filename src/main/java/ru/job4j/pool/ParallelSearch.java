package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<T> {

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
    protected T compute() {
        if (to - from < 10 && to > from) {
            for (int i = from; i <= to; i++) {
                if (array[i].equals(object)) {
                    return array[i];
                }
            }
        }
        int mid = (from + to) / 2;
        ParallelSearch<T> leftSearch = new ParallelSearch<>(array, object, from, mid);
        ParallelSearch<T> rightSearch = new ParallelSearch<>(array, object, mid + 1, to);
        leftSearch.fork();
        rightSearch.fork();
        T right = rightSearch.join();
        T left = leftSearch.join();
        if (right != null) {
            return right;
        }
        if (left != null) {
            return left;
        }
        return null;
    }

    public T search() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelSearch<>(array, object, from, to));
    }

    public static void main(String[] args) {
        String[] array = new String[100];
        for (int i = 0; i < 100; i++) {
            array[i] = String.format("String No%s", i);
        }
        String object = "String No20";
        ParallelSearch<String> parallelSearch = new ParallelSearch<>(array,
                object,
                0,
                array.length - 1);
        String result = parallelSearch.search();
        System.out.println(result);
    }
}
