package academy.pocu.comp3500.lab9;

import academy.pocu.comp3500.lab9.data.VideoClip;

import java.util.Arrays;
import java.util.Comparator;

public class CodingMan {
    public static int findMinClipsCount(final VideoClip[] clips, int time) {
        Arrays.sort(clips, Comparator.comparing(VideoClip::getStartTime));

        int start = 0;
        int count = 0;
        int i = 0;
        while (start < time) {
            int maxEnd = Integer.MIN_VALUE;
            VideoClip maxEndClip = null;
            // 가장 끝에 것만 생각해서 그게 time보다 같거나 커지는 지
            // 조건이 있는데 이전 클립의 end가 다음 클립의 사이에 있어야 함
            while (i < clips.length && start >= clips[i].getStartTime()) {
                if (maxEnd < clips[i].getEndTime()) {
                    maxEnd = clips[i].getEndTime();
                    maxEndClip = clips[i];
                }
                i++;
            }

            if (maxEndClip == null) {
                return -1;
            }
            start = maxEndClip.getEndTime();
            count++;
        }
        return count;
    }
}