package Client.View;

import Client.Model.Deck;
import Client.Model.Player;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateDeckController {

    public TextField deckNameTextfield;

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
        } else if (hasADeckByName(newDeckName)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("deck with name " + newDeckName + " already exists");
            alert.showAndWait();
        } else {
            creatingDeckProcess(newDeckName);
            Stage stage = (Stage) deckNameTextfield.getScene().getWindow();
            stage.close();
            deckMenuController.loadPlayerDecks();
            deckMenuController.addDecksToMenu();
        }
    }

    public boolean hasADeckByName(String deckName){
        try {
            Controller.getDataOutputStream().writeUTF("Deck player has deck" + deckName + "#" + Controller.getToken());
            Controller.getDataOutputStream().flush();
            String result = Controller.getDataInputStream().readUTF();
            return Boolean.parseBoolean(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void creatingDeckProcess(String deckName){
        try {
            Controller.getDataOutputStream().writeUTF("Deck create new deck" + deckName + "#" + Controller.getToken());
            Controller.getDataOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
