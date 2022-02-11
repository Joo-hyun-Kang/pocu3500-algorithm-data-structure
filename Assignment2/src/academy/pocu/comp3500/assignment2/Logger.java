package academy.pocu.comp3500.assignment2;

import academy.pocu.comp3500.assignment2.datastructure.ArrayList;
import academy.pocu.comp3500.assignment2.datastructure.Queue;

import javax.imageio.IIOException;
import java.io.BufferedWriter;
import java.io.IOException;

public final class Logger {
    private static ArrayList<String> loggingTexts = new ArrayList<>();
    private static int depth = 0;

    public static void log(final String text) {
        String whiteSpaces = "";

        if (depth != 0) {
            int spaceLength = 2;
            spaceLength *= depth;

            char spaces[] = new char[spaceLength];
            for (int i = 0; i < spaceLength; i++) {
                spaces[i] = ' ';
            }

            whiteSpaces = new String(spaces);
        }

        loggingTexts.add(String.format("%s%s", whiteSpaces, text));
    }

    public static void printTo(final BufferedWriter writer) throws IOException {
        for (int i = 0; i < loggingTexts.getSize(); i++) {
            writer.write(loggingTexts.get(i));
            writer.newLine();
        }

        writer.flush();
    }

    public static void printTo(final BufferedWriter writer, final String filter) {

    }

    public static void clear() {
        loggingTexts.clear();
    }

    public static Indent indent() {
        depth++;
        return new Indent();
    }

    public static void unindent() {
        depth--;
    }
}