package academy.pocu.comp3500.lab7;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Decryptor {
    // 트라이를 이용할 때 해시맵 or 배열?
    // 해시맵은 기본 버킷 16개 배열 알파벳은 26개
    // 해시맵을 사용하나 배열을 사용하나 별 차이 없다
    // 더 간편한 해시맵 선택

    Trie root = new Trie();

    ArrayList<Node> nodes = new ArrayList<>();

    public Decryptor(final String[] codeWords) {

        // 트라이의 원리?
        // 한 글자를 떼서 생각 abcd
        // a b
        for (String code : codeWords) {
            String lowercaseCode = convertLowercase(code);

            Node node = new Node(lowercaseCode);

            for (int i = 0; i < lowercaseCode.length(); i++) {
                int[] temp = node.getAlphabetCount();
                temp[lowercaseCode.charAt(i) - 'a']++;
                node.count++;
            }

            nodes.add(node);
        }



        /*
       for (String code : codeWords) {
            String lowercaseCode = convertLowercase(code);

            Trie temp = root;

            for (int i = 0; i < lowercaseCode.length(); i++) {
                char ch = lowercaseCode.charAt(i);

                if (temp.getChild().containsKey(ch) == false) {
                    temp.getChild().put(ch, new Trie(ch));
                }

                temp = temp.getChild().get(ch);
            }

            temp.setEndChar(true);
        }

         */

    }

    public String[] findCandidates(final String word) {
        if (word == null) {
            return new String[]{};
        }



        String lowercaseCode = convertLowercase(word);

        Node wordNode = new Node(lowercaseCode);

        for (int i = 0; i < lowercaseCode.length(); i++) {
            int[] temp = wordNode.getAlphabetCount();
            temp[lowercaseCode.charAt(i) - 'a']++;
            wordNode.count++;
        }

        ArrayList<String> result = new ArrayList<>();
        for (Node node : nodes) {
            if (node.getCount() == wordNode.getCount()) {
                int[] nodeAlphabet = node.getAlphabetCount();
                int[] wordAlphabet = wordNode.getAlphabetCount();

                int i = 0;
                while (i < nodeAlphabet.length && nodeAlphabet[i] == wordAlphabet[i]) {
                    i++;
                }

                if (i == nodeAlphabet.length) {
                    result.add(node.getStr());
                }
            }
        }


        return result.toArray(new String[]{});

        /*
        String lowerWord = convertLowercase(word);

        ArrayList<String> result = new ArrayList<>();

        findCandidatesRecursive(lowerWord, 0, lowerWord.length() - 1, result);

        return result.toArray(new String[]{});

         */
    }

    /*
    private void findCandidatesRecursive(String word, int start, int end, ArrayList<String> result) {
        // 글자가 일정하지 않을 때 날리기
        // trie를 BFS
        // abcd 모든 조합에 대해서 대칭하기
        // N! -> 일정 단어장
        // 일정 단어장 ->

        HashMap<Character, Boolean> isVisted = new HashMap<>();
        for (int i = 0; i < word.length(); i++) {
            isVisted.put(word.charAt(i), false);
        }

        root.getChild().
    }

     */

    private String convertLowercase(String str) {
        char[] arr = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {
            if (arr[i] >= 'A' && arr[i] <= 'Z') {
                arr[i] ^= 32;
            }
        }
        return new String(arr);
    }

    private String swap(String word, int index1, int index2) {
        char[] arr = word.toCharArray();
        char temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;

        return new String(arr);
    }

    //순열 재귀 이용 시간 복잡도 N! 사전 찾기
    //속도가 느려서 테스트를 통과하지 못한다
    //DFS식으로 끝까지 가는 것이 아니라 BFS를 사용하면 된다!
    private void findCandidatesRecursive(String word, int start, int end, ArrayList<String> result, ArrayList<String> codeWords) {
        if (start == end) {
            int index = 0;
            for (String code : codeWords) {
                if (code.equals(word)) {
                    result.add(code);
                    codeWords.remove(index);
                    break;
                }
                index++;
            }
        }

        for (int i = start; i <= end; i++) {
            if (codeWords.size() <= 0) {
                break;
            }

            word = swap(word, start, i);
            findCandidatesRecursive(word, start + 1, end, result, codeWords);
            word = swap(word, start, i);
        }
    }

    //순열 for문으로 작성
    public void printPermutation(int nums[]) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (i != j) {
                    for (int z = 0; z < nums.length; z++) {
                        if (i != z && j != z) {
                            System.out.printf("%d%d%d  ", nums[i], nums[j], nums[z]);
                        }
                    }
                }
            }
        }
    }

    //순열 재귀로 작성
    public void printPermutationRecursive(int nums[], int start, int end) {
        if (start == end) {
            for (int num : nums) {
                System.out.printf("%d", num);
            }
            System.out.printf("  ");
        }


        // start는 일단 고정, 깊이에 따라 달라질 뿐
        // 123, 213, 321 규칙이 있는데 1을 기준으로 그 뒤에 애들이랑 바뀐다
        // 1이 start가 되고 그 뒤에 증가하는 기준이 i가 됨
        // 하위로 내려가면 123 132에서 두번째 자리에서는 2자리가 start가 되고 배열의 길이까지 바꾼다
        // start가 end에 다다르면 출력을 한다
        // start는 깊이의 pivor이고 i는 변화하는 것, end는 종료조건
        // 시간복잡도는 !
        // https://minusi.tistory.com/entry/%EC%88%9C%EC%97%B4-%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98-Permutation-Algorithm
        for (int i = start; i <= end; i++) {
            swap(nums, start, i);
            printPermutationRecursive(nums, start + 1, end);
            swap(nums, start, i);
        }
    }

    private void swap(int[] nums, int index1, int index2) {
        int temp = nums[index1];
        nums[index1] = nums[index2];
        nums[index2] = temp;
    }
}
