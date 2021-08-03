package ru.job4j.concurrent;

public class CheckCache {
    public static void main(String[] args) throws InterruptedException {
        Cache cache = new Cache();
        Thread first = new Thread(cache::instOf);
        Thread second = new Thread(cache::instOf);
        Thread third = new Thread(cache::instOf);
        first.start();
        second.start();
        third.start();
        first.join();
        second.join();
        third.join();
    }
}
