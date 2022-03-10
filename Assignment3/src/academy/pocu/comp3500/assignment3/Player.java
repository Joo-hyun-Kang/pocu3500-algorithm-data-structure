package academy.pocu.comp3500.assignment3;

import academy.pocu.comp3500.assignment3.chess.Move;
import academy.pocu.comp3500.assignment3.chess.PlayerBase;

import javax.swing.plaf.IconUIResource;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Player extends PlayerBase {
    //PieceType[] pieces = new PieceType[] { PieceType.PAWN, PieceType.KNIHGT, PieceType.BISHOP, PieceType.QUEEN, PieceType.KING };
    private static int turnCount = 0;

    private final int MAXIMUN_PREDICATION_TURN = 2;

    private PieceType[] pieces = new PieceType[] { PieceType.PAWN };

    private final static int BOARD_SIZE = 8;

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

    public Player(boolean isWhite, int maxMoveTimeMilliseconds) {
        super(isWhite, maxMoveTimeMilliseconds);
    }


    // Project layOut
    // 재귀로 파고 들어가되 체스는 모든 경우의 수가 너무 많기 때문에 스택 오버플로우가 생긴다
    // 따라서, 미니맥스 + 알파베타 가지치기를 활용해야 한다

    //1. minMax
    //내가 둘 때는 상대가 최대 이득이 되는게 자식에서 반환되고 나는 그 중에서 내 최대 이득을 선택하여 호출자에게 반환
    //상대가 둘 때는 내가 최대 이득이 되는게 자식에서 반환되고 상대는 최대 이득이 되는 걸 호출자에게 반환

    //상대방 차례일 때 좋은 상황들(음수일수록 좋음 그래서 자식에서 최소값 반환)
    //       10  5 -10
    //나는 이제 10을 나에게 가장 좋은 10을 선택해서 반환한다
    //이해를 위한 중요한 개념은 높을수록 나에게 유리하고 낮을수록 상대에게 유리하다

    //2. 알파-베타 가치치기
    //https://going-to-end.tistory.com/entry/%EC%95%8C%ED%8C%8C-%EB%B2%A0%ED%83%80-%EA%B0%80%EC%A7%80%EC%B9%98%EA%B8%B0-Alpha-beta-pruning
    //http://egloos.zum.com/musicdiary/v/4274653
    //http://wiki.hash.kr/index.php/%EC%95%8C%ED%8C%8C%EB%B2%A0%ED%83%80_%EA%B0%80%EC%A7%80%EC%B9%98%EA%B8%B0


    //3. 전부다 훑을 수 없으니까 최대 깊이에서 나에게 얼마나 유리할지 공식화해야 한다
    //간단한 점수 계산 법으로 살아있는 말 마다 점수를 가져가기

    //4. 좋은 알고리듬
    //점수 계산 함수가 얼마나 뛰어난 지
    //얼마나 깊이 볼 수 있는 지 -> 알파베타 가지치기



    @Override
    public Move getNextMove(char[][] board) {
        return getNextMove(board, null);
    }

    @Override
    public Move getNextMove(char[][] board, Move opponentMove) {

        //8 * 8

        // 1. 플레이어가 블랙인지 화이트인지 확인
        // boolean aiPlayer = super.isWhite();


        // 2. 일단 말을 선택하고 그 말에 대해서 움직임을 계산한다
        // 딕텍토에서는 모든 인덱스에 대해서 처음부터 다 넣어보면 됌
        // 여기에는 인덱스에 대해서 접근할 수 있는게 말에 따라서 다름 따라서 말로 접근해야 함
        // 말은 저장하는 방식은 호출해야 한되니까 안 되고,
        // D0 폰 move
        // D1 상대 move 16
        // D2 나 move 16 * 16
        // D3 상대 move 16 * 16 * 16
        

        // 폰, 나이트, 비숍 ... 킹
        // 6개 말 중에 선택 후 움직이고 vaild 검토 그 상태에서
        // 다시 상대가 6개의 말 중에 선택 후 움직이고 vaild 검토 그 상테에서
        // .... 종료조건 depths.

        // 1. 폰, 나이트, 비숍 ... 킹 추가
        // 2. board를 재활용해서 하는 방법?
        // 3. 가지치기 ㄱㄱ

        NextMove optimalNextMove = getNextMoveRecursive(board, opponentMove);

        return optimalNextMove.getChildMove();
    }

    // 가지치기 도입
    // 체스말 추가해서 넣기
    private NextMove getNextMoveRecursive(final char[][] board, Move opponentMove) {
        // 재귀의 종료조건으로 현재 board에 대해서 평가 후 반환
        // T3가 되어도 T3를 하지 않고 T2에 반환함으로 - 1 => T3는 들어가지도 않아서 -1을 해준다
        // 화이트는 값이 높을수록 블랙은 값이 낮을수록 유리
        if (turnCount  - 1 == MAXIMUN_PREDICATION_TURN) {

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

            if (!isWhiteKingAlive) {
                score = Integer.MIN_VALUE;
            }

            if (!isBlackKingAlive) {
                score = Integer.MAX_VALUE;
            }

            NextMove nextMove = new NextMove(opponentMove, null, score);

            turnCount--;

            return nextMove;
        }


        /*
        for (PieceType piece : pieces) {
            char pieceSymbol = 0;
            switch (piece) {
                case PAWN:
                    pieceSymbol = 'p';
                    break;
                    /*
                case KNIHGT:
                    pieceSymbol = 'n';
                    break;
                case BISHOP:
                    pieceSymbol = 'b';
                    break;
                case ROOK:
                    pieceSymbol = 'r';
                    break;
                case QUEEN:
                    pieceSymbol = 'q';
                    break;
                case KING:
                    pieceSymbol = 'k';
                    break;


                default:
                    assert (false);
            }
            */

        //AI가 블랙으로 시작하냐, 화이트로 시작하냐에 따라 0부터 시작하는 턴이 누구인지 달라진다
        boolean isAiWhite = super.isWhite();

        boolean isWhitePieceTurn = isAiWhite ? turnCount % 2 == 0 : turnCount % 2 != 0;


        //현재 보드에서 말들을 찾고 움직일 수 있는 모든 옵션을 얻는다
        char pieceSymbol = 'p';

        if (!isWhitePieceTurn) {
            pieceSymbol ^= 32;
        }

        HashMap<Move, char[][]> moveOptions = new HashMap<>();

        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                if (board[y][x] == pieceSymbol) {
                    HashMap<Move, char[][]> temp = getPossabilityNextMove(board, x, y);
                    moveOptions.putAll(temp);
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

        //내 AI가 하얀색 적은 블랙
        //내 AI 턴일 때 Max를 선택해서 넘긴다 --> 그런데 이건 여기서 맥스를 선택하는게 아니라 여기서는 점수계싼 만..
        //ㅇㅋㄷㅋ

        // 점수 계산하는 것에 따라서 최악 최선 선택이 달라지네.

        //boolean isAiWhite = super.isWhite();

        //boolean isWhitePieceTurn = isAiWhite ? turnCount % 2 == 0 : turnCount % 2 != 0;

        // 내 AI가 화이트일 때
        // T0 나 화이트 최대값
        // T1 적 블랙 최소값
        // T2 나 화이트 최대값

        // 내 AI가 블랙일 때 달라져야 한다
        // T0 나 블랙 최소값
        // T1 적 화이트 최대값
        // T2 나 블랙 최소값.

        // T3 입성 시 계산

        NextMove optiamlMove = null;
        if (isWhitePieceTurn) {
            int max = Integer.MIN_VALUE;
            int maxIndex = -1;


            for (int i = 0; i < nextMoves.size(); i++) {
                if (max <= nextMoves.get(i).score) {
                    max = nextMoves.get(i).score;
                    maxIndex = i;
                }
            }

            if (nextMoves.size() != 0) {
                optiamlMove = new NextMove(opponentMove, nextMoves.get(maxIndex).getParentMoveOrNull(), max);
            } else {
                optiamlMove = new NextMove(opponentMove, null, max);
            }
        } else {
            int min = Integer.MAX_VALUE;

            int minIndex = -1;

            for (int i = 0; i < nextMoves.size(); i++) {
                if (min >= nextMoves.get(i).score) {
                    min = nextMoves.get(i).score;
                    minIndex = i;
                }
            }

            if (nextMoves.size() != 0) {
                optiamlMove = new NextMove(opponentMove, nextMoves.get(minIndex).getParentMoveOrNull(), min);
            } else {
                optiamlMove = new NextMove(opponentMove, new Move(), min);
            }
        }

        if (turnCount == 0 && optiamlMove == null) {
            int noPlaceX = -1;
            int noPlaceY = -1;
            for (int y = 0; y < BOARD_SIZE; y++) {
                for (int x = 0; x < BOARD_SIZE; x++) {
                    if (board[y][x] == pieceSymbol) {
                        noPlaceX = x;
                        noPlaceY = y;
                    }
                }
            }

            optiamlMove = new NextMove(opponentMove, new Move(noPlaceX, noPlaceY, noPlaceX, noPlaceY), 0);
        }

        if (turnCount != 0) {
            turnCount--;
        }

        return optiamlMove;
    }

    private HashMap<Move, char[][]> getPossabilityNextMove(final char[][] board, final int x, final int y) {
        return getPossabilityPawnMove(board, x, y);
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
            char toPiece = board[move.toY][move.toX];
            if (toPiece == 0) {
                char[][] newBoard = createCopy(board);

                newBoard[move.toY][move.toX] = board[y][x];

                newBoard[y][x] = toPiece;

                moves.put(move, newBoard);
            }
        }

        playerIndex++;


        return moves;
    }


    private Move getMaxPawnMovement(char[][] board, int x, int y) {

        char fromPiece = board[y][x];

        boolean isFormPieceWhite = Character.isLowerCase(fromPiece);

        final int WHITE_MOVE_OFFSET_START = 0;
        final int BLACK_MOVE_OFFSET_START = 4;

        int playerIndex = isFormPieceWhite ? WHITE_MOVE_OFFSET_START : BLACK_MOVE_OFFSET_START;

        ArrayList<Move> moves = new ArrayList<>();
        ArrayList<Integer> scores = new ArrayList<>();


        //1칸 전진하는 경우
        Move move = new Move(x, y, x + pawnMoveOffset[playerIndex][0], y + pawnMoveOffset[playerIndex][1]);

        if (move.toY < BOARD_SIZE) {
            char toPiece = board[move.toY][move.toX];
            if (toPiece == 0) {
                moves.add(move);
                scores.add(0);
            }
        }

        playerIndex++;

        //옆에 애를 잡는 경우
        for (int i = 0; i < 2; i++) {
            move = new Move(x, y, x + pawnMoveOffset[playerIndex][0], y + pawnMoveOffset[playerIndex][1]);

            if (move.toX < BOARD_SIZE && move.toY < BOARD_SIZE) {
                char toPiece = board[move.toY][move.toX];
                boolean isToPieceWhite = Character.isLowerCase(toPiece);

                if (toPiece != 0 && !isToPieceWhite) {
                    //뭐인지에 따라 평가함수 작성하기
                    moves.add(move);
                    scores.add(20);
                }
            }

            playerIndex++;
        }


        //2칸 전진하는 경우
        move = new Move(x, y, x + pawnMoveOffset[playerIndex][0], y + pawnMoveOffset[playerIndex][1]);

        boolean hasMoved = isFormPieceWhite ? y != 6 : y != 2;
        if (!hasMoved && move.toY < BOARD_SIZE) {
            char toPiece = board[move.toY][move.toX];
            if (toPiece == 0) {
                moves.add(move);
                scores.add(0);
            }
        }

        playerIndex++;
        assert(playerIndex == 4 || playerIndex == 8);

        int max = Integer.MIN_VALUE;
        int maxIndex = -1;

        for (int i = 0; i < scores.size(); i++) {
            if (scores.get(i) > max) {
                max = scores.get(i);
                maxIndex = i;
            }
        }

        assert(maxIndex != -1);
        return moves.get(maxIndex);
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
