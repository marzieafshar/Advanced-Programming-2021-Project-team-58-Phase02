package Client.View;

import com.sun.javafx.scene.traversal.ContainerTabOrder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class AuctionController implements Initializable {

    private Parent root;
    private Stage stage;
    private Scene scene;

    private ArrayList<String> playerAllCards = new ArrayList<>();
    private String selectedCardName;

    @FXML
    private GridPane gridPane;

    @FXML
    private GridPane gridPanePlayerCards;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField basePriceTextField;

    @FXML
    private Button createButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadOnGoingAuctions();
            setVisibleAuctionFields(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadPlayerCards();
        errorLabel.setText("");
    }

    private void loadOnGoingAuctions() throws IOException {
        Controller.getDataOutputStream().writeUTF("Auction send data");
        Controller.getDataOutputStream().flush();
        String[] auctionData = Controller.getDataInputStream().readUTF().split("#");
        int column = 1;
        for (int i = 0; i < auctionData.length; i = i + 4) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Fxmls/AuctionItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                AuctionItemController auctionItemController = fxmlLoader.getController();
                String imgSrc = auctionData[i];
                String ownerName = auctionData[i + 1];
                String lastPriceOffered = auctionData[i + 2];
                String cardName = auctionData[i + 3];
                auctionItemController.setItem(imgSrc, ownerName, lastPriceOffered, cardName);

                gridPane.add(anchorPane, column++, 0);

                GridPane.setMargin(anchorPane, new Insets(10));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void backToShop(ActionEvent event) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/Shop.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPlayerCards() {
        try {
            Controller.getDataOutputStream().writeUTF("Auction player cards data" + Controller.getToken());
            Controller.getDataOutputStream().flush();
            String[] tmp = Controller.getDataInputStream().readUTF().split("#");
            int column = 1;

            MyListener myListener = new MyListener() {
                @Override
                public void onClickListener(Object object) {
                    setSelectedCardName((String) object);
                }
            };
            if(!tmp[0].equals("")) {
                for (int i = 0; i < tmp.length; i += 2) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/Fxmls/playerCardsAuction.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    PlayerCardsAuctionController controller = fxmlLoader.getController();
                    controller.setCard(tmp[i], tmp[i + 1], myListener);

                    gridPanePlayerCards.add(anchorPane, column++, 0);
                    GridPane.setMargin(anchorPane, new Insets(2, 5, 0, 5));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setSelectedCardName(String object) {
        this.selectedCardName = object;
    }

    private void setVisibleAuctionFields(boolean value) {
        errorLabel.setVisible(value);
        basePriceTextField.setVisible(value);
        createButton.setVisible(value);
    }

    public void newAuction(ActionEvent event) {
        setVisibleAuctionFields(true);
    }

    public void createNewAuction(ActionEvent event) {
        if (selectedCardName == null) {
            errorLabel.setText("Please first select a card!");
        } else if (basePriceTextField.getText().equals("")) {
            errorLabel.setText("Please set base price for your auction!");
        } else if (!basePriceTextField.getText().matches("[0-9]+")) {
            errorLabel.setText("Base price must be an integer");
        } else {
            try {
                errorLabel.setText("");
                Controller.getDataOutputStream().writeUTF("Auction create new auction"
                        + Controller.getToken() + "#" + selectedCardName + "#"
                        + basePriceTextField.getText());
                Controller.getDataOutputStream().flush();
                basePriceTextField.setText("");
                setVisibleAuctionFields(false);
                loadOnGoingAuctions();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
