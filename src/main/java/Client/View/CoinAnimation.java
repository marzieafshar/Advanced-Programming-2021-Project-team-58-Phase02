package Client.View;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class CoinAnimation extends Transition {

    private ImageView imageView;

    public CoinAnimation(ImageView imageView) {
        this.imageView = imageView;
        setCycleDuration(Duration.millis(1000));
    }

    @Override
    protected void interpolate(double frac) {
        int frame = (int) Math.floor(frac * 19);
        Image image = new Image(getClass().getResourceAsStream("/Images/Coin/" + frame + ".png"));
        imageView.setImage(image);
    }


}
