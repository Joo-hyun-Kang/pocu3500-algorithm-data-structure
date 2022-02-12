package academy.pocu.comp3500.assignment2;

import academy.pocu.comp3500.assignment2.datastructure.ArrayList;

public final class Indent {
    private int start;
    private int end;
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

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
