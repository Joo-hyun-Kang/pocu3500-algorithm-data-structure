package academy.pocu.comp3500.assignment1;

import academy.pocu.comp3500.assignment1.pba.Player;
import academy.pocu.comp3500.assignment1.pba.GameStat;

public final class PocuBasketballAssociation {
    private PocuBasketballAssociation() {
    }

    public static void processGameStats(final GameStat[] gameStats, final Player[] outPlayers) {
        if (gameStats == null || outPlayers == null) {
            return;
        }

        ArrayUtils.gameStateQuickSort(gameStats);

        int index = 0;
        int gameCount = 0;

        outPlayers[index].setName(gameStats[0].getPlayerName());

        int pointByState = 0;
        int assistByState = 0;
        int passByState = 0;
        int goalSuccessByState = 0;
        int goalAttemptByState = 0;

        for (int j = 0; j < gameStats.length; j++) {
            if (!outPlayers[index].getName().equals(gameStats[j].getPlayerName())) {
                setPlayerState(outPlayers, index, pointByState, assistByState, passByState, goalSuccessByState, goalAttemptByState, gameCount);

                pointByState = 0;
                assistByState = 0;
                passByState = 0;
                goalSuccessByState = 0;
                goalAttemptByState = 0;

                index++;
                gameCount = 0;
            }

            gameCount++;

            outPlayers[index].setName(gameStats[j].getPlayerName());

            pointByState += gameStats[j].getPoints();
            assistByState += gameStats[j].getAssists();
            passByState += gameStats[j].getNumPasses();
            goalSuccessByState += gameStats[j].getGoals();
            goalAttemptByState += gameStats[j].getGoalAttempts();
        }

        setPlayerState(outPlayers, index, pointByState, assistByState, passByState, goalSuccessByState, goalAttemptByState, gameCount);
    }

    private static void setPlayerState(final Player[] outPlayers, int index, int point, int assist, int pass, int goalSuccess, int goalAttempt, int gameCount) {
        outPlayers[index].setPointsPerGame(point / gameCount);

        outPlayers[index].setAssistsPerGame(assist / gameCount);

        outPlayers[index].setPassesPerGame(pass / gameCount);

        outPlayers[index].setShootingPercentage((int) (goalSuccess / (double) goalAttempt * 100));
    }

    public static Player findPlayerPointsPerGame(final Player[] players, int targetPoints) {
        if (players == null) {
            return null;
        }

        return players[ArrayUtils.binarySearchPoint(players, targetPoints)];
    }

    public static Player findPlayerShootingPercentage(final Player[] players, int targetShootingPercentage) {
        if (players == null) {
            return null;
        }

        return players[ArrayUtils.binarySearchShooting(players, targetShootingPercentage)];
    }

    public static long find3ManDreamTeam(final Player[] players, final Player[] outPlayers, final Player[] scratch) {
        ArrayUtils.playerPassQuickSort(players);

        int back = 0;
        int stackCount = 0;

        int teamWork = 0;

        for (int i = 0; i < players.length; i++) {
            scratch[back] = players[i];
            back++;
            stackCount++;

            if (stackCount >= 3) {
                int passTotal = scratch[0].getPassesPerGame() + scratch[1].getPassesPerGame() + scratch[2].getPassesPerGame();

                int assistMin = scratch[0].getAssistsPerGame() < scratch[1].getAssistsPerGame() ? scratch[0].getAssistsPerGame() : scratch[1].getAssistsPerGame();

                assistMin = assistMin < scratch[2].getAssistsPerGame() ? assistMin : scratch[2].getAssistsPerGame();

                int temp = passTotal * assistMin;

                if (temp > teamWork) {
                    teamWork = temp;
                    outPlayers[0] = scratch[0];
                    outPlayers[1] = scratch[1];
                    outPlayers[2] = scratch[2];
                }

                back--;
                stackCount--;
            }
        }

        return teamWork;
    }

    public static long findDreamTeam(final Player[] players, int k, final Player[] outPlayers, final Player[] scratch) {
        return -1;
    }

    public static int findDreamTeamSize(final Player[] players, final Player[] scratch) {
        return -1;
    }
}
