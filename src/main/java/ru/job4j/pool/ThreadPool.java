package ru.job4j.pool;

import ru.job4j.queue.SimpleBlockingQueue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ThreadPool {

    private final List<Thread> threads = new LinkedList<>();

    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(5);


    public ThreadPool() {
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            threads.add(new Thread(new TaskWorker()));
            threads.get(i).start();
        }
    }

    public void work(Runnable job) {
        try {
            tasks.offer(job);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    private final class TaskWorker implements Runnable {

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Runnable nextTask = tasks.poll();
                    if (nextTask != null) {
                        nextTask.run();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        for (int i = 0; i < 100; i++) {
            threadPool.work(() -> System.out.println(Thread.currentThread().getName()));
        }
        threadPool.shutdown();
    }
}
