package Client.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.stage.Stage;

import java.io.DataOutputStream;
import java.io.IOException;

public class StageNumOfRoundController {
    @FXML
    private Button oneRoundButton;

    @FXML
    private Button threeRoundButton;
    String selectedNumOfRound;

    @FXML
    void selectOneRound(ActionEvent event) {
        threeRoundButton.setEffect(null);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setWidth(150);
        dropShadow.setHeight(100);
        oneRoundButton.setEffect(dropShadow);
        selectedNumOfRound = "1";
    }

    @FXML
    void selectThreeRound(ActionEvent event) {
        oneRoundButton.setEffect(null);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setWidth(150);
        dropShadow.setHeight(100);
        threeRoundButton.setEffect(dropShadow);
        selectedNumOfRound = "3";
    }

    @FXML
    void submit(ActionEvent event) throws IOException {
        if (selectedNumOfRound != null) {
            DataOutputStream dataOutputStream = Controller.getDataOutputStream();
            String token = Controller.getToken();
            dataOutputStream.writeUTF("Lobby start game" + selectedNumOfRound + "#" + token);
            selectedNumOfRound = null;
            Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
            stage.close();
        }
    }
}
