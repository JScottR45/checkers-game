package checkers;

import javafx.scene.shape.Circle;

import static checkers.PieceColor.*;
import static checkers.PieceType.*;

class BlackPiece extends Piece {
    /** The color of this Piece. */
    private PieceColor color;
    /** The type of this Piece (either King or Regular). */
    private PieceType type;
    /** The GUI piece that corresponds to this Piece. */
    private Circle GUIpiece;
    /** The current board row in which this Piece resides. */
    private int currRow;
    /** The current column in which this Piece resides. */
    private int currCol;
    /** True if this Piece is currently selected for movement; false otherwise. */
    private boolean isSelected;
    /** True if this Piece is a King; false otherwise. */
    private boolean permanantKing;
    /** True if this Piece is currently on the Checkers board; false otherwise. */
    private boolean onBoard;

    /** Initializes this Piece with its starting location on the board. */
    BlackPiece(int row, int col) {
        color = BLACK;
        type = REGULAR;
        currRow = row;
        currCol = col;
        isSelected = false;
        permanantKing = false;
        onBoard = true;
    }

    @Override
    PieceColor getColor() {
        return color;
    }

    @Override
    PieceType getType() {
        return type;
    }

    @Override
    int getCurrRow() {
        return currRow;
    }

    @Override
    int getCurrCol() {
        return currCol;
    }

    @Override
    int getBoardAbstractionRow() {
        return currRow + 1;
    }

    @Override
    int getBoardAbstractionCol() {
        return currCol + 1;
    }

    @Override
    void setLocation(int row, int col) {
        currRow = row;
        currCol = col;
    }

    @Override
    void select(boolean bool) {
        isSelected = bool;
    }

    @Override
    boolean isSelected() {
        return isSelected;
    }

    @Override
    boolean isPermanentKing() {
        return permanantKing;
    }

    @Override
    void setPermanentKing() {
        permanantKing = true;
    }

    @Override
    public boolean isOnBoard() {
        return onBoard;
    }

    @Override
    void setOnBoard() {
        onBoard = true;
    }

    @Override
    void setOffBoard() {
        onBoard = false;
    }

    @Override
    Circle getGUIpiece() {
        return GUIpiece;
    }

    @Override
    void setGUIpiece(Circle piece) {
        GUIpiece = piece;
    }

    @Override
    public void setType(PieceType type) {
        this.type = type;
    }
}
