package Controller;

import Model.Card;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class DeckCardsController {

    public ImageView cardImage;
    private MyListener myListener;
    private Card card;

    public void click(MouseEvent mouseEvent) {
        myListener.onClickListener(card);
    }

    public void setCard(Card card , MyListener myListener){
        this.card = card;
        Image image = new Image(getClass().getResourceAsStream(card.getImageSrc()));
        cardImage.setImage(image);
        this.myListener = myListener;
    }
}
