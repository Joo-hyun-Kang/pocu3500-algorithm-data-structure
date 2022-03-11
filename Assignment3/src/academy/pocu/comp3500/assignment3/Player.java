package academy.pocu.comp3500.assignment3;

import academy.pocu.comp3500.assignment3.chess.Move;
import academy.pocu.comp3500.assignment3.chess.PlayerBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Player extends PlayerBase {
    // Project layOut : 미니맥스 + 알파베타 가지치기를 활용해야 한다
    // 알파베타를 이용해서 코드를 마지막에 제출했으나 메모리 부족으로 다시 알파베타 사용하지 않은 걸로 롤백
    // 추가로 알파베타를 사용하지 않다가 알파베타로 바꾸려니까 굉장히 어려웠음

    //1. minMax
    //내가 둘 때는 상대가 최대 이득이 되는게 자식에서 반환되고 나는 그 중에서 내 최대 이득을 선택하여 호출자에게 반환
    //상대가 둘 때는 내가 최대 이득이 되는게 자식에서 반환되고 상대는 최대 이득이 되는 걸 호출자에게 반환

    //상대방 차례일 때 좋은 상황들(음수일수록 좋음 그래서 자식에서 최소값 반환)
    //       10  5 -10
    //나는 이제 10을 나에게 가장 좋은 10을 선택해서 반환한다
    //이해를 위한 중요한 개념은 높을수록 나에게 유리하고 낮을수록 상대에게 유리하다

    //2. 알파-베타 가치치기
    /*
        ==================================================================================
        function alphabeta(node, depth, α, β, Player)
        if  depth = 0 or node is a terminal node
            return the heuristic value of node
        if  Player = MaxPlayer
            for each child of node
        α := max(α, alphabeta(child, depth-1, α, β, not(Player) ))
                if β ≤ α
                    break                             (* Beta cut-off *)
                return α
        else
                for each child of node
        β := min(β, alphabeta(child, depth-1, α, β, not(Player) ))
                if β ≤ α
                    break                             (* Alpha cut-off *)
                return β
                (* Initial call *)
        alphabeta(origin, depth, -infinity, +infinity, MaxPlayer)
        ==================================================================================
    */
    // 규칙
    // min - max - min 일 때 2번째 max의 값을 뽑을 노드들 중에서 첫번째 노드를 제외하고 두번째 노드부터는 그 노드의 자식 값
    // 즉, 3번째 min에서 첫번째 노드보다 같거나 큰 값이 나오면 가지치면 된다. 반대로,
    // max - min - max 일 때 2번째 min의 값을 뽑을 노드들 중에서 첫번째 노드를 제외하고 두번째 노드부터는 그 노드의 자식 값
    // 즉, 3번째 max에서 첫번째 노드보다 같거나 작은 값이 나오면 가지치면 된다.

    // 증명
    // 증명은 간단하다. 가정을 사용하면 된다. 위에 두 경우에 대해서 트리를 만들고 2번째의 첫번째 노드가 1번째로 올라가는 걸 뽑인하고 가정하고
    // 예를 살펴보면 위에 규칙에 따라서 다른 경우는 살펴보지도 않아도 된다. 다른 때는 살펴봐야 하지만.
    // 이게 되는 근본적인 이유는 max, min 관계에서 노드 관계가 그렇게 규칙성 있음

    //3. 좋은 알고리듬
    //점수 계산 함수가 얼마나 뛰어난 지
    //얼마나 깊이 볼 수 있는 지 -> 알파베타 가지치기

    private final static int BOARD_SIZE = 8;

    private final int MAXIMUN_TURN = 2;

    private int turnCount = 0;

    private static int[][] pawnMoveOffset = {
            {0, 1},
            {1, 1},
            {-1, 1},
            {0, 2},
            {0, -1},
            {1, -1},
            {-1, -1},
            {0, -2}
    };

    private static int[][] knightMoveOffset = {
            {-2, -1},
            {-2, 1},
            {-1, -2},
            {-1, 2},
            {1, -2},
            {1, 2},
            {2, -1},
            {2, 1}
    };

    private static int[][] kingMoveOffsets = {
            {-1, 1},
            {-1, 0},
            {-1, -1},
            {0, 1},
            {0, -1},
            {1, 1},
            {1, 0},
            {1, -1}
    };

    public Player(boolean isWhite, int maxMoveTimeMilliseconds) {
        super(isWhite, maxMoveTimeMilliseconds);
    }

    @Override
    public Move getNextMove(char[][] board) {
        return getNextMove(board, null);
    }

    @Override
    public Move getNextMove(char[][] board, Move opponentMove) {

        NextMove optimalNextMove = getNextMoveRecursive(board, opponentMove);

        return optimalNextMove.getChildMove();
    }

    private NextMove getNextMoveRecursive(final char[][] board, Move opponentMove) {
        // 재귀의 종료조건으로 현재 board에 대해서 평가 후 조건에 따라 반환
        if (turnCount > 0) {

            boolean isWhiteKingAlive = false;
            boolean isBlackKingAlive = false;

            int score = 0;
            for (int y = 0; y < BOARD_SIZE; y++) {
                for (int x = 0; x < BOARD_SIZE; x++) {
                    if (board[y][x] != 0) {
                        switch (board[y][x]) {
                            case 'p':
                                score += 1;
                                break;
                            case 'n':
                                score += 3;
                                break;
                            case 'b':
                                score += 3;
                                break;
                            case 'r':
                                score += 5;
                                break;
                            case 'q':
                                score += 9;
                                break;
                            case 'k':
                                score += 100;
                                isWhiteKingAlive = true;
                                break;
                            case 'P':
                                score -= 1;
                                break;
                            case 'N':
                                score -= 3;
                                break;
                            case 'B':
                                score -= 3;
                                break;
                            case 'R':
                                score -= 5;
                                break;
                            case 'Q':
                                score -= 9;
                                break;
                            case 'K':
                                score -= 100;
                                isBlackKingAlive = true;
                                break;
                            default:
                                assert (false);
                        }
                    }
                }
            }

            // 킹이 잡혔을 때는 재귀가 끝나는 턴이 아니더라도 early return
            if (!isWhiteKingAlive) {
                score = Integer.MIN_VALUE;

                NextMove nextMove = new NextMove(opponentMove, null, score, turnCount);

                turnCount--;

                return nextMove;
            }

            // 킹이 잡혔을 때는 재귀가 끝나는 턴이 아니더라도 early return
            if (!isBlackKingAlive) {
                score = Integer.MAX_VALUE;

                NextMove nextMove = new NextMove(opponentMove, null, score, turnCount);

                turnCount--;

                return nextMove;
            }

            // 예를 들어, T3가 되어도 T3를 하지 않고 T2에 반환함으로 - 1 => T3는 들어가지도 않아서 -1을 해준다
            if (turnCount - 1 == MAXIMUN_TURN) {
                NextMove nextMove = new NextMove(opponentMove, null, score, turnCount);

                turnCount--;

                return nextMove;
            }
        }


        //AI가 블랙으로 시작하냐, 화이트로 시작하냐에 따라 0부터 시작하는 턴이 누구인지 달라진다
        boolean isAiWhite = super.isWhite();

        boolean isWhitePieceTurn = isAiWhite ? turnCount % 2 == 0 : turnCount % 2 != 0;


        //현재 보드에서 말들을 찾고 움직일 수 있는 모든 옵션을 얻는다
        HashMap<Move, char[][]> moveOptions = new HashMap<>();

        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                if (board[y][x] != 0) {
                    HashMap<Move, char[][]> temp = null;
                    if (isWhitePieceTurn) {
                        switch (board[y][x]) {
                            case 'p':
                                temp = getPossabilityPawnMove(board, x, y);
                                moveOptions.putAll(temp);
                                break;
                            case 'n':
                                temp = getPossabilityKnightMove(board, x, y);
                                moveOptions.putAll(temp);
                                break;
                            case 'b':
                                temp = getPossabilityBishopMove(board, x, y);
                                moveOptions.putAll(temp);
                                break;
                            case 'r':
                                temp = getPossabilityRookMove(board, x, y);
                                moveOptions.putAll(temp);
                                break;
                            case 'q':
                                temp = getPossabilityQueenMove(board, x, y);
                                moveOptions.putAll(temp);
                                break;
                            case 'k':
                                temp = getPossabilityKingMove(board, x, y);
                                moveOptions.putAll(temp);
                                break;
                        }
                    } else {
                        switch (board[y][x]) {
                            case 'P':
                                temp = getPossabilityPawnMove(board, x, y);
                                moveOptions.putAll(temp);
                                break;
                            case 'N':
                                temp = getPossabilityKnightMove(board, x, y);
                                moveOptions.putAll(temp);
                                break;
                            case 'B':
                                temp = getPossabilityBishopMove(board, x, y);
                                moveOptions.putAll(temp);
                                break;
                            case 'R':
                                temp = getPossabilityRookMove(board, x, y);
                                moveOptions.putAll(temp);
                                break;
                            case 'Q':
                                temp = getPossabilityQueenMove(board, x, y);
                                moveOptions.putAll(temp);
                                break;
                            case 'K':
                                temp = getPossabilityKingMove(board, x, y);
                                moveOptions.putAll(temp);
                                break;
                        }
                    }
                }
            }
        }


        //재귀로 움직일 수 있는 모든 옵션에 대해서 다음 수로 보낸다
        ArrayList<NextMove> nextMoves = new ArrayList<>();
        for (Map.Entry<Move, char[][]> entrySet : moveOptions.entrySet()) {
            turnCount++;

            NextMove tempOrNull = getNextMoveRecursive(entrySet.getValue(), entrySet.getKey());

            if (tempOrNull != null) {
                nextMoves.add(tempOrNull);
            }
        }

        // 내 AI가 화이트일 때
        // T0 나 화이트 최대값
        // T1 적 블랙 최소값
        // T2 나 화이트 최대값

        // 내 AI가 블랙일 때 달라져야 한다
        // T0 나 블랙 최소값
        // T1 적 화이트 최대값
        // T2 나 블랙 최소값.

        NextMove optiamlMove = null;
        if (isWhitePieceTurn) {
            if (nextMoves.size() == 0) {
                return null;
            }

            int max = nextMoves.get(0).score;
            int maxTurn = nextMoves.get(0).turn;
            int index = 0;

            for (int i = 1; i < nextMoves.size(); i++) {
                if (max < nextMoves.get(i).score) {
                    max = nextMoves.get(i).score;
                    maxTurn = nextMoves.get(i).turn;
                    index = i;
                } else if (max == nextMoves.get(i).score) {
                    if (max < 0 && maxTurn < nextMoves.get(i).turn) {
                        max = nextMoves.get(i).score;
                        maxTurn = nextMoves.get(i).turn;
                        index = i;
                    } else if (max > 0 && maxTurn > nextMoves.get(i).turn) {
                        max = nextMoves.get(i).score;
                        maxTurn = nextMoves.get(i).turn;
                        index = i;
                    } else if (maxTurn == nextMoves.get(i).turn) {
                        Random random = new Random();
                        if (random.nextBoolean()) {
                            max = nextMoves.get(i).score;
                            maxTurn = nextMoves.get(i).turn;
                            index = i;
                        }
                    }
                }
            }

            optiamlMove = new NextMove(opponentMove, nextMoves.get(index).getParentMoveOrNull(), max, maxTurn);

        } else {
            if (nextMoves.size() == 0) {
                return null;
            }

            int min = nextMoves.get(0).score;
            int minTurn = nextMoves.get(0).turn;
            int index = 0;

            for (int i = 1; i < nextMoves.size(); i++) {
                if (min > nextMoves.get(i).score) {
                    min = nextMoves.get(i).score;
                    minTurn = nextMoves.get(i).turn;
                    index = i;
                } else if (min == nextMoves.get(i).score) {
                    if (min < 0 && minTurn > nextMoves.get(i).turn) {
                        min = nextMoves.get(i).score;
                        minTurn = nextMoves.get(i).turn;
                        index = i;
                    } else if (min > 0 && minTurn < nextMoves.get(i).turn) {
                        min = nextMoves.get(i).score;
                        minTurn = nextMoves.get(i).turn;
                        index = i;
                    } else if (minTurn == nextMoves.get(i).turn) {
                        Random random = new Random();
                        if (random.nextBoolean()) {
                            min = nextMoves.get(i).score;
                            minTurn = nextMoves.get(i).turn;
                            index = i;
                        }
                    }
                }
            }

            optiamlMove = new NextMove(opponentMove, nextMoves.get(index).getParentMoveOrNull(), min, minTurn);

        }

        if (turnCount != 0) {
            turnCount--;
        }

        return optiamlMove;
    }

    private HashMap<Move, char[][]> getPossabilityPawnMove(final char[][] board, final int x, final int y) {

        char fromPiece = board[y][x];

        boolean isFormPieceWhite = Character.isLowerCase(fromPiece);

        //B W 일 때 각각 offset이 다르다.
        final int BLACK_MOVE_OFFSET_START = 0;
        final int WHITE_MOVE_OFFSET_START = 4;

        int playerIndex = isFormPieceWhite ? WHITE_MOVE_OFFSET_START : BLACK_MOVE_OFFSET_START;

        HashMap<Move, char[][]> moves = new HashMap<>();

        //1칸 전진하는 경우
        Move move = new Move(x, y, x + pawnMoveOffset[playerIndex][0], y + pawnMoveOffset[playerIndex][1]);

        if (move.toY >= 0 && move.toY < BOARD_SIZE) {
            char toPiece = board[move.toY][move.toX];
            if (toPiece == 0) {
                char[][] newBoard = createCopy(board);

                newBoard[move.toY][move.toX] = board[y][x];

                newBoard[y][x] = toPiece;

                moves.put(move, newBoard);
            }
        }

        playerIndex++;

        //옆에 애를 잡는 경우
        for (int i = 0; i < 2; i++) {
            move = new Move(x, y, x + pawnMoveOffset[playerIndex][0], y + pawnMoveOffset[playerIndex][1]);

            if (move.toX >= 0 && move.toX < BOARD_SIZE && move.toY >= 0 && move.toY < BOARD_SIZE) {
                char toPiece = board[move.toY][move.toX];

                boolean isToPieceWhite = Character.isLowerCase(toPiece);
                //

                if (toPiece != 0 && ((isFormPieceWhite && !isToPieceWhite) || (!isFormPieceWhite && isToPieceWhite))) {
                    char[][] newBoard = createCopy(board);

                    newBoard[move.toY][move.toX] = board[y][x];

                    newBoard[y][x] = 0;

                    moves.put(move, newBoard);
                }
            }

            playerIndex++;
        }


        //2칸 전진하는 경우
        move = new Move(x, y, x + pawnMoveOffset[playerIndex][0], y + pawnMoveOffset[playerIndex][1]);

        boolean hasMoved = isFormPieceWhite ? y != 6 : y != 1;
        if (!hasMoved && move.toY >= 0 && move.toY < BOARD_SIZE) {

            char pawnFrontPiece = board[y + pawnMoveOffset[playerIndex - 3][1]][x + pawnMoveOffset[playerIndex - 3][0]];
            char toPiece = board[move.toY][move.toX];

            if (pawnFrontPiece == 0 && toPiece == 0) {
                char[][] newBoard = createCopy(board);

                newBoard[move.toY][move.toX] = board[y][x];

                newBoard[y][x] = toPiece;

                moves.put(move, newBoard);
            }
        }

        playerIndex++;


        return moves;
    }

    private HashMap<Move, char[][]> getPossabilityKnightMove(final char[][] board, final int x, final int y) {
        char fromPiece = board[y][x];

        boolean isFormPieceWhite = Character.isLowerCase(fromPiece);

        HashMap<Move, char[][]> moves = new HashMap<>();

        for (int i = 0; i < knightMoveOffset.length; i++) {
            // 이동할 수 있는지 검사
            int toX = x + knightMoveOffset[i][0];
            int toY = y + knightMoveOffset[i][1];

            if (toX < 0 || toX >= BOARD_SIZE || toY < 0 || toY >= BOARD_SIZE) {
                continue;
            }

            // 이동하려는 곳에 아무것도 없거나 말을 잡을 수 있을 때
            char toPiece = board[toY][toX];

            boolean isToPieceWhite = Character.isLowerCase(toPiece);

            if (toPiece == 0 || (isFormPieceWhite && !isToPieceWhite) || (!isFormPieceWhite && isToPieceWhite)) {
                char[][] newBoard = createCopy(board);

                newBoard[toY][toX] = fromPiece;

                newBoard[y][x] = 0;

                Move move = new Move(x, y, toX, toY);

                moves.put(move, newBoard);
            }
        }
        return moves;
    }

    private HashMap<Move, char[][]> getPossabilityBishopMove(final char[][] board, final int x, final int y) {
        HashMap<Move, char[][]> moves = new HashMap<>();

        moves.putAll(getPossabilityBishopMoveSub(board, x, y, Movement.NORTHEAST));
        moves.putAll(getPossabilityBishopMoveSub(board, x, y, Movement.NORTWEST));
        moves.putAll(getPossabilityBishopMoveSub(board, x, y, Movement.SOUTHEAST));
        moves.putAll(getPossabilityBishopMoveSub(board, x, y, Movement.SOUTHWEST));

        return moves;
    }

    private HashMap<Move, char[][]> getPossabilityBishopMoveSub(final char[][] board, final int x, final int y, final Movement movement) {
        char fromPiece = board[y][x];

        boolean isFormPieceWhite = Character.isLowerCase(fromPiece);

        HashMap<Move, char[][]> moves = new HashMap<>();

        int xIncrement = 0;
        int yIncrement = 0;

        switch (movement) {
            case NORTHEAST:
                xIncrement = 1;
                yIncrement = 1;
                break;
            case NORTWEST:
                xIncrement = -1;
                yIncrement = 1;
                break;
            case SOUTHEAST:
                xIncrement = 1;
                yIncrement = -1;
                break;
            case SOUTHWEST:
                xIncrement = -1;
                yIncrement = -1;
                break;
            default:
                assert (false);
        }

        int toX = x + xIncrement;
        int toY = y + yIncrement;
        boolean isPieceOverlap = false;

        while (toX >= 0 && toX < BOARD_SIZE && toY >= 0 && toY < BOARD_SIZE && !isPieceOverlap) {
            char toPiece = board[toY][toX];

            boolean isToPieceWhite = Character.isLowerCase(toPiece);

            if (toPiece == 0 || (isFormPieceWhite && !isToPieceWhite) || (!isFormPieceWhite && isToPieceWhite)) {
                if ((isFormPieceWhite && !isToPieceWhite && toPiece != 0) || (!isFormPieceWhite && isToPieceWhite && toPiece != 0)) {
                    isPieceOverlap = true;
                }

                char[][] newBoard = createCopy(board);

                newBoard[toY][toX] = fromPiece;

                newBoard[y][x] = 0;

                Move move = new Move(x, y, toX, toY);

                moves.put(move, newBoard);
            } else {
                isPieceOverlap = true;
            }

            toX += xIncrement;
            toY += yIncrement;
        }

        return moves;
    }

    private HashMap<Move, char[][]> getPossabilityRookMove(final char[][] board, final int x, final int y) {
        HashMap<Move, char[][]> moves = new HashMap<>();

        moves.putAll(getPossabilityRookMoveSub(board, x, y, Movement.NORTH));
        moves.putAll(getPossabilityRookMoveSub(board, x, y, Movement.WEST));
        moves.putAll(getPossabilityRookMoveSub(board, x, y, Movement.EAST));
        moves.putAll(getPossabilityRookMoveSub(board, x, y, Movement.SOUTH));

        return moves;
    }

    private HashMap<Move, char[][]> getPossabilityRookMoveSub(final char[][] board, final int x, final int y, final Movement movement) {
        char fromPiece = board[y][x];

        boolean isFormPieceWhite = Character.isLowerCase(fromPiece);

        HashMap<Move, char[][]> moves = new HashMap<>();

        int xIncrement = 0;
        int yIncrement = 0;

        switch (movement) {
            case NORTH:
                xIncrement = 0;
                yIncrement = 1;
                break;
            case EAST:
                xIncrement = 1;
                yIncrement = 0;
                break;
            case WEST:
                xIncrement = -1;
                yIncrement = 0;
                break;
            case SOUTH:
                xIncrement = 0;
                yIncrement = -1;
                break;
            default:
                assert (false);
        }

        int toX = x + xIncrement;
        int toY = y + yIncrement;
        boolean isPieceOverlap = false;

        while (toX >= 0 && toX < BOARD_SIZE && toY >= 0 && toY < BOARD_SIZE && !isPieceOverlap) {
            char toPiece = board[toY][toX];

            boolean isToPieceWhite = Character.isLowerCase(toPiece);

            if (toPiece == 0 || (isFormPieceWhite && !isToPieceWhite) || (!isFormPieceWhite && isToPieceWhite)) {
                if ((isFormPieceWhite && !isToPieceWhite && toPiece != 0) || (!isFormPieceWhite && isToPieceWhite && toPiece != 0)) {
                    isPieceOverlap = true;
                }

                char[][] newBoard = createCopy(board);

                newBoard[toY][toX] = fromPiece;

                newBoard[y][x] = 0;

                Move move = new Move(x, y, toX, toY);

                moves.put(move, newBoard);
            } else {
                isPieceOverlap = true;
            }

            toX += xIncrement;
            toY += yIncrement;
        }

        return moves;
    }

    private HashMap<Move, char[][]> getPossabilityQueenMove(final char[][] board, final int x, final int y) {
        HashMap<Move, char[][]> moves = new HashMap<>();

        moves.putAll(getPossabilityRookMoveSub(board, x, y, Movement.NORTH));
        moves.putAll(getPossabilityRookMoveSub(board, x, y, Movement.WEST));
        moves.putAll(getPossabilityRookMoveSub(board, x, y, Movement.EAST));
        moves.putAll(getPossabilityRookMoveSub(board, x, y, Movement.SOUTH));

        moves.putAll(getPossabilityBishopMoveSub(board, x, y, Movement.NORTHEAST));
        moves.putAll(getPossabilityBishopMoveSub(board, x, y, Movement.NORTWEST));
        moves.putAll(getPossabilityBishopMoveSub(board, x, y, Movement.SOUTHEAST));
        moves.putAll(getPossabilityBishopMoveSub(board, x, y, Movement.SOUTHWEST));

        return moves;
    }

    private HashMap<Move, char[][]> getPossabilityKingMove(final char[][] board, final int x, final int y) {
        char fromPiece = board[y][x];

        boolean isFormPieceWhite = Character.isLowerCase(fromPiece);

        HashMap<Move, char[][]> moves = new HashMap<>();

        for (int i = 0; i < kingMoveOffsets.length; i++) {
            // 이동할 수 있는지 검사
            int toX = x + kingMoveOffsets[i][0];
            int toY = y + kingMoveOffsets[i][1];

            if (toX < 0 || toX >= BOARD_SIZE || toY < 0 || toY >= BOARD_SIZE) {
                continue;
            }

            // 이동하려는 곳에 아무것도 없거나 말을 잡을 수 있을 때
            char toPiece = board[toY][toX];

            boolean isToPieceWhite = Character.isLowerCase(toPiece);

            if (toPiece == 0 || (isFormPieceWhite && !isToPieceWhite) || (!isFormPieceWhite && isToPieceWhite)) {
                char[][] newBoard = createCopy(board);

                newBoard[toY][toX] = fromPiece;

                newBoard[y][x] = 0;

                Move move = new Move(x, y, toX, toY);

                moves.put(move, newBoard);
            }
        }
        return moves;
    }

    private char[][] createCopy(char[][] board) {
        char[][] newBoard = new char[BOARD_SIZE][BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                newBoard[i][j] = board[i][j];
            }
        }

        return newBoard;
    }

}
