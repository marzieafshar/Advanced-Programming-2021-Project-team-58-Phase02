package Controller;

import Model.Card;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class DeckCardsController {

    private static AnchorPane selected;
    public ImageView cardImage;
    public AnchorPane source;
    private MyListener myListener;
    private Card card;
    private int index;
    private String deckType;

    @FXML
    public void click(MouseEvent mouseEvent) {
        myListener.onClickListener(card);
    }

    public static AnchorPane getSelected() {
        return selected;
    }

    public int getIndex() {
        return index;
    }

    @FXML
    public void handleDragDetectionDeckCards(MouseEvent event) {
        selected = (AnchorPane) event.getSource();
        Dragboard db = source.startDragAndDrop(TransferMode.ANY);
        ClipboardContent cb = new ClipboardContent();
        cb.putString( "Deck" +deckType+ index);
        db.setContent(cb);
    }

    public void setCard(Card card , MyListener myListener , int index, String deckType){
        this.card = card;
        this.index = index;
        Image image = new Image(getClass().getResourceAsStream(card.getImageSrc()));
        cardImage.setImage(image);
        this.myListener = myListener;
        this.deckType = deckType;
    }


}
