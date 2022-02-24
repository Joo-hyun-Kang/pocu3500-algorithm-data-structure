package academy.pocu.comp3500.assignment2;

import academy.pocu.comp3500.assignment2.datastructure.LinkedList;

public final class Indent {
    private final LinkedList<Object> textsAndIndents = new LinkedList<>();
    private int numSpaces = 0;
    private boolean discarded = false;

    public Indent() {
    }

    public Indent(int numSpaces) {
        this.numSpaces = numSpaces;
    }

    public int getNumSpaces() {
        return numSpaces;
    }

    public void discard() {
        discarded = true;
    }

    public LinkedList<Object> getTextsAndIndents() {
        return textsAndIndents;
    }

    public void addTextsAndIndents(Object o) {
        textsAndIndents.add(o);
    }

    public boolean isDiscarded() {
        return discarded;
    }
}
