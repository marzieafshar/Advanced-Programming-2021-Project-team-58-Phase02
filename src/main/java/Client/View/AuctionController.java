package Client.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

public class AuctionController implements Initializable {

    private Parent root;
    private Stage stage;
    private Scene scene;

    private ArrayList<String> playerAllCards = new ArrayList<>();

    @FXML
    private GridPane gridPane;

    @FXML
    private GridPane gridPanePlayerCards;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadOnGoingAuctions();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadPlayerCards();
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
            for (int i = 0; i < tmp.length; i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Fxmls/playerCardsAuction.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                PlayerCardsAuctionController controller = fxmlLoader.getController();
                controller.setCard(tmp[i]);

                gridPanePlayerCards.add(anchorPane , column++ , 0);
                GridPane.setMargin(anchorPane , new Insets(2,5,0,5));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
