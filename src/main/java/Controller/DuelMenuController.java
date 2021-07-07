package Controller;

import Model.Player;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class DuelMenuController implements Initializable {
    Player player2 = SetOpponentController.getPlayer2();
    int numOfRounds = SetOpponentController.getNumOfRounds();
    int numOfCurrentRound;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
