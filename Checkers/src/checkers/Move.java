package checkers;

import java.util.LinkedList;

import static checkers.PieceColor.*;

class Move {
    /** The moving piece of this Move. */
    private Piece piece;
    /** The move itself. */
    private LinkedList<int[]> move;
    /** If this move is an attack, this Linked List will contain all of the captured pieces in the
     *  order in which they were captured. */
    private LinkedList<Piece> capturedPieces;
    /** The best attack for the piece of this Move if the move is an attack. */
    private LinkedList<int[]> bestCurrAttack;
    /** The captured pieces that correspond to this Move's best attack. */
    private LinkedList<Piece> bestCurrAttackCapturedPieces;
    /** The move value of this Move. */
    private int moveValue;
    /** True if this Move is an attack; false otherwise. */
    private boolean isAttack;
    /** The initial starting row of this Move's piece before the move is made. */
    private int startRow;
    /** The initial starting column of this Move's piece before the move is made. */
    private int startCol;

    /** Initializes this Move with piece P which has move M which has a move value of VAL. TYPE is true is
     *  this Move is an attack and is false otherwise. */
    Move(Piece p, int[] m, int val, boolean type) {
        piece = p;
        move = new LinkedList<>();
        moveValue = val;
        isAttack = type;

        move.add(m);
    }

    /** Initializes this Move with piece P of color COLOR and which has type TYPE. */
    Move(Piece p, boolean type, PieceColor color) {
        piece = p;
        move = new LinkedList<>();
        startRow = p.getCurrRow();
        startCol = p.getCurrCol();
        isAttack = type;
        capturedPieces = new LinkedList<>();
        bestCurrAttack = new LinkedList<>();
        bestCurrAttackCapturedPieces = new LinkedList<>();

        if (color == RED) {
            moveValue = Integer.MAX_VALUE;
        } else {
            moveValue = Integer.MIN_VALUE;
        }
    }

    /** Initializes this Move with piece P which has move M. */
    Move(Piece p, int[] m) {
        piece = p;
        move = new LinkedList<>();
        startRow = p.getCurrRow();
        startCol = p.getCurrCol();

        move.add(m);
    }

    /** Returns the move value of this Move. */
    int getVal() {
        return moveValue;
    }

    /** Returns the piece of this Move. */
    Piece getPiece() {
        return piece;
    }

    /** Returns the finalized move or attack(s) of this Move. */
    LinkedList<int[]> getMove() {
        return move;
    }

    /** Returns the captured pieces of this Move (must be an attack). */
    LinkedList<Piece> getCapturedPieces() {
        return capturedPieces;
    }

    /** Returns the best attack of this Move. */
    LinkedList<int[]> getBestCurrAttack() {
        return bestCurrAttack;
    }

    /** Returns the captured pieces which correspond to this Move's best attack. */
    LinkedList<Piece> getBestCurrAttackCapturedPieces() {
        return bestCurrAttackCapturedPieces;
    }

    /** Returns true if this Move is an attack; returns false otherwise. */
    boolean isAttack() {
        return isAttack;
    }

    /** Returns the starting row of this Move. */
    int getStartRow() {
        return startRow;
    }

    /** Returns the starting column of this Move. */
    int getStartCol() {
        return startCol;
    }

    /** Sets the move value of this Move. */
    void setMoveValue(int val) {
        moveValue = val;
    }

    /** Sets the attack of this Move. */
    void setAttack(LinkedList<int[]> attacks) {
        move = attacks;
    }

    /** Sets the captured pieces of this Move. */
    void setCapturedPieces(LinkedList<Piece> pieces) {
        capturedPieces = pieces;
    }

    /** Add an additional or chain attack to this Move. */
    void addAttack(int[] attack, Piece capturedPiece) {
        move.add(attack);
        capturedPieces.add(capturedPiece);
    }

    /** Sets the best currently known attack of this Move. */
    void setBestCurrAttack(LinkedList<int[]> attack) {
        bestCurrAttack = attack;
    }

    /** Sets the list of captured pieces which corresponds to the best currently known attack of this move. */
    void setBestCurrAttackCapturedPieces(LinkedList<Piece> pieces) {
        bestCurrAttackCapturedPieces = pieces;
    }
}
