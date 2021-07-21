package Client.View;

import Client.Model.*;
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
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.plaf.synth.ColorType;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

public class DeckMenuController implements Initializable {
    private Parent root;
    private Stage stage;
    private Scene scene;
    public Text selectedCardInfo;
    public GridPane gridDecks;
    public GridPane gridMainDeckCards;
    public GridPane gridSideDeckCards;
    public ImageView selectedCardImage;
    public GridPane gridPlayerCards;
    private MyListener myListener;
    int index;
    private String selectedDeck;
    private String selectedCard;
    private static boolean isGameStarted;

    String str = "Button_Click.mp3";
    Media media = new Media(new File(str).toURI().toString());
    private MediaPlayer mediaPlayer;

    private static ArrayList<String> playerAllCards = new ArrayList<>();
    private ArrayList<String> playerAllDecks = new ArrayList<>();

    public static void setIsGameStarted(boolean isGameStarted) {
        DeckMenuController.isGameStarted = isGameStarted;
    }

    public static boolean getIsGameStarted() {
        return isGameStarted;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadPlayerCards();
        loadPlayerDecks();
        showPlayerCards();
        if (playerAllDecks.size() > 0) {
            addDecksToMenu();
        }
    }

    public void setSelectedDeck(String selectedDeck) {
        this.selectedDeck = selectedDeck;
        showMainDeckCards(selectedDeck);
        showSideDeckCards(selectedDeck);
    }

