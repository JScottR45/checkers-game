package checkers;


import javafx.scene.shape.Circle;

abstract class Piece {

    /** Return the color of this Piece. */
    abstract PieceColor getColor();

    /** Return the type of this Piece. */
    abstract PieceType getType();

    /** Return the current board row number in which this Piece currently resides. */
    abstract int getCurrRow();

    /** Return the current board column number in which this Piece currently resides. */
    abstract int getCurrCol();

    abstract int getBoardAbstractionRow();

    abstract int getBoardAbstractionCol();

    /** Sets the current location of this Piece to ROW, COLUMN.  */
    abstract void setLocation(int row, int col);

    /** Return true if this Piece is currently selected for movement; return false otherwise. */
    abstract boolean isSelected();

    abstract boolean isPermanentKing();

    abstract boolean isOnBoard();

    abstract void setOnBoard();

    abstract void setOffBoard();

    abstract void setPermanentKing();

    /** Set the selected status of this Piece to BOOL. */
    abstract void select(boolean bool);

    /** Return the GUI piece that corresponds to this Piece. */
    abstract Circle getGUIpiece();

    /** Ties this Piece to a corresponding GUI piece on the board. */
    abstract void setGUIpiece(Circle piece);

    abstract void setType(PieceType type);

}
