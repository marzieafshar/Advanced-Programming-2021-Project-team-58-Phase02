package Client.View;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class OnlinePlayersController {

    @FXML
    private ImageView playerImage;

    @FXML
    private Label playerNickname;

    public void setItem(Image image , String nickname){
        playerImage.setImage(image);
        playerNickname.setText(nickname);
    }

}
