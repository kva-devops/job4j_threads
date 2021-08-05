package ru.job4j.storage;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class UserStorageTest {

    private class ThreadTransfer extends Thread {

        private final UserStorage storage;

        private ThreadTransfer(final UserStorage storage) {
            this.storage = storage;
        }

        @Override
        public void run() {
            this.storage.transfer(1, 2, 100_000);
        }
    }

    @Test
    public void whenExecute1ThreadTransferTrue() throws InterruptedException {
        final UserStorage storage = new UserStorage();
        User user1 = new User(1, 500_000);
        User user2 = new User(2, 800_000);
        storage.add(user1);
        storage.add(user2);
        Thread first = new ThreadTransfer(storage);
        first.start();
        first.join();
        assertThat(storage.get(1).amount, is(400_000));
    }

    @Test
    public void whenExecute2ThreadTransfer() throws InterruptedException {
        final UserStorage storage = new UserStorage();
        storage.add(new User(1, 500_000));
        storage.add(new User( 2, 800_000));
        Thread first = new ThreadTransfer(storage);
        Thread second = new ThreadTransfer(storage);
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(storage.get(1).amount, is(300_000));
    }

    private class ThreadDelete extends Thread {

        private final UserStorage storage;

        private final User user1 = new User(1, 500_000);
        private final User user2 = new User(2, 800_000);

        private ThreadDelete(final UserStorage storage) {
            this.storage = storage;
        }

        @Override
        public void run() {
            storage.add(user1);
            storage.add(user2);
            this.storage.delete(user1);
        }
    }

    @Test
    public void whenDeleteUser() throws InterruptedException {
        final UserStorage storage = new UserStorage();
        Thread first = new ThreadDelete(storage);
        first.start();
        first.join();
        assertNull(storage.get(1));
    }
}