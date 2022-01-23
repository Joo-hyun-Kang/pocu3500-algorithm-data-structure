package academy.pocu.comp3500.lab3;

import java.util.ArrayList;

public final class MissionControl {
    private MissionControl() {
    }

    public static int findMaxAltitudeTime(final int[] altitudes) {
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

    public static ArrayList<Integer> findAltitudeTimes(final int[] altitudes, final int targetAltitude) {

        ArrayList<Integer> list = new ArrayList<>();

        //  final int[] altitudes = new int[] { 1, 2, 3, 4, 5, 6, 7, 4, 3, 2 };

        for (int i = 0; i < altitudes.length; i++) {
            if (altitudes[i] == targetAltitude) {
                list.add(i);
            }
        }

        return list;
    }
}