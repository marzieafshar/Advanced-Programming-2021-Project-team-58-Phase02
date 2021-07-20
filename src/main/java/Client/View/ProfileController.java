package Client.View;


import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.*;
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
    private MyListener myListener;

    String str = "Button_Click.mp3";
    Media media = new Media(new File(str).toURI().toString());
    private MediaPlayer mediaPlayer = new MediaPlayer(media);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUserInfo();
        myListener = object -> setPlayerImage((Image) object);
        showAllAvatars();
    }

    @FXML
    void backToMainMenu(ActionEvent event) throws IOException {
        Media media = new Media(new File(str).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/MainMenu.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    void nicknameChange(MouseEvent event) {
        Media media = new Media(new File(str).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);

        try {
            if (nicknameChangeField == null) return;
            String token = Controller.getToken();
            String newNickName = nicknameChangeField.getText();
            String message = "Profile change nickname" + newNickName + "#" + token;

            Controller.getDataOutputStream().writeUTF(message);
            Controller.getDataOutputStream().flush();

            String result = Controller.getDataInputStream().readUTF();

            switch (result) {
                case "nickname already exists":
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("This nickname already exists");
                    alert.show();
                case "nickname changed":
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setContentText("Nickname Changed Successfully !");
                    alert2.showAndWait();
            }
            nicknameChangeField.setPromptText("Your nickname: " + getPlayerInfo("nickname"));
            nicknameChangeField.setText("");
//            nicknameChangeField.isFocused();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void passwordChange(MouseEvent event) {
        Media media = new Media(new File(str).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        String newPassword1 = passwordChangeField.getText();
        String newPassword2 = repeatPasswordChangeField.getText();
        if (newPassword2 == null || newPassword1 == null) return;
        if (!newPassword1.equals(newPassword2)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Password didn't match!");
            alert.showAndWait();
        } else {
            try {
                String token = Controller.getToken();
                String message = "Profile change password" + newPassword1 + "#" + token;
                Controller.getDataOutputStream().writeUTF(message);
                Controller.getDataOutputStream().flush();

                String result = Controller.getDataInputStream().readUTF();

                switch (result) {
                    case "same password":
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Your current password is " + newPassword1 +
                                "\nplaese enter a new password");
                        alert.showAndWait();
                    case "password changed":
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setContentText("Password changed successfully");
                        alert2.showAndWait();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        for (int i = 0; i < 32; i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Fxmls/Avatars.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                AvatarsController avatarsController = fxmlLoader.getController();

                String str = "/Images/Characters/Chara001.dds" + i + ".png";
                avatarsController.setImage(new Image(getClass().getResourceAsStream(str)), myListener);
                if (column == 8) {
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
        try {
            String imageJson = new YaGson().toJson(image);
            int x = imageJson.length() / 64000;
            Controller.getDataOutputStream().writeUTF("Profile length" + (x + 1) + "#" + Controller.getToken());
            Controller.getDataOutputStream().flush();

            for (int i = 0; i < x; i++) {
                String tmp = imageJson.substring(0, 64000);
                imageJson = imageJson.substring(64000);
                Controller.getDataOutputStream().writeUTF("Profile change" + tmp);
                Controller.getDataOutputStream().flush();
            }
            Controller.getDataOutputStream().writeUTF("Profile change" + imageJson);
            Controller.getDataOutputStream().flush();
            Controller.getDataOutputStream().writeUTF("Profile change");
            Controller.getDataOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        itemImage.setImage(image);
    }

    private void setUserInfo() {
        try {
            itemImage.setImage(getImage());
            username.setText(getPlayerInfo("username"));
            userMoney.setText(getPlayerInfo("money"));
            userScore.setText(getPlayerInfo("score"));
            userActiveDeckName.setText(getPlayerInfo("active deck name"));
            userNumOfDecks.setText(getPlayerInfo("decks size"));
            userNumOfWins.setText(getPlayerInfo("win matches"));
            userNumOfLosses.setText(getPlayerInfo("lose matches"));
            nicknameChangeField.setPromptText("Your nickname: " + getPlayerInfo("nickname"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Image getImage() {
        try {
            String token = Controller.getToken();
            Controller.getDataOutputStream().writeUTF("Profile image" + token);
            String imageJson = "";
            String imageLengthMessage = Controller.getDataInputStream().readUTF();
            int imageLength = Integer.parseInt(imageLengthMessage);

            for (int i = 0; i < imageLength; i++) {
                String imageMessage = Controller.getDataInputStream().readUTF();
                imageJson = imageJson + imageMessage;
            }
            Image image = new YaGson().fromJson(imageJson, new TypeToken<Image>() {
            }.getType());
            return image;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getPlayerInfo(String str){
        try {
            String token = Controller.getToken();
            Controller.getDataOutputStream().writeUTF( "Profile " + str + token);
            String result = Controller.getDataInputStream().readUTF();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}

