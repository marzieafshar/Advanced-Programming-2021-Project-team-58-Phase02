package Client.View;

import Client.Model.Card;
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
    private String cardName;
    private int index;
    @FXML
    private AnchorPane source;
    @FXML
    private ImageView cardImage;

    @FXML
    void click(MouseEvent event) {
        myListener.onClickListener(cardName);
    }

    @FXML
    void handleDragDetectionPlayerCards(MouseEvent event) {
        Dragboard db = source.startDragAndDrop(TransferMode.ANY);
        ClipboardContent cb = new ClipboardContent();
        cb.putString( "player" + index);
        db.setContent(cb);
    }

    public void setCard(String cardName, MyListener myListener , int index) {
        this.cardName = cardName;
        this.index = index;
        Image image = new Image(getClass().getResourceAsStream(ShopController.getCardInfo(cardName, "imageSrc")));
        cardImage.setImage(image);
        this.myListener = myListener;
    }
}
