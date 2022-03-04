package academy.pocu.comp3500.lab7;

public class Node {
    String str;
    int[] alphabetCount;
    int count;

    public Node(String str) {
        alphabetCount = new int[26];
        this.str = str;
    }

    public int[] getAlphabetCount() {
        return alphabetCount;
    }

    public int getCount() {
        return count;
    }

    public String getStr() {
        return str;
    }
}
