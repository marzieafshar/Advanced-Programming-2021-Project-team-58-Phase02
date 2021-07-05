package Controller;

import Model.Deck;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class DeckIconController {
    @FXML
    private Label deckName;

    private Deck deck;
    private MyListener myListener;

    public void click(MouseEvent mouseEvent) {
        myListener.onClickListener(deck);
    }

    public void setDeckLabel(Deck deck , MyListener myListener){
        this.deck = deck;
        this.myListener = myListener;
        deckName.setText(deck.getDeckName());
    }
}
