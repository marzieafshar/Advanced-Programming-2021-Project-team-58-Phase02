package Client.View;

import Client.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

public class ShopController implements Initializable {
    public Label numberOfCardInShop;
    @FXML
    private Label numberOfCard;

    @FXML
    private TextField searchBox;

    @FXML
    private ImageView selectedCardImage;

    @FXML
    private Label selectedCardName;

    @FXML
    private Label selectedCardPrice;

    @FXML
    private Button buyButton;

    @FXML
    private GridPane grid;

    @FXML
    private Label playerMoney;

    private Image image;
    private MyListener myListener;
    public Player player = Controller.getLoggedInPlayer();
    private Parent root;
    private Stage stage;
    private Scene scene;

    String str = "Button_Click.mp3";
    private MediaPlayer mediaPlayer;

    private ArrayList<String> allCards = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadCards();
        setPlayerMoney();
        setChosenCard(allCards.get(0));
        showCards();
    }

    public void showCards() {
        myListener = new MyListener() {
            @Override
            public void onClickListener(Object object) {
                setChosenCard((String) object);
            }
        };
        grid.setMinHeight(Region.USE_COMPUTED_SIZE);
        grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
        grid.setMaxHeight(Region.USE_PREF_SIZE);

        grid.setMinWidth(Region.USE_COMPUTED_SIZE);
        grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
        grid.setMaxWidth(Region.USE_PREF_SIZE);
        int column = 0;
        int row = 1;
        for (int i = 0; i < allCards.size(); i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Fxmls/Items.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController ItemController = fxmlLoader.getController();
                ItemController.setItem(allCards.get(i), myListener);
                if (column == 8) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row);

                GridPane.setMargin(anchorPane, new Insets(5));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setChosenCard(String cardName) {
        selectedCardName.setText(cardName);
        selectedCardPrice.setText(getCardInfo(cardName, "price"));
        image = new Image(getClass().getResourceAsStream(getCardInfo(cardName, "imageSrc")));
        selectedCardImage.setImage(image);
        int cardPrice = Integer.parseInt(getCardInfo(cardName, "price"));
        int playerMoney = Integer.parseInt(ProfileController.getPlayerInfo("money"));
        numberOfCard.setText(getNumberOfPlayerCard(cardName));
        numberOfCardInShop.setText(getNumberOfShopCard(cardName));
        if (cardPrice > playerMoney) {
            buyButton.setDisable(true);
        } else {
            buyButton.setDisable(false);
        }

    }

    public void setPlayerMoney() {
        playerMoney.setText(ProfileController.getPlayerInfo("money"));
    }

    public void buyCard(ActionEvent actionEvent) {
        Media media = new Media(new File(str).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        try {
            Controller.getDataOutputStream().writeUTF("Shop buy"
                    + selectedCardName.getText() + "#" + Controller.getToken());
            Controller.getDataOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setPlayerMoney();
        int playerMoney = Integer.parseInt(ProfileController.getPlayerInfo("money"));
        int cardPrice = Integer.parseInt(getCardInfo(selectedCardName.getText(), "price"));
        int numberInShop = Integer.parseInt(getNumberOfShopCard(selectedCardName.getText()));
        if ((playerMoney < cardPrice) || (numberInShop == 0)) {
            buyButton.setDisable(true);
        }
        numberOfCard.setText(getNumberOfPlayerCard(selectedCardName.getText()));
        numberOfCardInShop.setText(getNumberOfShopCard(selectedCardName.getText()));
    }

    public void showInfo(ActionEvent actionEvent) {
        Media media = new Media(new File(str).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        try {
            Controller.getDataOutputStream().writeUTF("Shop show info" + selectedCardName.getText());
            Controller.getDataOutputStream().flush();
            String result = Controller.getDataInputStream().readUTF();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(selectedCardName.getText());
            alert.setTitle("Information");
            alert.setContentText(result);
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void search(ActionEvent e) {
        Media media = new Media(new File(str).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        String cardName = searchBox.getText();
        if (allCards.contains(cardName))
            setChosenCard(cardName);
    }

    public void backToMainMenu(ActionEvent event) throws IOException {
        Media media = new Media(new File(str).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        JsonSaveAndLoad.save();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/MainMenu.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        setPlayerMoney();
    }

    public void loadCards() {
        try {
            Controller.getDataOutputStream().writeUTF("Shop get cards");
            Controller.getDataOutputStream().flush();
            String result = Controller.getDataInputStream().readUTF();
            String[] allCardsArray = result.split("#");
            allCards.addAll(Arrays.asList(allCardsArray));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getCardInfo(String cardName, String info) {
        try {
            Controller.getDataOutputStream().writeUTF("Shop get card" + info + "#" + cardName);
            Controller.getDataOutputStream().flush();
            String result = Controller.getDataInputStream().readUTF();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getNumberOfPlayerCard(String cardName) {
        try {
            String message = "Shop num of card" + cardName + "#" + Controller.getToken();
            Controller.getDataOutputStream().writeUTF(message);
            Controller.getDataOutputStream().flush();
            String result = Controller.getDataInputStream().readUTF();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getNumberOfShopCard(String cardName) {
        try {
            String message = "Shop number of card" + cardName;
            Controller.getDataOutputStream().writeUTF(message);
            Controller.getDataOutputStream().flush();
            String result = Controller.getDataInputStream().readUTF();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}


