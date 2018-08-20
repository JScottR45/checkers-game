package checkers;

import javafx.animation.PauseTransition;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

import java.util.LinkedList;

import static checkers.PieceColor.*;
import static checkers.PieceType.*;
import static checkers.OpponentType.*;

class Controller {
    /** The Checkers board abstraction. */
    private static Board board;
    /** The Checkers board GUI object. */
    private static BoardGUI boardGUI;
    /** The type of opponent for this game (either human or computer with varying difficulty). */
    private static OpponentType opponent;
    /** The player with current turn of the game. */
    private static PieceColor currentPlayer;
    /** The computer AI. */
    private static AI computerPlayer;
    /** True if the current player can make an additional/chain attack; false otherwise. */
    private static boolean additionalAttack;
    /** Indicates whether or not a game piece is currently selected for movement. */
    private static boolean selected;
    /** The currently selected piece. */
    private static Piece selectedPiece;
    /** The color of a selected red game piece. */
    private static final String SELECTRED = "#80ffff";
    /** The color of a selected black game piece. */
    private static final String SELECTBLACK = "#80ffff";
    /** The color of an unselected red game piece. */
    private static final String UNSELECTRED = "#B30000";
    /** The color of an unselected black game piece. */
    private static final String UNSELECTBLACK = "#333333";
    /** The borderstroke size for a King piece. */
    private static final double KINGSTROKE = 4;
    /** The borderstroke  color for a red King piece. */
    private static final String REDSTROKE = "#B30000";
    /** The color white. */
    private static final String WHITE = "#FFFFFF";

    /** Initializes the game board and runs a human vs human game. */
    static void playHumanVsHuman() {
        board = new Board();
        boardGUI = new BoardGUI(board);
        opponent = HUMAN;
        currentPlayer = RED;
        additionalAttack = false;
        selected = false;
        selectedPiece = null;

        displayText("Red player turn.");
        boardGUI.getWindow().show();
    }

    /** Initializes the game board and runs a human vs computer game on easy difficulty. */
    static void playHumanVsComputerEasy() {
        board = new Board();
        boardGUI = new BoardGUI(board);
        computerPlayer = new AI(COMPUTER_EASY, board, boardGUI);
        opponent = COMPUTER_EASY;
        currentPlayer = RED;
        additionalAttack = false;
        selected = false;
        selectedPiece = null;

        displayText("Your move.");
        boardGUI.getWindow().show();
    }

    /** Initializes the game board and runs a human vs computer game on medium difficulty. */
    static void playHumanVsComputerMedium() {
        board = new Board();
        boardGUI = new BoardGUI(board);
        computerPlayer = new AI(COMPUTER_MEDIUM, board, boardGUI);
        opponent = COMPUTER_MEDIUM;
        currentPlayer = RED;
        additionalAttack = false;
        selected = false;
        selectedPiece = null;

        displayText("Your move.");
        boardGUI.getWindow().show();
    }

    /** Initializes the game board and runs a human vs computer game on hard difficulty. */
    static void playHumanVsComputerHard() {
        board = new Board();
        boardGUI = new BoardGUI(board);
        computerPlayer = new AI(COMPUTER_HARD, board, boardGUI);
        opponent = COMPUTER_HARD;
        currentPlayer = RED;
        additionalAttack = false;
        selected = false;
        selectedPiece = null;

        displayText("Your move.");
        boardGUI.getWindow().show();
    }

    /** Outputs MESSAGE to the current player. */
    static void displayText(String message) {
        boardGUI.getMessageBox().setText(message);
    }

    /** Resets the Checkers game to a fresh start. BRD is a brand new board. */
    static void reset(Board brd) {
        board = brd;
        currentPlayer = RED;
        additionalAttack = false;
        selected = false;
        selectedPiece = null;

        if (opponent != HUMAN) {
            computerPlayer = new AI(opponent, board, boardGUI);
            displayText("Your move.");
        } else {
            displayText("Red player turn.");
        }
    }

    /** If there is a selected piece and the tile represented by OBJ is a location to which the
     *  selected piece can legally move, then the selected piece is moved to that location. */
    static void tileClick (Object obj) {
        Piece piece;
        BorderPane destTile = (BorderPane) obj;
        int destRow = GridPane.getRowIndex(destTile);
        int destCol = GridPane.getColumnIndex(destTile);
        boolean successfulMove;

        if (selected) {
            piece = selectedPiece;
            successfulMove = attemptMove(destTile, piece, destRow, destCol);

            if (gameOver()) {
                if (opponent != HUMAN) {
                    displayText("You win!");
                }
                WinnerAlertBox.display(currentPlayer.opposite(), opponent, boardGUI);
            } else if (opponent != HUMAN && successfulMove) {
                displayText("");
                computerPlayer.makeMove();

                if (gameOver()) {
                    PauseTransition pause = new PauseTransition();
                    pause.setDuration(new Duration(500));
                    pause.setOnFinished(f -> {
                        WinnerAlertBox.display(currentPlayer, opponent, boardGUI);
                    });
                    pause.play();
                } else {
                    currentPlayer = currentPlayer.opposite();
                }
            } else if (currentPlayer == RED && opponent == HUMAN) {
                displayText("Red player turn.");
            } else if (currentPlayer == BLACK && opponent == HUMAN) {
                displayText("Black player turn.");
            }
        }
    }

