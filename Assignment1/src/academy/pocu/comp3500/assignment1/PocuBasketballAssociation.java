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

        double shootingPercent = (double) goalSuccess / goalAttempt * 100;

        outPlayers[index].setShootingPercentage((int) shootingPercent);
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
        //팀에 속한 패스 수 전체 * 어시스트 최소 값
        /*
        new Player("Player 2", 5, 12, 14, 50),
        new Player("Player 6", 15, 2, 5, 40),
        new Player("Player 5", 11, 1, 11, 54),
        new Player("Player 4", 10, 3, 51, 88),
        new Player("Player 7", 16, 8, 5, 77),
        new Player("Player 1", 1, 15, 2, 22),
        new Player("Player 3", 7, 5, 8, 66)
         */

        return -1;
    }

    public static long findDreamTeam(final Player[] players, int k, final Player[] outPlayers, final Player[] scratch) {
        return -1;
    }

    public static int findDreamTeamSize(final Player[] players, final Player[] scratch) {
        return -1;
    }
}
