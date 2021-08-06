package ru.job4j.buffer;

import ru.job4j.queue.SimpleBlockingQueue;


public class ParallelSearch {
    public static void main(String[] args) {

        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);

        final int limit = queue.getLimit();

        final Thread consumer = new Thread(() -> {
            int count = 0;
            while (!Thread.currentThread().isInterrupted()) {
                if (count++ < limit) {
                    try {
                        System.out.println(queue.poll());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    Thread.currentThread().interrupt();
                }
            }
        });
        Thread producer = new Thread(() -> {
            for (int index = 0; index != queue.getLimit(); index++) {
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
    }
}
