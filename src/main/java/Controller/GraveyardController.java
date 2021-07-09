package Controller;

import Model.Card;
import Model.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GraveyardController {
    @FXML
    private GridPane graveYardGridPane;

    public void addCardsToGraveYardPane(Player player) {
//        graveYardGridPane.getChildren().clear();
        MyListener myListener = new MyListener() {
            @Override
            public void onClickListener(Object object) {
//                setSelectedCardHand((Card) object);;
            }
        };
        int column = 0;
        int row = 1;
        for (int i = 0; i < player.getBoard().getGraveYard().size(); i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Fxmls/Items.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController ItemController = fxmlLoader.getController();
                ItemController.setItem(player.getBoard().getGraveYard().get(i), myListener);
                if (column == 5) {
                    column = 0;
                    row++;
                }
                graveYardGridPane.add(anchorPane, column++, row);

                GridPane.setMargin(anchorPane, new Insets(5));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
