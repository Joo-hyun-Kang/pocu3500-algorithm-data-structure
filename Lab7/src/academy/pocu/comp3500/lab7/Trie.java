package academy.pocu.comp3500.lab7;

import java.util.HashMap;

public class Trie {
    private HashMap<Character, Trie> child;
    private char value;
    private boolean isEndChar;

    public Trie() {
        child = new HashMap<>();
    }

    public Trie(char value) {
        child = new HashMap<>();
        this.value = value;
    }

    public HashMap<Character, Trie> getChild() {
        return child;
    }

    public boolean isEndChar() {
        return isEndChar;
    }

    public void setEndChar(boolean endChar) {
        isEndChar = endChar;
    }
}
