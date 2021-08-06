package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CasCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        int countValue;
        int temp;
        do {
            temp = count.get();
            countValue = temp + 1;
        } while (!count.compareAndSet(temp, countValue));
    }

    public int get() {
        return count.get();
    }
}
