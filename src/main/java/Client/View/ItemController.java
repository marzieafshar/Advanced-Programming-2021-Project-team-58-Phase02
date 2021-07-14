package Client.View;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ItemController {
    private String cardName;
    private MyListener myListener;
    @FXML
    private ImageView cardPicture;

    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(cardName);
    }

    public void setItem(String cardName, MyListener myListener) {
        this.cardName = cardName;
        this.myListener = myListener;
        String imageSrc = ShopController.getCardInfo(cardName, "imageSrc");
        Image image = new Image(getClass().getResourceAsStream(imageSrc));
        cardPicture.setImage(image);
    }
}
