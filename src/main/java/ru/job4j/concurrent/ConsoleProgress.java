package ru.job4j.concurrent;

import java.util.ArrayList;
import java.util.List;

public class ConsoleProgress implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(3500);
        progress.interrupt();
    }

    @Override
    public void run() {

        int count = 0;

        ArrayList<Character> characters = new ArrayList<>(
                List.of('-', '\\', '|', '/')
        );

        while (!Thread.currentThread().isInterrupted()) {
            try {
                System.out.print("\r loading : " + characters.get(count));
                Thread.sleep(350);
                count = (count < 3) ? count + 1 : 0;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