    /** Returns true if the game is over; returns false otherwise. */
    static boolean gameOver() {
        return board.getRedPieces().size() == 0 || board.getBlackPieces().size() == 0;
    }

    /** Attempts to make a legal move based on the current player's input. Makes the move if it is legal and returns true; does not
     *  make a move and returns false otherwise. PIECE is the moving or attacking piece, DESTTILE is the tile to which the piece is
     *  attempting to move, and DESTROW, DESTCOL is the location of DESTTILE on the board. */
    private static boolean attemptMove(BorderPane destTile, Piece piece, int destRow, int destCol) {
        BorderPane currTile = findTile(piece.getCurrRow(), piece.getCurrCol());
        LinkedList<int[]> possibleAttacks = searchForAttacks(piece);
        Text capturedBlackCount = boardGUI.getCapturedBlackCount();

        if (possibleAttacks.size() > 0) {

            for (int[] attack : possibleAttacks) {
                if ((attack[0] - 1) == destRow && (attack[1] - 1) == destCol) {
                    addPieceToSidePanel(board.getPieceGUILoc(attack[2] - 1, attack[3] - 1));
                    capturedBlackCount.setText((Integer.parseInt(capturedBlackCount.getText()) + 1) + "");
                    board.removePieceAbstractionLoc(attack[2], attack[3]);
                    BorderPane capturedPieceTile = findTile(attack[2] - 1, attack[3] - 1);

                    capturedPieceTile.getChildren().clear();
                    currTile.getChildren().clear();
                    board.movePiece(piece.getCurrRow(), piece.getCurrCol(), destRow, destCol);
                    piece.setLocation(destRow, destCol);

                    possibleAttacks = searchForAttacks(piece);

                    if (possibleAttacks.size() > 0) {
                        destTile.setCenter(piece.getGUIpiece());
                        additionalAttack = true;
                        return false;

                    } else {

                        if (piece.getType() == REGULAR && currentPlayer == RED && destRow == 0) {
                            makeKing(piece, currentPlayer);
                        } else if (piece.getType() == REGULAR && currentPlayer == BLACK && destRow == 7) {
                            makeKing(piece, currentPlayer);
                        }

                        unselectPiece(piece, piece.getGUIpiece());
                        destTile.setCenter(piece.getGUIpiece());
                        selectedPiece = null;
                        selected = false;
                        currentPlayer = currentPlayer.opposite();
                        additionalAttack = false;
                        return true;
                    }
                }

            }
        } else if (legalMove(piece, destTile, destRow, destCol)) {

            if (piece.getType() == REGULAR && currentPlayer == RED && destRow == 0) {
                makeKing(piece, currentPlayer);
            } else if (piece.getType() == REGULAR && currentPlayer == BLACK && destRow == 7) {
                makeKing(piece, currentPlayer);
            }

            currTile.getChildren().clear();
            unselectPiece(piece, piece.getGUIpiece());
            destTile.setCenter(piece.getGUIpiece());
            board.movePiece(piece.getCurrRow(), piece.getCurrCol(), destRow, destCol);
            piece.setLocation(destRow, destCol);
            selectedPiece = null;
            selected = false;
            currentPlayer = currentPlayer.opposite();
            return true;
        }
        return false;
    }

    /** Adds the captured PIECE to one of the side panels adjacent to the Checkers board depending on the color of PIECE. */
    static void addPieceToSidePanel(Piece piece) {
        VBox innerPanel;

        if (piece.getColor() == RED) {
            innerPanel = (VBox) boardGUI.getRightPanel().getChildren().get(0);
            innerPanel.getChildren().add(piece.getGUIpiece());
        } else {
            innerPanel = (VBox) boardGUI.getLeftPanel().getChildren().get(2);
            innerPanel.getChildren().add(piece.getGUIpiece());
        }
    }

    /** Converts PIECE to type King based on CURRENTPLAYER. */
    static void makeKing(Piece piece, PieceColor currentPlayer) {
        Circle GUIpiece = piece.getGUIpiece();

        if (currentPlayer == RED) {
            GUIpiece.setFill(Color.web(WHITE));
            GUIpiece.setStroke(Color.web(REDSTROKE));
            GUIpiece.setStrokeWidth(KINGSTROKE);
        } else {
            GUIpiece.setFill(Color.web(WHITE));
            GUIpiece.setStrokeWidth(KINGSTROKE);
        }
        piece.setType(KING);
        piece.setPermanentKing();
    }

