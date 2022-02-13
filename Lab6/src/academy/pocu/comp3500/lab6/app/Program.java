package academy.pocu.comp3500.lab6.app;

import academy.pocu.comp3500.lab6.League;
import academy.pocu.comp3500.lab6.leagueofpocu.Player;

public class Program {

    public static void main(String[] args) {
        {
            League emptyLeague = new League();

            Player[] emptyLeaguePlayers = emptyLeague.getTop(10);

            assert (emptyLeaguePlayers.length == 0);

            Player player1 = new Player(1, "player1", 4);
            Player player2 = new Player(2, "player2", 6);
            Player player3 = new Player(3, "player3", 7);
            Player player4 = new Player(4, "player4", 9);
            Player player5 = new Player(5, "player5", 11);
            Player player6 = new Player(6, "player6", 12);

            League league = new League(new Player[]{player6, player4, player1, player2, player5, player3});

            // findMatchOrNull()
            Player match = league.findMatchOrNull(player3);
            assert (match.getId() == player2.getId());

            match = league.findMatchOrNull(player4);
            assert (match.getId() == player5.getId());

            match = league.findMatchOrNull(player6);
            assert (match.getId() == player5.getId());

            // getTop(), getBottom()
            Player[] topPlayers = league.getTop(3);

            assert (topPlayers[0].getId() == player6.getId());
            assert (topPlayers[1].getId() == player5.getId());
            assert (topPlayers[2].getId() == player4.getId());

            Player[] bottomPlayers = league.getBottom(5000);

            assert (bottomPlayers[0].getId() == player1.getId());
            assert (bottomPlayers[1].getId() == player2.getId());
            assert (bottomPlayers[2].getId() == player3.getId());

            // join()
            boolean joinSuccess = league.join(new Player(7, "player7", 10));
            assert (joinSuccess);

            joinSuccess = league.join(new Player(1, "player1", 4));
            assert (!joinSuccess);

            // leave()
            boolean leaveSuccess = league.leave(new Player(5, "player5", 11));
            assert (leaveSuccess);

            leaveSuccess = league.leave(new Player(5, "player5", 11));
            assert (!leaveSuccess);
        }


        {
            Player player1 = new Player(1, "player1", 12);
            Player player2 = new Player(2, "player2", 15);

            League league = new League(new Player[]{player1, player2});

            Player newPlayer = new Player(3, "player3", 13);

            boolean success = league.join(newPlayer); // true
            assert(success = league.join(newPlayer) == false); // false
            assert(success = league.join(player2) == false); // false
        }



        {
            League league = new League();

            Player player1 = new Player(1, "player1", 4);
            Player player2 = new Player(2, "player2", 6);
            Player player3 = new Player(3, "player3", 7);
            Player player4 = new Player(4, "player4", 9);
            Player player5 = new Player(5, "player5", 11);
            Player player6 = new Player(6, "player6", 12);

            boolean sucess = league.join(player6);
            assert(sucess);

            sucess = league.join(player4);
            assert(sucess);

            sucess = league.join(player1);
            assert(sucess);

            sucess = league.join(player2);
            assert(sucess);

            sucess = league.join(player5);
            assert(sucess);

            sucess = league.join(player3);
            assert(sucess);
        }

    }
}
