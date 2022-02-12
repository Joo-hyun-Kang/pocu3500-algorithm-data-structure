package academy.pocu.comp3500.assignment2;

import academy.pocu.comp3500.assignment2.datastructure.ArrayList;
import academy.pocu.comp3500.assignment2.datastructure.Queue;
import academy.pocu.comp3500.assignment2.datastructure.Stack;

import javax.imageio.IIOException;
import java.io.BufferedWriter;
import java.io.IOException;

public final class Logger {
    private static ArrayList<String> loggingTexts = new ArrayList<>();
    private static ArrayList<Indent> indents = new ArrayList<>();
    private static Queue<Integer> indentDepths = new Queue<>();
    private static int depth = 1;

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
        int i = 0;
        int j = 0;
        int k = 0;

        int level = indents.getSize();
        String whiteSpaces = "";

        while (i < loggingTexts.getSize()) {
            if (j < indents.getSize() && i == indents.get(j).getStart()) {
                int spaceLength = 2;
                spaceLength *= indents.get(j).getLevel();

                char spaces[] = new char[spaceLength];
                for (int z = 0; z < spaceLength; z++) {
                    spaces[z] = ' ';
                }

                indents.get(j).setDelimiters(new String(spaces));
                whiteSpaces = indents.get(j).getDelimiters();
                j++;

                k = j;
            }


            if (k > 0 && i == indents.get(k - 1).getEnd()) {
                if (indents.get(k - 1).getLevel() == 1) {
                    whiteSpaces = "";
                } else {
                    whiteSpaces = indents.get(k - 2).getDelimiters();
                }

                k--;
            }

            writer.write(whiteSpaces);
            writer.write(loggingTexts.get(i));

            i++;

            if (i != loggingTexts.getSize()) {
                writer.newLine();
            }
        }

        writer.flush();
    }

    public static void printTo(final BufferedWriter writer, final String filter) {
    }

    public static void clear() {
        loggingTexts.clear();
    }

    public static Indent indent() {
        final int UNINDENT_INITIAL = -1;

        Indent tmp = new Indent(loggingTexts.getSize(), UNINDENT_INITIAL);

        indents.add(tmp);

        tmp.setLevel(depth);

        indentDepths.enqueue(depth++);

        return indents.get(indents.getSize() - 1);
    }

    public static void unindent() {
        indents.get(indents.getSize() - indentDepths.dequeue()).setEnd(loggingTexts.getSize());

        depth--;
    }
}