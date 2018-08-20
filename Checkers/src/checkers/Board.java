package checkers;

import java.util.LinkedList;
import static checkers.PieceColor.*;
import static checkers.PieceType.*;

class Board {
    /** The total number of captured red pieces. */
    private int numCapturedRedPieces;
    /** The total number of captured black pieces. */
    private int numCapturedBlackPieces;
    /** The 2-D array which represents the Checkers board. */
    private Piece[][] board;
    /** A Linked List containing all of the red pieces currently on the board. */
    private LinkedList<RedPiece> redPieces;
    /** A Linked List containing all of the black pieces currently on the board. */
    private LinkedList<BlackPiece> blackPieces;
    /** The length and width of the Checkers board, including the perimeter of Blocks around it. */
    private static final int SIZE = 10;

    /** Initialises the starting state of this Board. */
    Board() {
        numCapturedRedPieces = 0;
        numCapturedBlackPieces = 0;
        board = new Piece[SIZE][SIZE];
        redPieces = new LinkedList<>();
        blackPieces = new LinkedList<>();

        initializeBoard();
    }

    /** Returns the total number of captured red pieces. */
    int getNumCapturedRedPieces() {
        return numCapturedRedPieces;
    }

    /** Returns the total number of captured black pieces. */
    int getNumCapturedBlackPieces() {
        return numCapturedBlackPieces;
    }

    /** Returns the piece located at ROW, COL on the board. */
    Piece getPieceGUILoc(int row, int col) {
        return board[row + 1][col + 1];
    }

    Piece getPieceAbstractionLoc(int row, int col) {
        return board[row][col];
    }

    /** Returns the Linked List containing all of the red pieces currently on the board. */
    LinkedList<RedPiece> getRedPieces() {
        return redPieces;
    }

    /** Returns the Linked List containing all of the black pieces currently on the board. */
    LinkedList<BlackPiece> getBlackPieces() {
        return blackPieces;
    }

    /** Moves the piece located at CURRROW, CURRCOL to DESTROW, DESTCOL on the board. */
    void movePiece(int currRow, int currCol, int destRow, int destCol) {
        Piece piece = getPieceGUILoc(currRow, currCol);

        board[currRow + 1][currCol + 1] = null;
        board[destRow + 1][destCol + 1] = piece;
    }

    /** Removes piece located at ROW, COL from the board and from the Linked List which contains
     *  the other pieces of the same color. */
    void removePieceAbstractionLoc(int row, int col) {
        Piece capturedPiece = board[row][col];
        capturedPiece.setOffBoard();

        if (capturedPiece.getColor() == RED) {
            RedPiece casted = (RedPiece) capturedPiece;
            redPieces.remove(casted);
        } else {
            BlackPiece casted = (BlackPiece) capturedPiece;
            blackPieces.remove(casted);
        }

        board[row][col] = null;
    }

    /** Removes piece located at ROW, COL from the board. Used for temporary purposes. */
    void tempRemovePieceAbstractionLoc(int row, int col) {
        Piece piece = board[row][col];

        if (piece.getColor() == RED) {
            numCapturedRedPieces += 1;
        } else {
            numCapturedBlackPieces += 1;
        }

        piece.setOffBoard();
        board[row][col] = null;
    }

    /** Adds PIECE onto the board at ROW, COL. Used to undo a call to tempRemovePieceAbstractionLoc. */
    void addPieceGUILoc(Piece piece, int row, int col) {

        if (piece.getColor() == RED) {
            numCapturedRedPieces -= 1;
        } else if (piece.getColor() == BLACK){
            numCapturedBlackPieces -= 1;
        }

        piece.setOnBoard();
        board[row + 1][col + 1] = piece;
    }

    /*void printBoard() {
        Piece piece;

        for (int i = 0; i < SIZE; i++) {

            for (int j = 0; j < SIZE; j++) {
                piece = board[i][j];

                if (piece != null) {
                    if (piece.getType() == BLOCK) {
                        System.out.print("x ");
                    } else if (piece.getColor() == RED) {
                        if (piece.getType() == KING) {
                            System.out.print("R ");
                        } else {
                            System.out.print("r ");
                        }
                    } else if (piece.getColor() == BLACK) {
                        if (piece.getType() == KING) {
                            System.out.print("B ");
                        } else {
                            System.out.print("b ");
                        }
                    }
                } else {
                    System.out.print("_ ");
                }
            }
            System.out.println();
        }
    }*/

    /** Creates the Checkers board abstraction and places all of the pieces in their correct locations. */
    private void initializeBoard() {
        BlackPiece black;
        RedPiece red;

        for (int i = 0; i < SIZE; i++) {
            board[0][i] = new Block();
            board[SIZE - 1][i] = new Block();
        }

        for (int i = 1; i < SIZE - 1; i++) {

            for (int j = 0; j < SIZE; j++) {

                if (j == 0 || j == SIZE - 1) {
                    board[i][j] = new Block();
                }

                else if (i <= 3) {
                    if (i % 2 == 1) {
                        if (j % 2 == 0) {
                            black = new BlackPiece(i - 1, j - 1);
                            board[i][j] = black;
                            blackPieces.add(black);
                        }
                    } else if (j % 2 == 1) {
                        black = new BlackPiece(i - 1, j - 1);
                        board[i][j] = black;
                        blackPieces.add(black);
                    }
                }

                else if (i >= 6) {
                    if (i % 2 == 1) {
                        if (j % 2 == 0) {
                            red = new RedPiece(i - 1, j - 1);
                            board[i][j] = red;
                            redPieces.add(red);
                        }
                    } else if (j % 2 == 1) {
                        red = new RedPiece(i - 1, j - 1);
                        board[i][j] = red;
                        redPieces.add(red);
                    }
                }
            }
        }
    }
}
