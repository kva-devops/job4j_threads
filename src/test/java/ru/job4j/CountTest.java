package ru.job4j;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CountTest {

    private class ThreadCount extends Thread {

        private final Count count;

        private ThreadCount(final Count count) {
            this.count = count;
        }

        @Override
        public void run() {
            this.count.increment();
        }
    }

    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
        final Count count = new Count();
        Thread first = new ThreadCount(count);
        Thread second = new ThreadCount(count);
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(count.get(), is(2));
    }

    @Test
    public void whenGetOnly() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final CasCount countCas = new CasCount();
        Thread first = new Thread(() -> buffer.add(countCas.get()));
        first.start();
        Thread second = new Thread(() -> buffer.add(countCas.get()));
        second.start();
        first.join();
        second.join();
        assertThat(buffer, is(Arrays.asList(0, 0)));
    }
}