package ru.job4j.concurrent;

import java.io.*;
import java.net.URL;

public class Wget implements Runnable {

    private final String url;

    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("someFile")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long refTime = referenceLoadTime(url);
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                if (refTime < speed) {
                    try {
                        Thread.sleep(speed - refTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private long referenceLoadTime(String url) {
        long referenceTime;
        long before = System.currentTimeMillis();
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("someFile")) {
                byte[] dataBuffer = new byte[1024];
                int bytesRead;
                bytesRead = in.read(dataBuffer, 0, 1024);
                fileOutputStream.write(dataBuffer, 0, bytesRead);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long after = System.currentTimeMillis();
        referenceTime = after - before;
        System.out.println(referenceTime);
        return referenceTime;
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
