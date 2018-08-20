package checkers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.*;

import java.util.LinkedList;

class BoardGUI {
    /** The board abstraction for this Checkers game. */
    private Board board;
    /** The window of this GUI. */
    private Stage window;
    /** The board component of this GUI. */
    private GridPane boardGUI;
    /** The left side panel adjacent to the Checkers board (Used to display captured black pieces). */
    private HBox leftPanel;
    /** The right side panel adjacent to the Checkers board (Used to display captured red pieces). */
    private HBox rightPanel;
    /** The box atop the Checkers board used to display messages to the current player. */
    private Text messageBox;

    private Text capturedRedCount;

    private Text capturedBlackCount;
    /** The length and width of the Checkers board in terms of number of tiles. */
    private static final int SIZE = 8;
    /** The height of each tile in terms of pixels. */
    private static final int PREFWIDTH = 90;
    /** The width of each tile in terms of pixels. */
    private static final int PREFHEIGHT = 90;
    /** The radius of the GUI board pieces in terms of pixels. */
    private static final double RADIUS = 22.5;
    /** The width of the border around the GUI pieces in terms of pixels.  */
    private static final double STROKE = 2.5;
    /** The color of the lightly colored tiles. */
    private static final String LIGHTCOLOR = "-fx-background-color: #FFFFFF";
    /** The color of the darkly colored tiles. */
    private static final String DARKCOLOR = "-fx-background-color: #b3b3b3";
    /** The type of red that colors the red GUI board pieces. */
    private static final String RED = "#B30000";
    /** The type of black that colors the black GUI board pieces. */
    private static final String LIGHTBLACK = "#333333";
    /** The color of the borders around each GUI board piece. */
    private static final String BLACK = "#000000";
    /** The color gold. */
    private static final String GOLD = "#ffd700";
    /** The green background of the game window. */
    private static final String BACKGROUND = "-fx-background-color: #005712";
    /** The color of the wood borders which surround the game window. */
    private static final String BACKGROUND_2 = "-fx-background-color: #5a2d0c";
    /** The color of the areas of the side panels which display captured pieces. */
    private static final String DARK_GREEN = "-fx-background-color: #003D00";
    /** The color of the message box. */
    private static final String MESSAGE_BOX_COLOR = "-fx-background-color: #000000";
    /** A black border. */
    private static final String BLACKBORDER = "-fx-background-color: #000000";
    /** The degree to which the corners of buttons are rounded. */
    private static final String BUTTONRADIUS = "-fx-background-radius: 20 20 20 20;";

    /** Generates a Checkers board GUI. */
    BoardGUI(Board brd) {
        board = brd;
        createGUI();
    }

    /** Return the window of this GUI. */
    Stage getWindow() {
        return window;
    }

    /** Return the board component of this GUI. */
    GridPane getGUI() {
        return boardGUI;
    }

    /** Returns the left panel which displays captured black pieces. */
    HBox getLeftPanel() {
        return leftPanel;
    }

    /** Returns the right panel which displays captured red pieces. */
    HBox getRightPanel() {
        return rightPanel;
    }

    /** Returns the message box. */
    Text getMessageBox() {
        return messageBox;
    }

    Text getCapturedRedCount() {
        return capturedRedCount;
    }

    Text getCapturedBlackCount() {
        return capturedBlackCount;
    }

    /** Returns the Checkers board abstraction. */
    Board getBoard() {
        return board;
    }

