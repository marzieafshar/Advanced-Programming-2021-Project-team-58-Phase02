package Client.View;

import Client.Model.Card;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;

public class DeckCardsController {

    private static AnchorPane selected;
    public ImageView cardImage;
    public AnchorPane source;
    private MyListener myListener;
    private String cardName;
    private int index;
    private String deckType;

    @FXML
    public void click(MouseEvent mouseEvent) {
        myListener.onClickListener(cardName);
    }

    public static AnchorPane getSelected() {
        return selected;
    }

    @FXML
    public void handleDragDetectionDeckCards(MouseEvent event) {
        selected = (AnchorPane) event.getSource();
        Dragboard db = source.startDragAndDrop(TransferMode.ANY);
        ClipboardContent cb = new ClipboardContent();
        cb.putString("Deck" + deckType + index);
        db.setContent(cb);
    }

    public void setCard(String cardName, MyListener myListener, int index, String deckType) {
        this.cardName = cardName;
        this.index = index;
        Image image = new Image(getClass().getResourceAsStream(ShopController.getCardInfo(cardName, "imageSrc")));
        cardImage.setImage(image);
        this.myListener = myListener;
        this.deckType = deckType;
    }


}
