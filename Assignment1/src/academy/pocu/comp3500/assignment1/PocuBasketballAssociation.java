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
        outPlayers[index].setShootingPercentage((int)(100 * ((double)goalSuccess / goalAttempt)));
    }

    public static Player findPlayerPointsPerGame(final Player[] players, int targetPoints) {
        return null;
    }

    public static Player findPlayerShootingPercentage(final Player[] players, int targetShootingPercentage) {
        return null;
    }

    public static long find3ManDreamTeam(final Player[] players, final Player[] outPlayers, final Player[] scratch) {
        return -1;
    }

    public static long findDreamTeam(final Player[] players, int k, final Player[] outPlayers, final Player[] scratch) {
        return -1;
    }

    public static int findDreamTeamSize(final Player[] players, final Player[] scratch) {
        return -1;
    }
}