    /** Searches for all possible attacks that PIECE may have. If PIECE does have one or more available attacks, returns a Linked
     *  List which contains them. */
    static LinkedList<int[]> searchForAttacks(Piece piece) {
        int currRow = piece.getBoardAbstractionRow();
        int currCol = piece.getBoardAbstractionCol();
        LinkedList<int[]> possibleAttacks = new LinkedList<>();
        Piece possibleEnemy;

        if (piece.getType() == KING) {

            possibleEnemy = board.getPieceAbstractionLoc(currRow - 1, currCol - 1);
            if (possibleEnemy != null && possibleEnemy.getType() != BLOCK && possibleEnemy.getColor() == piece.getColor().opposite()) {
                if (board.getPieceAbstractionLoc(currRow - 2, currCol - 2) == null) {
                    possibleAttacks.add(new int[]{currRow - 2, currCol - 2, currRow - 1, currCol - 1, currRow, currCol});
                }
            }

            possibleEnemy = board.getPieceAbstractionLoc(currRow - 1, currCol + 1);
            if (possibleEnemy != null && possibleEnemy.getType() != BLOCK && possibleEnemy.getColor() == piece.getColor().opposite()) {
                if (board.getPieceAbstractionLoc(currRow - 2, currCol + 2) == null) {
                    possibleAttacks.add(new int[]{currRow - 2, currCol + 2, currRow - 1, currCol + 1, currRow, currCol});
                }
            }

            possibleEnemy = board.getPieceAbstractionLoc(currRow + 1, currCol - 1);
            if (possibleEnemy != null && possibleEnemy.getType() != BLOCK  && possibleEnemy.getColor() == piece.getColor().opposite()) {
                if (board.getPieceAbstractionLoc(currRow + 2, currCol - 2) == null) {
                    possibleAttacks.add(new int[]{currRow + 2, currCol - 2, currRow + 1, currCol - 1, currRow, currCol});
                }
            }

            possibleEnemy = board.getPieceAbstractionLoc(currRow + 1, currCol + 1);
            if (possibleEnemy != null && possibleEnemy.getType() != BLOCK  && possibleEnemy.getColor() == piece.getColor().opposite()) {
                if (board.getPieceAbstractionLoc(currRow + 2, currCol + 2) == null) {
                    possibleAttacks.add(new int[]{currRow + 2, currCol + 2, currRow + 1, currCol + 1, currRow, currCol});
                }
            }
        } else {
            if (piece.getColor() == RED) {

                possibleEnemy = board.getPieceAbstractionLoc(currRow - 1, currCol - 1);
                if (possibleEnemy != null && possibleEnemy.getType() != BLOCK  && possibleEnemy.getColor() == piece.getColor().opposite()) {
                    if (board.getPieceAbstractionLoc(currRow - 2, currCol - 2) == null) {
                        possibleAttacks.add(new int[]{currRow - 2, currCol - 2, currRow - 1, currCol - 1, currRow, currCol});
                    }
                }

                possibleEnemy = board.getPieceAbstractionLoc(currRow - 1, currCol + 1);
                if (possibleEnemy != null && possibleEnemy.getType() != BLOCK  && possibleEnemy.getColor() == piece.getColor().opposite()) {
                    if (board.getPieceAbstractionLoc(currRow - 2, currCol + 2) == null) {
                        possibleAttacks.add(new int[]{currRow - 2, currCol + 2, currRow - 1, currCol + 1, currRow, currCol});
                    }
                }
            } else {

                possibleEnemy = board.getPieceAbstractionLoc(currRow + 1, currCol - 1);
                if (possibleEnemy != null && possibleEnemy.getType() != BLOCK  && possibleEnemy.getColor() == piece.getColor().opposite()) {
                    if (board.getPieceAbstractionLoc(currRow + 2, currCol - 2) == null) {
                        possibleAttacks.add(new int[]{currRow + 2, currCol - 2, currRow + 1, currCol - 1, currRow, currCol});
                    }
                }

                possibleEnemy = board.getPieceAbstractionLoc(currRow + 1, currCol + 1);
                if (possibleEnemy != null && possibleEnemy.getType() != BLOCK  && possibleEnemy.getColor() == piece.getColor().opposite()) {
                    if (board.getPieceAbstractionLoc(currRow + 2, currCol + 2) == null) {
                        possibleAttacks.add(new int[]{currRow + 2, currCol + 2, currRow + 1, currCol + 1, currRow, currCol});
                    }
                }
            }
        }

        return possibleAttacks;
    }

