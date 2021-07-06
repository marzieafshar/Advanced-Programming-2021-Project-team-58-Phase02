package Controller;

import Model.Card;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;

public class DeckCardsController {

    public ImageView cardImage;
    public AnchorPane source;
    private MyListener myListener;
    private Card card;

    public void click(MouseEvent mouseEvent) {
        myListener.onClickListener(card);
    }

    @FXML
    public void handleDragDetection(MouseEvent event) {

    }

    public void setCard(Card card , MyListener myListener){
        this.card = card;
        Image image = new Image(getClass().getResourceAsStream(card.getImageSrc()));
        cardImage.setImage(image);
        this.myListener = myListener;
    }
}
