package ru.job4j.concurrent;

import java.util.ArrayList;
import java.util.List;

public class ConsoleProgress implements Runnable {

    int count = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(3500);
        progress.interrupt();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                System.out.print("\r loading : " + process());
                Thread.sleep(350);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private char process() {
        char buff;
        List<Character> characterList = new ArrayList<>(
                List.of(
                        '-', '\\', '|', '/'
                )
        );
        buff = characterList.get(count);
        if (count < 3) {
            count++;
        } else {
            count = 0;
        }
        return buff;
    }
}
