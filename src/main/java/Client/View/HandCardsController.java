package Client.View;

import Server.Model.Card;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class HandCardsController {

    private MyListener myListener;
    private Card card;
    private int index;
    @FXML
    private ImageView cardImage;

    @FXML
    void click(MouseEvent event) {
        myListener.onClickListener(card);
    }

    public void setCard(Card card, MyListener myListener, int index, boolean isTurnOfPlayer) {
        this.card = card;
        this.index = index;
        Image image;
        if (isTurnOfPlayer)
            image = new Image(getClass().getResourceAsStream(card.getImageSrc()));
        else
            image = new Image(getClass().getResourceAsStream("/Images/Monster/Unknown.jpg"));
        cardImage.setImage(image);
        this.myListener = myListener;
    }
}
