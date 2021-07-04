package Java;

import Java.Model.Card;
import Java.MyListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ItemController {
    @FXML
    private ImageView cardPicture;
    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(card);
    }
    private Card card;
    private MyListener myListener;

    public void setItem(Card card,MyListener myListener) {
        this.card = card;
        this.myListener = myListener;
        Image image = new Image(getClass().getResourceAsStream(card.getImageSrc()));
        cardPicture.setImage(image);
    }

}
