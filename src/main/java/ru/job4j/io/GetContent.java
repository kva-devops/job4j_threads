package ru.job4j.io;

import java.io.File;
import java.util.function.Predicate;

public interface GetContent {
    String content(Predicate<Character> filter, File file);
}
