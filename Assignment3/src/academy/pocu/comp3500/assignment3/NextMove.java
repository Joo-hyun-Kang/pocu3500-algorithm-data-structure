package academy.pocu.comp3500.assignment3;

import academy.pocu.comp3500.assignment3.chess.Move;

public class NextMove {
    public Move parentMove;
    public Move childMove;
    int score;
    int turn;

    public NextMove(Move parentMove, Move childMove, int score, int turnCount) {
        this.parentMove = parentMove;
        this.childMove = childMove;
        this.score = score;
        this.turn = turnCount;
    }

    public Move getParentMoveOrNull() {
        if (parentMove == null) {
            return null;
        }
        return parentMove;
    }

    public Move getChildMove() {
        if (childMove == null) {
            return null;
        }
        return childMove;
    }
}
