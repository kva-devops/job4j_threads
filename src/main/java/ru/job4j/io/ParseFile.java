package ru.job4j.io;

import java.io.*;

public class ParseFile {

    private final File file;

    private final GetContent getterContentUnicode = new GetterContentUnicode();

    private final GetContent getterContentWithoutUnicode = new GetterContentWithoutUnicode();

    private final Save saveContent = new Save();

    public ParseFile(File file) {
        this.file = file;
    }

    public static void main(String[] args) {
        File unicodeCharFile = new File("unicodeSaveFile");
        File withoutUnicodeCharFile = new File("withoutUnicodeSaveFile");
        ParseFile parseFile = new ParseFile(new File("someFile"));
        String buffString;
        buffString = parseFile.getterContentWithoutUnicode.content((character -> character < 0x80), parseFile.file);
        parseFile.saveContent.saveContent(buffString, withoutUnicodeCharFile);
        buffString = parseFile.getterContentUnicode.content((character -> character > 0), parseFile.file);
        parseFile.saveContent.saveContent(buffString, unicodeCharFile);
    }
}
