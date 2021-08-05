package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile implements GetContent {

    private final File file;

    private final Save saveContent = new Save();

    public ParseFile(File file) {
        this.file = file;
    }

    public static void main(String[] args) {
        File unicodeCharFile = new File("unicodeSaveFile");
        File withoutUnicodeCharFile = new File("withoutUnicodeSaveFile");
        ParseFile parseFile = new ParseFile(new File("someFile"));
        String buffString;
        buffString = parseFile.content((character -> character < 0x80), parseFile.file);
        parseFile.saveContent.saveContent(buffString, withoutUnicodeCharFile);
        buffString = parseFile.content((data -> true), parseFile.file);
        parseFile.saveContent.saveContent(buffString, unicodeCharFile);
    }

    @Override
    public String content(Predicate<Character> filter, File file) {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = bis.read()) != -1) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
