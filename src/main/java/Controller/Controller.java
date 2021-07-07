package Controller;

import Model.Card;
import Model.Deck;
import Model.Player;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.*;

import java.util.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Controller {
    public ImageView testImageView;
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

    public static Player getLoggedInPlayer() {
        return loggedInPlayer;
    }

    public static void setLoggedInPlayer(Player player) {
        loggedInPlayer = player;
    }

    public void switchToLoginMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/Login.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }
    public void switchToRegisterMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/Register.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }

    public void Login(ActionEvent event) throws IOException {
        String username = usernameLoginField.getText();
        String password = passwordLoginField.getText();
        if (username.equals("")||password.equals("")) return;
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
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/WelcomeMenu.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }
    public void Register(ActionEvent event) throws IOException {
        String username = usernameRegisterField.getText();
        String password = passwordRegisterField.getText();
        String nickname = nicknameRegisterField.getText();
        if (username.equals("")||nickname.equals("")||password.equals("")) return;
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
                        jsonSaveAndLoad.save();
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
        System.exit(0);
    }

}