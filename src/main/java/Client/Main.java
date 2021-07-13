package Client;

import Client.View.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.media.*;

import java.io.IOException;

public class Main extends Application{
    private MediaPlayer mediaPlayer;

    @Override
    public void start(Stage primaryStage) throws Exception{
//        String str = "05. Grabbing the Hatchet.mp3";
//        Media media = new Media(new File(str).toURI().toString());
//        mediaPlayer = new MediaPlayer(media);
//        mediaPlayer.setVolume(0.5);
//        mediaPlayer.setAutoPlay(true);


        Parent root = FXMLLoader.load(getClass().getResource("/Fxmls/WelcomeMenu.fxml"));
        primaryStage.setTitle("Yu-Gi-Oh");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            quit(primaryStage);
        });
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {
        Controller.setupConnection();
        launch(args);
    }

    public void quit(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setContentText("Are you sure you want to exit the program?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            Controller.closeAll();
            System.exit(0);
        }
    }
}
