package Controller;

import Model.Card;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DeckCardsController {

    public ImageView cardImage;
    private MyListener myListener;
    private Card card;

    public void setCard(Card card , MyListener myListener){
        this.card = card;
        Image image = new Image(getClass().getResourceAsStream(card.getImageSrc()));
        cardImage.setImage(image);
        this.myListener = myListener;
    }
}
