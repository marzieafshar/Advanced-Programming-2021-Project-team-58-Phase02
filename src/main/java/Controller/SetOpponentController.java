package Controller;

import Model.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SetOpponentController implements Initializable {
    private static String roundChoice;
    private static Player player2;
    private static Stage mainMenuStage;
    private Parent root;
    private Stage stage;
    private Scene scene;
    @FXML
    private TextField opponentUsername;

    @FXML
    private Label ErrorLabel;

    @FXML
    private ComboBox<String> comboBox;

    public static void setMainMenuStage(Stage menuStage) {
        mainMenuStage = menuStage;
    }

    public static void setPlayer2(Player player2) {
        SetOpponentController.player2 = player2;
    }

    public static Player getPlayer2() {
        return player2;
    }

    public static void setRoundChoice(String roundChoice) {
        SetOpponentController.roundChoice = roundChoice;
    }

    public static int getNumOfRounds() {
        return Integer.parseInt(roundChoice);
    }

    public void checkOpponent(ActionEvent event) throws IOException {
        String opponentName = opponentUsername.getText();
        if (!Player.getAllPlayers().contains(Player.getPlayerByUsername(opponentName)))
            ErrorLabel.setText("There is no player with this username!");
        else {
            setPlayer2(Player.getPlayerByUsername(opponentName));
            if (player2.getActiveDeck() == null)
                ErrorLabel.setText("Your opponent doesn't have any active deck!");
            else if (!player2.getActiveDeck().isValid())
                ErrorLabel.setText("Your opponent's active deck is not valid!");
            else if (roundChoice == null)
                ErrorLabel.setText("Please choose a round number!");
            else if (opponentName.equals(Controller.getLoggedInPlayer().getUsername()))
                ErrorLabel.setText("You can't play with yourself!");
            else {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/DuelMenu.fxml")));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.close();
                mainMenuStage.setScene(scene);
            }
        }
    }

    public void round(javafx.event.ActionEvent event) {
        setRoundChoice(comboBox.getValue());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> availableChoices = FXCollections.observableArrayList("1", "3");
        comboBox.setItems(availableChoices);
        comboBox.setOnAction(this::round);

    }
}
