package academy.pocu.comp3500.assignment3.app;

import academy.pocu.comp3500.assignment3.Player;
import academy.pocu.comp3500.assignment3.chess.Move;
import academy.pocu.comp3500.assignment3.chess.PlayerBase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Program {
    public static void main(String[] args) {
        {
            // TODO
            // pawn 2 move
            // 시간에 맞게
        }
        {
            // getNextMove returns a valid move when there is only one piece in board
            char[] symbols = {'k', 'n', 'b', 'r', 'q', 'p'};
            Position[] positions = {new Position(3, 5), new Position(0, 7), new Position(7, 7)};
            for (char s : symbols) {
                for (Position p : positions) {
                    char[][] board = {
                            {'R', 'N', 'B', 'K', 'Q', 'B', 'N', 'R'},
                            {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
                            {0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0},
                            {0, 0, 0, 0, 0, 0, 0, 0},
                    };
                    board[p.y][p.x] = s;
                    Player player = new Player(true, 10000);

                    Move move = player.getNextMove(board);

                    if (!Game.isMoveValid(board, player, move)) {
                        player.getNextMove(board);
                    }
                    assert Game.isMoveValid(board, player, move);
                }
            }
        }

        {
            // player captures piece when possible
            char[][] board = {
                    {0, 0, 0, 'K', 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 'Q', 0},
                    {0, 0, 0, 0, 0, 0, 'k', 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
            };
            Player player = new Player(true, 10000);

            Move move = player.getNextMove(board);

            assert Game.isMoveValid(board, player, move);
            assert move.fromX == 6;
            assert move.fromY == 5;
            assert move.toX == 6;
            assert move.toY == 4;
        }

        {
            // player captures piece when possible
            char[][] board = {
                    {0, 0, 0, 'K', 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 'k', 'Q', 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
            };
            Player player = new Player(true, 10000);

            Move move = player.getNextMove(board);

            assert Game.isMoveValid(board, player, move);
            assert move.fromX == 5;
            assert move.fromY == 4;
            assert move.toX == 6;
            assert move.toY == 4;
        }

        {
            // player captures piece when there are multiple pieces
            char[][] board = {
                    {0, 0, 0, 'K', 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 'Q', 0},
                    {'k', 0, 0, 0, 0, 'b', 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
            };
            Player player = new Player(true, 10000);

            Move move = player.getNextMove(board);

            assert Game.isMoveValid(board, player, move);
            assert move.fromX == 5;
            assert move.fromY == 5;
            assert move.toX == 6;
            assert move.toY == 4;
        }
        {
            // player dodges
            char[][] board = {
                    {0, 0, 0, 0, 'K', 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 'R', 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 'R', 0},
                    {'k', 0, 0, 0, 0, 'Q', 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
            };
            Player player = new Player(true, 10000);

            Move move = player.getNextMove(board);

            assert Game.isMoveValid(board, player, move);
            assert move.fromX == 0;
            assert move.fromY == 6;
            assert move.toX == 0;
            assert move.toY == 7;
        }

        {
            // player dodges
            char[][] board = {
                    {0, 0, 0, 0, 'K', 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 'R', 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {'k', 0, 0, 0, 0, 'Q', 0, 0},
                    {0, 0, 0, 0, 0, 0, 'R', 0},
            };
            Player player = new Player(true, 10000);

            Move move = player.getNextMove(board);

            assert Game.isMoveValid(board, player, move);
            assert move.fromX == 0;
            assert move.fromY == 6;
            assert move.toX == 0;
            assert move.toY == 5;
        }

        {
            // pawn captures
            char[][] board = {
                    {0, 0, 0, 0, 'K', 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 'Q', 0},
                    {0, 0, 0, 0, 0, 'p', 0, 0},
                    {'k', 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
            };
            Player player = new Player(true, 10000);

            Move move = player.getNextMove(board);

            assert Game.isMoveValid(board, player, move);
            assert move.fromX == 5;
            assert move.fromY == 5;
            assert move.toX == 6;
            assert move.toY == 4;
        }

    }


    static class Position {
        public int x;
        public int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    /*
    public static void main(String[] args) {
        final boolean IS_AUTO_PLAY = true; // true 라면 주기적으로 자동으로 다음 턴이 진행됨; false 라면 Enter/Return 키를 누를 때 진행됨
        final boolean IS_WHITE_KEYBOARD_PLAYER = false; // true 라면 하얀색 플레이어의 수를 콘솔에 입력해야 함
        final boolean IS_BLACK_KEYBOARD_PLAYER = false; // true 라면 검은색 플레이어의 수를 콘솔에 입력해야 함

        final int MAX_MOVE_TIME_MILLISECONDS = Integer.MAX_VALUE; // Player 가 턴마다 수를 결정하는 데에 주어진 시간
        final long AUTO_PLAY_TURN_DURATION_IN_MILLISECONDS = 1000; // Autoplay 중 턴마다 일시중지 되는 기간

        PlayerBase whitePlayer;
        PlayerBase blackPlayer;

        if (IS_WHITE_KEYBOARD_PLAYER) {
            whitePlayer = new KeyboardPlayer(true);
        } else {
            whitePlayer = new Player(true, MAX_MOVE_TIME_MILLISECONDS);
        }
        if (IS_BLACK_KEYBOARD_PLAYER) {
            blackPlayer = new KeyboardPlayer(false);
        } else {
            blackPlayer = new Player(false, MAX_MOVE_TIME_MILLISECONDS);
        }

        Game game = new Game(whitePlayer, blackPlayer, MAX_MOVE_TIME_MILLISECONDS);

        System.out.println("Let the game begin!");
        System.out.println(game.toString());

        for (int i = 0; i < 50; ++i) {
            if (game.isNextTurnWhite() && IS_BLACK_KEYBOARD_PLAYER
                    || !game.isNextTurnWhite() && IS_WHITE_KEYBOARD_PLAYER) {
                if (IS_AUTO_PLAY) {
                    pause(AUTO_PLAY_TURN_DURATION_IN_MILLISECONDS);
                } else {
                    continueOnEnter();
                }
            }

            game.nextTurn();

            clearConsole();
            System.out.println(game.toString());

            if (game.isGameOver()) {
                break;
            }
        }
    }

    public static void pause(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public static void continueOnEnter() {
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Press enter to continue:");
            reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clearConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     */
}