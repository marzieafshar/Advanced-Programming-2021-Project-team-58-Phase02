package Java;

import Java.ItemController;
import Java.Model.*;
import Java.MyListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class ShopController implements Initializable {
    @FXML
    private TextField searchBox;

    @FXML
    private Button searchButton;

    @FXML
    private ImageView selectedCardImage;

    @FXML
    private Label selectedCardName;

    @FXML
    private Label selectedCardPrice;

    @FXML
    private Button infoButton;

    @FXML
    private Button buyButton;

    @FXML
    private Button backButton;

    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;

    @FXML
    private Label playerMoney;

    private Image image;
    private MyListener myListener;
    public Player player;

    private ArrayList<Card> allCards = Card.getAllCards();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Card.getAllCards().size() > 0) {
            player = new Player("Marzie", "Marzie", "Marzie");
            setPlayerMoney();
            setChosenCard(Card.getAllCards().get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Card card) {
                    setChosenCard(card);
                }
            };
        }
        grid.setMinHeight(Region.USE_COMPUTED_SIZE);
        grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
        grid.setMaxHeight(Region.USE_PREF_SIZE);

        grid.setMinWidth(Region.USE_COMPUTED_SIZE);
        grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
        grid.setMaxWidth(Region.USE_PREF_SIZE);
        int column = 0;
        int row = 1;
        for (int i = 0; i < Card.getAllCards().size(); i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Items.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController ItemController = fxmlLoader.getController();
                ItemController.setItem(Card.getAllCards().get(i), myListener);
                if (column == 8) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row);

                GridPane.setMargin(anchorPane, new Insets(10));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setChosenCard(Card card) {
        selectedCardName.setText(card.getCardName());
        selectedCardPrice.setText(String.valueOf(card.getPrice()));
        image = new Image(getClass().getResourceAsStream(card.getImageSrc()));
        selectedCardImage.setImage(image);
    }

    public void setPlayerMoney() {
        playerMoney.setText(String.valueOf(player.getMoney()));
    }

    public void buyCard(ActionEvent actionEvent) {
        player.decreaseMoney(Card.getCardByName(selectedCardName.getText()).getPrice());
        player.getAllCards().add(Card.getCardByName(selectedCardName.getText()));
        setPlayerMoney();
    }

    public void showInfo(ActionEvent actionEvent) {
        Card card = Card.getCardByName(selectedCardName.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(selectedCardName.getText());
        alert.setTitle("Information");
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
