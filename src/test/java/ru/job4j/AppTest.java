package ru.job4j;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AppTest {

    @Test
    public void whenTrue() {
        boolean val = true;
        assertThat(val, is(true));
    }
}
