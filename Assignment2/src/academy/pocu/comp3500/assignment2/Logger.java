package academy.pocu.comp3500.assignment2;

import academy.pocu.comp3500.assignment2.datastructure.ArrayList;
import academy.pocu.comp3500.assignment2.datastructure.Stack;

import java.io.BufferedWriter;
import java.io.IOException;

public final class Logger {
    private static ArrayList<String> loggingTexts = new ArrayList<>();
    private static ArrayList<Indent> indents = new ArrayList<>();
    private static Stack<Indent> levels = new Stack<>();

    public static void log(final String text) {
        loggingTexts.add(text);
    }

    public static void printTo(final BufferedWriter writer) throws IOException {
        int i = 0;
        int j = 0;

        String whiteSpaces = "";
        Stack<Indent> levels = new Stack<>();

        while (i < loggingTexts.getSize()) {
            while (j < indents.getSize() && i == indents.get(j).getStart()) {
                int indentCount = indents.get(j).getEnd() - indents.get(j).getStart();

                if (indentCount != 0 && indents.get(j).isNotDiscard()) {
                    levels.push(indents.get(j));
                    whiteSpaces = indents.get(j).getDelimiters();
                }

                /*
                if (indentCount > 0 && indents.get(j).isNotDiscard()) {
                    while (i < )
                }
                 */
                j++;
            }

            while (levels.getSize() > 0 && i == levels.peek().getEnd()) {
                levels.pop();

                if (levels.getSize() > 0) {
                    whiteSpaces = levels.peek().getDelimiters();
                } else {
                    whiteSpaces = "";
                }
            }

            writer.write(whiteSpaces);
            writer.write(loggingTexts.get(i));
            writer.write('\n');

            i++;
        }

        writer.flush();
    }

    public static void printTo(final BufferedWriter writer, final String filter) {
    }

    public static void clear() {
        loggingTexts.clear();
    }

    public static Indent indent() {
        Indent newIndent = new Indent(loggingTexts.getSize());

        indents.add(newIndent);

        levels.push(newIndent);

        levels.peek().setLevel(levels.getSize());

        int spaceLength = 2;
        spaceLength *= newIndent.getLevel();

        char spaces[] = new char[spaceLength];
        for (int z = 0; z < spaceLength; z++) {
            spaces[z] = ' ';
        }

        newIndent.setDelimiters(new String(spaces));

        return newIndent;
    }

    public static void unindent() {
        if (levels.getSize() == 0) {
            return;
        }

        Indent unindentedIndent = levels.pop();

        unindentedIndent.setEnd(loggingTexts.getSize());
    }
}