package Client.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MainMenuController {
    private Parent root;
    private Stage stage;
    private Scene scene;

    String str = "Button_Click.mp3";
    private MediaPlayer mediaPlayer;

    public void startANewGame(ActionEvent actionEvent) throws Exception {
//        Media media = new Media(new File(str).toURI().toString());
//        mediaPlayer = new MediaPlayer(media);
//        mediaPlayer.setAutoPlay(true);
////        Player loggedInPlayer = Controller.getLoggedInPlayer();
//
//        if (loggedInPlayer.getActiveDeck() == null) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setContentText("You don't have any active deck!");
//            alert.showAndWait();
//        } else if (!loggedInPlayer.getActiveDeck().isValid()) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setContentText("Your active deck is not valid!");
//            alert.showAndWait();
//        } else {
//            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/SetOpponent.fxml")));
//            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
//            SetOpponentController.setMainMenuStage(stage);
//            Stage stageSetOpponent = new Stage();
//            scene = new Scene(root);
//            stageSetOpponent.setScene(scene);
//            stageSetOpponent.show();
//        }
    }

    public void goToShop(ActionEvent event) throws IOException {
        Media media = new Media(new File(str).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/Shop.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }

    public void goToDeckMenu(ActionEvent event) throws IOException {
        Media media = new Media(new File(str).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/DeckMenu.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }

    public void goToLobby(ActionEvent actionEvent) {
        Media media = new Media(new File(str).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        try {
            DataOutputStream dataOutputStream = Controller.getDataOutputStream();
            String token = Controller.getToken();
            dataOutputStream.writeUTF("Lobby enter" + token);
            dataOutputStream.flush();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/Lobby.fxml")));
            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToProfileMenu(ActionEvent event) throws IOException {
        Media media = new Media(new File(str).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/Profile.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }


    public void goToChatRoom(ActionEvent event) throws IOException {
        Media media = new Media(new File(str).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/chat.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

    }

    public void goToScoreBoard(ActionEvent event) throws IOException {
        Media media = new Media(new File(str).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/Scoreboard.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }

    public void logout(ActionEvent event) throws IOException {
        Media media = new Media(new File(str).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);

        Controller.getDataOutputStream().writeUTF("Logout" + Controller.getToken());
        Controller.getDataOutputStream().flush();

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/Login.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }

}
