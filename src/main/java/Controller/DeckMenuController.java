package Controller;

import Model.Card;
import Model.Deck;
import Model.Player;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DeckMenuController implements Initializable {


    public GridPane gridDecks;
    public GridPane gridDeckCards;
    private MyListener myListener;
    private Player logInPlayer;

    private Deck selectedDeck;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.logInPlayer = Controller.getLoggedInPlayer();
        System.out.println(logInPlayer.getDecks().size());
        if(logInPlayer.getDecks().size() > 0) {
            myListener = new MyListener() {
                @Override
                public void onClickListener(Object object) {
                    setSelectedDeck((Deck)object);
                }
            };
            int row = 1;


            for (int i = 0; i < logInPlayer.getDecks().size(); i++) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/deckIcons.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    DeckIconController deckIconController = fxmlLoader.getController();
                    deckIconController.setDeckLabel(logInPlayer.getDecks().get(i), myListener);
//                anchorPane.setCursor();
                    gridDecks.add(anchorPane, 0, row);
                    row++;
                    GridPane.setMargin(anchorPane, new Insets(5));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setSelectedDeck(Deck selectedDeck) {
        this.selectedDeck = selectedDeck;
        int row = 1;
        int column = 0;
        for (int i = 0; i < selectedDeck.getMainDeckSize(); i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/deckItems.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                DeckCardsController deckIconController = fxmlLoader.getController();
                deckIconController.setCard(selectedDeck.getMainDeck().get(i), myListener);
//                anchorPane.setCursor();
                if(column == 12){
                    column = 0;
                    row++;
                }
                gridDeckCards.add(anchorPane, column++, row);

                GridPane.setMargin(anchorPane, new Insets(5));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
