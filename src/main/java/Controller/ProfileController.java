package Controller;

import Model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
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
    private ImageView itemImage;
    @FXML
    private Label username;
    @FXML
    private Label userScore;
    @FXML
    private Label userMoney;
    @FXML
    private Label userNumOfDecks;
    @FXML
    private Label userActiveDeckName;
    @FXML
    private Label userNumOfWins;
    @FXML
    private Label userNumOfLosses;
    @FXML
    private GridPane gridPane;
    private Stage stage;
    private MyListener myListener;

    @FXML
    void backToMainMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/MainMenu.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void nicknameChange(javafx.scene.input.MouseEvent event) {
        String newNickName = nicknameChangeField.getText();
        if (nicknameChangeField == null) return;
        Controller.getLoggedInPlayer().setNickname(newNickName);
        nicknameChangeField.setPromptText("Your nickname: "+Controller.getLoggedInPlayer().getNickname());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Nickname Changed Successfully !");
        alert.showAndWait();
    }

    @FXML
    public void passwordChange(javafx.scene.input.MouseEvent event) {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUserInfo();
        myListener = object -> setPlayerImage((Image) object);
        showAllAvatars();
    }

    private void showAllAvatars() {
        int column = 0;
        int row = 1;

        gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
        gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
        gridPane.setMaxHeight(Region.USE_PREF_SIZE);

        gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
        gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
        gridPane.setMaxWidth(Region.USE_PREF_SIZE);

        for (int i = 0; i < 35; i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Fxmls/Avatars.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                AvatarsController avatarsController = fxmlLoader.getController();

                String str = "/Images/Characters/Chara001.dds" + i + ".png";
                avatarsController.setImage( new Image(getClass().getResourceAsStream(str)), myListener);
                if (column == 7) {
                    column = 0;
                    row++;
                }
                gridPane.add(anchorPane, column++, row);

                GridPane.setMargin(anchorPane, new Insets(5));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setPlayerImage(Image image) {
        Player player = Controller.getLoggedInPlayer();
        player.setImage(image);
        itemImage.setImage(player.getImage());
    }

    private void setUserInfo() {
        Player player = Controller.getLoggedInPlayer();
        itemImage.setImage(player.getImage());
        username.setText(player.getUsername());
        userMoney.setText(String.valueOf(player.getMoney()));
        userScore.setText(String.valueOf(player.getScore()));
        if (player.getActiveDeck() == null)
            userActiveDeckName.setText("Not set yet");
        else
        userActiveDeckName.setText(player.getActiveDeck().getDeckName());
        userNumOfDecks.setText(String.valueOf(player.getDecks().size()));
        userNumOfWins.setText(String.valueOf(player.getWinMatches()));
        userNumOfLosses.setText(String.valueOf(player.getLoseMatches()));
        nicknameChangeField.setPromptText("Your nickname: "+player.getNickname());
    }
}

