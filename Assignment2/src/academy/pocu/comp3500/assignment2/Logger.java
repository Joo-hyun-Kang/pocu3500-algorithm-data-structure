package academy.pocu.comp3500.assignment2;

import academy.pocu.comp3500.assignment2.datastructure.ArrayList;
import academy.pocu.comp3500.assignment2.datastructure.Queue;
import academy.pocu.comp3500.assignment2.datastructure.Stack;

import javax.imageio.IIOException;
import java.io.BufferedWriter;
import java.io.IOException;

public final class Logger {
    private static ArrayList<String> loggingTexts = new ArrayList<>();
    private static ArrayList<Indent> indentLevles = new ArrayList<>();
    private static int depths;


    public static void log(final String text) {
        /*
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
         */

        loggingTexts.add(text);
    }

    public static void printTo(final BufferedWriter writer) throws IOException {
        int j = 0;
        String whiteSpaces = "";

        for (int i = 0; i < loggingTexts.getSize(); i++) {
            if (j < indentLevles.getSize() && i == indentLevles.get(j).getStart()) {
                //빈칸 출력
                int spaceLength = 2;
                spaceLength *= j + 1;

                char spaces[] = new char[spaceLength];
                for (int z = 0; z < spaceLength; z++) {
                    spaces[z] = ' ';
                }

                whiteSpaces = new String(spaces);

                j++;
            }

            writer.write(whiteSpaces);
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
        indentLevles.add(new Indent(loggingTexts.getSize()));
        depths++;

        return indentLevles.get(depths - 1);
    }

    public static void unindent() {
        indentLevles.get(depths - 1).setEnd(loggingTexts.getSize());
    }
}