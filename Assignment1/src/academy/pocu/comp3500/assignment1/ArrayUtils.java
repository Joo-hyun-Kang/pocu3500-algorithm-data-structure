package academy.pocu.comp3500.assignment1;

import academy.pocu.comp3500.assignment1.pba.GameStat;
import academy.pocu.comp3500.assignment1.pba.Player;

public final class ArrayUtils {
    private ArrayUtils() {
    }

    public static int binarySearch(final Player[] players, int target) {
        return binarySearchRecursive(players, 0, players.length - 1, target);
    }

    private static int binarySearchRecursive(final Player[] players, int left, int right, int target) {
        if (left > right) {
            if (left > players.length - 1) {
                left = players.length - 1;
            }

            if (right < 0) {
                right = 0;
            }

            int leftApproximation = players[left].getPointsPerGame() - target;

            int rightApproximation = target - players[right].getPointsPerGame();

            return leftApproximation > rightApproximation ? right : left;
        }

        int middle = (left + right) / 2;
        int result = -1;

        if (target < players[middle].getPointsPerGame()) {
            result = binarySearchRecursive(players, left, middle - 1, target);
        } else if (target > players[middle].getPointsPerGame()) {
            result = binarySearchRecursive(players, middle + 1, right, target);
        } else {
            result = middle;
        }

        /*
        if (result == -1) {
            int leftApproximation = target - players[left].getPointsPerGame();
            int rightApproximation = players[right].getPointsPerGame() - target;

            result = leftApproximation > rightApproximation ? right : left;
        }
        */

        return result;
    }

    public static void gameStateQuickSort(final GameStat[] gameStats) {
        gameStateQuickSortRecursive(gameStats, 0, gameStats.length - 1);
    }

    private static void gameStateQuickSortRecursive(final GameStat[] gameStats, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivotPos = gameStatePartition(gameStats, left, right);

        gameStateQuickSortRecursive(gameStats, left, pivotPos - 1);
        gameStateQuickSortRecursive(gameStats, pivotPos + 1, right);
    }

    private static int gameStatePartition(final GameStat[] gameStats, int left, int right) {
        String pivot = gameStats[right].getPlayerName();

        int i = left;

        for (int j = left; j < right; ++j) {
            if (gameStats[j].getPlayerName().compareTo(pivot) < 0) {
                gameStateSwap(gameStats, i, j);
                i++;
            }
        }

        int pivotPos = i;
        gameStateSwap(gameStats, pivotPos, right);

        return pivotPos;
    }

    private static void gameStateSwap(final GameStat[] gameStats, int dst, int src) {
        GameStat temp = gameStats[dst];
        gameStats[dst] = gameStats[src];
        gameStats[src] = temp;
    }
}
