//package Client.View;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.image.ImageView;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Objects;
//import java.util.ResourceBundle;
//
//public class DuelMenuController implements Initializable {
//    private Parent root;
//    private Stage stage;
//    private Scene scene;
//
//
//    Player player1 = Controller.getLoggedInPlayer();
////    Player player2 = SetOpponentController.getPlayer2();
//
//    private static int player1SetsWin;
//    private static int player2SetsWin;
//
//    private static ArrayList<Integer> LPsPlayer1 = new ArrayList<>();
//    private static ArrayList<Integer> LPsPlayer2 = new ArrayList<>();
//
//    int numOfRounds = SetOpponentController.getNumOfRounds();
//    private static int numOfCurrentRound = 0;
//
//    private static Player winnerOfRound;
//    private static Player loserOfRound;
//
//    private static Player winnerOfMatch;
//    private static Player loserOfMatch;
//
//    @FXML
//    private Text matchResult;
//
//    @FXML
//    private Button nextRoundButton;
//
//    @FXML
//    private Button editDeckButton;
//
//    @FXML
//    private Label numberOfRounds;
//
//    @FXML
//    private Label roundsPlayed;
//
//    @FXML
//    private ImageView player1Avatar;
//
//    @FXML
//    private Label player1NickName;
//
//    @FXML
//    private Label player2NickName;
//
//    @FXML
//    private ImageView player2Avatar;
//
//    @FXML
//    private Label player1Score;
//
//    @FXML
//    private Label player2Score;
//
//    @FXML
//    private Label player1MaxLP;
//
//    @FXML
//    private Label player2MaxLP;
//
//    String str = "Button_Click.mp3";
//    Media media = new Media(new File(str).toURI().toString());
//    private MediaPlayer mediaPlayer = new MediaPlayer(media);
//
//    private static boolean isFromGame = false;
//
//    public static void setFromGame(boolean fromGame) {
//        isFromGame = fromGame;
//    }
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        if(isFromGame || numOfCurrentRound == 0)
//            handleRound();
//        setPlayersInfo();
//        if (isAnyOneWonTheMatch()) {
//            endOfGameSettings();
//        }
//    }
//
//    private boolean isAnyOneWonTheMatch() {
//        return ((numOfRounds == 3) && ((player1SetsWin == 2) || (player2SetsWin == 2))) ||
//                ((numOfRounds == 1) && ((player1SetsWin == 1) || (player2SetsWin == 1)));
//    }
//
//    public static void setMatchWinner(Player matchWinner) {
//        winnerOfMatch = matchWinner;
//    }
//
//    public static void setMatchLoser(Player matchLoser) {
//        loserOfMatch = matchLoser;
//    }
//
//    public static void setLoserOfRound(Player loserOfRound) {
//        DuelMenuController.loserOfRound = loserOfRound;
//    }
//
//    public static void setWinnerOfRound(Player winnerOfRound) {
//        DuelMenuController.winnerOfRound = winnerOfRound;
//    }
//
//    public void startANewRound(ActionEvent event) {
//        Media media = new Media(new File(str).toURI().toString());
//        mediaPlayer = new MediaPlayer(media);
//        mediaPlayer.setAutoPlay(true);
//        try {
//            if (numOfCurrentRound == 0) {
//                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/FlipCoin.fxml")));
//            } else {
//                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/DuelField.fxml")));
//            }
//            numOfCurrentRound++;
//            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            scene = new Scene(root);
//            stage.setScene(scene);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void goToDeckMenu(ActionEvent event) {
//        Media media = new Media(new File(str).toURI().toString());
//        mediaPlayer = new MediaPlayer(media);
//        mediaPlayer.setAutoPlay(true);
//        try {
//            DeckMenuController.setIsGameStarted(true);
//            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/DeckMenu.fxml")));
//            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            scene = new Scene(root);
//            stage.setScene(scene);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void handleRound() {
//        if (numOfCurrentRound == 0) {
//            nextRoundButton.setText("start game");
//            editDeckButton.setDisable(true);
//            player1SetsWin = 0;
//            player2SetsWin = 0;
//            LPsPlayer1.clear();
//            LPsPlayer2.clear();
//        } else {
//            if (player1.equals(winnerOfRound))
//                player1SetsWin++;
//            else
//                player2SetsWin++;
//            LPsPlayer1.add(player1.getLP());
////            LPsPlayer2.add(player2.getLP());
//            int maxLP1 = Collections.max(LPsPlayer1);
//            int maxLP2 = Collections.max(LPsPlayer2);
//
//            player1Score.setText(String.valueOf(player1SetsWin));
//            player2Score.setText(String.valueOf(player2SetsWin));
//            player2MaxLP.setText(String.valueOf(maxLP1));
//            player1MaxLP.setText(String.valueOf(maxLP2));
//            editDeckButton.setDisable(false);
//            nextRoundButton.setText("next round");
//        }
//
//    }
//
//    public void setPlayersInfo() {
//        int maxLP1 = 0;
//        int maxLP2 = 0;
//        if (LPsPlayer1.size() > 0) {
//            maxLP1 = Collections.max(LPsPlayer1);
//            maxLP2 = Collections.max(LPsPlayer2);
//        }
//
//        player1NickName.setText(player1.getNickname());
////        player2NickName.setText(player2.getNickname());
//        player1Avatar.setImage(player1.getImage());
////        player2Avatar.setImage(player2.getImage());
//        player1Score.setText(String.valueOf(player1SetsWin));
//        player2Score.setText(String.valueOf(player2SetsWin));
//        player1MaxLP.setText(String.valueOf(maxLP1));
//        player2MaxLP.setText(String.valueOf(maxLP2));
//
//        numberOfRounds.setText(String.valueOf(numOfRounds));
//        roundsPlayed.setText(String.valueOf(numOfCurrentRound));
//    }
//
//    public void test (ActionEvent event){
//    }
//
//    public void endOfGameSettings() {
//        nextRoundButton.setVisible(false);
//        editDeckButton.setVisible(false);
//        Integer maxLpWinner;
//        if (numOfRounds == 3) {
//            if (player1SetsWin == 2) {
//                setMatchWinner(player1);
////                setMatchLoser(player2);
//                maxLpWinner = Collections.max(LPsPlayer1);
//
//            } else {
////                setMatchWinner(player2);
//                setMatchLoser(player1);
//                maxLpWinner = Collections.max(LPsPlayer2);
//            }
//        } else {
//            setMatchWinner(winnerOfRound);
//            setMatchLoser(loserOfRound);
//            if (winnerOfMatch.equals(player1)) {
//                maxLpWinner = Collections.max(LPsPlayer1);
//            } else {
//                maxLpWinner = Collections.max(LPsPlayer2);
//            }
//        }
//        winnerOfMatch.increaseScore(numOfRounds * 1000);
//        winnerOfMatch.increaseMoney(numOfRounds * (1000 + maxLpWinner));
//        loserOfMatch.increaseMoney(100 * numOfRounds);
//        matchResult.setText(winnerOfMatch.getNickname() + " won the whole match with score: "
//                + player1SetsWin + " - " + player2SetsWin);
//    }
//
//    public void backToMainMenu(ActionEvent event) throws IOException {
//        numOfCurrentRound = 0;
//        setFromGame(false);
//        JsonSaveAndLoad.save();
//        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/MainMenu.fxml")));
//        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//    }
//}