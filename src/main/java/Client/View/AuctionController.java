package Client.View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AuctionController implements Initializable {

    @FXML
    private GridPane gridPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadOnGoingAuctions();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        loadPlayerCards();
    }

    private void loadOnGoingAuctions() throws IOException {
        Controller.getDataOutputStream().writeUTF("Auction send data");
        Controller.getDataOutputStream().flush();
        String[] auctionData = Controller.getDataInputStream().readUTF().split("#");
        int column = 0;
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

//        private void loadPlayerCards () {
//        }
    }
}
