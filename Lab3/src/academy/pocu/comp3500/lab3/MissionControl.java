package academy.pocu.comp3500.lab3;

import java.util.ArrayList;

public final class MissionControl {
    private MissionControl() {
    }

    public static int findMaxAltitudeTime(final int[] altitudes) {
        // 예외 처리

        // time complex optimization
        return findMaxAltitudeTimeRecursive(altitudes, 0, altitudes.length - 1);

        // non - time complex optimization
        /*
        {
            int max = 0;
            int index = -1;

            for (int i = 0; i < altitudes.length; i++) {
                if (altitudes[i] > max) {
                    max = altitudes[i];
                    index = i;
                }
            }
            return index;
        }

         */

    }

    private static int findMaxAltitudeTimeRecursive(final int[] altittudes, int start, int end) {
        // 5 7 14 4 3 2 1
        // 5 6 12 4 2 1
        // 핵심가정은 한 쪽에 정렬되어 있으면 다른쪽은 정렬 안 됌
        // 반대로 한 쪽이 정렬 안되어 있으면 다른쪽은 정렬 됌
        // 정렬된 곳은 그냥 넘어가면 됀다!

        if (start == end) {
            return start;
        }

        if (start + 1 == end) {
            return altittudes[start] >= altittudes[end] ? start : end;
        }

        int mid = (start + end) / 2;

        // 동일한 값이 연속하여 있을 때 최대값을 구하기 위해서 두번쨰 항은 크다로 수정
        // final int[] altitudes = new int[] { 5, 7, 10, 10, 10, 11, 1 };
        if (altittudes[mid] >= altittudes[mid - 1] && altittudes[mid] > altittudes[mid + 1]) {
            return mid;
        }

        //still inorder
        if (altittudes[mid] >= altittudes[mid - 1] && altittudes[mid] <= altittudes[mid + 1]) {
            return findMaxAltitudeTimeRecursive(altittudes, mid + 1, end);
        } else {
            return findMaxAltitudeTimeRecursive(altittudes, start, mid - 1);
        }
    }



    public static ArrayList<Integer> findAltitudeTimes(final int[] altitudes, final int targetAltitude) {
        ArrayList<Integer> result = new ArrayList<>();

        // 최대값을 기준으로 쪼개야 하는 이유
        // inal int[] altitudes = new int[] { 6, 8, 6, 4, 2 };
        int maxAltituteIndex = findMaxAltitudeTime(altitudes);

        if (targetAltitude == altitudes[maxAltituteIndex]) {
            result.add(maxAltituteIndex);
        } else {
            findAltitudeTimesRecursive(altitudes, targetAltitude, 0, maxAltituteIndex - 1, result);
            findAltitudeTimesRecursive(altitudes, targetAltitude, maxAltituteIndex + 1, altitudes.length - 1, result);
        }
        return result;

        // non - time complex optimization
        /*
        {
            ArrayList<Integer> list = new ArrayList<>();

            for (int i = 0; i < altitudes.length; i++) {
                if (altitudes[i] == targetAltitude) {
                    list.add(i);
                }
            }

            return list;
        }
         */
    }

    private static int findAltitudeTimesRecursive(final int[] altitudes, final int targetAltitude, int start, int end, ArrayList<Integer> result) {
        // 5 7 14 4 3 2 1
        // { 5, 6, 12, 4, 2, 1 };
        // 핵심가정은 한 쪽에 정렬되어 있으면 다른쪽은 정렬 안 됌
        // 반대로 한 쪽이 정렬 안되어 있으면 다른쪽은 정렬 됌
        // 정렬된 곳은 그냥 넘어가면 됀다!

        if (start > end) {
            return -1;
        }

        int mid = (start + end) / 2;

        if (altitudes[mid] == targetAltitude) {
            result.add(mid);
            return 1;
        }

        if (altitudes[start] <= altitudes[mid]) {
            if (targetAltitude <= altitudes[mid]) {
                findAltitudeTimesRecursive(altitudes, targetAltitude, start, mid - 1, result);
            }

            return findAltitudeTimesRecursive(altitudes, targetAltitude, mid + 1, end, result);
        } else {
            if (targetAltitude <= altitudes[mid]) {
                findAltitudeTimesRecursive(altitudes, targetAltitude, mid + 1, end, result);
            }

            return findAltitudeTimesRecursive(altitudes, targetAltitude, start, mid - 1, result);
        }
    }


}