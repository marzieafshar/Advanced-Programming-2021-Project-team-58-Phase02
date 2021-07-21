package Client.View;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlayerCardsAuctionController {

    @FXML
    private ImageView imageViewCard;

    public void setCard(String imageSrc){
        Image image = new Image(getClass().getResourceAsStream(imageSrc));
        imageViewCard.setImage(image);
    }
}
