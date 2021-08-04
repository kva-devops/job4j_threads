package ru.job4j.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.Predicate;

public class GetterContentUnicode implements GetContent {

    @Override
    public String content(Predicate<Character> filter, File file) {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = bis.read()) != -1) {
                output.append((char) data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
