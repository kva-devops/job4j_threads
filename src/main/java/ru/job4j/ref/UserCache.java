package ru.job4j.ref;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class UserCache {
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    public void add(User user) {
        String name = user.getName();
        users.put(id.incrementAndGet(), User.of(name));
    }

    public User findById(int id) {
        return User.of(users.get(id).getName());
    }

    public List<User> findAll() {
        ArrayList<User> buff = new ArrayList<>();
        for (User user : users.values()) {
            buff.add(User.of(user.getName()));
        }
        return buff;
    }
}
