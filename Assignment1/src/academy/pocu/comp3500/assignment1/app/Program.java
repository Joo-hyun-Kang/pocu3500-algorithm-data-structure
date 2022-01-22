package academy.pocu.comp3500.assignment1.app;

import academy.pocu.comp3500.assignment1.PocuBasketballAssociation;
import academy.pocu.comp3500.assignment1.pba.Player;
import academy.pocu.comp3500.assignment1.pba.GameStat;

public class Program {

    public static void main(String[] args) {
        {
            GameStat[] gameStats = new GameStat[] {
                    new GameStat("Player 1", 1, 13, 5, 6, 10, 1)
            };

            Player[] players = new Player[] {
                    new Player()
            };

            PocuBasketballAssociation.processGameStats(gameStats, players);

            Player player = getPlayerOrNull(players, "Player 1");
            assert (player != null);
            assert (player.getPointsPerGame() == 13);
            assert (player.getAssistsPerGame() == 10);
            assert (player.getPassesPerGame() == 1);
            assert (player.getShootingPercentage() == 83);
        }

        {
            GameStat[] gameStats = new GameStat[] {
                    new GameStat("Player 1", 1, 13, 5, 6, 10, 1),
                    new GameStat("Player 1", 3, 12, 6, 9, 8, 5),
                    new GameStat("Player 2", 2, 5, 2, 5, 0, 10),
                    new GameStat("Player 3", 1, 31, 15, 40, 5, 3),
                    new GameStat("Player 2", 1, 3, 1, 3, 12, 2),
                    new GameStat("Player 1", 2, 11, 6, 11, 9, 3),
                    new GameStat("Player 2", 3, 9, 3, 3, 1, 11),
                    new GameStat("Player 3", 4, 32, 15, 51, 4, 2),
                    new GameStat("Player 4", 3, 44, 24, 50, 1, 1),
                    new GameStat("Player 1", 4, 11, 5, 14, 8, 3),
                    new GameStat("Player 2", 4, 5, 1, 3, 1, 9),
            };

            Player[] players = new Player[] {
                    new Player(),
                    new Player(),
                    new Player(),
                    new Player()
            };

            PocuBasketballAssociation.processGameStats(gameStats, players);

            Player player = getPlayerOrNull(players, "Player 1");
            assert (player != null);
            assert (player.getPointsPerGame() == 11);
            assert (player.getAssistsPerGame() == 8);
            assert (player.getPassesPerGame() == 3);
            assert (player.getShootingPercentage() == 55);

            player = getPlayerOrNull(players, "Player 2");
            assert (player != null);
            assert (player.getPointsPerGame() == 5);
            assert (player.getAssistsPerGame() == 3);
            assert (player.getPassesPerGame() == 8);
            assert (player.getShootingPercentage() == 50);

            player = getPlayerOrNull(players, "Player 3");
            assert (player != null);
            assert (player.getPointsPerGame() == 31);
            assert (player.getAssistsPerGame() == 4);
            assert (player.getPassesPerGame() == 2);
            assert (player.getShootingPercentage() == 32);

            player = getPlayerOrNull(players, "Player 4");
            assert (player != null);
            assert (player.getPointsPerGame() == 44);
            assert (player.getAssistsPerGame() == 1);
            assert (player.getPassesPerGame() == 1);
            assert (player.getShootingPercentage() == 48);
        }
    }

    private static Player getPlayerOrNull(final Player[] players, final String id) {
        for (Player player : players) {
            if (player.getName().equals(id)) {
                return player;
            }
        }

        return null;
    }
}
