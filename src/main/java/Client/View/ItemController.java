package Client.View;

import javafx.fxml.FXML;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ItemController {
    private String cardName;
    private MyListener myListener;
    @FXML
    private ImageView cardPicture;

    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(cardName);
    }

    public void setItem(String cardName, MyListener myListener , boolean isForbidden) {
        this.cardName = cardName;
        this.myListener = myListener;
        String imageSrc = ShopController.getCardInfo(cardName, "imageSrc");
        Image image = new Image(getClass().getResourceAsStream(imageSrc));
        cardPicture.setImage(image);
        if(isForbidden){
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setSaturation(-1);
            colorAdjust.setBrightness(-0.5);
            cardPicture.setEffect(colorAdjust);
        }

    }
}
