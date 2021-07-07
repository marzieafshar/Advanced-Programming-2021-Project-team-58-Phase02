package Controller;

import Model.Card;
import Model.Deck;
import Model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainMenuController {
    private Parent root;
    private Stage stage;
    private Scene scene;


    public void startANewGame(ActionEvent actionEvent) throws Exception {

        Player loggedInPlayer = Controller.getLoggedInPlayer();

        if (loggedInPlayer.getActiveDeck() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You don't have any active deck!");
            alert.showAndWait();
        } else if (!loggedInPlayer.getActiveDeck().isValid()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Your active deck is not valid!");
            alert.showAndWait();
        } else {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/SetOpponent.fxml")));
            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
        }
    }

    public void goToShop(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/Shop.fxml")));
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

        Controller.getLoggedInPlayer().getDecks().add(deck1);
        Controller.getLoggedInPlayer().getDecks().add(deck2);
        Controller.getLoggedInPlayer().getDecks().add(deck3);
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/DeckMenu.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }

    public void goToScoreBoard(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/Scoreboard.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }

    public void goToProfileMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/Profile.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }

    public void logout(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/Login.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }
}
