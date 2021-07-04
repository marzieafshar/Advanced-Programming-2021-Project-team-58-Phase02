package Java;

import Java.Model.Player;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.stage.*;

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
    @FXML
    private TextField passwordChangeField;
    @FXML
    private TextField repeatPasswordChangeField;
    @FXML
    private TextField nicknameChangeField;

    private static Player loggedInPlayer;

    public static Player getLoggedInPlayer() {
        return loggedInPlayer;
    }

    public static void setLoggedInPlayer(Player player) {
        loggedInPlayer = player;
    }

    public void switchToLoginMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }
    public void switchToRegisterMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Register.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }
    public void goToScoreBoard(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Scoreboard.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }
    public void goToProfileMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Profile.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }
    public void Login(ActionEvent event) throws IOException {
        String username = usernameLoginField.getText();
        String password = passwordLoginField.getText();
        if (username == null) return;
        else if (password == null) return;
        else if (Player.getPlayerByUsername(username) == null) {
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
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                }
            }
        }
    }
    public void backToWelcomeMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("WelcomeMenu.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }
    public void Register(ActionEvent event) throws IOException {
        String username = usernameRegisterField.getText();
        String password = passwordRegisterField.getText();
        String nickname = nicknameRegisterField.getText();
        if (username == null) return;
        else if (nickname == null) return;
        else if (password == null) return;
        else {
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
                    alert3.setContentText("user created successfully!");
                    alert3.show();
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                }
            }
        }
    }
    public void Exit(ActionEvent event) {
        System.exit(0);
    }
    public void LogOut(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }
    public void nicknameChange(ActionEvent event) {
        String newNickName = nicknameChangeField.getText();
        if (nicknameChangeField == null) return;
        getLoggedInPlayer().setNickname(newNickName);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Nickname Changed Successfully !");
        alert.showAndWait();
    }
    public void passwordChange(ActionEvent event) {
        String newPassword1 = passwordChangeField.getText();
        String newPassword2 = repeatPasswordChangeField.getText();
        if (newPassword2 == null || newPassword1 == null) return;
        else if (!newPassword1.equals(newPassword2)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Password didn't match !");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            getLoggedInPlayer().setPassword(newPassword1);
            alert.setContentText("Password Changed Successfully !");
            alert.showAndWait();
        }
    }
    public void BackToMainMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }

    public void goToShop(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Shop.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }

}