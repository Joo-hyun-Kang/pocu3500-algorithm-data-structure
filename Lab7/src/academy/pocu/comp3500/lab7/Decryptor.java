package academy.pocu.comp3500.lab7;

import java.util.ArrayList;

public class Decryptor {
    String[] codeWords;

    public Decryptor(final String[] codeWords) {
        this.codeWords = codeWords;
    }

    public String[] findCandidates(final String word) {
        if (word == null) {
            return new String[]{};
        }


        String wordLowercase = convertLowercase(word);

        ArrayList<String> result = new ArrayList<>();

        //매개변수 word에서 캐릭터가 중복되어서 서로 섞는 경우에 같은 단어가 동시에 나올 수 있음
        //aaa를 섞으면 aaa가 총 6개 나온다 이걸 codeWord와 그냥 매칭하면 생기는 문제가
        //반환을 6개 하게 됌
        ArrayList<String> codeWords = new ArrayList<>();
        for (String code : this.codeWords) {
            String temp = convertLowercase(code);
            if (!codeWords.contains(temp)) {
                codeWords.add(temp);
            }
        }

        findCandidatesRecursive(wordLowercase, 0, word.length() - 1, result, codeWords);

        return result.toArray(new String[result.size()]);
    }

    //순열 재귀 이용 시간 복잡도 N! 사전 찾기

    public void findCandidatesRecursive(String word, int start, int end, ArrayList<String> result, ArrayList<String> codeWords) {
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

    private String convertLowercase(String str) {
        char[] arr = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {
            if (arr[i] >= 'A' && arr[i] <= 'Z') {
                arr[i] ^= 32;
            }
        }
        return new String(arr);
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

    private String swap(String word, int index1, int index2) {
        char[] arr = word.toCharArray();
        char temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;

        return new String(arr);
    }


}
