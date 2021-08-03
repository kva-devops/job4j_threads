package ru.job4j;

public final class DCLSingleton {

    private static volatile DCLSingleton inst;

    public static DCLSingleton instOf() {
        if (inst == null) {
            synchronized (DCLSingleton.class) {
                if (inst == null) {
                    inst = new DCLSingleton();
                }
            }
        }
        return inst;
    }

    private DCLSingleton() {
    }

    public static void main(String[] args) throws InterruptedException {
        DCLSingleton object = new DCLSingleton();
        Thread thread1 = new Thread(
                () -> {
                    object.instOf();
                    System.out.println(Thread.currentThread().getName());
                    System.out.println(object);
                }
        );
        Thread thread2 = new Thread(
                () -> {
                    object.instOf();
                    System.out.println(Thread.currentThread().getName());
                    System.out.println(object);
                }
        );
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }
}
