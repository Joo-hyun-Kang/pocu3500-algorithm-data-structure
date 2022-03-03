package academy.pocu.comp3500.lab7.app;

import academy.pocu.comp3500.lab7.Decryptor;

import java.util.ArrayList;

public class Program {

    public static void main(String[] args) {
        {
            ArrayList<String> test = new ArrayList<>();
            test.add("1234");
            test.add("3333");
            test.remove(1);



            String[] codeWords = new String[]{"cat", "CATS", "AcTS", "SCAN", "acre", "aNTS"};

            Decryptor decryptor = new Decryptor(codeWords);

            String[] candidates = decryptor.findCandidates("cat");

            assert (candidates.length == 1);
            assert (candidates[0].equals("cat"));

            candidates = decryptor.findCandidates("race");

            assert (candidates.length == 1);
            assert (candidates[0].equals("acre"));

            candidates = decryptor.findCandidates("ca");

            assert (candidates.length == 0);

            candidates = decryptor.findCandidates("span");

            assert (candidates.length == 0);

            candidates = decryptor.findCandidates("ACT");

            assert (candidates.length == 1);
            assert (candidates[0].equals("cat"));

            candidates = decryptor.findCandidates("cats");

            assert (candidates.length == 2);
            assert (candidates[0].equals("cats") || candidates[0].equals("acts"));
            assert (candidates[1].equals("cats") || candidates[1].equals("acts"));

            candidates = decryptor.findCandidates("SCAt");

            assert (candidates.length == 2);
            assert (candidates[0].equals("cats") || candidates[0].equals("acts"));
            assert (candidates[1].equals("cats") || candidates[1].equals("acts"));
        }

        {
            String[] codeWords = new String[]{};
            Decryptor decryptor = new Decryptor(codeWords);

            String[] candidates = decryptor.findCandidates("cat");

            assert (candidates.length == 0);
        }

        {
            String[] codeWords = new String[]{"asdfasd"};
            Decryptor decryptor = new Decryptor(codeWords);

            String[] candidates = decryptor.findCandidates(null);

            assert (candidates.length == 0);
        }

        {
            String[] codeWords = new String[]{"asDFasd", "asdFasd"};
            Decryptor decryptor = new Decryptor(codeWords);

            String[] candidates = decryptor.findCandidates("asDSAdf");

            assert (candidates.length == 1);
        }

        {
            String[] codeWords = new String[]{"asDFasd", "dasw", "xvcz", "qweaz", "123"};
            Decryptor decryptor = new Decryptor(codeWords);

            for (String word : codeWords) {
                String[] candidates = decryptor.findCandidates(word);
                assert (candidates.length == 1);
                assert (candidates[0].equals(word.toLowerCase()));
            }
        }

        {
            String[] codeWords = new String[]{"acf"};
            Decryptor decryptor = new Decryptor(codeWords);

            String[] candidates = decryptor.findCandidates("acfz");
            assert (candidates.length == 0);
        }
    }
}
