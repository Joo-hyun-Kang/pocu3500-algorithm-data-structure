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
            }
        }

        for (int i = 0; i < altitudes.length; i++) {
            if (altitudes[i] == max) {
                index = i;
            }
        }

        return index;
    }

    public static ArrayList<Integer> findAltitudeTimes(final int[] altitudes, final int targetAltitude) {

        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 0; i < altitudes.length; i++) {
            if (altitudes[i] == targetAltitude) {
                list.add(i);
            }
        }

        return list;
    }
}