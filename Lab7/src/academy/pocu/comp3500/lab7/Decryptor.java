package academy.pocu.comp3500.lab7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Decryptor {
    // 트라이를 이용할 때 해시맵 or 배열?
    // 해시맵은 기본 버킷 16개 배열 알파벳은 26개
    // 해시맵을 사용하나 배열을 사용하나 별 차이 없다
    // 더 간편한 해시맵 선택

    HashMap<String, ArrayList<String>> dictionary = new HashMap<>();

    public Decryptor(final String[] codeWords) {
        // 기수정렬 사용
        /*

        // 접근방법 1 : 트라이로 후보 문자열을 만들고 매개변수로 들어오는 word의 모든 조합에 대해 비교
        // 문제점 : word를 모든 조합으로 비교하는데 N!, 트라이를 탐색하는데 N
        // 트라이로 해결할려면 정렬하고 비교하는데 O(1)이 나와야 한다


        // 접근방법 2 : 순서가 중요하지 않으므로 글자 개수와 알파벳의 총 수 길이 비교
        // 트라이로 구현하면 순서가 중요하게 되는데 그러면
        // word의 모든 경우의 수를 계산하거나 아니면 계산하는 도중에 트라이를 탐색해야 함
        // 그러나 글자의 개수와 알파벳 총 수의 길이가 같기만 하면 같은 후보글자라고 생각할 수 있다

        // 현재 문제점 : 시간복잡도 O(n^2), 들어오는 문자열 당 순서가 상관 없기 때문에 암호문 하나의 a-z까지의 문자의 개수와 각 a-z의 개수를 비교함
        // 해결책 : int[]를 해시코드로 비교 or 기수정렬 이용해서 정렬된 문자열끼리 비교

        // 접근방법 3 : 가장 빠른 기수정렬을 사용해서 순서를 다 동일하게 만들고 비교한다. 접근 방법 2에서 조금 더 발전해서 순서가 상관 없으니 정렬한다
        //             후보 문자열 정렬 n^2, 매개변수 정렬 n, 해시로 비교 O(1)
        // https://www.zerocho.com/category/Algorithm/post/58007c338475ed00152d6c4c
        // https://yabmoons.tistory.com/248
        */



        //String은 기수 정렬된 배열
        //찾는 건 들어온 값 기수 정렬 후에
        //해시 함수로 찾고 ArrayList반환


        for (String code : codeWords) {
            String lowercaseCode = convertLowercase(code);

            String sortedCode = radixSort(lowercaseCode);

            if (!dictionary.containsKey(sortedCode)) {
                dictionary.put(sortedCode, new ArrayList<>());
            }

            dictionary.get(sortedCode).add(lowercaseCode);
        }








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

        String lowercaseWord = convertLowercase(word);

        String sortedWord = radixSort(lowercaseWord);

        if (dictionary.containsKey(sortedWord)) {
            return dictionary.get(sortedWord).toArray(new String[]{});
        }

        return new String[]{};

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

    private String radixSort(String word) {
        char[] wordChar = word.toCharArray();

        Stack<Character>[] stacks = new Stack[10];
        for (int i = 0; i < stacks.length; i++) {
            stacks[i] = new Stack<>();
        }

        int pos = 1000;
        for (int i = 1; i < pos; i = i * 10) {
            for (int j = wordChar.length - 1; j >= 0; j--) {
                int index = 0;

                if (wordChar[j] > i) {
                    index = wordChar[j] / i % 10;
                }

                stacks[index].push(wordChar[j]);
            }

            int index = 0;
            for (int k = 0; k < stacks.length; k++) {
                while (!stacks[k].empty()) {
                    wordChar[index] = stacks[k].pop();
                    index++;
                }
            }
        }

        return new String(wordChar);
    }

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

    private void swap(int[] nums, int index1, int index2) {
        int temp = nums[index1];
        nums[index1] = nums[index2];
        nums[index2] = temp;
    }

    //순열 재귀 이용 시간 복잡도 N! 사전 찾기 속도가 느려서 테스트를 통과하지 못한다
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


}
