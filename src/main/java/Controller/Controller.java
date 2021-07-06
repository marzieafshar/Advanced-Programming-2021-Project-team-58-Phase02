package Controller;

import Model.Card;
import Model.Deck;
import Model.Player;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Login.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }

    public void switchToRegisterMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Register.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }

    public void goToScoreBoard(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Scoreboard.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }

    public void goToProfileMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Profile.fxml")));
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
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/MainMenu.fxml")));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                }
            }
        }
    }

    public void backToWelcomeMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/WelcomeMenu.fxml")));
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
                    try {
                        jsonSaveAndLoad.save();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    alert3.setContentText("user created successfully!");
                    alert3.show();
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Login.fxml")));
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
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Login.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }

    public void BackToMainMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/MainMenu.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }

    public void goToShop(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Shop.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }

    public void goToDeckMenu(ActionEvent event) throws IOException {
        Deck deck1 = new Deck("first");
        Deck deck2 = new Deck("second");
        Deck deck3 = new Deck("third");
        for (int i = 0; i < 7; i++) {
            deck1.addCardToMainDeck(Card.getCardByName("Trap Hole"));
            deck1.addCardToMainDeck(Card.getCardByName("Trap Hole"));
            deck1.addCardToMainDeck(Card.getCardByName("Silver Fang"));
            deck1.addCardToMainDeck(Card.getCardByName("Suijin"));
            deck1.addCardToMainDeck(Card.getCardByName("Suijin"));
            deck1.addCardToMainDeck(Card.getCardByName("Raigeki"));
            deck1.addCardToMainDeck(Card.getCardByName("Wattkid"));
        }
        deck2.addCardToMainDeck(Card.getCardByName("Raigeki"));
        deck2.addCardToMainDeck(Card.getCardByName("Raigeki"));
        deck2.addCardToMainDeck(Card.getCardByName("Raigeki"));
        deck2.addCardToMainDeck(Card.getCardByName("Suijin"));
        deck2.addCardToMainDeck(Card.getCardByName("Axe Raider"));
        deck2.addCardToMainDeck(Card.getCardByName("Bitron"));
        deck2.addCardToMainDeck(Card.getCardByName("Dark Hole"));

        deck3.addCardToMainDeck(Card.getCardByName("Silver Fang"));
        deck3.addCardToMainDeck(Card.getCardByName("Yomi Ship"));
        deck3.addCardToMainDeck(Card.getCardByName("Dark Hole"));
        deck3.addCardToMainDeck(Card.getCardByName("Fireyarou"));
        deck3.addCardToMainDeck(Card.getCardByName("Suijin"));
        deck3.addCardToMainDeck(Card.getCardByName("Command Knight"));
        deck3.addCardToMainDeck(Card.getCardByName("Dark Hole"));

        getLoggedInPlayer().getDecks().add(deck1);
        getLoggedInPlayer().getDecks().add(deck2);
        getLoggedInPlayer().getDecks().add(deck3);
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/DeckMenu.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }

}