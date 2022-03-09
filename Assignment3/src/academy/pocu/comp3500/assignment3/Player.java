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

    PieceType[] pieces = new PieceType[] { PieceType.PAWN };

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




        return getNextMoveRecursive(board, opponentMove, null, 0);
    }

    private Move getNextMoveRecursive(char[][] board, Move opponentMove, PieceType pieceType, int depth) {


        ArrayList<Integer> scores = new ArrayList<>();
        ArrayList<Move> moves = new ArrayList<>();

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

                     */
                default:
                    assert (false);
            }

            boolean isPlayerWhite = super.isWhite();
            if (!isPlayerWhite) {
                pieceSymbol ^= 32;
            }

            //chage name
            HashMap<Move, char[][]> moveOptions = new HashMap<>();

            for (int y = 0; y < BOARD_SIZE; y++) {
                for (int x = 0; x < BOARD_SIZE; x++) {
                    if (board[y][x] == pieceSymbol) {
                        HashMap<Move, char[][]> temp = getPossabilityNextMove(board, x, y, piece);

                        moveOptions.putAll(temp);
                    }
                }
            }

            // 밑에 평가 함수 고쳐서 모든 가능성 계산할 것
            // 그리고 나서 그 가능성을 던져주고
            // 평가는 애들이 몇개 남아 있는지로 게산해서
            // 나 일 때는 최대값
            // 적일 때는 최소값 반환

            // 그리고 테스트 통과하면 리펙토링해서 깊이 늘리기


            // depth 가 짝수일 때는 나
            // 홀수일 때는 적

            // 나 일 경우 최대값으로 반환
            // 상대일 경우 최소값으로 반환

            // 하얀색일 때       블랙일 때
            // depth 1 블랙     하얀
            // depth 2 하얀색    블랙
            // depth 3 블랙     하양

            // depth 0
            // ooooo
            //
            //   x
            // xx xx

            // depth 1
            // oo oo
            //   o
            //   x
            // xx xx

            // depth 2
            // oo oo
            //   o
            // x x
            //  x xx

            // depth 3일 때는 안 보고 depth 2일때 상황





            for (Map.Entry<Move, char[][]> entrySet : moveOptions.entrySet()) {
                if (depth < 2) {
                    
                    Move best =  getNextMoveRecursive(entrySet.getValue(), entrySet.getKey(), pieceType, depth + 1);
                    //best가 지금 수에 대해서 아래에서 보낸 미니 ㅁ개스
                    //best를 또 모아서 여기서 최적 ㄱㄱ
                    // 그리고 일단 piece를 나누는 건 나중에 하자


                    //1. 수정해야 할 거 뎁스가 깊어질 때 상대껄로 안 나옴
                    //2. 지금 미니맥스가 아니라 재귀로 파고 들어가서 가장 최적의 상태를 반환하고 있음 따라서 수정 필요
                    // 받아서 다시 또 계산해서 최고 최저 하기
                    //3. 한 가지 문제가 더 있었는데 기억 안나네? 아마 피스 나누는 거 나중에?

                }

                boolean isPeaceWhite = isPlayerWhite ? depth % 2 == 0 : depth % 2 != 0;

                boolean isOpponentKingAlive = false;

                int score = 0;

                char[][] caseBoard = entrySet.getValue();

                for (int y = 0; y < BOARD_SIZE; y++) {
                    for (int x = 0; x < BOARD_SIZE; x++) {
                        if (isPeaceWhite && caseBoard[y][x] != 0) {
                            switch (caseBoard[y][x]) {
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
                                    break;
                                case 'K':
                                    isOpponentKingAlive = false;
                                    break;
                            }
                        }else if (!isPeaceWhite && caseBoard[y][x] != 0) {
                            switch (caseBoard[y][x]) {
                                case 'P':
                                    score += 1;
                                    break;
                                case 'N':
                                    score += 3;
                                    break;
                                case 'B':
                                    score += 3;
                                    break;
                                case 'R':
                                    score += 5;
                                    break;
                                case 'Q':
                                    score += 9;
                                    break;
                                case 'K':
                                    score += 100;
                                    break;
                                case 'k':
                                    isOpponentKingAlive = false;
                                    break;
                            }
                        }

                    }
                }

                if (!isOpponentKingAlive) {
                    score = Integer.MAX_VALUE;
                }

                scores.add(score);
                moves.add(entrySet.getKey());
            }

        }

        int max = Integer.MIN_VALUE;
        int maxIndex = -1;
        for (int i = 0; i < scores.size(); i++) {
            if (scores.get(i) > max) {
                max = scores.get(i);
                maxIndex = i;
            }
        }

        return moves.get(maxIndex);





            // 디텍토에서는 인덱스 반환환
        //움직일 장기말 선택 위치 쳌


        // 개체지향이니까 move를 PAWN에 저장하고 있을 듯
        // 절차지향으로 사용하면

        //그 장기말들 폰이면 8개에 대해서 순환


        // 시간복잡도
        // 움직일 수 있나? 움직일 수 있는 모든 가능성
        // 16개의 말이 있고 각각 음직일 수 있는 가능성을 10개라고 하면
        // 16 * 10 = 160가지 상대편도 160가지 움직일 수 있기에 턴마다 160^n씩 증가하게 되네....

        // 폰이 움직일 수 있는 방식
        // 처음에 앞으로 2칸, 그리고 1칸씩 전진, 옆에 적이 있을 때 잡을 수 있음
        }





    private HashMap<Move, char[][]> getPossabilityNextMove(final char[][] board, final int x, final int y, final PieceType type) {

        switch (type) {
            case PAWN:
                return getPossabilityPawnMove(board, x, y);
                /*
            case KNIHGT:
                break;
            case BISHOP:
                break;
            case ROOK:
                break;
            case QUEEN:
                break;
            case KING:
                break;

                 */
            default:
                assert (false);
        }

        return null;

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

                if ((isFormPieceWhite && !isToPieceWhite) || (!isFormPieceWhite && isToPieceWhite)) {
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
