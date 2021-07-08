package Controller;

import Model.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
//import sun.jvm.hotspot.runtime.Threads;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class FlipCoinController implements Initializable {
    public BorderPane borderPane;
    public Label winnerLabel;
    @FXML
    private ImageView imageView;

    private static Player winner;//check it later! (static)
    private static Player loser;

    private CoinAnimation animation1;
    private CoinAnimation animation2;
    private CoinAnimation animation3;
    private CoinAnimation animation4;

    public static Player getWinner() {
        return winner;
    }

    public static Player getLoser() {
        return loser;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        winnerLabel.setText("");
        animation1 = new CoinAnimation(imageView);
        animation2 = new CoinAnimation(imageView);
        animation3 = new CoinAnimation(imageView);
        animation4 = new CoinAnimation(imageView);
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
                loadNextScene();
            }
        });

    }

    private void loadNextScene() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Fxmls/DuelField.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) imageView.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void heartOrStar() {
        Random rand = new Random();
        int a = rand.nextInt(2);
        if(a == 1) {
            winner = Controller.getLoggedInPlayer();
            loser = SetOpponentController.getPlayer2();
            String str = "/Images/Coin/0.png";
            Image image = new Image(getClass().getResourceAsStream(str));
            imageView.setImage(image);
        }
        else {
            winner = SetOpponentController.getPlayer2();
            loser = Controller.getLoggedInPlayer();
            String str = "/Images/Coin/Gold_21.png";
            Image image = new Image(getClass().getResourceAsStream(str));
            imageView.setImage(image);
        }
        winnerLabel.setText(winner.getNickname() + " starts the game");

    }
}