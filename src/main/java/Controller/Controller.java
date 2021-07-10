package Controller;

import Model.Player;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.*;

import java.io.File;
import java.util.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Controller {
    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private TextField usernameLoginField;
    @FXML
    private TextField passwordLoginField;
    @FXML
    private TextField usernameRegisterField;
    @FXML
    private TextField nicknameRegisterField;
    @FXML
    private TextField passwordRegisterField;

    private static Player loggedInPlayer;

    String str = "Button_Click.mp3";
    private MediaPlayer mediaPlayer;


    public static Player getLoggedInPlayer() {
        return loggedInPlayer;
    }

    public static void setLoggedInPlayer(Player player) {
        loggedInPlayer = player;
    }

    public void switchToLoginMenu(ActionEvent event) throws IOException {
        Media media = new Media(new File(str).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/Login.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }

    public void switchToRegisterMenu(ActionEvent event) throws IOException {
        Media media = new Media(new File(str).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/Register.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }

    public void Login(ActionEvent event) throws IOException {
        Media media = new Media(new File(str).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        String username = usernameLoginField.getText();
        String password = passwordLoginField.getText();
        if (username.equals("") || password.equals("")) return;
        if (Player.getPlayerByUsername(username) == null) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setContentText("Username and password didn't match!");
            alert1.show();
        } else {
            if (Objects.requireNonNull(Player.getPlayerByUsername(username)).getPassword() != null) {
                if (!Objects.requireNonNull(Player.getPlayerByUsername(username)).getPassword().equals(password)) {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setContentText("Username and password didn't match!");
                    alert1.show();
                } else {
                    Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                    setLoggedInPlayer(Player.getPlayerByUsername(username));
                    alert3.setContentText("user logged in successfully!");
                    alert3.show();
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/MainMenu.fxml")));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                }
            }
        }
    }

    public void backToWelcomeMenu(ActionEvent event) throws IOException {
        Media media = new Media(new File(str).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/WelcomeMenu.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }

    public void Register(ActionEvent event) throws IOException {
        Media media = new Media(new File(str).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        String username = usernameRegisterField.getText();
        String password = passwordRegisterField.getText();
        String nickname = nicknameRegisterField.getText();
        if (username.equals("") || nickname.equals("") || password.equals("")) return;
        if (Player.getPlayerByUsername(username) != null) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setContentText("user with username " + username + " already exists");
            alert1.show();
        } else {
            if (Player.isNicknameExists(nickname)) {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setContentText("user with nickname " + nickname + " already exists");
                alert2.show();
            } else {
                Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                new Player(username, password, nickname);
                try {
                    JsonSaveAndLoad.save();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                alert3.setContentText("user created successfully!");
                alert3.show();
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/Login.fxml")));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
            }
        }

    }

    public void Exit(ActionEvent event) {
        Media media = new Media(new File(str).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        System.exit(0);
    }

}