    /** Creates and initializes the board GUI for this Checkers game based on the input of the
     *  BoardGUI constructor. */
    private void createGUI() {
        int row;
        int col;
        int colorIndicator = 0;
        BorderPane borderPane;
        BorderPane tile;
        Circle circle;
        Stage stage = new Stage();
        GridPane gridPane = new GridPane();
        LinkedList<RedPiece> redPieces = board.getRedPieces();
        LinkedList<BlackPiece> blackPieces = board.getBlackPieces();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {

                borderPane = new BorderPane();

                if (colorIndicator == 0) {
                    borderPane.setStyle(LIGHTCOLOR);
                    colorIndicator = 1;
                } else {
                    borderPane.setStyle(DARKCOLOR);
                    colorIndicator = 0;
                }

                borderPane.setPrefSize(PREFWIDTH, PREFHEIGHT);
                borderPane.setOnMouseClicked(f -> Controller.tileClick(f.getSource()));
                gridPane.add(borderPane, i, j);
            }

            if (colorIndicator == 1) {
                colorIndicator = 0;
            } else {
                colorIndicator = 1;
            }
        }
        for (RedPiece red : redPieces) {
            row = red.getCurrRow();
            col = red.getCurrCol();

            for (Node child : gridPane.getChildren()) {
                if (GridPane.getRowIndex(child) == row && GridPane.getColumnIndex(child) == col) {
                    tile = (BorderPane) child;
                    circle = new Circle(RADIUS);
                    circle.setFill(Color.web(RED));
                    circle.setStroke(Color.web(BLACK));
                    circle.setStrokeWidth(STROKE);
                    circle.setOnMouseClicked(f -> Controller.pieceClick(f.getSource()));
                    tile.setCenter(circle);
                    red.setGUIpiece(circle);
                    break;
                }
            }
        }
        for (BlackPiece black : blackPieces) {
            row = black.getCurrRow();
            col = black.getCurrCol();

            for (Node child : gridPane.getChildren()) {
                if (GridPane.getRowIndex(child) == row && GridPane.getColumnIndex(child) == col) {
                    tile = (BorderPane) child;
                    circle = new Circle(RADIUS);
                    circle.setFill(Color.web(LIGHTBLACK));
                    circle.setStroke(Color.web(BLACK));
                    circle.setStrokeWidth(STROKE);
                    circle.setOnMouseClicked(f -> Controller.pieceClick(f.getSource()));
                    tile.setCenter(circle);
                    black.setGUIpiece(circle);
                    break;
                }
            }
        }

        gridPane.setGridLinesVisible(true);
        BorderStroke borderStroke = new BorderStroke(Color.web(BLACK), BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(1));
        gridPane.setBorder(new Border(borderStroke));

        borderPane = new BorderPane();
        borderPane.setCenter(gridPane);
        addFeatures(borderPane);

        stage.setResizable(false);
        stage.setTitle("Checkers");
        stage.setScene(new Scene(borderPane, 1060, 950));

