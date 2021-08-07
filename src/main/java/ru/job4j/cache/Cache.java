package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        Base rsl = memory.computeIfPresent(model.getId(), (key, val) -> {
            Base stored = memory.get(key);
            if (stored.getVersion() != model.getVersion()) {
                throw new OptimisticException("Version are not equal");
            }
            String name = stored.getName();
            val = new Base(key, stored.getVersion() + 1);
            val.setName(name);
            return val;
        });

        return rsl != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }

    public Base get(int id) {
        return memory.get(id);
    }
}
