package Controller;

import Model.Card;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

public class ItemController {
    private Card card;
    private MyListener myListener;
    @FXML
    private ImageView cardPicture;
    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(card);
    }

    public void setItem(Card card,MyListener myListener) {
        this.card = card;
        this.myListener = myListener;
        Image image = new Image(getClass().getResourceAsStream(card.getImageSrc()));
        cardPicture.setImage(image);
    }

}
