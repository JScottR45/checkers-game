package checkers;

import javafx.scene.shape.Circle;

import static checkers.PieceType.*;

class Block extends Piece {
    /** The type of this special Piece (always BLOCK). */
    private PieceType type;

    /** Initializes this Block by indicating its type as such. */
    Block() {
        type = BLOCK;
    }

    @Override
    PieceType getType() {
        return type;
    }

    @Override
    PieceColor getColor() {
        throw new UnsupportedOperationException();
    }

    @Override
    int getCurrRow() {
        throw new UnsupportedOperationException();
    }

    @Override
    int getCurrCol() {
        throw new UnsupportedOperationException();
    }

    @Override
    int getBoardAbstractionRow() {
        throw new UnsupportedOperationException();
    }

    @Override
    int getBoardAbstractionCol() {
        throw new UnsupportedOperationException();
    }

    @Override
    void setLocation(int row, int col) {
        throw new UnsupportedOperationException();
    }

    @Override
    void select(boolean bool) {
        throw new UnsupportedOperationException();
    }

    @Override
    boolean isSelected() {
        throw new UnsupportedOperationException();
    }

    @Override
    boolean isPermanentKing() {
        throw new UnsupportedOperationException();
    }

    @Override
    void setPermanentKing() {
        throw new UnsupportedOperationException();
    }

    @Override
    boolean isOnBoard() {
        throw new UnsupportedOperationException();
    }

    @Override
    void setOnBoard() {
        throw new UnsupportedOperationException();
    }

    @Override
    void setOffBoard() {
        throw new UnsupportedOperationException();
    }

    @Override
    Circle getGUIpiece() {
        throw new UnsupportedOperationException();
    }

    @Override
    void setGUIpiece(Circle piece) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setType(PieceType type) {
        throw new UnsupportedOperationException();
    }
}
