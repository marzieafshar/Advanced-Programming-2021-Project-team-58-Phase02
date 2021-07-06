package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    @FXML
    private TextField passwordChangeField;
    @FXML
    private TextField repeatPasswordChangeField;
    @FXML
    private TextField nicknameChangeField;
    @FXML
    private Circle myCircle;
    private Stage stage;

    public void nicknameChange(ActionEvent event) {
        String newNickName = nicknameChangeField.getText();
        if (nicknameChangeField == null) return;
        Controller.getLoggedInPlayer().setNickname(newNickName);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Nickname Changed Successfully !");
        alert.showAndWait();
    }
    public void passwordChange(ActionEvent event) {
        String newPassword1 = passwordChangeField.getText();
        String newPassword2 = repeatPasswordChangeField.getText();
        if (newPassword2 == null || newPassword1 == null) return;
        else if (!newPassword1.equals(newPassword2)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Password didn't match !");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            Controller.getLoggedInPlayer().setPassword(newPassword1);
            alert.setContentText("Password Changed Successfully !");
            alert.showAndWait();
        }
    }
    public void BackToMainMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/MainMenu.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image(getClass().getResourceAsStream("/Images/Monster/Bitron.jpg"));
        myCircle.setFill(new ImagePattern(image));

    }
}
