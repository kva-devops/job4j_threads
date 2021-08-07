package ru.job4j.cache;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenAddOneElementToCache() {
        Cache cache = new Cache();
        Base model = new Base(1, 0);
        cache.add(model);
        assertThat(cache.get(1), is(model));
    }

    @Test
    public void whenAddAndDeleteOneElementToCache() {
        Cache cache = new Cache();
        Base model = new Base(1, 0);
        cache.add(model);
        cache.delete(model);
        assertNull(cache.get(1));
    }

    @Test
    public void whenUpdateOneElementThenChangeVersion() {
        Cache cache = new Cache();
        Base model = new Base(1, 0);
        cache.add(model);
        cache.update(model);
        assertThat(cache.get(1).getVersion(), is(1));
    }

    @Test
    public void whenUpdateOneElementAndNameThenChangeName() {
        Cache cache = new Cache();
        Base model = new Base(1, 0);
        model.setName("first model");
        cache.add(model);
        model.setName("second model");
        cache.update(model);
        assertThat(cache.get(1).getName(), is("second model"));
    }

    @Test
    public void whenUpdateElementThenReturnTrue() {
        Cache cache = new Cache();
        Base model = new Base(1, 0);
        cache.add(model);
        assertTrue(cache.update(model));
    }

    @Test(expected = OptimisticException.class)
    public void whenUpdateDifferentVersion() {
        Cache cache = new Cache();
        Base model = new Base(1, 0);
        cache.add(model);
        Base modelErrorVersion = new Base(1, 1);
        cache.update(modelErrorVersion);
    }
}