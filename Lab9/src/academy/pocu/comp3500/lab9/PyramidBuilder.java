package academy.pocu.comp3500.lab9;

import java.util.Arrays;

public class PyramidBuilder {
    public static int findMaxHeight(final int[] widths, int statue) {
        Arrays.sort(widths);
        int sum = 0;
        int x = 0;

        //석상까지의 최적의 크기를 구하는 코드
        while (statue >= sum) {
            if (x >= widths.length) {
                return 0;
            }
            sum += widths[x];
            x++;
        }

        // 석상 아래의 돌 개수에서
        // 1씩 늘려가면서 다음 레벨을 빼줌
        // 다음 레벨에 돌이 부족하게 되면 그대로 h++해서 최종 결과 값에서 --해준다
        // 돌이 딱 맞게 되어도 h++되어서 최종 결과 값에 --해주어도 문제 없음
        int a = Math.max(x, 2);
        int h = 0;
        int remain = widths.length;
        while (remain >= 0) {
            remain -= a;
            a++;
            h++;
        }

        return h - 1;
    }
}