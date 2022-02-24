package academy.pocu.comp3500.assignment2;

import academy.pocu.comp3500.assignment2.datastructure.LinkedList;

import java.io.BufferedWriter;
import java.io.IOException;

public final class Logger {
    private static final LinkedList<Indent> INDENTLIST = new LinkedList<>();
    private static int spaceCount = -1;

    public static void log(final String text) {
        if (INDENTLIST.getSize() == 0) {
            Indent indent = new Indent(++spaceCount);
            INDENTLIST.add(indent);
        }
        INDENTLIST.getLast().addLog(text);
    }

    public static void printTo(final BufferedWriter writer) throws IOException {
        for (Indent indent : INDENTLIST) {
            indent.print(writer);
        }
    }

    public static void printTo(final BufferedWriter writer, final String filter) throws IOException {
        for (Indent indent : INDENTLIST) {
            indent.print(writer, filter);
        }
    }

    public static void clear() {
        INDENTLIST.clear();
        spaceCount = -1;
    }

    public static Indent indent() {
        if (spaceCount == -1) {
            spaceCount = 0;
        }
        Indent indent = new Indent(++spaceCount);
        INDENTLIST.add(indent);
        return indent;
    }

    public static void unindent() {
        INDENTLIST.add(new Indent(--spaceCount));
    }

    public static void discardLogs(Indent discardIndent) {
        for (int i = 0; i < INDENTLIST.getSize(); i++) {
            if (INDENTLIST.get(i) == discardIndent) {
                int indentSpace = discardIndent.getSpaceCount();
                INDENTLIST.remove(i);
                try {
                    while (indentSpace <= INDENTLIST.get(i).getSpaceCount()) {
                        INDENTLIST.remove(i);
                    }
                } catch (IndexOutOfBoundsException ignore) {
                }
                break;
            }
        }
    }
}