package academy.pocu.comp3500.assignment2;

import academy.pocu.comp3500.assignment2.datastructure.ArrayList;

public final class Indent {
    private int start;
    private int end;
    private int level;
    private String delimiters;
    private boolean isDiscard;

    public Indent(int start) {
        this.start = start;
    }

    public void discard() {
        isDiscard = true;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setDelimiters(String delimiters) {
        this.delimiters = delimiters;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getLevel() {
        return level;
    }

    public String getDelimiters() {
        return  delimiters;
    }
}
