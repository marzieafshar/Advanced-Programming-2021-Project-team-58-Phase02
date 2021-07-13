package Client.View;

import Server.Model.Player;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.Socket;
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

    private Alert alert;

    private static String token;

    String str = "Button_Click.mp3";
    private MediaPlayer mediaPlayer;

    private static DataOutputStream dataOutputStream;
    private static DataInputStream dataInputStream;
    private static Socket socket;

    public static Socket getSocket() {
        return socket;
    }

    public static DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public static DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public static Player getLoggedInPlayer(){
        return new Player();
    }

    public static void setupConnection() {
        try {
            socket = new Socket("localhost", 1234);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeAll(){
        try {
            dataOutputStream.writeUTF("end");
            dataOutputStream.flush();
            dataOutputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setToken(String token) {
        Controller.token = token;
    }

    public static String getToken() {
        return token;
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

        String message = "Login" + username + "#" + password + "#" + token;
        dataOutputStream.writeUTF(message);
        dataOutputStream.flush();

        String result = dataInputStream.readUTF();
        if (result.startsWith("user logged")) {
            String token = result.substring(27);
            System.out.println(token);
            setToken(token);
            result = result.substring(0, 27);
        }
        switch (result){
            case "username and password didn't match":
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username and password didn't match");
                alert.show();
                break;
            case "user logged in successfully":
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("user logged in successfully!");
                alert.show();
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/MainMenu.fxml")));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                break;
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

        String message = "Register" + username + "#" + nickname + "#" + password;
        dataOutputStream.writeUTF(message);
        dataOutputStream.flush();

        String result = dataInputStream.readUTF();
        switch (result){
            case "username already exists":
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("user with username " + username + " already exists");
                alert.show();
                break;
            case "nickname already exists":
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("user with nickname " + nickname + " already exists");
                alert.show();
                break;
            case "user created successfully":
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("user created successfully!");
                alert.show();
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/Login.fxml")));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                break;
        }
    }

    public void Exit(ActionEvent event) {
        Media media = new Media(new File(str).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        closeAll();
        System.exit(0);
    }

}