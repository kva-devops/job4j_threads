package ru.job4j.ref;

public class ShareNotSafe {
    public static void main(String[] args) throws InterruptedException {
        UserCache cache = new UserCache();
        User user = User.of("name");
        User user1 = User.of("name1");
        cache.add(user);
        cache.add(user1);
        Thread first = new Thread(
                () -> {
                    user.setName("rename");
                }
        );
        Thread second = new Thread(
                () -> {
                    user1.setName("rename2");
                }
        );
        first.start();
        first.join();
        second.start();
        second.join();
        System.out.println(cache.findById(1).getName());
        System.out.println(cache.findAll().toString());
    }
}
