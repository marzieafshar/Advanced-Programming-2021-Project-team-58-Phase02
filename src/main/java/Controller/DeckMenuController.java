package Controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DeckMenuController implements Initializable {


    public GridPane gridDecks;
    public GridPane gridDeckCards;
    public ImageView selectedCardImage;
    private MyListener myListener;
    private Player logInPlayer;

    private Deck selectedDeck;
    private Card selectedCard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.logInPlayer = Controller.getLoggedInPlayer();
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

                DeckCardsController deckCardsController = fxmlLoader.getController();
                MyListener myListenerSelectedCard = new MyListener() {
                    @Override
                    public void onClickListener(Object object) {
                        setSelectedCardImage((Card) object);
                    }
                };
                deckCardsController.setCard(selectedDeck.getMainDeck().get(i), myListenerSelectedCard);
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

    public void back(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/MainMenu.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void showInfo(ActionEvent actionEvent) {
        if(selectedCard != null) {
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

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    public void setSelectedCardImage(Card card){
        Image image = new Image(card.getImageSrc());
        selectedCardImage.setImage(image);
        setSelectedCard(card);

    }

}
