package Controller;

import Model.Card;
import Model.Game;
import Model.MonsterCard;
import Model.TrapAndSpellCard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DuelFieldController implements Initializable {


    public GridPane gridHandCards;
    public GridPane gridHandCardsOpponent;
    public ImageView selectedCardImage;
    @FXML
    private ImageView oppositionAvatar;

    @FXML
    private Label oppositionLP;

    @FXML
    private ProgressBar oppositionProgressBar;

    @FXML
    private ImageView turnOfPlayerAvatar;

    @FXML
    private ProgressBar turnOfPlayerProgressBar;

    @FXML
    private Label turnOfPlayerLP;

    private Game game;

    private Card selectedCard;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        game = new Game(FlipCoinController.getWinner(),FlipCoinController.getLoser());
        game.startOfGameSettings(FlipCoinController.getWinner(), turnOfPlayerLP, oppositionLP);
        game.setController(this);
        game.run();
    }


    public void setSelectedCardImage(Card card) {
        if(card == null){
            Image image = new Image(getClass().getResourceAsStream("/Images/Monster/Unknown.jpg"));
            selectedCardImage.setImage(image);
            selectedCard = null;
        }
        else{
            Image image = new Image(getClass().getResourceAsStream(card.getImageSrc()));
            selectedCardImage.setImage(image);
            selectedCard = card;
        }
    }

    public void showOppositionHandCards(){
        int margin = -5;
        int handSize = game.getOpposition().getHand().size();
        ArrayList<Card> hand = game.getOpposition().getHand();
        int column = 0;
        for (int i = 0; i < handSize; i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Fxmls/handCards.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                HandCardsController handCardsController = fxmlLoader.getController();
                MyListener myListenerSelectedCard = new MyListener() {
                    @Override
                    public void onClickListener(Object object) {
                        setSelectedCardImage(null);
                    }
                };
                handCardsController.setCard(hand.get(i), myListenerSelectedCard, i , false);
                gridHandCardsOpponent.add(anchorPane , column , 0);
                column++;
                if(handSize >= 8){
                    int x = margin*(handSize-7);
                    GridPane.setMargin(anchorPane, new Insets(x));
                }
                else{
                    GridPane.setMargin(anchorPane, new Insets(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void showTurnOfPlayerHandCards() {
        int margin = -5;
        int handSize = game.getTurnOfPlayer().getHand().size();
        ArrayList<Card> hand = game.getTurnOfPlayer().getHand();
        int column = 0;
        for (int i = 0; i < handSize; i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Fxmls/handCards.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                HandCardsController handCardsController = fxmlLoader.getController();
                MyListener myListenerSelectedCard = new MyListener() {
                    @Override
                    public void onClickListener(Object object) {
                        setSelectedCardImage((Card) object);
                    }
                };
                handCardsController.setCard(hand.get(i), myListenerSelectedCard, i , true);
                gridHandCards.add(anchorPane , column , 0);
                column++;
                if(handSize >= 8){
                    int x = margin*(handSize-7);
                    GridPane.setMargin(anchorPane, new Insets(x));
                }
                else{
                    GridPane.setMargin(anchorPane, new Insets(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void showInfo(ActionEvent actionEvent) {
        if (selectedCard != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(selectedCard.getCardName());
            alert.setTitle("Information");
            Card card = selectedCard;
            if (card instanceof MonsterCard) {
                alert.setContentText("Level: " + ((MonsterCard) card).getCardLevel() +
                        "\nType: " + ((MonsterCard) card).getMonsterType() +
                        "\nATK: " + ((MonsterCard) card).getAttack() +
                        "\nDEF: " + ((MonsterCard) card).getDefense() +
                        "\nDescription: " + card.getCardDescription());
            } else {
                TrapAndSpellCard TPCard = (TrapAndSpellCard) card;
                alert.setContentText("Type: " + TPCard.getTrapOrSpellTypes() +
                        "\nDescription: " + TPCard.getCardDescription());
            }
//        alert.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("Images/Bitron.jpg"))));
            alert.showAndWait();
        }
    }
}
