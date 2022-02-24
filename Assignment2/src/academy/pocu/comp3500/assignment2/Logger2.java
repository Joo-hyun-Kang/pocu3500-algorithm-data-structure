package academy.pocu.comp3500.assignment2;

import academy.pocu.comp3500.assignment2.datastructure.Stack;

import java.io.BufferedWriter;
import java.io.IOException;

public final class Logger {
    private static Indent root = new Indent();
    private static Indent cursor = root;
    private static Stack<Indent> cursorHistory = new Stack<>();

    public static void log(final String text) {
        cursor.addTextsAndIndents(text);
    }

    public static void printTo(final BufferedWriter writer) {
        try {
            print(root, writer);
            writer.flush();
        } catch (IOException e) {
            // intentionally empty
        }
    }

    public static void print(Indent indent, final BufferedWriter writer) throws IOException {
        for (Object o : indent.getTextsAndIndents()) {
            if (o instanceof String) {
                for (int i = 0; i < indent.getNumSpaces(); i++) {
                    writer.write(" ");
                }
                writer.write((String) o);
                writer.newLine();
            } else {
                Indent sub = (Indent) o;
                if (!sub.isDiscarded()) {
                    print((Indent) o, writer);
                }
            }
        }
    }

    public static void printTo(final BufferedWriter writer, final String filter) {
        try {
            print(root, writer, filter);
            writer.flush();
        } catch (IOException e) {
            // intentionally empty
        }
    }

    public static void print(Indent indent, final BufferedWriter writer, final String filter) throws IOException {
        for (Object o : indent.getTextsAndIndents()) {
            if (o instanceof String) {
                String s = (String) o;
                if (s.contains(filter)) {
                    for (int i = 0; i < indent.getNumSpaces(); i++) {
                        writer.write(" ");
                    }
                    writer.write((String) o);
                    writer.newLine();
                }
            } else {
                Indent sub = (Indent) o;
                if (!sub.isDiscarded()) {
                    print((Indent) o, writer, filter);
                }
            }
        }
    }

    public static void clear() {
        root = new Indent();
        cursor = root;
        cursorHistory = new Stack<>();
    }

    public static Indent indent() {
        cursorHistory.push(cursor);

        Indent subIndent = new Indent(cursor.getNumSpaces() + 2);
        cursor.addTextsAndIndents(subIndent);
        cursor = subIndent;
        return subIndent;
    }

    public static void unindent() {
        if (cursor == root) {
            return;
        }
        cursor = cursorHistory.pop();
    }

}
