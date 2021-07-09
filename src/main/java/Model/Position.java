package Model;

import Controller.MyListener;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Position {
    private StatusOfPosition status;
    private Card card;
    private boolean isStatusChanged;
    private MyListener myListener;
    private ImageView imageView;



    public Position(StatusOfPosition status) {
        setStatus(status);
    }

    public void setImageViewStatus(){
        imageView.setRotate(0);
        if(status.equals(StatusOfPosition.DEFENSIVE_HIDDEN)) {
            imageView.setImage(new Image(getClass().getResourceAsStream("/Images/Monster/Unknown.jpg")));
            imageView.setRotate(90);
        }
        if(status.equals(StatusOfPosition.DEFENSIVE_OCCUPIED)) {
            imageView.setImage(new Image(getClass().getResourceAsStream(card.getImageSrc())));
            imageView.setRotate(90);
        }
        if(status.equals(StatusOfPosition.OFFENSIVE_OCCUPIED)) {
            imageView.setImage(new Image(getClass().getResourceAsStream(card.getImageSrc())));
        }
        if (status.equals(StatusOfPosition.EMPTY)){
            imageView.setImage(null);
        }
        if(status.equals(StatusOfPosition.SPELL_OR_TRAP_HIDDEN)){
            imageView.setImage(new Image(getClass().getResourceAsStream("/Images/Monster/Unknown.jpg")));
        }
        if (status.equals(StatusOfPosition.SPELL_OR_TRAP_OCCUPIED)){
            imageView.setImage(new Image(getClass().getResourceAsStream(card.getImageSrc())));
        }
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
        setImageViewStatus();
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setMyListener(MyListener myListener) {
        this.myListener = myListener;
    }

    public MyListener getMyListener() {
        return myListener;
    }

    public StatusOfPosition getStatus() {
        return status;
    }

    public void setStatus(StatusOfPosition status) {
        this.status = status;
        setImageViewStatus();
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        //اول status بعد card
        //برای خالی کردن پوزیشن setCard(null)
        this.card = card;
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (card != null)
                    click(event);
            }
        });
        setImageViewStatus();
    }

    public void click(MouseEvent event){
        myListener.onClickListener(this);
    }

    public void setStatusChanged(boolean statusChanged) {
        isStatusChanged = statusChanged;
    }

    public boolean getIsStatusChanged() {
        return isStatusChanged;
    }
}
