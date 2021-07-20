package Client;

import Client.View.ChatRoomController;
import Client.View.Controller;
import Client.View.MainMenuController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.media.*;

import java.awt.event.ActionEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.SplittableRandom;

public class Main extends Application {
    //    private MediaPlayer mediaPlayer;
    private static String message;

    public static ArrayList<Thread> threads = new ArrayList<>();

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        Main.message = message;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
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
            try {
                quit(primaryStage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {
        Controller.setupConnection();
        launch(args);
    }

    public void quit(Stage stage) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit");
        alert.setContentText("Are you sure you want to exit the program?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            Controller.getDataOutputStream().writeUTF("Logout" + Controller.getToken());
            Controller.getDataOutputStream().flush();

            String result = Controller.getDataInputStream().readUTF();
            switch (result) {
                case "logged out successfully":
                    Controller.closeAll();
                    System.exit(0);
            }
        }
    }
}
