package Client.View;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AuctionItemController {

    @FXML
    private ImageView cardImg;

    @FXML
    private Label ownerName;

    @FXML
    private Label lastPriceOffered;

    @FXML
    private Label cardName;

    public void setItem(String imgSrc, String ownerName, String lastPriceOffered, String cardName) {
        this.ownerName.setText(ownerName);
        this.cardName.setText(cardName);
        this.lastPriceOffered.setText(String.valueOf(lastPriceOffered));
        Image image = new Image(getClass().getResourceAsStream(imgSrc));
        cardImg.setImage(image);
    }
}