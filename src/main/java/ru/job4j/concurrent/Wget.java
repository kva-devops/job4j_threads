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
        String filename = "someFile";
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(filename)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                long before = System.currentTimeMillis();
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                long after = System.currentTimeMillis();
                long referenceTime = after - before;
                if (referenceTime < speed) {
                    try {
                        Thread.sleep(speed - referenceTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2) {
            throw new InterruptedException("You need enter 2 arguments");
        }
        if (!args[0].contains("https://")) {
            throw new InterruptedException("Enter correct link: http://someadress");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        if (speed < 0) {
            throw new InterruptedException("Enter correct speed");
        }
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