    /** Return true if the tile located at DESTROW, DESTCOL can be legally moved to based on
     *  the location and type of PIECE. */
    private static boolean legalMove(Piece piece, BorderPane destTile, int destRow, int destCol) {
        int currRow = piece.getCurrRow();
        int currCol = piece.getCurrCol();
        int rowDiff = currRow - destRow;
        int colDiff = Math.abs(currCol - destCol);

        if (piece.getColor() == RED) {
            if (piece.getType() == KING) {
                return destTile.getChildren().size() == 0 && Math.abs(rowDiff) == 1 && colDiff == 1;
            } else {
                return destTile.getChildren().size() == 0 && rowDiff == 1 && colDiff == 1;
            }
        } else {
            if (piece.getType() == KING) {
                return destTile.getChildren().size() == 0 && Math.abs(rowDiff) == 1 && colDiff == 1;
            } else {
                return destTile.getChildren().size() == 0 && rowDiff == -1 && colDiff == 1;
            }
        }
    }

    /** Returns the tile located at ROW, COL in the board GUI. */
    static BorderPane findTile(int row, int col) {
        ObservableList<Node> tiles = boardGUI.getGUI().getChildren();

        for (Node tile : tiles) {
            if (GridPane.getRowIndex(tile) == row && GridPane.getColumnIndex(tile) == col) {
                return (BorderPane) tile;
            }
        }
        return null;
    }

    /** If the piece clicked on is a piece of the player with the current turn, the piece lights up
     *  and is recognized as being selected by this class. */
    static void pieceClick(Object obj) {
        Circle GUIpiece = (Circle) obj;
        int row = GridPane.getRowIndex(GUIpiece.getParent());
        int col = GridPane.getColumnIndex(GUIpiece.getParent());
        boolean attackSelected = false;
        Piece boardPiece = board.getPieceGUILoc(row, col);
        LinkedList<Piece> attackerPieces = new LinkedList<>();

        if (additionalAttack) {
            return;
        }

        if (boardPiece.getColor() == currentPlayer) {

            if (currentPlayer == RED) {
                for (Piece redPiece : board.getRedPieces()) {
                    LinkedList<int[]> possibleAttacks = searchForAttacks(redPiece);

                    if (possibleAttacks.size() > 0) {
                        attackerPieces.add(redPiece);
                    }
                }
            } else {
                for (Piece blackPiece : board.getBlackPieces()) {
                    LinkedList<int[]> possibleAttacks = searchForAttacks(blackPiece);

                    if (possibleAttacks.size() > 0) {
                        attackerPieces.add(blackPiece);
                    }
                }
            }
            if (attackerPieces.size() > 0) {
                for (Piece attackerPiece : attackerPieces) {
                    int currRow = attackerPiece.getCurrRow();
                    int currCol = attackerPiece.getCurrCol();

                    if (currRow == row && currCol == col) {
                        attackSelected = true;
                    }
                }

                if (!attackSelected) {
                    displayText("You are able to make an attack. You must do so.");
                    return;
                }
            }

            if (!selected) {
                selectPiece(boardPiece, GUIpiece);
                selected = true;
                selectedPiece = boardPiece;
            } else if (boardPiece.isSelected()) {
                unselectPiece(boardPiece, GUIpiece);
                selected = false;
                selectedPiece = null;
            } else {
                Piece prevBoardPiece = selectedPiece;
                Circle prevGUIpiece = prevBoardPiece.getGUIpiece();

                unselectPiece(prevBoardPiece, prevGUIpiece);

                selectPiece(boardPiece, GUIpiece);
                selectedPiece = boardPiece;
            }
        }
    }

    /** Lights up GUIPIECE on the board GUI that corresponds to the location of BOARDPIECE. This gives the
     *  current player a visual confirmation that BOARDPIECE has been selected for movement. */
    private static void selectPiece(Piece boardPiece, Circle GUIpiece) {
        if (boardPiece.getColor() == RED) {
            GUIpiece.setFill(Color.web(SELECTRED));
        } else {
            GUIpiece.setFill(Color.web(SELECTBLACK));
        }
        boardPiece.select(true);
    }

    /** Returns GUIPIECE to its normal color on the board GUI which corresponds to the location of BOARDPIECE.
     *  This gives the current player a visual confirmation that BOARDPIECE has been unselected. */
    private static void unselectPiece(Piece boardPiece, Circle GUIpiece) {
        if (boardPiece.getType() == KING) {
            GUIpiece.setFill(Color.web(WHITE));
        } else if (boardPiece.getColor() == RED) {
            GUIpiece.setFill(Color.web(UNSELECTRED));
        } else {
            GUIpiece.setFill(Color.web(UNSELECTBLACK));
        }
        boardPiece.select(false);
    }
}
