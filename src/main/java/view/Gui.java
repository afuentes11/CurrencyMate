package main.java.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Gui extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("home/home.fxml"));
        primaryStage.setTitle("CurrencyMate");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void startVisualInterface(String[] args) {
        launch(args);
    }
}
