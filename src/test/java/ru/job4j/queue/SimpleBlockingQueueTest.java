package ru.job4j.queue;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SimpleBlockingQueueTest {

    @Test
    public void addAndRetrieves() throws InterruptedException {
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>();
        Thread producer = new Thread(() -> {
            simpleBlockingQueue.offer(1);
            simpleBlockingQueue.offer(2);
        });
        Thread consumer = new Thread(simpleBlockingQueue::poll);
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(simpleBlockingQueue.poll(), is(2));
    }

    @Test
    public void addIfNotFreePlace() throws InterruptedException {
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>();
        Thread producer = new Thread(() -> {
            simpleBlockingQueue.offer(1);
            simpleBlockingQueue.offer(2);
            simpleBlockingQueue.offer(3);
        });
        Thread consumer = new Thread(() -> {
            simpleBlockingQueue.poll();
            simpleBlockingQueue.poll();
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(simpleBlockingQueue.poll(), is(3));


    }
}