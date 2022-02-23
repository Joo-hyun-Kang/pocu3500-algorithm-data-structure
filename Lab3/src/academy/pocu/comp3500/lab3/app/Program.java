package academy.pocu.comp3500.lab3.app;

import academy.pocu.comp3500.lab3.MissionControl;

import java.util.ArrayList;

public class Program {

    public static void main(String[] args) {
        {
            final int[] altitudes = new int[] { 5, 7, 10, 10, 10, 11, 1 };

            final int maxAltitudeTime = MissionControl.findMaxAltitudeTime(altitudes);

            assert (maxAltitudeTime == 5);
        }

        {
            final int[] altitudes = new int[] { 5, 7, 10, 1};

            ArrayList<Integer> bounds = MissionControl.findAltitudeTimes(altitudes, 10);

            assert (bounds.size() == 1);

            assert (bounds.get(0) == 2);

            bounds = MissionControl.findAltitudeTimes(altitudes, 12);

            assert (bounds.size() == 0);
        }

        sejinTest();
    }

    public static void sejinTest() {
        {
            final int[] altitudes = new int[] { 1, 2, 3, 4, 5, 6, 7, 4, 3, 2 };

            final int maxAltitudeTime = MissionControl.findMaxAltitudeTime(altitudes);
            assert (maxAltitudeTime == 6);
        }

        {
            final int[] altitudes = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
            final int maxAltitudeTime = MissionControl.findMaxAltitudeTime(altitudes);
            assert (maxAltitudeTime == 9);
        }

        {
            final int[] altitudes = new int[] {10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };

            final int maxAltitudeTime = MissionControl.findMaxAltitudeTime(altitudes);

            assert (maxAltitudeTime == 0);
        }

        {
            final int[] altitudes = new int[] {4, 5, 4, 3, 2 };

            final int maxAltitudeTime = MissionControl.findMaxAltitudeTime(altitudes);
            assert (maxAltitudeTime == 1);
        }

        {
            final int[] altitudes = new int[] {1, 2, 3, 4, 3, 0 };

            final int maxAltitudeTime = MissionControl.findMaxAltitudeTime(altitudes);
            assert (maxAltitudeTime == 3);
        }

        {
            final int[] altitudes = new int[] { 1 };

            final int maxAltitudeTime = MissionControl.findMaxAltitudeTime(altitudes);
            assert (maxAltitudeTime == 0);
        }

        {
            final int[] altitudes = new int[] { 1, 2, 3, 4, 5, 6, 7, 4, 3, 2 };

            ArrayList<Integer> bounds = MissionControl.findAltitudeTimes(altitudes, 2);

            assert (bounds.size() == 2);

            assert (bounds.get(0) == 1);
            assert (bounds.get(1) == 9);

            bounds = MissionControl.findAltitudeTimes(altitudes, 5);

            assert (bounds.size() == 1);
            assert (bounds.get(0) == 4);
        }

        {
            final int[] altitudes = new int[] { 1 };
            ArrayList<Integer> bounds = MissionControl.findAltitudeTimes(altitudes, 0);
            assert (bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 2);
            assert (bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 1);
            assert (bounds.size() == 1);
            assert (bounds.get(0) == 0);
        }

        // 상승
        {
            final int[] altitudes = new int[] { 2, 4, 6, 8, 10 };
            ArrayList<Integer> bounds = MissionControl.findAltitudeTimes(altitudes, 1);
            assert (bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 3);
            assert (bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 5);
            assert (bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 7);
            assert (bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 9);
            assert (bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 11);
            assert (bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 2);
            assert (bounds.size() == 1);
            assert (bounds.get(0) == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 4);
            assert (bounds.size() == 1);
            assert (bounds.get(0) == 1);

            bounds = MissionControl.findAltitudeTimes(altitudes, 6);
            assert (bounds.size() == 1);
            assert (bounds.get(0) == 2);

            bounds = MissionControl.findAltitudeTimes(altitudes, 8);
            assert (bounds.size() == 1);
            assert (bounds.get(0) == 3);

            bounds = MissionControl.findAltitudeTimes(altitudes, 10);
            assert (bounds.size() == 1);
            assert (bounds.get(0) == 4);
        }

        // 하강
        {
            final int[] altitudes = new int[] { 10, 8, 6, 4, 2 };
            ArrayList<Integer> bounds = MissionControl.findAltitudeTimes(altitudes, 1);
            assert (bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 3);
            assert (bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 5);
            assert (bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 7);
            assert (bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 9);
            assert (bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 11);
            assert (bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 10);
            assert (bounds.size() == 1);
            assert (bounds.get(0) == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 8);
            assert (bounds.size() == 1);
            assert (bounds.get(0) == 1);

            bounds = MissionControl.findAltitudeTimes(altitudes, 6);
            assert (bounds.size() == 1);
            assert (bounds.get(0) == 2);

            bounds = MissionControl.findAltitudeTimes(altitudes, 4);
            assert (bounds.size() == 1);
            assert (bounds.get(0) == 3);

            bounds = MissionControl.findAltitudeTimes(altitudes, 2);
            assert (bounds.size() == 1);
            assert (bounds.get(0) == 4);
        }

        // 상승-하강
        {
            final int[] altitudes = new int[] { 2, 4, 6, 4, 2 };
            ArrayList<Integer> bounds = MissionControl.findAltitudeTimes(altitudes, 1);
            assert (bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 3);
            assert (bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 5);
            assert (bounds.size() == 0);


            bounds = MissionControl.findAltitudeTimes(altitudes, 7);
            assert (bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 2);
            assert (bounds.size() == 2);
            assert (bounds.get(0) == 0);
            assert (bounds.get(1) == 4);

            bounds = MissionControl.findAltitudeTimes(altitudes, 4);
            assert (bounds.size() == 2);
            assert (bounds.get(0) == 1);
            assert (bounds.get(1) == 3);

            bounds = MissionControl.findAltitudeTimes(altitudes, 6);
            assert (bounds.size() == 1);
            assert (bounds.get(0) == 2);
        }

        {
            final int[] altitudes = new int[] { 6, 8, 6, 4, 2 };
            ArrayList<Integer> bounds = MissionControl.findAltitudeTimes(altitudes, 1);
            assert (bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 3);
            assert (bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 5);
            assert (bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 7);
            assert (bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 9);
            assert (bounds.size() == 0);

            bounds = MissionControl.findAltitudeTimes(altitudes, 2);
            assert (bounds.size() == 1);
            assert (bounds.get(0) == 4);

            bounds = MissionControl.findAltitudeTimes(altitudes, 4);
            assert (bounds.size() == 1);
            assert (bounds.get(0) == 3);

            bounds = MissionControl.findAltitudeTimes(altitudes, 6);
            assert (bounds.size() == 2);
            assert (bounds.get(0) == 0);
            assert (bounds.get(1) == 2);

            bounds = MissionControl.findAltitudeTimes(altitudes, 8);
            assert (bounds.size() == 1);
            assert (bounds.get(0) == 1);
        }
    }

}
