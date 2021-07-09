package Controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class DeckMenuController implements Initializable {
    private Parent root;
    private Stage stage;
    private Scene scene;

    public GridPane gridDecks;
    public GridPane gridMainDeckCards;
    public GridPane gridSideDeckCards;
    public ImageView selectedCardImage;
    public GridPane gridPlayerCards;
    private MyListener myListener;
    private Player logInPlayer;
    int index;
    private Deck selectedDeck;
    private Card selectedCard;
    private static boolean isGameStarted;

    public static void setIsGameStarted(boolean isGameStarted) {
        DeckMenuController.isGameStarted = isGameStarted;
    }

    public static boolean getIsGameStarted() {
        return isGameStarted;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.logInPlayer = Controller.getLoggedInPlayer();
        if (logInPlayer.getAllCards().size() > 0) {
            showPlayerCards();
        }
        if (logInPlayer.getDecks().size() > 0) {
            addDecksToMenu();
        }
    }

    public void setSelectedDeck(Deck selectedDeck) {
        this.selectedDeck = selectedDeck;
        showMainDeckCards(selectedDeck);
        showSideDeckCards(selectedDeck);
    }

    public void showPlayerCards() {
        gridPlayerCards.getChildren().clear();
        MyListener listenerAllCards = new MyListener() {
            @Override
            public void onClickListener(Object object) {
                setSelectedCardImage((Card) object);
            }
        };
        int column = 0;
        for (int i = 0; i < logInPlayer.getAllCards().size(); i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Fxmls/PlayerCards.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                PlayerCardsController playerCardsController = fxmlLoader.getController();
                playerCardsController.setCard(logInPlayer.getAllCards().get(i), listenerAllCards, i);

                gridPlayerCards.add(anchorPane, column, 1);
                column++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showMainDeckCards(Deck selectedDeck) {
        showDeck(selectedDeck, gridMainDeckCards);
    }

    public void showSideDeckCards(Deck selectedDeck) {
        showDeck(selectedDeck, gridSideDeckCards);
    }

    private void showDeck(Deck selectedDeck, GridPane gridDeckCards) {
        if (selectedDeck != null) {
            ArrayList<Card> tempDeck;
            String deckType = null;
            if (gridDeckCards.equals(gridMainDeckCards)) {
                tempDeck = selectedDeck.getMainDeck();
                deckType = "Main";
            } else {
                tempDeck = selectedDeck.getSideDeck();
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
                                setSelectedCardImage((Card) object);
                            }
                        };
                        deckCardsController.setCard(tempDeck.get(i), myListenerSelectedCard, i, deckType);
//                anchorPane.setCursor();
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
//        JsonSaveAndLoad.save();
        if (isGameStarted) {
            if (logInPlayer.getActiveDeck() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You don't have any active deck!");
                alert.showAndWait();
            } else if (!logInPlayer.getActiveDeck().isValid()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Your active deck is not valid!");
                alert.showAndWait();
            } else {
                setIsGameStarted(false);
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/DuelMenu.fxml")));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
            }
        } else {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/MainMenu.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
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

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    public void setSelectedCardImage(Card card) {
        Image image = new Image(card.getImageSrc());
        selectedCardImage.setImage(image);
        setSelectedCard(card);

    }

    public void createNewDeck(MouseEvent event) {
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
        if (selectedDeck == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You haven't chosen a deck yet!");
            alert.showAndWait();
        } else {
            giveBackCards(selectedDeck);
            Controller.getLoggedInPlayer().getDecks().remove(selectedDeck);
            showPlayerCards();
            addDecksToMenu();
            gridSideDeckCards.getChildren().clear();
            gridMainDeckCards.getChildren().clear();
            setSelectedDeck(null);
        }
    }

    private static void giveBackCards(Deck deck) {
        ArrayList<Card> mainDeck = deck.getMainDeck();
        ArrayList<Card> sideDeck = deck.getSideDeck();
        for (Card card : mainDeck) {
            Controller.getLoggedInPlayer().getAllCards().add(card);
        }
        for (Card card : sideDeck) {
            Controller.getLoggedInPlayer().getAllCards().add(card);
        }
    }

    public void addDecksToMenu() {
        gridDecks.getChildren().clear();
        myListener = new MyListener() {
            @Override
            public void onClickListener(Object object) {
                setSelectedDeck((Deck) object);
            }
        };
        int row = 1;
        for (int i = 0; i < logInPlayer.getDecks().size(); i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Fxmls/DeckIcons.fxml"));
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

    public void handleDragOverDeckCards(DragEvent event) {
        if (event.getDragboard().hasString())
            if(selectedDeck!=null)
                event.acceptTransferModes(TransferMode.ANY);
    }

    public void handleCardDropMainDeckCards(DragEvent event) {
        String message = event.getDragboard().getString();
        if (selectedDeck != null) {
            if (message.startsWith("DeckSide")) {
                index = Integer.parseInt(message.substring(8));
                selectedDeck.addCardToMainDeck(selectedDeck.getSideDeck().get(index));
                selectedDeck.getSideDeck().remove(index);
                showSideDeckCards(selectedDeck);
            } else if (message.startsWith("player")){
                index = Integer.parseInt(message.substring(6));
                selectedDeck.addCardToMainDeck(logInPlayer.getAllCards().get(index));
                logInPlayer.getAllCards().remove(index);
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
                selectedDeck.addCardToSideDeck(selectedDeck.getMainDeck().get(index));
                selectedDeck.getMainDeck().remove(index);
                showSideDeckCards(selectedDeck);
                showMainDeckCards(selectedDeck);
            } else if (message.startsWith("player")){
                index = Integer.parseInt(message.substring(6));
                selectedDeck.addCardToSideDeck(logInPlayer.getAllCards().get(index));
                logInPlayer.getAllCards().remove(index);
                showPlayerCards();
                showSideDeckCards(selectedDeck);
            }
        }
    }


    public void handleCardDropPlayerCards(DragEvent event) {
        String message = event.getDragboard().getString();
        index = Integer.parseInt(message.substring(8));
        if (message.startsWith("DeckMain")) {
            logInPlayer.getAllCards().add(selectedDeck.getMainDeck().get(index));
            selectedDeck.getMainDeck().remove(index);
            showMainDeckCards(selectedDeck);
        } else {
            logInPlayer.getAllCards().add(selectedDeck.getSideDeck().get(index));
            selectedDeck.getSideDeck().remove(index);
            showSideDeckCards(selectedDeck);
        }
        showPlayerCards();
    }

    public void activateDeck(ActionEvent event) {
        logInPlayer.setActiveDeck(selectedDeck);
        addDecksToMenu();
    }
}
