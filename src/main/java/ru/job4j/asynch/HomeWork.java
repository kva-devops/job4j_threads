package ru.job4j.asynch;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class HomeWork {
    private static void iWork() throws InterruptedException {
        int count = 0;
        while (count < 10) {
            System.out.println("I working. Thread: "
                    + Thread.currentThread().getName());
            TimeUnit.SECONDS.sleep(1);
            count++;
        }
    }

    public static CompletableFuture<Void> goToTrash() {
        return CompletableFuture.runAsync(
                () -> {
                    System.out.println("Son taking out the garbage. Thread: "
                            + Thread.currentThread().getName());
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Son come back. Thread: "
                            + Thread.currentThread().getName());
                }
        );
    }

    public static CompletableFuture<String> buyProduct(String product) {
        return CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("son going in the shop");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("son buy " + product);
                    return product;
                }
        );
    }

    public static CompletableFuture<Void> washHands(String name) {
        return CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + ", washing hands");
        });
    }

    public static CompletableFuture<String> whoWashHands(String name) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return name + ", washing hands";
        });
    }

    public static void runAsyncExample() throws Exception {
        CompletableFuture<Void> gtt = goToTrash();
        iWork();
    }

    public static void thenRunExample() throws Exception {
        CompletableFuture<Void> gtt = goToTrash();
        gtt.thenRun(() -> {
            int count = 0;
            while (count < 3) {
                System.out.println("Son washing hand");
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
            }
            System.out.println("Son washed hand");
        });
        iWork();
    }

    public static void supplyAsyncExample() throws Exception {
        CompletableFuture<String> bm = buyProduct("Milk");
        iWork();
        System.out.println("Buyed: " + bm.get());
    }

    public static void thenAcceptExample() throws Exception {
        CompletableFuture<String> bm = buyProduct("Milk");
        bm.thenAccept((product) -> System.out.println("Son put the " + product + " in the fridge"));
        iWork();
        System.out.println("Buyed: " + bm.get());
    }

    public static void thenComposeExample() throws Exception {
        CompletableFuture<Void> result = buyProduct("Milk").thenCompose(a -> goToTrash());
        iWork();
    }

    public static void allOfExample() throws Exception {
        CompletableFuture<Void> all = CompletableFuture.allOf(
                washHands("Dad"), washHands("Mom"), washHands("Ivan")
        );
        TimeUnit.SECONDS.sleep(3);
    }

    public static void anyOfExample() throws Exception {
        CompletableFuture<Object> first = CompletableFuture.anyOf(
                whoWashHands("Dad"), whoWashHands("Mom"), whoWashHands("Ivan")
        );
        System.out.println("Who washing hands now?");
        TimeUnit.SECONDS.sleep(1);
        System.out.println(first.get());
    }

    public static void main(String[] args) throws Exception {
        //HomeWork.runAsyncExample();
        //HomeWork.supplyAsyncExample();
        //HomeWork.thenRunExample();
        //HomeWork.thenAcceptExample();
        //HomeWork.thenComposeExample();
        //HomeWork.allOfExample();
        HomeWork.anyOfExample();
    }
}
