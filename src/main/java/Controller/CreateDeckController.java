package Controller;

import Model.Card;
import Model.Deck;
import Model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateDeckController {

    public TextField deckNameTextfield;

    private Player player = Controller.getLoggedInPlayer();
    private Stage stage;
    private static Stage deckMenuStage;

    public static void setDeckMenuStage(Stage deckMenuStage) {
        CreateDeckController.deckMenuStage = deckMenuStage;
    }

    public void createNewDeck(ActionEvent event){
        String newDeckName = deckNameTextfield.getText();
        if(newDeckName.equals("")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("text field is empty!");
            alert.showAndWait();
        }
        else if(player.hasADeck(player.getDeckByName(newDeckName))){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("deck with name " + newDeckName + " already exists");
            alert.showAndWait();
        }
        else{
            Deck deck = new Deck(newDeckName);
            player.getDecks().add(deck);
            stage = (Stage) deckNameTextfield.getScene().getWindow();
            stage.close();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/DeckMenu.fxml"));
                Scene scene = new Scene(root);
                deckMenuStage.setScene(scene);
//                deckMenuStage.setMaximized(true);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
