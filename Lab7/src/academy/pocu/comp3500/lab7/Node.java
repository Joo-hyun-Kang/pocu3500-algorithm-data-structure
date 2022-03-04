package academy.pocu.comp3500.lab7;

public class Node {
    String str;
    int[] alphabetCount = new int[26];
    int count;
    int sum;

    public Node() {
    }

    public Node(String str) {
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
