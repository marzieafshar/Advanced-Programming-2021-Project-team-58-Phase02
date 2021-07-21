package Client.View;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class DeckIconController {
    public ImageView deckIcon;
    @FXML
    private Label deckName;

    private String deckNameStr;
    private MyListener myListener;

    public void click(MouseEvent mouseEvent) {
        myListener.onClickListener(deckNameStr);
    }

    public void setDeckLabel(String deckNameStr, MyListener myListener) {

        String result = ProfileController.getPlayerInfo("active deck name", Controller.getToken());
        if(!result.equals("Not set yet")) {
            if (result.equals(deckNameStr)) {
                Image image = new Image(getClass().getResourceAsStream("/Images/Monster/activeDeck.jpg"));
                deckIcon.setImage(image);
            }
        }
        this.deckNameStr = deckNameStr;
        this.myListener = myListener;
        deckName.setText(deckNameStr);
    }
}
