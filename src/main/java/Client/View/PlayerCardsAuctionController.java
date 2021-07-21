package Client.View;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class PlayerCardsAuctionController {

    @FXML
    private ImageView imageViewCard;

    private MyListener myListener;

    private String cardName;

    public void click(MouseEvent event) {
        myListener.onClickListener(cardName);
    }

    public void setCard(String imageSrc, String cardName, MyListener myListener) {
        this.myListener = myListener;
        Image image = new Image(getClass().getResourceAsStream(imageSrc));
        imageViewCard.setImage(image);
        this.cardName = cardName;
    }
}
