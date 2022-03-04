package academy.pocu.comp3500.lab7;

import java.util.ArrayList;
import java.util.HashMap;

public class Decryptor {
    // 트라이를 이용할 때 해시맵 or 배열?
    // 해시맵은 기본 버킷 16개 배열 알파벳은 26개
    // 해시맵을 사용하나 배열을 사용하나 별 차이 없다
    // 더 간편한 해시맵 선택
    
    // 트라이로 구현하면 순서가 중요하게 되는데 그러면
    // word의 모든 경우의 수를 계산하거나 아니면 계산하는 도중에 트라이를 탐색해야 함
    // 그러나 글자의 개수와 알파벳 총 수의 길이가 같기만 하면 같은 후보글자라고 생각할 수 있다
    ArrayList<Node> nodes = new ArrayList<>();

    public Decryptor(final String[] codeWords) {
        for (String code : codeWords) {
            String lowercaseCode = convertLowercase(code);

            Node node = new Node(lowercaseCode);

            for (int i = 0; i < lowercaseCode.length(); i++) {
                int[] temp = node.getAlphabetCount();
                int index = lowercaseCode.charAt(i) - 'a';
                temp[index]++;
                node.count++;
            }

            nodes.add(node);
        }
        // 기수정렬 사용
        /*
        HashMap<String, ArrayList<String>> test = new HashMap<>();
        test.put("dog", new ArrayList<>());
        if (test.containsKey("dog")) {
            test.get("dog").add("god");
        }


         */
        // 이전에 시도 했던 트라이 방법
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

        Node wordNode = new Node();

        for (int i = 0; i < word.length(); i++) {
            int[] temp = wordNode.getAlphabetCount();
            char ch = word.charAt(i);

            if (ch >= 'A' && ch <= 'Z') {
                ch ^= 32;
            }

            int index = ch - 'a';
            temp[index]++;

            wordNode.count++;
        }

        ArrayList<String> result = new ArrayList<>();
        for (Node node : nodes) {
            if (node.getCount() == wordNode.getCount()) {
                int[] nodeAlphabet = node.getAlphabetCount();
                int[] wordAlphabet = wordNode.getAlphabetCount();

                nodeAlphabet.equals(wordAlphabet);

                int i = 0;
                while (i < nodeAlphabet.length && nodeAlphabet[i] == wordAlphabet[i]) {
                    i++;
                }

                if (i == nodeAlphabet.length) {
                    result.add(node.getStr());
                }

                result.add(node.getStr());
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

    //N! word의 모든 조합을 trie와 비교하는 로직
    /*
    private void findCandidatesRecursive(String word, int start, int end, ArrayList<String> result) {
        //root는 트라이의 꼭지점으로 전역변수였는데 삭제함
        if (start == end) {
            Trie temp = root;
            for (int i = 0; i < word.length(); i++) {
                if (temp.getChild().containsKey(word.charAt(i)) == false) {
                    break;
                }

                temp = temp.getChild().get(word.charAt(i));

                if (i == word.length() - 1 && temp.isEndChar() == true && !result.contains(word)) {
                    result.add(word);
                }
            }
        }

        for (int i = start; i <= end; i++) {
            word = swap(word, start, i);
            findCandidatesRecursive(word, start + 1, end, result);
            word = swap(word, start, i);
        }
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
