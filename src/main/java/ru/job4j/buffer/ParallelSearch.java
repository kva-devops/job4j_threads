package ru.job4j.buffer;

import ru.job4j.queue.SimpleBlockingQueue;


public class ParallelSearch {
    public static void main(String[] args) throws InterruptedException {

        int limit = 3;

        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(limit);

        final Thread consumer = new Thread(() -> {
            int count = 0;
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    System.out.println(queue.poll());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                count++;
                if (count == limit) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        Thread producer = new Thread(() -> {
            for (int index = 0; index != limit; index++) {
                try {
                    Thread.sleep(500);
                    queue.offer(index);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        consumer.start();
        producer.start();
        consumer.join();
        consumer.interrupt();
    }
}
