package Client.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminPasswordController {

    private static ShopController shopController;
    @FXML
    private PasswordField passwordTextField;

    public static void setShopController(ShopController shop) {
        shopController = shop;
    }

    @FXML
    void changeToAdmin(ActionEvent event) {
        String loggedInPlayerPassword = ProfileController.getPlayerInfo("password");
        if (passwordTextField.getText().equals("admin")) {
            Image image = new Image(getClass().getResourceAsStream("/Images/Icon/player.png"));
            shopController.adminImageView.setImage(image);
            shopController.setIsAdmin(true);
            shopController.enableAdminButtons();
            Stage stage = (Stage) passwordTextField.getScene().getWindow();
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            if (passwordTextField.getText().equals(loggedInPlayerPassword)) {
                alert.setContentText("Admin password is different from your password!");
            } else {
                alert.setContentText("Password incorrect!");
            }
            alert.show();
        }
    }
}