    public void showPlayerCards() {
        gridPlayerCards.getChildren().clear();
        MyListener listenerAllCards = new MyListener() {
            @Override
            public void onClickListener(Object object) {
                setSelectedCardImage((String) object);
            }
        };
        int column = 0;
        for (int i = 0; i < playerAllCards.size(); i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Fxmls/PlayerCards.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                PlayerCardsController playerCardsController = fxmlLoader.getController();
                playerCardsController.setCard(playerAllCards.get(i), listenerAllCards, i);

                gridPlayerCards.add(anchorPane, column, 1);
                column++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showMainDeckCards(String selectedDeck) {
        showDeck(selectedDeck, gridMainDeckCards);
    }

    public void showSideDeckCards(String selectedDeck) {
        showDeck(selectedDeck, gridSideDeckCards);
    }

    private void showDeck(String selectedDeck, GridPane gridDeckCards) {
        if (selectedDeck != null) {
            ArrayList<String> tempDeck;
            String deckType = "";
            if (gridDeckCards.equals(gridMainDeckCards)) {
                tempDeck = getMainDeckCards(selectedDeck);
                deckType = "Main";
            } else {
                tempDeck = getSideDeckCards(selectedDeck);
                deckType = "Side";
            }
            gridDeckCards.getChildren().clear();
            int row = 1;
            int column = 0;
            if (tempDeck.size() > 0) {
                for (int i = 0; i < tempDeck.size(); i++) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/Fxmls/DeckItems.fxml"));
                        AnchorPane anchorPane = fxmlLoader.load();

                        DeckCardsController deckCardsController = fxmlLoader.getController();
                        MyListener myListenerSelectedCard = new MyListener() {
                            @Override
                            public void onClickListener(Object object) {
                                setSelectedCardImage((String) object);
                            }
                        };
                        deckCardsController.setCard(tempDeck.get(i), myListenerSelectedCard, i, deckType);
                        if (column == 12) {
                            column = 0;
                            row++;
                        }
                        gridDeckCards.add(anchorPane, column++, row);

                        GridPane.setMargin(anchorPane, new Insets(5));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                gridDeckCards.getChildren().clear();
            }
        }
    }

    public void back(MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/MainMenu.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }

    public void showInfo() {
        try {
            selectedCardInfo.setWrappingWidth(250);
            Controller.getDataOutputStream().writeUTF("Shop show info" + selectedCard);
            Controller.getDataOutputStream().flush();
            String result = Controller.getDataInputStream().readUTF();
            selectedCardInfo.setText(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSelectedCard(String selectedCardName) {
        this.selectedCard = selectedCardName;
    }

    public void setSelectedCardImage(String cardName) {
        Image image = new Image(ShopController.getCardInfo(cardName, "imageSrc"));
        selectedCardImage.setImage(image);
        setSelectedCard(cardName);
        showInfo();

    }

    public void createNewDeck(MouseEvent event) {
        Media media = new Media(new File(str).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        try {
            Stage thisStage = (Stage) selectedCardImage.getScene().getWindow();
            CreateDeckController.setDeckMenuStage(thisStage);
            CreateDeckController.setDeckMenuController(this);
            Parent root = FXMLLoader.load(getClass().getResource("/Fxmls/PopupCreateDeck.fxml"));
            FXMLLoader fxmlLoader = new FXMLLoader();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteDeck(MouseEvent event) {
        Media media = new Media(new File(str).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        if (selectedDeck == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You haven't chosen a deck yet!");
            alert.showAndWait();
        } else {
            giveBackCards(selectedDeck);
            removeDeckFromPlayerDecks(selectedDeck);
            gridSideDeckCards.getChildren().clear();
            gridMainDeckCards.getChildren().clear();
            setSelectedDeck(null);
            loadPlayerCards();
            loadPlayerDecks();
            showPlayerCards();
            addDecksToMenu();
        }
    }

    private static void giveBackCards(String deckName) {
        ArrayList<String> mainDeck = getMainDeckCards(deckName);
        ArrayList<String> sideDeck = getSideDeckCards(deckName);
        for (String cardName : mainDeck) {
            addToPlayerCards(cardName);
        }
        for (String cardName : sideDeck) {
            addToPlayerCards(cardName);
        }
    }

    public void addDecksToMenu() {
        gridDecks.getChildren().clear();
        myListener = new MyListener() {
            @Override
            public void onClickListener(Object object) {
                setSelectedDeck((String) object);
            }
        };
        int row = 1;
        for (int i = 0; i < playerAllDecks.size(); i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Fxmls/DeckIcons.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                DeckIconController deckIconController = fxmlLoader.getController();
                deckIconController.setDeckLabel(playerAllDecks.get(i), myListener);
                gridDecks.add(anchorPane, 0, row);
                row++;
                GridPane.setMargin(anchorPane, new Insets(5));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleDragOverDeckCards(DragEvent event) {
        if (event.getDragboard().hasString())
            if (selectedDeck != null)
                event.acceptTransferModes(TransferMode.ANY);
    }

    public void handleCardDropMainDeckCards(DragEvent event) {
        String message = event.getDragboard().getString();
        if (selectedDeck != null) {
            if (message.startsWith("DeckSide")) {
                index = Integer.parseInt(message.substring(8));
                addCardToMainDeck(getSideDeckCards(selectedDeck).get(index), selectedDeck);
                removeFromSideDeck(index, selectedDeck);
                showSideDeckCards(selectedDeck);
            } else if (message.startsWith("player")) {
                index = Integer.parseInt(message.substring(6));
                addCardToMainDeck(playerAllCards.get(index), selectedDeck);
                removeCardFromPlayerCards(index);
                loadPlayerCards();
                showPlayerCards();
            }
            showMainDeckCards(selectedDeck);
        }
    }

    public void handleCardDropSideDeckCards(DragEvent event) {
        String message = event.getDragboard().getString();
        if (selectedDeck != null) {
            if (message.startsWith("DeckMain")) {
                index = Integer.parseInt(message.substring(8));
                addCardToSideDeck(getMainDeckCards(selectedDeck).get(index), selectedDeck);
                removeFromMainDeck(index, selectedDeck);
                showSideDeckCards(selectedDeck);
                showMainDeckCards(selectedDeck);
            } else if (message.startsWith("player")) {
                index = Integer.parseInt(message.substring(6));
                addCardToSideDeck(playerAllCards.get(index), selectedDeck);
                removeCardFromPlayerCards(index);
                loadPlayerCards();
                showPlayerCards();
                showSideDeckCards(selectedDeck);
            }
        }
    }


    public void handleCardDropPlayerCards(DragEvent event) {
        String message = event.getDragboard().getString();
        index = Integer.parseInt(message.substring(8));
        if (message.startsWith("DeckMain")) {
            addToPlayerCards(getMainDeckCards(selectedDeck).get(index));
            removeFromMainDeck(index, selectedDeck);
            showMainDeckCards(selectedDeck);
        } else {
            addToPlayerCards(getSideDeckCards(selectedDeck).get(index));
            removeFromSideDeck(index, selectedDeck);
            showSideDeckCards(selectedDeck);
        }
        loadPlayerCards();
        showPlayerCards();
    }


    public void activateDeck(ActionEvent event) {
        Media media = new Media(new File(str).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        activateDeckByName(selectedDeck);
        loadPlayerDecks();
        addDecksToMenu();
    }

    public void activateDeckByName(String deckName) {
        try {
            Controller.getDataOutputStream().writeUTF("Deck set activate"
                    + deckName + "#" + Controller.getToken());
            Controller.getDataOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPlayerCards() {
        try {
            Controller.getDataOutputStream().writeUTF("Deck player cards" + Controller.getToken());
            Controller.getDataOutputStream().flush();
            String str = Controller.getDataInputStream().readUTF();
            String[] allCardsArray = str.split("#");
            playerAllCards.clear();
            if (!allCardsArray[0].equals("")) {
                playerAllCards.addAll(Arrays.asList(allCardsArray));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPlayerDecks() {
        try {
            Controller.getDataOutputStream().writeUTF("Deck player decks"
                    + Controller.getToken());
            Controller.getDataOutputStream().flush();
            String str = Controller.getDataInputStream().readUTF();
            String[] allDecksArray = str.split("#");
            playerAllDecks.clear();
            if (!allDecksArray[0].equals("")) {
                playerAllDecks.addAll(Arrays.asList(allDecksArray));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getMainDeckCards(String deckName) {
        try {
            ArrayList<String> result = new ArrayList<>();
            Controller.getDataOutputStream().writeUTF("Deck main deck cards"
                    + deckName + "#" + Controller.getToken());
            Controller.getDataOutputStream().flush();
            String str = Controller.getDataInputStream().readUTF();
            String[] mainDeckArray = str.split("#");
            if (!mainDeckArray[0].equals("")) {
                result.addAll(Arrays.asList(mainDeckArray));
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<String> getSideDeckCards(String deckName) {
        try {
            ArrayList<String> result = new ArrayList<>();
            Controller.getDataOutputStream().writeUTF("Deck side deck cards"
                    + deckName + "#" + Controller.getToken());
            Controller.getDataOutputStream().flush();
            String str = Controller.getDataInputStream().readUTF();
            String[] sideDeckArray = str.split("#");
            if (!sideDeckArray[0].equals("")) {
                result.addAll(Arrays.asList(sideDeckArray));
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addToPlayerCards(String cardName) {
        try {
            Controller.getDataOutputStream().writeUTF("Deck add card player"
                    + cardName + "#" + Controller.getToken());
            Controller.getDataOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeDeckFromPlayerDecks(String selectedDeckName) {
        try {
            Controller.getDataOutputStream().writeUTF("Deck remove deck player"
                    + selectedDeckName + "#" + Controller.getToken());
            Controller.getDataOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeFromSideDeck(int index, String deckName) {
        try {
            Controller.getDataOutputStream().writeUTF("Deck remove card from side"
                    + index + "#" + deckName + "#" + Controller.getToken());
            Controller.getDataOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeFromMainDeck(int index, String deckName) {
        try {
            Controller.getDataOutputStream().writeUTF("Deck remove card from main"
                    + index + "#" + deckName + "#" + Controller.getToken());
            Controller.getDataOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeCardFromPlayerCards(int index) {
        try {
            Controller.getDataOutputStream().writeUTF("Deck remove from player cards"
                    + index + "#" + Controller.getToken());
            Controller.getDataOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addCardToMainDeck(String cardName, String selectedDeck) {
        try {
            Controller.getDataOutputStream().writeUTF("Deck add card to main"
                    + cardName + "#" + selectedDeck + "#" + Controller.getToken());
            Controller.getDataOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addCardToSideDeck(String cardName, String selectedDeck) {
        try {
            Controller.getDataOutputStream().writeUTF("Deck add card to side"
                    + cardName + "#" + selectedDeck + "#" + Controller.getToken());
            Controller.getDataOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