        boardGUI = gridPane;
        window = stage;
    }

    /** Resets the board and returns it back to its starting state. */
    void resetBoard() {
        BorderPane tile;
        Circle circle;
        VBox innerPanel;

        board = new Board();

        for (Node child : boardGUI.getChildren()) {
            try{
                tile = (BorderPane) child;
                tile.getChildren().clear();
            } catch (ClassCastException ex) {
                continue;
            }
        }
        for (RedPiece piece : board.getRedPieces()) {
            circle = new Circle(RADIUS);
            circle.setFill(Color.web(RED));
            circle.setStroke(Color.web(BLACK));
            circle.setStrokeWidth(STROKE);
            circle.setOnMouseClicked(f -> Controller.pieceClick(f.getSource()));
            piece.setGUIpiece(circle);
            tile = Controller.findTile(piece.getCurrRow(), piece.getCurrCol());
            tile.setCenter(circle);
        }
        for (BlackPiece piece : board.getBlackPieces()) {
            circle = new Circle(RADIUS);
            circle.setFill(Color.web(LIGHTBLACK));
            circle.setStroke(Color.web(BLACK));
            circle.setStrokeWidth(STROKE);
            circle.setOnMouseClicked(f -> Controller.pieceClick(f.getSource()));
            piece.setGUIpiece(circle);
            tile = Controller.findTile(piece.getCurrRow(), piece.getCurrCol());
            tile.setCenter(circle);
        }
        innerPanel = (VBox) leftPanel.getChildren().get(2);
        innerPanel.getChildren().clear();

        innerPanel = (VBox) rightPanel.getChildren().get(0);
        innerPanel.getChildren().clear();

        capturedRedCount.setText("0");
        capturedBlackCount.setText("0");
    }

    /** Adds the side, top, and bottom panels to the Checkers board GUI. */
    private void addFeatures(BorderPane mainWindow) {
        addSidePanels(mainWindow);
        addBottomPanel(mainWindow);
        addTopPanel(mainWindow);
    }

    /** Adds the side panels to the Checkers board GUI. Used to display captured pieces. */
    private void addSidePanels(BorderPane mainWindow) {
        BorderStroke borderStroke1 = new BorderStroke(null, Color.web(BLACK), null, Color.web(BLACK), null,
                BorderStrokeStyle.SOLID, null, BorderStrokeStyle.SOLID, new CornerRadii(0), BorderStroke.MEDIUM, null);

        HBox leftPanel = new HBox();
        leftPanel.setPrefWidth(170);
        leftPanel.setStyle(BACKGROUND);
        leftPanel.setAlignment(Pos.CENTER_LEFT);

        VBox leftBorder = new VBox();
        leftBorder.setPrefSize(30, 920);
        leftBorder.setStyle(BACKGROUND_2);
        leftBorder.setBorder(new Border(borderStroke1));

        VBox leftBuffer = new VBox();
        leftBuffer.setPrefWidth(32.5);

        VBox leftInnerPanel = new VBox(10);
        leftInnerPanel.setPadding(new Insets(15, 0, 15, 0));
        leftInnerPanel.setAlignment(Pos.TOP_CENTER);
        leftInnerPanel.setStyle(DARK_GREEN);
        leftInnerPanel.setPrefWidth(75);
        leftInnerPanel.setMaxHeight(720);

        HBox rightPanel = new HBox();
        rightPanel.setPrefWidth(170);
        rightPanel.setStyle(BACKGROUND);
        rightPanel.setAlignment(Pos.CENTER_RIGHT);

        VBox rightBorder = new VBox();
        rightBorder.setPrefSize(30, 920);
        rightBorder.setStyle(BACKGROUND_2);
        rightBorder.setBorder(new Border(borderStroke1));

        VBox rightBuffer = new VBox();
        rightBuffer.setPrefWidth(32.5);

        VBox rightInnerPanel = new VBox(10);
        rightInnerPanel.setPadding(new Insets(15, 0, 15, 0));
        rightInnerPanel.setAlignment(Pos.TOP_CENTER);
        rightInnerPanel.setStyle(DARK_GREEN);
        rightInnerPanel.setPrefWidth(75);
        rightInnerPanel.setMaxHeight(720);

        leftPanel.getChildren().addAll(leftBorder, leftBuffer, leftInnerPanel);
        rightPanel.getChildren().addAll(rightInnerPanel, rightBuffer, rightBorder);

        mainWindow.setLeft(leftPanel);
        mainWindow.setRight(rightPanel);

        this.leftPanel = leftPanel;
        this.rightPanel = rightPanel;
    }

    /** Adds the bottom panel to the Checkers board GUI. Used to display buttons. */
    private void addBottomPanel(BorderPane mainWindow) {
        BorderStroke borderStroke = new BorderStroke(null, Color.web(BLACK), null, Color.web(BLACK), null,
                BorderStrokeStyle.SOLID, null, BorderStrokeStyle.SOLID, new CornerRadii(0), BorderStroke.MEDIUM, null);

        Button quit = new Button("Quit");
        quit.setFont(new Font(15));
        quit.setStyle(BUTTONRADIUS);
        quit.setOnAction(f -> window.close());

        Button undoMove = new Button("Undo Move");
        undoMove.setFont(new Font(15));
        undoMove.setStyle(BUTTONRADIUS);

        Button restart = new Button("Restart");
        restart.setFont(new Font(15));
        restart.setStyle(BUTTONRADIUS);
        restart.setOnAction(f -> {
            resetBoard();
            Controller.reset(board);
        });

        Button opponentSelection = new Button("Select New Opponent");
        opponentSelection.setFont(new Font(14));
        opponentSelection.setStyle(BUTTONRADIUS);
        opponentSelection.setOnAction(f -> {
            window.close();
            OpponentSelectorBox.display();
        });

        HBox topBuffer = new HBox();
        topBuffer.setPrefSize(1060, 40);
        topBuffer.setAlignment(Pos.CENTER);

        HBox innerBuffer = new HBox();
        innerBuffer.setPrefSize(1000, 40);

        HBox leftBorder = new HBox();
        leftBorder.setPrefSize(30, 40);
        leftBorder.setStyle(BACKGROUND_2);
        leftBorder.setBorder(new Border(borderStroke));

        HBox rightBorder = new HBox();
        rightBorder.setPrefSize(30, 40);
        rightBorder.setStyle(BACKGROUND_2);
        rightBorder.setBorder(new Border(borderStroke));

        topBuffer.getChildren().addAll(leftBorder, innerBuffer, rightBorder);

        borderStroke = new BorderStroke(Color.web(BLACK), BorderStrokeStyle.SOLID, new CornerRadii(0), BorderStroke.MEDIUM);

        HBox buttonPanel = new HBox(20);
        buttonPanel.setPrefSize(1066, 60);
        buttonPanel.setAlignment(Pos.CENTER);
        buttonPanel.setStyle(BACKGROUND_2);
        buttonPanel.setBorder(new Border(borderStroke));
        buttonPanel.getChildren().addAll(restart, opponentSelection, quit);

        HBox buttonPanelDivider = new HBox();
        buttonPanelDivider.setPrefSize(1000, 3);
        buttonPanelDivider.setStyle(BLACKBORDER);

        VBox bottomPanel = new VBox();
        bottomPanel.setPrefHeight(100);
        bottomPanel.setStyle(BACKGROUND);
        bottomPanel.getChildren().addAll(topBuffer, buttonPanel);

        mainWindow.setBottom(bottomPanel);
    }

    /** Adds the top panel to the Checkers board GUI. Used to display messages to the current player. */
    private void addTopPanel(BorderPane mainWindow) {
        BorderStroke borderStroke1 = new BorderStroke(null, Color.web(BLACK), null, Color.web(BLACK), null,
                BorderStrokeStyle.SOLID, null, BorderStrokeStyle.SOLID, new CornerRadii(0), BorderStroke.MEDIUM, null);
        BorderStroke borderStroke2 = new BorderStroke(Color.web(GOLD), BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(1));
        BorderStroke borderStroke3 = new BorderStroke(Color.web(BLACK), BorderStrokeStyle.SOLID, new CornerRadii(0), BorderStroke.MEDIUM);

        HBox buffer = new HBox();
        buffer.setPrefSize(1060, 100);
        buffer.setAlignment(Pos.CENTER);

        Text capturedBlackCount = new Text();
        capturedBlackCount.setFont(new Font(18));
        capturedBlackCount.setFill(Color.web("#FFFFFF"));
        capturedBlackCount.setText("0");

        HBox capturedBlackCountContainer = new HBox();
        capturedBlackCountContainer.setPrefWidth(40);
        capturedBlackCountContainer.setMaxHeight(40);
        capturedBlackCountContainer.setAlignment(Pos.CENTER);
        capturedBlackCountContainer.setStyle("-fx-background-color: #000000");
        capturedBlackCountContainer.setBorder(new Border(borderStroke2));
        capturedBlackCountContainer.getChildren().add(capturedBlackCount);

        HBox leftInnerBuffer = new HBox();
        leftInnerBuffer.setPrefSize(200, 70);
        leftInnerBuffer.setAlignment(Pos.BOTTOM_LEFT);
        leftInnerBuffer.setPadding(new Insets(0, 0, 10, 50));
        leftInnerBuffer.getChildren().add(capturedBlackCountContainer);

        Text capturedRedCount = new Text();
        capturedRedCount.setFont(new Font(18));
        capturedRedCount.setFill(Color.web("#FFFFFF"));
        capturedRedCount.setText("0");

        HBox capturedRedCountContainer = new HBox();
        capturedRedCountContainer.setPrefWidth(40);
        capturedRedCountContainer.setMaxHeight(40);
        capturedRedCountContainer.setAlignment(Pos.CENTER);
        capturedRedCountContainer.setStyle("-fx-background-color: #000000");
        capturedRedCountContainer.setBorder(new Border(borderStroke2));
        capturedRedCountContainer.getChildren().add(capturedRedCount);

        HBox rightInnerBuffer = new HBox();
        rightInnerBuffer.setPrefSize(200, 70);
        rightInnerBuffer.setAlignment(Pos.BOTTOM_RIGHT);
        rightInnerBuffer.setPadding(new Insets(0, 50, 10, 0));
        rightInnerBuffer.getChildren().add(capturedRedCountContainer);

        HBox leftBorder = new HBox();
        leftBorder.setPrefSize(30, 100);
        leftBorder.setStyle(BACKGROUND_2);
        leftBorder.setBorder(new Border(borderStroke1));

        HBox rightBorder = new HBox();
        rightBorder.setPrefSize(30, 100);
        rightBorder.setStyle(BACKGROUND_2);
        rightBorder.setBorder(new Border(borderStroke1));

        Text messageBox = new Text();
        messageBox.setFont(new Font(18));
        messageBox.setFill(Color.web("#FFFFFF"));

        HBox messageBoxContainer = new HBox();
        messageBoxContainer.setPrefWidth(600);
        messageBoxContainer.setMaxHeight(50);
        messageBoxContainer.setStyle(MESSAGE_BOX_COLOR);
        messageBoxContainer.setAlignment(Pos.CENTER);
        messageBoxContainer.getChildren().add(messageBox);
        messageBoxContainer.setBorder(new Border(borderStroke2));

        buffer.getChildren().addAll(leftBorder, leftInnerBuffer, messageBoxContainer, rightInnerBuffer, rightBorder);

        HBox topBorder = new HBox();
        topBorder.setPrefSize(1060, 30);
        topBorder.setStyle(BACKGROUND_2);
        topBorder.setBorder(new Border(borderStroke3));

        VBox topPanel = new VBox();
        topPanel.setPrefSize(1060, 130);
        topPanel.setAlignment(Pos.CENTER);
        topPanel.setStyle(BACKGROUND);
        topPanel.getChildren().addAll(topBorder, buffer);

        mainWindow.setTop(topPanel);

        this.messageBox = messageBox;
        this.capturedBlackCount = capturedBlackCount;
        this.capturedRedCount = capturedRedCount;
    }
}
