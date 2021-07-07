package Controller;

import Model.Card;
import Model.Deck;
import Model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class MainMenuController {
    private Parent root;
    private Stage stage;
    private Scene scene;
    @FXML
    private TextField opponentUsername;

    @FXML
    private Label ErrorLabel;

//    @FXML
//    private ChoiceBox<String> roundChoiceBox;

    private final String[] rounds = {"1", "3"};
//    private String roundChoice;

    public void checkOpponent(ActionEvent event) throws IOException {

        String opponentName = opponentUsername.getText();
        if (!Player.getAllPlayers().contains(Player.getPlayerByUsername(opponentName)))
            ErrorLabel.setText("There is no player with this username!");
        else {
            Player opponentPlayer = Player.getPlayerByUsername(opponentName);
            if (opponentPlayer.getActiveDeck() == null)
                ErrorLabel.setText("Your opponent doesn't have any active deck!");
            else if (!opponentPlayer.getActiveDeck().isValid())
                ErrorLabel.setText("Your opponent's active deck is not valid!");
//            else if (roundChoice == null)
//                ErrorLabel.setText("Please choose a round number!");
            else {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/round.fxml")));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
            }
        }
    }


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
//            roundChoiceBox.getItems().addAll(rounds);
//            roundChoiceBox.setOnAction(this::round);
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/setOpponent.fxml")));
            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
        }
    }
//    public void round(javafx.event.ActionEvent event){
//        String round = roundChoiceBox.getValue();
//        this.roundChoice = round;
//    }

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

    public void LogOut(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/Login.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }
}
