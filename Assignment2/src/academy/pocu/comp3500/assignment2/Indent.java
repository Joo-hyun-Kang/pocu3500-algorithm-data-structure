package academy.pocu.comp3500.assignment2;

import academy.pocu.comp3500.assignment2.datastructure.ArrayList;

import java.lang.reflect.Array;

public final class Indent {
    private ArrayList<String> loggingText = new ArrayList<>();
    private int whitespaceCount;
    private String delimiter;
    private boolean isDiscardOn;

    public Indent(int whitespaceCount) {
        this.whitespaceCount = whitespaceCount;
        this.delimiter = "";

        if (whitespaceCount > 0) {
            char whitespace[] = new char[whitespaceCount * 2];
            for (int i = 0; i < whitespaceCount * 2; i++) {
                whitespace[i] = ' ';
            }
            delimiter = new String(whitespace);
        }
    }

    public void addLog(String log) {
        loggingText.add(log);
    }

    public void discard() {
        this.isDiscardOn = true;
    }

    public ArrayList<String> getLoggingText() {
        return loggingText;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public int getWhitespaceCount() {
        return whitespaceCount;
    }

    public boolean isDiscardOn() {
        return isDiscardOn;
    }
}
