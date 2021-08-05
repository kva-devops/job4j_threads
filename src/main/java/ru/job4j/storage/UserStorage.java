package ru.job4j.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.*;

@ThreadSafe
public class UserStorage {

    @GuardedBy("this")
    Map<Integer, User> userMap = new HashMap<>();

    public synchronized boolean add(User user) {
        return Objects.equals(userMap.put(user.id, user), user);
    }

    public synchronized boolean update(User user) {
        return Objects.equals(userMap.replace(user.id, user), user);
    }

    public synchronized boolean delete(User user) {
        return userMap.remove(user.id, user);
    }

    public synchronized User get(int id) {
        return userMap.get(id);
    }

    public void transfer(int fromId, int toId, int amount) {
        User userFrom = get(fromId);
        User userTo = get(toId);
        if (userFrom != null && userTo != null && userTo.amount >= amount) {
            userFrom.amount -= amount;
            userTo.amount += amount;
        }
        update(userFrom);
        update(userTo);
    }
}
