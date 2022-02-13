package academy.pocu.comp3500.assignment2;

import academy.pocu.comp3500.assignment2.datastructure.ArrayList;

public final class Indent {
    private int start;
    private int end;
    private int level;
    private String delimiters;
    private boolean isNotDiscard;

    public Indent(int start) {
        this.start = start;
        this.end = -1;
        isNotDiscard = true;
    }

    public void discard() {
        isNotDiscard = false;
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
        return delimiters;
    }

    public boolean isNotDiscard() {
        return isNotDiscard;
    }
}
