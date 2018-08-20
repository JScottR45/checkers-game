package checkers;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.*;

class HomeGUI {
    /** The window of this GUI. */
    private Stage window;
    /** The length and width of a Checkers board in terms of number of tiles. */
    private static final int SIZE = 8;
    /** The height of each tile in terms of pixels. */
    private static final int PREFWIDTH = 50;
    /** The width of each tile in terms of pixels. */
    private static final int PREFHEIGHT = 50;
    /** The color of the lightly colored tiles. */
    private static final String LIGHTCOLOR = "-fx-background-color: #FFFFFF";
    /** The color of the darkly colored tiles. */
    private static final String DARKCOLOR = "-fx-background-color: #b3b3b3";
    /** The degree to which the corners of buttons are rounded.  */
    private static final String BUTTONRADIUS = "-fx-background-radius: 20 20 20 20;";
    /** The radius of the GUI board pieces in terms of pixels. */
    private static final int PIECERADIUS = 15;
    /** The type of red that colors the red GUI board pieces. */
    private static final String RED = "#B30000";
    /** The type of black that colors the black GUI board pieces. */
    private static final String LIGHTBLACK = "#333333";
    /** The green background of the window. */
    private static final String BACKGROUND = "-fx-background-color: #005712";

    private static final String BACKGROUND_2 = "-fx-background-color: #5a2d0c";

    private static final String BLACK = "#000000";
    /** The color of text. */
    private static final String TEXTCOLOR = "-fx-text-fill: #e6e6e6";

    /** Calls createHomeGUI which creates the home page GUI. */
    HomeGUI() {
        createHomeGUI();
    }

    /** Returns the window of this GUI. */
    Stage getWindow() {
        return window;
    }

    /** Creates the home page GUI. */
    private void createHomeGUI() {
        BorderStroke borderStroke1 = new BorderStroke(Color.web(BLACK), BorderStrokeStyle.SOLID, new CornerRadii(0), BorderStroke.MEDIUM);
        BorderStroke borderStroke2 = new BorderStroke(null, Color.web(BLACK), null, Color.web(BLACK), null,
                BorderStrokeStyle.SOLID, null, BorderStrokeStyle.SOLID, new CornerRadii(0), BorderStroke.MEDIUM, null);

        Label welcome = new Label("Welcome to Checkers");
        welcome.setFont(new Font(43));
        welcome.setStyle(TEXTCOLOR);

        Button play = new Button("Play");
        play.setFont(new Font(19));
        play.setStyle(BUTTONRADIUS);
        play.setOnAction(f -> {
            window.close();
            OpponentSelectorBox.display();
        });

        VBox topPanel = new VBox();
        topPanel.setPrefHeight(130);
        topPanel.setAlignment(Pos.TOP_CENTER);

        HBox topBorder = new HBox();
        topBorder.setStyle(BACKGROUND_2);
        topBorder.setPrefSize(560, 30);
        topBorder.setBorder(new Border(borderStroke1));

        HBox topContainer = new HBox();
        topBorder.setAlignment(Pos.CENTER);
        topContainer.setPrefSize(560, 100);

        VBox topLeftBorder = new VBox();
        topLeftBorder.setStyle(BACKGROUND_2);
        topLeftBorder.setBorder(new Border(borderStroke2));
        topLeftBorder.setPrefSize(30, 100);

        HBox labelHolder = new HBox();
        labelHolder.setAlignment(Pos.CENTER);
        labelHolder.setPrefSize(500, 100);
        labelHolder.getChildren().add(welcome);

        VBox topRightBorder = new VBox();
        topRightBorder.setStyle(BACKGROUND_2);
        topRightBorder.setBorder(new Border(borderStroke2));
        topRightBorder.setPrefSize(30, 100);

        topContainer.getChildren().addAll(topLeftBorder, labelHolder,  topRightBorder);

        HBox leftPanel = new HBox();
        leftPanel.setPrefWidth(80);
        leftPanel.setAlignment(Pos.CENTER_LEFT);

        VBox leftBorder = new VBox();
        leftBorder.setStyle(BACKGROUND_2);
        leftBorder.setPrefSize(30, 400);
        leftBorder.setBorder(new Border(borderStroke2));

        HBox rightPanel = new HBox();
        rightPanel.setPrefWidth(80);
        rightPanel.setAlignment(Pos.CENTER_RIGHT);

        VBox rightBorder = new VBox();
        rightBorder.setStyle(BACKGROUND_2);
        rightBorder.setPrefSize(30, 400);
        rightBorder.setBorder(new Border(borderStroke2));

        VBox bottomPanel = new VBox();
        bottomPanel.setPrefSize(560, 105);
        bottomPanel.setAlignment(Pos.BOTTOM_CENTER);

        HBox bottomBorder = new HBox();
        bottomBorder.setStyle(BACKGROUND_2);
        bottomBorder.setPrefSize(560, 30);
        bottomBorder.setBorder(new Border(borderStroke1));

        HBox bottomContainer = new HBox();
        bottomBorder.setAlignment(Pos.CENTER);
        bottomContainer.setPrefSize(560, 75);

        VBox bottomLeftBorder = new VBox();
        bottomLeftBorder.setStyle(BACKGROUND_2);
        bottomLeftBorder.setBorder(new Border(borderStroke2));
        bottomLeftBorder.setPrefSize(30, 75);

        HBox buttonHolder = new HBox();
        buttonHolder.setAlignment(Pos.CENTER);
        buttonHolder.setPrefSize(500, 75);
        buttonHolder.getChildren().add(play);

        VBox bottomRightBorder = new VBox();
        bottomRightBorder.setStyle(BACKGROUND_2);
        bottomRightBorder.setBorder(new Border(borderStroke2));
        bottomRightBorder.setPrefSize(30, 75);

        bottomContainer.getChildren().addAll(bottomLeftBorder, buttonHolder, bottomRightBorder);

        GridPane centerPanel = createCheckersDesign();

        topPanel.getChildren().addAll(topBorder, topContainer);
        leftPanel.getChildren().add(leftBorder);
        rightPanel.getChildren().add(rightBorder);
        bottomPanel.getChildren().addAll(bottomContainer, bottomBorder);

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle(BACKGROUND);
        borderPane.setTop(topPanel);
        borderPane.setLeft(leftPanel);
        borderPane.setRight(rightPanel);
        borderPane.setCenter(centerPanel);
        borderPane.setBottom(bottomPanel);

        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(new Scene(borderPane, 560, 635));
        stage.setTitle("Checkers");

        window = stage;
    }

