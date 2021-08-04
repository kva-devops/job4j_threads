package ru.job4j.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;

@ThreadSafe
public class UserStorage {

    @GuardedBy("this")
    List<User> userList = new ArrayList<>();

    public synchronized boolean add(User user) {
        return userList.add(user);
    }

    public synchronized boolean update(User user) {
        int index = userList.indexOf(user);
        return userList.set(index, user).equals(user);
    }

    public synchronized boolean delete(User user) {
        return userList.remove(user);
    }

    public synchronized User get(int index) {
        return userList.get(index);
    }

    public void transfer(int fromId, int toId, int amount) {
        User userFrom = findById(fromId);
        User userTo = findById(toId);
        if (userFrom != null && userTo != null && userTo.amount >= amount) {
            userFrom.amount -= amount;
            userTo.amount += amount;
        }
        update(userFrom);
        update(userTo);
    }

    private synchronized User findById(int id) {
        for (User user : userList) {
            if (user.id == id) {
                return user;
            }
        }
        return null;
    }
}
