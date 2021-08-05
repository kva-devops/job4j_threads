package ru.job4j;

public class CheckCountBarrier {
    public static void main(String[] args) throws InterruptedException {
        CountBarrier countBarrier = new CountBarrier(5);
        Thread mainThread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " started");
            for (int i = 0; i < 10; i++) {
                countBarrier.count();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "main Thread");
        Thread secondaryThread = new Thread(() -> {
            countBarrier.await();
            System.out.println(Thread.currentThread().getName() + " started");
        }, "secondary Thread");
        mainThread.start();
        secondaryThread.start();
    }
}
