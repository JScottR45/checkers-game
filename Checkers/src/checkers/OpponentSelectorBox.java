package checkers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.*;

class OpponentSelectorBox {
    /** The degree to which the corners of buttons are rounded. */
    private static final String BUTTONRADIUS = "-fx-background-radius: 20 20 20 20;";
    /** The green background of this GUI. */
    private static final String BACKGROUND = "-fx-background-color: #005712";

    private static final String BACKGROUND_2 = "-fx-background-color: #5a2d0c";
    /** The color of text. */
    private static final String TEXTCOLOR = "-fx-text-fill: #e6e6e6";

    private static final String BLACK = "#000000";

    /** The radius of the GUI board pieces in terms of pixels. */
    private static final double RADIUS = 25;
    /** The width of the border around the GUI pieces in terms of pixels.  */
    private static final double STROKE = 2.5;

    /** The type of red that colors the red GUI board pieces. */
    private static final String RED = "#B30000";

    /** The type of black that colors the black GUI board pieces. */
    private static final String LIGHTBLACK = "#333333";

    /** Creates and displays the opponent selector GUI. */
    static void display() {
        Stage stage = new Stage();
        BorderPane borderPane = new BorderPane();
        BorderStroke borderStroke1 = new BorderStroke(Color.web(BLACK), BorderStrokeStyle.SOLID, new CornerRadii(0), BorderStroke.MEDIUM);
        BorderStroke borderStroke2 = new BorderStroke(null, Color.web(BLACK), null, Color.web(BLACK), null,
                BorderStrokeStyle.SOLID, null, BorderStrokeStyle.SOLID, new CornerRadii(0), BorderStroke.MEDIUM, null);

        Label header = new Label("Opponent Selection");
        header.setFont(new Font(40));
        header.setStyle(TEXTCOLOR);

        Label computerOptions = new Label("Computer Opponent Options...");
        computerOptions.setFont(new Font(22));
        computerOptions.setStyle(TEXTCOLOR);

        Button human = new Button("Human");
        human.setFont(new Font(15.5));
        human.setStyle(BUTTONRADIUS);
        human.setOnAction(f -> {
            stage.close();
            Controller.playHumanVsHuman();
        });

        Button computerEasy = new Button("Computer Easy");
        computerEasy.setFont(new Font(15.5));
        computerEasy.setStyle(BUTTONRADIUS);
        computerEasy.setOnAction(f -> {
            stage.close();
            Controller.playHumanVsComputerEasy();
        });

        Button computerMedium = new Button("Computer Medium");
        computerMedium.setFont(new Font(15.5));
        computerMedium.setStyle(BUTTONRADIUS);
        computerMedium.setOnAction(f -> {
            stage.close();
            Controller.playHumanVsComputerMedium();
        });

        Button computerHard = new Button("Computer Hard");
        computerHard.setFont(new Font(15.5));
        computerHard.setStyle(BUTTONRADIUS);
        computerHard.setOnAction(f -> {
            stage.close();
            Controller.playHumanVsComputerHard();
        });


        VBox topPanel = new VBox();
        topPanel.setPrefSize(500, 100);
        topPanel.setAlignment(Pos.TOP_CENTER);

        HBox topBorder = new HBox();
        topBorder.setStyle(BACKGROUND_2);
        topBorder.setPrefSize(500, 30);
        topBorder.setBorder(new Border(borderStroke1));

        HBox topContainer = new HBox();
        topContainer.setAlignment(Pos.CENTER);
        topContainer.setPrefSize(500, 70);

        VBox topLeftBorder = new VBox();
        topLeftBorder.setStyle(BACKGROUND_2);
        topLeftBorder.setPrefSize(30, 70);
        topLeftBorder.setBorder(new Border(borderStroke2));

        HBox headerContainer = new HBox();
        headerContainer.setPrefSize(440, 70);
        headerContainer.setPadding(new Insets(20, 0, 0, 0));
        headerContainer.setAlignment(Pos.CENTER);
        headerContainer.getChildren().add(header);

        VBox topRightBorder = new VBox();
        topRightBorder.setStyle(BACKGROUND_2);
        topRightBorder.setPrefSize(30, 70);
        topRightBorder.setBorder(new Border(borderStroke2));

        topContainer.getChildren().addAll(topLeftBorder, headerContainer, topRightBorder);




        VBox bottomPanel = new VBox();
        bottomPanel.setPrefSize(500, 30);

        HBox bottomBorder = new HBox();
        bottomBorder.setStyle(BACKGROUND_2);
        bottomBorder.setPrefSize(500, 30);
        bottomBorder.setBorder(new Border(borderStroke1));




        HBox leftPanel = new HBox();
        leftPanel.setAlignment(Pos.CENTER_LEFT);
        leftPanel.setPrefSize(30, 220);

        VBox leftBorder = new VBox();
        leftBorder.setStyle(BACKGROUND_2);
        leftBorder.setBorder(new Border(borderStroke2));
        leftBorder.setPrefSize(30, 220);




        HBox rightPanel = new HBox();
        rightPanel.setAlignment(Pos.CENTER_RIGHT);
        rightPanel.setPrefSize(30, 220);

        VBox rightBorder = new VBox();
        rightBorder.setStyle(BACKGROUND_2);
        rightBorder.setBorder(new Border(borderStroke2));
        rightBorder.setPrefSize(30, 220);


        Circle redPiece1 = new Circle(RADIUS);
        redPiece1.setFill(Color.web(RED));
        redPiece1.setStroke(Color.web(BLACK));
        redPiece1.setStrokeWidth(STROKE);

        Circle blackPiece1 = new Circle(RADIUS);
        blackPiece1.setFill(Color.web(LIGHTBLACK));
        blackPiece1.setStroke(Color.web(BLACK));
        blackPiece1.setStrokeWidth(STROKE);

        Circle redPiece2 = new Circle(RADIUS);
        redPiece2.setFill(Color.web(RED));
        redPiece2.setStroke(Color.web(BLACK));
        redPiece2.setStrokeWidth(STROKE);

        Circle blackPiece2 = new Circle(RADIUS);
        blackPiece2.setFill(Color.web(LIGHTBLACK));
        blackPiece2.setStroke(Color.web(BLACK));
        blackPiece2.setStrokeWidth(STROKE);

        HBox centerPanel = new HBox();
        centerPanel.setPrefSize(440, 220);
        centerPanel.setAlignment(Pos.CENTER);

        VBox leftPieces = new VBox(70);
        leftPieces.setPrefSize(100, 220);
        leftPieces.setAlignment(Pos.CENTER_RIGHT);
        //leftPieces.getChildren().addAll(blackPiece1, redPiece1);

        VBox rightPieces = new VBox(70);
        rightPieces.setPrefSize(100, 220);
        rightPieces.setAlignment(Pos.CENTER_LEFT);
        //rightPieces.getChildren().addAll(blackPiece2, redPiece2);


        VBox buttonPanel = new VBox(20);
        buttonPanel.setAlignment(Pos.CENTER);
        buttonPanel.setPrefSize(240, 220);
        buttonPanel.setPadding(new Insets(0, 0, 10, 0));
        buttonPanel.getChildren().addAll(human, computerEasy, computerMedium, computerHard);

        centerPanel.getChildren().addAll(leftPieces, buttonPanel, rightPieces);



        topPanel.getChildren().addAll(topBorder, topContainer);
        bottomPanel.getChildren().add(bottomBorder);
        leftPanel.getChildren().add(leftBorder);
        rightPanel.getChildren().add(rightBorder);

        borderPane.setStyle(BACKGROUND);
        borderPane.setTop(topPanel);
        borderPane.setBottom(bottomPanel);
        borderPane.setLeft(leftPanel);
        borderPane.setRight(rightPanel);
        borderPane.setCenter(centerPanel);

        Scene scene = new Scene(borderPane, 500, 370);
        stage.setTitle("Checkers");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
