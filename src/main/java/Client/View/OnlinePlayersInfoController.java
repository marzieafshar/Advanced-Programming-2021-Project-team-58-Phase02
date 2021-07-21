package Client.View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class OnlinePlayersInfoController implements Initializable {

    @FXML
    private Label usernameLabel;

    @FXML
    private Label nicknameLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label winLabel;

    @FXML
    private Label loseLabel;

    @FXML
    private ImageView playerImageView;

    private static Image imagePlayer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setInfo();
    }

    public void setInfo() {
        String message = ChatRoomController.getMessage();
        String[] tmp = message.split("#");
        playerImageView.setImage(imagePlayer);
        usernameLabel.setText(tmp[0]);
        nicknameLabel.setText(tmp[1]);
        scoreLabel.setText(tmp[2]);
        winLabel.setText(tmp[3]);
        loseLabel.setText(tmp[4]);
    }

    public static void setImagePlayer(Image imagePlayer) {
        OnlinePlayersInfoController.imagePlayer = imagePlayer;
    }
}
