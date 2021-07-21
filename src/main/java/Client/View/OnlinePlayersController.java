package Client.View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Objects;

public class OnlinePlayersController {

    @FXML
    private ImageView playerImage;

    @FXML
    private Label playerNickname;

    private Image imagePlayer;

    public void click(MouseEvent event){
        try {
            Controller.getDataOutputStream().writeUTF("Chat get player info"
                    + playerNickname.getText());
            Controller.getDataOutputStream().flush();
            OnlinePlayersInfoController.setImagePlayer(imagePlayer);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/OnlinePlayersInfo.fxml")));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setItem(Image image , String nickname){
        imagePlayer = image;
        playerImage.setImage(image);
        playerNickname.setText(nickname);
    }
}
