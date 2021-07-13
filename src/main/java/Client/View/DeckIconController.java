package Client.View;

import Server.Model.Deck;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class DeckIconController {
    public ImageView deckIcon;
    @FXML
    private Label deckName;

    private Deck deck;
    private MyListener myListener;

    public void click(MouseEvent mouseEvent) {
        myListener.onClickListener(deck);
    }

    public void setDeckLabel(Deck deck, MyListener myListener) {
        if (Controller.getLoggedInPlayer().getActiveDeck() != null) {
            if (Controller.getLoggedInPlayer().getActiveDeck().equals(deck)) {
                Image image = new Image(getClass().getResourceAsStream("/Images/Monster/activeDeck.jpg"));
                deckIcon.setImage(image);
            }
        }
        this.deck = deck;
        this.myListener = myListener;
        deckName.setText(deck.getDeckName());
    }
}
