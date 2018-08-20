package checkers;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        HomeGUI home = new HomeGUI();
        home.getWindow().show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
