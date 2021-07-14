package Client.View;

import Client.Model.*;
import com.gilecode.yagson.YaGson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

public class ImportExportMenuController {

    private static ArrayList<Card> importedCards = new ArrayList<>();

    @FXML
    private ImageView selectedCardImage;

    @FXML
    private Text selectedCardInfo;

    @FXML
    private TextField importCardName;
    String importCardNameStr;

    @FXML
    private TextField exportCardName;
    String exportCardNameStr;

    @FXML
    private Label errorLabel;

    private Parent root;

    @FXML
    void back(MouseEvent event) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/MainMenu.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    void exportCard(ActionEvent event) {
        errorLabel.setTextFill(Color.RED);
        exportCardNameStr = exportCardName.getText();
        if (!exportCardNameStr.equals("")) {
            try {
                String str = new String(Files.readAllBytes(Paths.get("ExportedCards.txt")));
                ArrayList<Card> cards = new YaGson().fromJson(str,
                        new TypeToken<ArrayList<Card>>() {
                        }.getType());
                if (Card.getCardByName(exportCardNameStr) == null) {
                    errorLabel.setText("There is no card with this name!");
                } else {
                    cards.add(Card.getCardByName(exportCardNameStr));

                    FileWriter writer = new FileWriter("ExportedCards.txt");
                    writer.write(new YaGson().toJson(cards));
                    writer.close();
                    errorLabel.setTextFill(Color.GREEN);
                    errorLabel.setText("Card " + exportCardNameStr + " exported successfully!");
                    showSelectedCard(Card.getCardByName(exportCardNameStr));
                }
            } catch (Exception e) {
                errorLabel.setText("Failed to export the card");
            }
        } else errorLabel.setText("Please enter a card name");
    }

    @FXML
    void importCard(ActionEvent event) {
        errorLabel.setTextFill(Color.RED);
        importCardNameStr = importCardName.getText();
        if (!importCardNameStr.equals("")) {
            try {
                String str = new String(Files.readAllBytes(Paths.get("CardsDatabase.txt")));
                ArrayList<Card> cards = new YaGson().fromJson(str,
                        new TypeToken<ArrayList<Card>>() {
                        }.getType());
                if (Card.getCardByName(importCardNameStr) != null) {
                    for (Card card : cards) {
                        if (card.getCardName().equals(importCardNameStr)) {
                            importedCards.add(card);
                            errorLabel.setTextFill(Color.GREEN);
                            errorLabel.setText("Card '" + importCardNameStr + "' imported successfully!");
                            showSelectedCard(card);
                        }
                    }
                } else {
                    errorLabel.setText("There is no card with this name!");
                }
            } catch (Exception e) {
                errorLabel.setText("Failed to import the card");
                e.printStackTrace();
            }
        } else {
            errorLabel.setText("Please enter a card name");
        }
    }

    public void showSelectedCard(Card card) {
        Image image = new Image(getClass().getResourceAsStream(card.getImageSrc()));
        selectedCardImage.setImage(image);
        selectedCardInfo.setWrappingWidth(250);
        if (card instanceof MonsterCard) {
            selectedCardInfo.setText("Card Name: " + card.getCardName()+
                    "\nLevel: " + ((MonsterCard) card).getCardLevel() +
                    "\nType: " + ((MonsterCard) card).getMonsterType() +
                    "\nATK: " + ((MonsterCard) card).getAttack() +
                    "\nDEF: " + ((MonsterCard) card).getDefense() +
                    "\nDescription: " + card.getCardDescription());
        } else {
            TrapAndSpellCard TPCard = (TrapAndSpellCard) card;
            selectedCardInfo.setText("Card Name: "+card.getCardName()+
                    "\nType: " + TPCard.getTrapOrSpellTypes() +
                    "\nDescription: " + TPCard.getCardDescription());
        }
    }
}
