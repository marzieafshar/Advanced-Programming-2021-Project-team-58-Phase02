package Client.View;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class AvatarsController {

    @FXML
    private ImageView imageView;

    private MyListener myListener;

    public void setMyListener(MyListener myListener) {
        this.myListener = myListener;
    }

    public void click(MouseEvent event) {
        myListener.onClickListener(imageView.getImage());
    }

    public void setImage(Image image, MyListener myListener) {
        imageView.setImage(image);
        setMyListener(myListener);
    }


}
