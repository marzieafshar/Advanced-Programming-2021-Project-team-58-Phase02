package Client.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodTextRun;
import javafx.scene.input.MouseEvent;
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

    @FXML
    private ImageView decreaseImageView;

    @FXML
    private ImageView increaseImageView;

    @FXML
    private ImageView forbiddenImageView;

    @FXML
    private ImageView cardImageView;

    @FXML
    private Button sellButton;

    public ImageView adminImageView;

    private Image image;
    private MyListener myListener;
    private Parent root;
    private Stage stage;
    private Scene scene;
    private boolean isAdmin;

    String str = "Button_Click.mp3";
    private MediaPlayer mediaPlayer;
    private ArrayList<String> allCards = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isAdmin = false;
        disableAdminButtons();
        loadCards();
        setPlayerMoney();
        setChosenCard(allCards.get(0));
        showCards();
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void disableAdminButtons() {
        decreaseImageView.setVisible(false);
        increaseImageView.setVisible(false);
        forbiddenImageView.setVisible(false);
        cardImageView.setVisible(false);
    }

    public void enableAdminButtons() {
        decreaseImageView.setVisible(true);
        increaseImageView.setVisible(true);
        forbiddenImageView.setVisible(true);
        cardImageView.setVisible(true);
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
                ItemController.setItem(allCards.get(i), myListener, isCardForbidden(allCards.get(i)));
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
        Reflection reflection = new Reflection();
        reflection.setFraction(0.4);
        reflection.setTopOpacity(0.35);
        if (isCardForbidden(cardName)) {
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setSaturation(-1);
            colorAdjust.setBrightness(0);
            reflection.setInput(colorAdjust);
        }
        selectedCardImage.setEffect(reflection);

        int cardPrice = Integer.parseInt(getCardInfo(cardName, "price"));
        int playerMoney = Integer.parseInt(ProfileController.getPlayerInfo("money", Controller.getToken()));
        int numberInShop = Integer.parseInt(getNumberOfShopCard(cardName));
        int playerCardNumber = Integer.parseInt(getNumberOfPlayerCard(cardName));

        numberOfCard.setText(getNumberOfPlayerCard(cardName));
        numberOfCardInShop.setText(getNumberOfShopCard(cardName));
        sellButton.setDisable(playerCardNumber == 0);
        buyButton.setDisable((cardPrice > playerMoney) || (isCardForbidden(cardName)) || (numberInShop == 0));
    }


    public void setPlayerMoney() {
        playerMoney.setText(ProfileController.getPlayerInfo("money", Controller.getToken()));
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
        int playerMoney = Integer.parseInt(ProfileController.getPlayerInfo("money", Controller.getToken()));
        int cardPrice = Integer.parseInt(getCardInfo(selectedCardName.getText(), "price"));
        int numberInShop = Integer.parseInt(getNumberOfShopCard(selectedCardName.getText()));
        int playerCardNumber = Integer.parseInt(getNumberOfPlayerCard(selectedCardName.getText()));

        if ((playerMoney < cardPrice) || (numberInShop == 0)) {
            buyButton.setDisable(true);
        }
        if (playerCardNumber > 0) {
            sellButton.setDisable(false);
        }
        numberOfCard.setText(getNumberOfPlayerCard(selectedCardName.getText()));
        numberOfCardInShop.setText(getNumberOfShopCard(selectedCardName.getText()));
        setPlayerMoney();

    }

    public void sellCard(ActionEvent event) {
        Media media = new Media(new File(str).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);

        try {
            Controller.getDataOutputStream().writeUTF("Shop sell" + selectedCardName.getText()
                    + "#" + Controller.getToken());
            Controller.getDataOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int playerCardNumber = Integer.parseInt(getNumberOfPlayerCard(selectedCardName.getText()));
        int numberInShop = Integer.parseInt(getNumberOfShopCard(selectedCardName.getText()));

        if (playerCardNumber == 0) {
            sellButton.setDisable(true);
        }
        if (numberInShop > 0) {
            buyButton.setDisable(false);
        }

        numberOfCard.setText(getNumberOfPlayerCard(selectedCardName.getText()));
        numberOfCardInShop.setText(getNumberOfShopCard(selectedCardName.getText()));
        setPlayerMoney();
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
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/MainMenu.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        setPlayerMoney();
    }

    public void changeMode(MouseEvent event) {
        if (isAdmin) {
            disableAdminButtons();
            isAdmin = false;
            Image image = new Image(getClass().getResourceAsStream("/Images/Icon/admin.png"));
            adminImageView.setImage(image);
        } else {
            AdminPasswordController.setShopController(this);
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/PopupAdminPassword.fxml")));
                Stage stage1 = new Stage();
                scene = new Scene(root);
                stage1.setScene(scene);
                stage1.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    public void forbidCard(MouseEvent event) {
        try {
            Controller.getDataOutputStream().writeUTF("Shop forbid" + selectedCardName.getText());
            Controller.getDataOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        showCards();
        setChosenCard(selectedCardName.getText());
    }

    private static boolean isCardForbidden(String cardName) {
        try {
            Controller.getDataOutputStream().writeUTF("Shop is card forbidden" + cardName);
            Controller.getDataOutputStream().flush();
            return Boolean.parseBoolean(Controller.getDataInputStream().readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void increaseCardNumberInShop(MouseEvent event) {
        try {
            Controller.getDataOutputStream().writeUTF("Shop increase shop card" + selectedCardName.getText());
            Controller.getDataOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        numberOfCardInShop.setText(getNumberOfShopCard(selectedCardName.getText()));
    }

    public void decreaseCardNumberInShop(MouseEvent event) {
        try {
            Controller.getDataOutputStream().writeUTF("Shop decrease shop card" + selectedCardName.getText());
            Controller.getDataOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        numberOfCardInShop.setText(getNumberOfShopCard(selectedCardName.getText()));
    }

    public void goToAuction(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/Auction.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }
}


