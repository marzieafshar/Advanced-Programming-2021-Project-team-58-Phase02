package Client.View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class LobbyController implements Initializable {
    @FXML
    private ImageView userAvatar;

    @FXML
    private Label userNickname;

    @FXML
    private Label userScore;

    @FXML
    private ImageView player1Avatar;

    @FXML
    private ImageView player2Avatar;

    @FXML
    private ImageView player3Avatar;

    @FXML
    private ImageView player4Avatar;

    @FXML
    private ImageView player5Avatar;

    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private String token;
    private Parent root;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializer();
        setPlayerInfo();
        setOtherPlayersInfo();
    }

    private void initializer() {
        token = Controller.getToken();
        dataOutputStream = Controller.getDataOutputStream();
        dataInputStream = Controller.getDataInputStream();
    }

    private void setPlayerInfo() {
        userAvatar.setImage(ProfileController.getImage(Controller.getToken()));
        userNickname.setText("Your Nickname: " + ProfileController.getPlayerInfo("nickname", Controller.getToken()));
        userScore.setText("Your Score: " + ProfileController.getPlayerInfo("score", Controller.getToken()));
    }

    private void setOtherPlayersInfo() {
        ArrayList<ImageView> allImageViews = new ArrayList<>();
        allImageViews.add(player1Avatar);
        allImageViews.add(player2Avatar);
        allImageViews.add(player3Avatar);
        allImageViews.add(player4Avatar);
        allImageViews.add(player5Avatar);
        try {
            dataOutputStream.writeUTF("Lobby get tokens" + Controller.getToken());
            dataOutputStream.flush();
            String[] tokens = dataInputStream.readUTF().split("#");
            for (int i = 0; i < tokens.length; i++)
                System.out.println(tokens[i]);

            System.out.println(Controller.getToken());
            for (int i = 0; i < tokens.length; i++) {
                Image image;
                if (tokens[i] != null && !tokens[i].equals("")) {
                    image = ProfileController.getImage(tokens[i]);
                    allImageViews.get(i).setImage(image);
                    System.out.println(tokens[i]);
                }
            }

//            dataOutputStream.writeUTF("Lobby get players nickname");
//            dataOutputStream.flush();
//            String result = dataInputStream.readUTF();
//            String[] playersInLobby = result.split("#");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void startAGame(MouseEvent event) throws IOException {
        dataOutputStream.writeUTF("Profile has active deck" + token);
        dataOutputStream.flush();
        boolean hasActiveDeck = Boolean.parseBoolean(dataInputStream.readUTF());
        if (!hasActiveDeck) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You don't have any active deck!");
            alert.showAndWait();
        } else {
            dataOutputStream.writeUTF("Profile is active deck valid" + token);
            dataOutputStream.flush();
            boolean isActiveDeckValid = Boolean.parseBoolean(dataInputStream.readUTF());
            if (!isActiveDeckValid) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Your active deck is not valid!");
                alert.showAndWait();
            } else {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/StageNumOfRound.fxml")));
                Stage stageNumOfRound = new Stage();
                stageNumOfRound.setScene(new Scene(root));
                stageNumOfRound.show();
            }
        }
    }

    @FXML
    void backToMainMenu(MouseEvent event) throws IOException {
        dataOutputStream.writeUTF("Lobby exit" + token);
        dataOutputStream.flush();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/MainMenu.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void goToChatRoom(MouseEvent mouseEvent) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/chat.fxml")));
        stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        ChatRoomController.setIsFromLobby(true);
    }
}
