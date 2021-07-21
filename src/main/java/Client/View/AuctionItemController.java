package Client.View;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class AuctionItemController {

    @FXML
    private ImageView cardImg;

    @FXML
    private Label ownerName;

    @FXML
    private Label lastPriceOffered;

    @FXML
    private Label cardName;

    private MyListener myListener;

    private String id;

    public void click(MouseEvent event) {
        myListener.onClickListener(this);
    }

    public void setItem(String imgSrc, String ownerName, String lastPriceOffered, String cardName, MyListener myListener, String id) {
        this.ownerName.setText(ownerName);
        this.cardName.setText(cardName);
        this.lastPriceOffered.setText(String.valueOf(lastPriceOffered));
        Image image = new Image(getClass().getResourceAsStream(imgSrc));
        cardImg.setImage(image);
        this.myListener = myListener;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getOwnerName() {
        return ownerName.getText();
    }
}