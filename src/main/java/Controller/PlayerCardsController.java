package Controller;

import Model.Card;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;

public class PlayerCardsController {

    private MyListener myListener;
    private Card card;
    @FXML
    private AnchorPane source;

    @FXML
    private ImageView cardImage;

    @FXML
    void click(MouseEvent event) {
        myListener.onClickListener(card);
    }

    @FXML
    void handleDragDetection(MouseEvent event) {
        Dragboard db = source.startDragAndDrop(TransferMode.ANY);
        ClipboardContent cb = new ClipboardContent();
        cb.putString(card.getCardName());
        db.setContent(cb);
    }

    public void setCard(Card card, MyListener myListener) {
        this.card = card;
        Image image = new Image(getClass().getResourceAsStream(card.getImageSrc()));
        cardImage.setImage(image);
        this.myListener = myListener;
    }
}
