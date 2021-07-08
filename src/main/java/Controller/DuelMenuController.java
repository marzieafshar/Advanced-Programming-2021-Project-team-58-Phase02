package Controller;

import Model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.ResourceBundle;

public class DuelMenuController implements Initializable {
    private Parent root;
    private Stage stage;
    private Scene scene;


    Player player1 = Controller.getLoggedInPlayer();
    Player player2 = SetOpponentController.getPlayer2();

    private static int player1SetsWin;
    private static int player2SetsWin;

    private static ArrayList<Integer> LPsPlayer1 = new ArrayList<>();
    private static ArrayList<Integer> LPsPlayer2 = new ArrayList<>();

    int numOfRounds = SetOpponentController.getNumOfRounds();
    int numOfCurrentRound = 0;

    @FXML
    private Button nextRoundButton;

    @FXML
    private Button editDeckButton;

    @FXML
    private Label numberOfRounds;

    @FXML
    private Label roundsPlayed;

    @FXML
    private ImageView player1Avatar;

    @FXML
    private Label player1NickName;

    @FXML
    private Label player2NickName;

    @FXML
    private ImageView player2Avatar;

    @FXML
    private Label player1Score;

    @FXML
    private Label player2Score;

    @FXML
    private Label player1MaxLP;

    @FXML
    private Label player2MaxLP;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleRound();
        setPlayersInfo();
    }

    public void startANewRound(ActionEvent event) {
        try {
            if(numOfCurrentRound == 0){
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/FlipCoin.fxml")));
            }
            else {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/DuelField.fxml")));
            }
            numOfCurrentRound++;
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void goToDeckMenu(ActionEvent event) {
        try {
            DeckMenuController.setIsGameStarted(true);
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/DeckMenu.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void handleRound() {
        if (numOfCurrentRound == 0) {
            nextRoundButton.setText("start game");
//            editDeckButton.setDisable();
            player1SetsWin = 0;
            player2SetsWin = 0;
            LPsPlayer1.clear();
            LPsPlayer2.clear();
        } else {
            editDeckButton.setDisable(true);
            nextRoundButton.setText("next round");
        }
    }

    public void setPlayersInfo() {
        int maxLP1 = 0;
        int maxLP2 = 0;
        if (LPsPlayer1.size() > 0) {
            maxLP1 = Collections.max(LPsPlayer1);
            maxLP2 = Collections.max(LPsPlayer2);
        }

        player1NickName.setText(player1.getNickname());
        player2NickName.setText(player2.getNickname());
        player1Avatar.setImage(player1.getImage());
        player2Avatar.setImage(player2.getImage());
        player1Score.setText(String.valueOf(player1SetsWin));
        player2Score.setText(String.valueOf(player2SetsWin));
        player1MaxLP.setText(String.valueOf(maxLP1));
        player2MaxLP.setText(String.valueOf(maxLP2));

        numberOfRounds.setText(String.valueOf(numOfRounds));
        roundsPlayed.setText(String.valueOf(numOfCurrentRound));
    }
}