    /** Creates and places the checkers board design on the front of the GUI. */
    private GridPane createCheckersDesign() {
        int colorIndicator = 0;
        GridPane gridPane = new GridPane();
        BorderPane tile;
        Board board = new Board();
        Circle GUIpiece;

        for (int i = 0; i < SIZE; i++) {

            for (int j = 0; j < SIZE; j++) {
                tile = new BorderPane();

                if (colorIndicator == 0) {
                    tile.setStyle(LIGHTCOLOR);
                    colorIndicator = 1;
                } else {
                    tile.setStyle(DARKCOLOR);
                    colorIndicator = 0;
                }
                tile.setPrefSize(PREFWIDTH, PREFHEIGHT);
                gridPane.add(tile, i, j);
            }
            if (colorIndicator == 1) {
                colorIndicator = 0;
            } else {
                colorIndicator = 1;
            }
        }
        for (RedPiece redPiece : board.getRedPieces()) {
            int row = redPiece.getCurrRow();
            int col = redPiece.getCurrCol();

            for (Node child : gridPane.getChildren()) {
                if (GridPane.getRowIndex(child) == row && GridPane.getColumnIndex(child) == col) {
                    tile = (BorderPane) child;
                    GUIpiece = new Circle(PIECERADIUS);
                    GUIpiece.setFill(Color.web(RED));
                    tile.setCenter(GUIpiece);
                }
            }
        }
        for (BlackPiece blackPiece : board.getBlackPieces()) {
            int row = blackPiece.getCurrRow();
            int col = blackPiece.getCurrCol();

            for (Node child : gridPane.getChildren()) {
                if (GridPane.getRowIndex(child) == row && GridPane.getColumnIndex(child) == col) {
                    tile = (BorderPane) child;
                    GUIpiece = new Circle(PIECERADIUS);
                    GUIpiece.setFill(Color.web(LIGHTBLACK));
                    tile.setCenter(GUIpiece);
                }
            }
        }
        return gridPane;
    }
}
