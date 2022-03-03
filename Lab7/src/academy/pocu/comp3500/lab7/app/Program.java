package academy.pocu.comp3500.lab7.app;

import academy.pocu.comp3500.lab7.Decryptor;

public class Program {

    public static void main(String[] args) {

        String[] codeWords = new String[]{"cat", "CATS", "AcTS", "SCAN", "acre", "aNTS"};

        Decryptor decryptor = new Decryptor(codeWords);

        String[] candidates = decryptor.findCandidates("dog");  // []
        candidates = decryptor.findCandidates("cat");  // ["cat"]
        candidates = decryptor.findCandidates("cats");  // ["cats", "acts"]
        candidates = decryptor.findCandidates("cAsT");  // ["cats", "acts"]
    }
}
