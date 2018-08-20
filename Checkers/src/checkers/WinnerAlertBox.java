package checkers;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static checkers.PieceColor.*;
import static checkers.OpponentType.*;

class WinnerAlertBox {
    /** The degree to which the corners of buttons are rounded. */
    private static final String BUTTONRADIUS = "-fx-background-radius: 20 20 20 20;";
    /** The black background used when the black player wins the game. */
    private static final String BLACK_BACKGROUND = "-fx-background-color: #262626";
    /** The red background used when the red player wins the game. */
    private static final String RED_BACKGROUND = "-fx-background-color: #660000";
    /** The color of text. */
    private static final String TEXTCOLOR = "-fx-text-fill: #e6e6e6";

    /** Creates and displays the Winner Alert Box GUI. */
    static void display(PieceColor winner, OpponentType opponent, BoardGUI boardGUI) {
        String message;
        Stage stage = new Stage();

        if (winner == RED) {
            if (opponent == HUMAN) {
                message = "RED PLAYER WINS!";
            } else {
                message = "YOU WIN!";
            }
        } else {
            message = "BLACK PLAYER WINS!";
        }

        Label winnerMessage = new Label(message);
        winnerMessage.setStyle(TEXTCOLOR);

        if (winner == RED && opponent != HUMAN) {
            winnerMessage.setFont(new Font(45));
        } else {
            winnerMessage.setFont(new Font(35));
        }

        Button playAgain = new Button("Play Again");
        playAgain.setStyle(BUTTONRADIUS);
        playAgain.setFont(new Font(15));
        playAgain.setOnAction(f -> {
            stage.close();
            boardGUI.resetBoard();
            Controller.reset(boardGUI.getBoard());
        });

        Button backOpponentSelection = new Button("Pick a New Opponent");
        backOpponentSelection.setStyle(BUTTONRADIUS);
        backOpponentSelection.setFont(new Font(15));
        backOpponentSelection.setOnAction(f -> {
            boardGUI.getWindow().close();
            stage.close();
            OpponentSelectorBox.display();
        });

        Button quit = new Button("Quit");
        quit.setStyle(BUTTONRADIUS);
        quit.setFont(new Font(15));
        quit.setOnAction(f -> {
            boardGUI.getWindow().close();
            stage.close();
        });

        HBox messagePanel = new HBox();
        messagePanel.setAlignment(Pos.CENTER);
        messagePanel.setPrefHeight(75);
        messagePanel.getChildren().add(winnerMessage);

        VBox buttonPanel = new VBox(20);
        buttonPanel.setAlignment(Pos.CENTER);
        buttonPanel.getChildren().addAll(playAgain, backOpponentSelection, quit);

        VBox overallLayout = new VBox(20);
        overallLayout.setAlignment(Pos.CENTER);
        overallLayout.getChildren().addAll(messagePanel, buttonPanel);

        if (winner == RED) {
            overallLayout.setStyle(RED_BACKGROUND);
        } else {
            overallLayout.setStyle(BLACK_BACKGROUND);
        }

        Scene scene = new Scene(overallLayout, 420, 300);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
