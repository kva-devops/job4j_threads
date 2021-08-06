package ru.job4j.buffer;

import ru.job4j.queue.SimpleBlockingQueue;


public class ParallelSearch {
    public static void main(String[] args) {

        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();

        final int limit = 3;

        final Thread consumer = new Thread(() -> {
            int count = 0;
            while (count++ < limit) {
                System.out.println(queue.poll());
            }

        });
        Thread producer = new Thread(() -> {
            for (int index = 0; index != limit; index++) {
                queue.offer(index);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        consumer.start();
        producer.start();
    }
}
