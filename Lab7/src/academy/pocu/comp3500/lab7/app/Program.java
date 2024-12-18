package academy.pocu.comp3500.lab7.app;

import academy.pocu.comp3500.lab7.Decryptor;

import java.util.ArrayList;

public class Program {

    public static void main(String[] args) {
        //bill's test
        {
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

        //colleague test
        {
            String[] codeWords = new String[]{};
            Decryptor decryptor = new Decryptor(codeWords);

            String[] candidates = decryptor.findCandidates("cat");

            assert (candidates.length == 0);
        }

        {
            String[] codeWords = new String[]{"asdfasd"};
            Decryptor decryptor = new Decryptor(codeWords);

            String[] candidates = decryptor.findCandidates("cat");

            assert (candidates.length == 0);
        }

        {
            String[] codeWords = new String[]{"asDFasd"};
            Decryptor decryptor = new Decryptor(codeWords);

            String[] candidates = decryptor.findCandidates("asDSAdf");

            assert (candidates.length == 1);
        }

        {
            String[] codeWords = new String[]{"asDFasd", "dasw", "xvcz", "qweaz"};
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

        //jokang's Test
        {
            String[] codeWords = new String[]{"acf"};
            Decryptor decryptor = new Decryptor(codeWords);

            String[] candidates = decryptor.findCandidates(null);
            assert (candidates.length == 0);
        }

        {
            String[] codeWords = new String[]{"acf"};
            Decryptor decryptor = new Decryptor(codeWords);

            String[] candidates = decryptor.findCandidates("");
            assert (candidates.length == 0);
        }

        {
            String[] codeWords = new String[]{"numberissidsfsfdsfsfewfwfwefewfwes"};
            Decryptor decryptor = new Decryptor(codeWords);

            String[] candidates = decryptor.findCandidates("numberissidsfsfdsfsfewfwfwefewfwes");

            assert (candidates.length == 1);
        }
    }
}
