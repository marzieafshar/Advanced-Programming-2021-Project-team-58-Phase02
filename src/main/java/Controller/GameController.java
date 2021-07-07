package Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
//import sun.jvm.hotspot.runtime.Threads;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML
    private ImageView imageView;
    @FXML
    private AnchorPane anchorPane;

    private CoinAnimation1 animation1;
    private CoinAnimation1 animation2;
    private CoinAnimation1 animation3;
    private CoinAnimation1 animation4;

    Stage stage;
    int index = 0;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        animation1 = new CoinAnimation1(imageView);
        animation2 = new CoinAnimation1(imageView);
        animation3 = new CoinAnimation1(imageView);
        animation4 = new CoinAnimation1(imageView);
        animation1.play();
        animation1.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                animation2.play();
            }
        });
        animation2.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                animation3.play();
            }
        });
        animation3.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                animation4.play();
            }
        });
        animation4.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                imageView.setImage(null);
                heartOrStar();
            }
        });
    }

    private void loadNextScene() {
        AnchorPane pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource("/Fxmls/DeckMenu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        anchorPane.getChildren().setAll(pane);
    }

    public void heartOrStar() {
        Random rand = new Random();
        int a = rand.nextInt(2);
        if(a == 1) {
            String str = "/Images/Coin/0.png";
            Image image = new Image(getClass().getResourceAsStream(str));
            imageView.setImage(image);
        }
        else {
            String str = "/Images/Coin/Silver_21.png";
            Image image = new Image(getClass().getResourceAsStream(str));
            imageView.setImage(image);
        }
    }
}