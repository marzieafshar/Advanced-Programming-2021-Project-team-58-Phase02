package Controller;

import Model.Deck;
import Model.Player;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateDeckController {

    public TextField deckNameTextfield;

    private Player player = Controller.getLoggedInPlayer();
    private static Stage deckMenuStage;
    private static DeckMenuController deckMenuController;

    public static void setDeckMenuController(DeckMenuController deckMenuController) {
        CreateDeckController.deckMenuController = deckMenuController;
    }

    public static void setDeckMenuStage(Stage deckMenuStage) {
        CreateDeckController.deckMenuStage = deckMenuStage;
    }

    public void createNewDeck(ActionEvent event) {
        String newDeckName = deckNameTextfield.getText();
        if (newDeckName.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("text field is empty!");
            alert.showAndWait();
        } else if (player.hasADeck(player.getDeckByName(newDeckName))) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("deck with name " + newDeckName + " already exists");
            alert.showAndWait();
        } else {
            Deck deck = new Deck(newDeckName);
            player.getDecks().add(deck);
            Stage stage = (Stage) deckNameTextfield.getScene().getWindow();
            stage.close();
            deckMenuController.addDecksToMenu();
        }
    }
}
