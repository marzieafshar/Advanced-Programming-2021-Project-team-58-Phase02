package Client.View;

import Server.Model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Game implements Initializable {


    public Label oppositionLP;
    public Label turnOfPlayerLP;
    public ImageView selectedCardImage;
    public ImageView turnOfPlayerAvatar;
    public ImageView oppositionAvatar;
    public ProgressBar turnOfPlayerProgressBar;
    public ProgressBar oppositionProgressBar;
    public GridPane gridHandCardsTurnOfPlayer;
    public GridPane gridHandCardsOpponent;
    public GridPane gridPanePhaseIcons;
    public Text infoTextArea;
    public Text errorText;
    private Card selectedCardGraphic;

    @FXML
    private ImageView imageViewMonster15;

    @FXML
    private ImageView imageViewMonster13;

    @FXML
    private ImageView imageViewMonster11;

    @FXML
    private ImageView imageViewMonster12;

    @FXML
    private ImageView imageViewMonster14;

    @FXML
    private ImageView imageViewMonster24;

    @FXML
    private ImageView imageViewMonster22;

    @FXML
    private ImageView imageViewMonster21;

    @FXML
    private ImageView imageViewMonster23;

    @FXML
    private ImageView imageViewMonster25;

    @FXML
    private ImageView imageViewTrap15;

    @FXML
    private ImageView imageViewTrap13;

    @FXML
    private ImageView imageViewTrap11;

    @FXML
    private ImageView imageViewTrap12;

    @FXML
    private ImageView imageViewTrap14;

    @FXML
    private ImageView imageViewTrap25;

    @FXML
    private ImageView imageViewTrap23;

    @FXML
    private ImageView imageViewTrap21;

    @FXML
    private ImageView imageViewTrap22;

    @FXML
    private ImageView imageViewTrap24;

    private static int nextPhaseCheck;

    private Player player1;
    private Player player2;

    private Phase phase = Phase.DRAW;
    private Player turnOfPlayer;
    public boolean isSurrendered = false;
    private Position selectedPosition = null;
    private Card selectedCardHand;
    private boolean isAnyCardSummoned;
    private int cheatCounter;

    private Position oppositionCardPosition;
    private Position tributeCardPosition;

    List<Position> attackedCards = new ArrayList<Position>();
    List<Position> activatedSpells = new ArrayList<>();

    String str = "LP.wav";
    String str2 = "attack.wav";
    String str3 = "summon.mp3";
    private MediaPlayer mediaPlayer;
    private int numberOfCardsTributed = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPlayer1(Controller.getLoggedInPlayer());
        setPlayer2(SetOpponentController.getPlayer2());
        startOfGameSettings();
    }

    public void startOfGameSettings() {
        player1.setBoard(new Board());
        player2.setBoard(new Board());
        setTurnOfPlayer(FlipCoinController.getWinner());
        setPlayersLp();
        setPlayersAvatar();
        setPlayersProgressBar();
        clearHand();
        setPositionsListener(player1);
        setPositionsListener(player2);

        setPositionsOfTurnOfPlayer();
        setPositionsOfOpposition();
        clearBoardGame();
        setPlayersDeckOnBoard();

        drawAtFirstTurn(player1);
        drawAtFirstTurn(player2);

        setCheatCounter();

        setIcons();
        setPhase(Phase.END);
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public void setTurnOfPlayer(Player turnOfPlayer) {
        this.turnOfPlayer = turnOfPlayer;
    }

    public void setPlayersLp() {
        turnOfPlayer.setLP(4000);
        getOpposition().setLP(4000);
        showPlayersLP();
    }

    private void setPlayersAvatar() {
        turnOfPlayerAvatar.setImage(turnOfPlayer.getImage());
        oppositionAvatar.setImage(getOpposition().getImage());
    }

    public void clearHand() {
        player1.getHand().clear();
        player2.getHand().clear();
    }

    public void clearBoardGame() {
        player1.getBoard().clearBoard();
        player2.getBoard().clearBoard();
    }

    public void drawAtFirstTurn(Player player) {
        for (int i = 0; i < 6; i++) {
            int mainDeckSize = player.getBoard().getMainDeck().size();
            if (mainDeckSize == 0) {
                return;
            }
            Random rand = new Random();
            int index = rand.nextInt(mainDeckSize);
            player.addCardToHand(player.getBoard().getMainDeck().get(index));
            player.getBoard().getMainDeck().remove(index);
        }
        if (player.equals(turnOfPlayer))
            showTurnOfPlayerHandCards();
        else
            showOppositionHandCards();
    }

    public void drawPhase(ActionEvent event) {
        if (phase.equals(Phase.END)) {
            nextPhaseCheck = 0;
            setPhase(Phase.DRAW);
            showPhaseAlert();
            draw();
            if (turnOfPlayer.getBoard().getMainDeck().size() == 0) {
                showEndOfGameAlert("deck");
                DuelMenuController.setLoserOfRound(turnOfPlayer);
                DuelMenuController.setWinnerOfRound(getOpposition());
                goToDuelMenu();
            }
        } else if (phase.equals(Phase.DRAW)) {
            errorText.setText("You are in Draw Phase now");
        } else {
            phaseNavigationAlert(Phase.DRAW);
        }
    }

    public void mainPhase() {
        if (phase.equals(Phase.DRAW) || (phase.equals(Phase.BATTLE))) {
            if (phase.equals(Phase.DRAW))
                nextPhaseCheck = 1;
            else
                nextPhaseCheck = 0;
            setPhase(Phase.MAIN);
            showPhaseAlert();
        } else if (phase.equals(Phase.MAIN)) {
            errorText.setText("You are in Client.Client.View.View.Main Phase now!");
        } else {
            phaseNavigationAlert(Phase.MAIN);
        }
    }

    public void battlePhase(ActionEvent event) {
        if (phase.equals(Phase.MAIN) && nextPhaseCheck == 1) {
            nextPhaseCheck = 0;
            setPhase(Phase.BATTLE);
            showPhaseAlert();
        } else {
            phaseNavigationAlert(Phase.BATTLE);
        }
    }

    public void endPhase(ActionEvent event) {
        setPhase(Phase.END);
        setAllPositionsChangeStatus();
        changeTurnOfPlayer();
        showWhichPlayersTurn();
        isAnyCardSummoned = false;
        clearAttackedCardsArrayList();
    }

    public void draw() {
        int mainDeckSize = turnOfPlayer.getBoard().getMainDeck().size();
        Random rand = new Random();
        int index = rand.nextInt(mainDeckSize);
        turnOfPlayer.addCardToHand(turnOfPlayer.getBoard().getMainDeck().get(index));
        turnOfPlayer.getBoard().getMainDeck().remove(index);
        showTurnOfPlayerHandCards();
    }


    public void setCheatCounter() {
        this.cheatCounter = 0;
    }

    public void increaseCheatCounter() {
        this.cheatCounter++;
    }

    public void setSelectedPosition(Position selectedPosition) {
        selectedCardHandNulling();
        this.selectedPosition = selectedPosition;
        if (getOpposition().getBoard().getMonsterCards().contains(selectedPosition) &&
                (selectedPosition.getStatus().equals(StatusOfPosition.DEFENSIVE_HIDDEN)))
            setSelectedCardImage(null);
        else if (selectedPosition.getStatus().equals(StatusOfPosition.SPELL_OR_TRAP_HIDDEN) &&
                getOpposition().getBoard().getTrapAndSpellCards().contains(selectedPosition))
            setSelectedCardImage(null);
        else
            setSelectedCardImage(selectedPosition.getCard());
    }

    public void setSelectedCardHand(Card selectedCardHand) {
        selectedPositionNulling();
        this.selectedCardHand = selectedCardHand;
        setSelectedCardImage(selectedCardHand);
    }

    public Card getSelectedCardHand() {
        return selectedCardHand;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    private void deSelect() {
        selectedPositionNulling();
        selectedCardHandNulling();
        infoTextArea.setText("No card is selected!");
    }

    public void set() {
        if (selectedCardHand instanceof TrapAndSpellCard) {
            setTrapSpellOnBoard();
        } else {
            if (isAnyCardSummoned) {
                isAnyCardSummoned = setMonsterCardOnBoard();
                isAnyCardSummoned = true;
            } else {
                isAnyCardSummoned = setMonsterCardOnBoard();
            }
        }
    }

    private void setAllPositionsChangeStatus() {
        for (int i = 0; i < 5; i++) {
            turnOfPlayer.getBoard().getMonsterCards().get(i).setStatusChanged(false);
        }
    }

    public void changeTurnOfPlayer() {
        if (turnOfPlayer.equals(player1))
            turnOfPlayer = player2;
        else {
            turnOfPlayer = player1;
        }
        setPlayersAvatar();
        showPlayersLP();
        showTurnOfPlayerHandCards();
        showOppositionHandCards();
        setPositionsOfTurnOfPlayer();
        setPositionsOfOpposition();
    }

    public void setPlayersDeckOnBoard() {
        player1.getBoard().setMainDeck((ArrayList<Card>) (player1.getActiveDeck().getMainDeck().clone()));
        player2.getBoard().setMainDeck((ArrayList<Card>) (player2.getActiveDeck().getMainDeck().clone()));
    }

    public void surrender(MouseEvent event) {
        isSurrendered = true;
        DuelMenuController.setLoserOfRound(turnOfPlayer);
        DuelMenuController.setWinnerOfRound(getOpposition());
        goToDuelMenu();
    }

    private String convertStatusToChar(StatusOfPosition status) {
        if (status.equals(StatusOfPosition.EMPTY)) {
            return "E";
        } else if (status.equals(StatusOfPosition.OFFENSIVE_OCCUPIED)) {
            return "OO";
        } else if (status.equals(StatusOfPosition.DEFENSIVE_OCCUPIED)) {
            return "DO";
        } else if (status.equals(StatusOfPosition.DEFENSIVE_HIDDEN)) {
            return "DH";
        } else if (status.equals(StatusOfPosition.SPELL_OR_TRAP_HIDDEN)) {
            return "H";
        } else if (status.equals(StatusOfPosition.SPELL_OR_TRAP_OCCUPIED)) {
            return "O";
        } else {
            return "E";
        }
    }

    public void selectedPositionNulling() {
        selectedPosition = null;
        setSelectedCardImage(null);
    }

    public void selectedCardHandNulling() {
        selectedCardHand = null;
        setSelectedCardImage(null);
    }

    public void oppositionCardPositionNulling() {
        oppositionCardPosition = null;
    }

    public void tributeCardPositionNulling(){
        tributeCardPosition = null;
    }

    private int firstEmptyIndex(ArrayList<Position> array) {
        int n = 0;
        int i = 1;
        while (n == 0) {
            if (array.get(convertIndex(i)).getStatus().equals(StatusOfPosition.EMPTY)) {
                n = 1;
            } else {
                i++;
            }
        }
        return convertIndex(i);
    }

    public void summon() {
        errorText.setText("summon selected");
        if (isAnyCardSummoned) {
            isAnyCardSummoned = summonMonsterOnBoard();
            isAnyCardSummoned = true;
        } else {
            isAnyCardSummoned = summonMonsterOnBoard();
        }
    }

    public boolean summonMonsterOnBoard() {
        numberOfCardsTributed = 0;
        if ((selectedCardHand == null) && (selectedPosition == null)) {
            errorText.setText("no card is selected yet");
            return false;
        } else if (selectedCardHand == null || !(selectedCardHand instanceof MonsterCard)) {
            errorText.setText("you can’t summon this card");
            return false;
        } else if (!this.phase.equals(Phase.MAIN)) {
            errorText.setText("action not allowed in this phase");
            return false;
        } else if (turnOfPlayer.getBoard().isMonsterZoneFull()) {
            errorText.setText("monster card zone is full");
            return false;
        } else if (this.isAnyCardSummoned) {
            errorText.setText("you already summoned/set on this turn");
            return true;
        } else {
            if (((MonsterCard) selectedCardHand).getCardLevel() < 5)
                return lastStepForSummon();
            else {
                if (((MonsterCard) selectedCardHand).getCardLevel() < 7) {
                    if (turnOfPlayer.getBoard().cardsInMonsterZone() == 0) {
                        errorText.setText("there are not enough cards for tribute");
                        return false;
                    } else {
                        tributeStandby();
                        return true;
                    }
                } else {
                    if (turnOfPlayer.getBoard().cardsInMonsterZone() < 2) {
                        errorText.setText("there are not enough cards for tribute");
                        return false;
                    } else {
                        tributeStandby();
                        return true;
                    }
                }
            }
        }
    }

    private boolean lastStepForSummon() {
        Media media = new Media(new File(str3).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        int i = firstEmptyIndex(turnOfPlayer.getBoard().getMonsterCards());
        turnOfPlayer.getBoard().getMonsterCards().get(i).setStatus(StatusOfPosition.OFFENSIVE_OCCUPIED);
        turnOfPlayer.getBoard().getMonsterCards().get(i).setCard(selectedCardHand);
        turnOfPlayer.getHand().remove(selectedCardHand);
        showTurnOfPlayerHandCards();
        errorText.setText("summoned successfully");
        selectedCardHandNulling();
        selectedPositionNulling();
        return true;
    }

    private void tribute() {
        sendToGraveyard(tributeCardPosition, turnOfPlayer);
        tributeCardPositionNulling();
        numberOfCardsTributed ++;
    }

    public boolean setMonsterCardOnBoard() {
        if ((selectedCardHand == null) && (selectedPosition == null)) {
            errorText.setText("no card is selected yet");
            return false;
        } else if (selectedCardHand == null) {
            errorText.setText("you can’t set this card");
            return false;
        } else if ((selectedCardHand instanceof MonsterCard) && !(this.phase.equals(Phase.MAIN))) {
            errorText.setText("you can’t do this action in this phase");
            return false;
        } else if (turnOfPlayer.getBoard().isMonsterZoneFull()) {
            errorText.setText("monster card zone is full");
            return false;
        } else if (this.isAnyCardSummoned) {
            errorText.setText("you already summoned/set on this turn");
            return true;
        } else {
            int i = firstEmptyIndex(turnOfPlayer.getBoard().getMonsterCards());
            turnOfPlayer.getBoard().getMonsterCards().get(i).setStatus(StatusOfPosition.DEFENSIVE_HIDDEN);
            turnOfPlayer.getBoard().getMonsterCards().get(i).setStatusChanged(true);
            turnOfPlayer.getBoard().getMonsterCards().get(i).setCard(selectedCardHand);
            turnOfPlayer.getHand().remove(selectedCardHand);
            selectedCardHandNulling();
            errorText.setText("set successfully");
            showTurnOfPlayerHandCards();
            return true;
        }
    }

    public void changeMonsterStatus(String newStatus) {
        if (selectedPosition == null) {
            errorText.setText("no card is selected on the board yet");
        } else if (!(selectedPosition.getCard() instanceof MonsterCard)) {
            errorText.setText("you can’t change this card position");
        } else if (!this.phase.equals(Phase.MAIN)) {
            errorText.setText("you can’t do this action in this phase");
        } else if (selectedPosition.getStatus().equals(StatusOfPosition.DEFENSIVE_HIDDEN)) {
            errorText.setText("you can't change the position of cards that are in DH. Use flip-summon");
        } else if (((selectedPosition.getStatus().equals(StatusOfPosition.OFFENSIVE_OCCUPIED)) && (newStatus.equals("attack")))
                || ((selectedPosition.getStatus().equals(StatusOfPosition.DEFENSIVE_OCCUPIED)) && (newStatus.equals("defense")))) {
            errorText.setText("this card is already in the wanted position");
        } else if (selectedPosition.getIsStatusChanged()) {
            errorText.setText("you already changed this card position in this turn");
        } else {
            selectedPosition.changeStatus();

            errorText.setText("monster card position changed successfully");
            selectedPosition.setStatusChanged(true);
            selectedPositionNulling();
            selectedCardHandNulling();
        }
    }

    public static int convertIndex(int index) {
        if (index == 1) {
            return 2;
        } else if (index == 2) {
            return 3;
        } else if (index == 3) {
            return 1;
        } else if (index == 4) {
            return 4;
        } else {
            return 0;
        }
    }

    public Player getOpposition() {
        if (turnOfPlayer.equals(player1)) {
            return player2;
        }
        return player1;
    }
//    ----------------------------------------------------------------------------------------------------
//    ----------------------------------------BATTLE PHASE------------------------------------------------

    public boolean isConditionsUnsuitableForAttack() {
        if ((selectedPosition == null) && (selectedCardHand == null)) {
            errorText.setText("no card is selected yet");
            return true;
        }
        if (selectedPosition == null) {
            errorText.setText("selected card should not be in your hand");
            return true;
        }
        if (!(selectedPosition.getCard() instanceof MonsterCard)) {
            errorText.setText("you can’t attack with this card");
            return true;
        }

        if (!phase.equals(Phase.BATTLE)) {
            errorText.setText("you can’t do this action in this phase");
            return true;
        }
        if (hasCardAttackedInThisPhase(selectedPosition)) {
            errorText.setText("this card already attacked");
            return true;
        }
        if (!selectedPosition.getStatus().equals(StatusOfPosition.OFFENSIVE_OCCUPIED)) {
            errorText.setText("selected card is in defensive position!");
            return true;
        }
        return false;
    }

    public boolean hasCardAttackedInThisPhase(Position position) {
        return attackedCards.contains(position);
    }

    public void clearAttackedCardsArrayList() {
        attackedCards.clear();
    }

    public void battleStandby() {
        setPhase(Phase.BATTLE_STANDBY);
        if (oppositionCardPosition != null) {
            setPhase(Phase.BATTLE);
            attackToMonster();
        }

    }

    public void tributeStandby() {
        setPhase(Phase.TRIBUTE);
        errorText.setText("Please choose a monsters for tribute");
        if (tributeCardPosition != null) {
            tribute();
            if((((MonsterCard) selectedCardHand).getCardLevel() > 7) && (numberOfCardsTributed == 1)){
                tributeStandby();
            }
            else {
                boolean tmp = lastStepForSummon();
                setPhase(Phase.MAIN);
            }
        }
    }

    public void setTributePosition(Position position) {
        if (turnOfPlayer.getBoard().getMonsterCards().contains(position)) {
            this.tributeCardPosition = position;
            tributeStandby();
        } else {
            errorText.setText("you can't tribute this card");
        }
    }

    public void setOppositionCardPosition(Position position) {
        if (getOpposition().getBoard().getMonsterCards().contains(position)) {
            this.oppositionCardPosition = position;
            battleStandby();
        } else {
            errorText.setText("You can't attack this card!");
        }
    }

    public void attackToMonster() {
        Media media2 = new Media(new File(str2).toURI().toString());
        mediaPlayer = new MediaPlayer(media2);
        mediaPlayer.setAutoPlay(true);
        StatusOfPosition statusOfOpposition = oppositionCardPosition.getStatus();
        int selectedCardAttack = ((MonsterCard) selectedPosition.getCard()).getAttack();
        int oppositionCardAttack = ((MonsterCard) oppositionCardPosition.getCard()).getAttack();
        int oppositionCardDefense = ((MonsterCard) oppositionCardPosition.getCard()).getDefense();

        if (statusOfOpposition.equals(StatusOfPosition.OFFENSIVE_OCCUPIED)) {
            if (selectedCardAttack > oppositionCardAttack) {
                int damage = selectedCardAttack - oppositionCardAttack;
                sendToGraveyard(oppositionCardPosition, getOpposition());
                getOpposition().decreaseLP(damage);
                errorText.setText("your opponent’s monster is destroyed and your opponent receives "
                        + damage + " battle damage");

            } else if (selectedCardAttack < oppositionCardAttack) {
                int damage = oppositionCardAttack - selectedCardAttack;
                sendToGraveyard(selectedPosition, turnOfPlayer);
                turnOfPlayer.decreaseLP(damage);
                errorText.setText("Your monster card is destroyed and you received " + damage +
                        " battle damage");

            } else {
                sendToGraveyard(oppositionCardPosition, getOpposition());
                sendToGraveyard(selectedPosition, turnOfPlayer);
                errorText.setText("both you and your opponent monster cards "
                        + "are destroyed and no one receives damage");
            }
        } else {
            String cardName = oppositionCardPosition.getCard().getCardName();
            if (selectedCardAttack > oppositionCardDefense) {
                sendToGraveyard(oppositionCardPosition, getOpposition());
                if (statusOfOpposition.equals(StatusOfPosition.DEFENSIVE_OCCUPIED))
                    errorText.setText("the defense position monster is destroyed");
                else {
                    errorText.setText("opponent’s monster card was " + cardName +
                            " and the defense position monster is destroyed");
                }
            } else if (selectedCardAttack < oppositionCardDefense) {
                int damage = oppositionCardDefense - selectedCardAttack;
                turnOfPlayer.decreaseLP(damage);
                if (statusOfOpposition.equals(StatusOfPosition.DEFENSIVE_OCCUPIED))
                    errorText.setText("no card is destroyed and you received " + damage + " battle damage");
                else
                    errorText.setText("opponent’s monster card was " + cardName +
                            " no card is destroyed and you received " + damage + " battle damage");
            } else {
                if (statusOfOpposition.equals(StatusOfPosition.DEFENSIVE_OCCUPIED))
                    errorText.setText("no card is destroyed");
                else
                    errorText.setText("opponent’s monster card was " + cardName + " no card is destroyed");
            }
        }
        Media media = new Media(new File(str).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        showPlayersLP();
        attackedCards.add(selectedPosition);
        oppositionCardPositionNulling();
        selectedCardHandNulling();
        selectedPositionNulling();
        if (getOpposition().getLP() == 0) {
            showEndOfGameAlert("attack");
            DuelMenuController.setLoserOfRound(getOpposition());
            DuelMenuController.setWinnerOfRound(turnOfPlayer);
            goToDuelMenu();
        }
        if (getOpposition().getLP() == 0) {
            showEndOfGameAlert("attack");
            DuelMenuController.setLoserOfRound(turnOfPlayer);
            DuelMenuController.setWinnerOfRound(getOpposition());
            goToDuelMenu();
        }
    }

    public boolean isOpponentMonsterZoneEmpty() {
        for (int i = 0; i < 5; i++) {
            if (!getOpposition().getBoard().getMonsterCards().get(i).getStatus().equals(StatusOfPosition.EMPTY))
                return false;
        }
        return true;
    }

    public void directAttack() {

        Media media2 = new Media(new File(str2).toURI().toString());
        mediaPlayer = new MediaPlayer(media2);
        mediaPlayer.setAutoPlay(true);

        if (isConditionsUnsuitableForAttack())
            return;
        if (!isOpponentMonsterZoneEmpty())
            errorText.setText("you can’t attack the opponent directly");
        else {
            Media media = new Media(new File(str).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
            int damage = ((MonsterCard) selectedPosition.getCard()).getAttack();
            getOpposition().decreaseLP(damage);
            errorText.setText("you opponent receives " + damage + " battle damage");
            attackedCards.add(selectedPosition);
            showPlayersLP();
            selectedPositionNulling();
            selectedCardHandNulling();
        }
        if (getOpposition().getLP() == 0) {
            showEndOfGameAlert("attack");
            DuelMenuController.setLoserOfRound(getOpposition());
            DuelMenuController.setWinnerOfRound(turnOfPlayer);
            goToDuelMenu();
        }
    }

//    --------------------------------------------------------------------------------------------------------
//    --------------------------------------------------------------------------------------------------------

    public void sendToGraveyard(Position position, Player player) {
        if (!(position.getStatus().equals(StatusOfPosition.EMPTY))) {
            player.getBoard().addToGraveyard(position.getCard());
            position.setStatus(StatusOfPosition.EMPTY);
            position.setCard(null);
            attackedCards.remove(position);
            activatedSpells.remove(position);
        }
    }

    public void setTrapSpellOnBoard() {
        if ((selectedCardHand == null) && (selectedPosition == null)) {
            errorText.setText("no card is selected yet");
        } else if ((selectedCardHand == null)) {
            errorText.setText("you can’t set this card");
        } else if (!this.phase.equals(Phase.MAIN)) {
            errorText.setText("you can’t do this action in this phase");
        } else if (turnOfPlayer.getBoard().isTrapAndSpellZoneFull()) {
            errorText.setText("spell card zone is full");
            setCardToFieldZone(selectedCardHand);
        } else {
            int i = firstEmptyIndex(turnOfPlayer.getBoard().getTrapAndSpellCards());
            turnOfPlayer.getBoard().getTrapAndSpellCards().get(i).setStatus(StatusOfPosition.SPELL_OR_TRAP_HIDDEN);
            turnOfPlayer.getBoard().getTrapAndSpellCards().get(i).setStatusChanged(true);
            turnOfPlayer.getBoard().getTrapAndSpellCards().get(i).setCard(selectedCardHand);
            turnOfPlayer.getHand().remove(selectedCardHand);
            selectedCardHandNulling();
            showTurnOfPlayerHandCards();
            errorText.setText("set successfully");
        }
    }

    public void flipSummon() {
        if ((selectedPosition == null) && (selectedCardHand == null)) {
            errorText.setText("no card is selected yet");
        } else if ((selectedCardHand != null) || !(selectedPosition.getCard() instanceof MonsterCard)) {
            errorText.setText("you can’t change this card position");
        } else if (!this.phase.equals(Phase.MAIN)) {
            errorText.setText("you can’t do this action in this phase");
        } else if ((!selectedPosition.getStatus().equals(StatusOfPosition.DEFENSIVE_HIDDEN)) || (selectedPosition.getIsStatusChanged())) {
            errorText.setText("you can’t flip summon this card");
        } else {
            Media media = new Media(new File(str3).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
            selectedPosition.setStatus(StatusOfPosition.OFFENSIVE_OCCUPIED);
            errorText.setText("flip summoned successfully");
        }
    }

    private int getCheatCounter() {
        return this.cheatCounter;
    }

    public void setCardToFieldZone(Card card) {
        if (turnOfPlayer.getBoard().getFieldZone().getCard() != null) {
            sendToGraveyard(turnOfPlayer.getBoard().getFieldZone(), turnOfPlayer);
        }
        turnOfPlayer.getBoard().getFieldZone().setCard(card);
        turnOfPlayer.getBoard().getFieldZone().setStatus(StatusOfPosition.SPELL_OR_TRAP_OCCUPIED);
    }

    //----------------------------------------Alerts---------------------------------------
    public void showEndOfGameAlert(String reason) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        if (reason.equals("deck")) {
            alert.setContentText(turnOfPlayer.getNickname() + "'s deck is empty");
        } else if (turnOfPlayer.getLP() == 0) {
            alert.setContentText(turnOfPlayer.getNickname() + "'s LP is 0");
        } else {
            alert.setContentText(getOpposition().getNickname() + "'s LP is 0");
        }
        alert.showAndWait();
    }

    public void showPhaseAlert() {
        errorText.setText(phase.name());
    }

    private void showWhichPlayersTurn() {
        errorText.setText("it is " + turnOfPlayer.getNickname() + "'s turn");
    }

    private void phaseNavigationAlert(Phase destination) {
        errorText.setText("navigation between " + phase.name() +
                " phase and " + destination.name() + " phase is not possible");
    }
    //------------------------------------------------------------------------------------

    //---------------------------------------visual control-------------------------------
    public void showOppositionHandCards() {
        gridHandCardsOpponent.getChildren().clear();
        int margin = -5;
        int handSize = getOpposition().getHand().size();
        ArrayList<Card> hand = getOpposition().getHand();
        int column = 0;
        for (int i = 0; i < handSize; i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Fxmls/handCards.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                HandCardsController handCardsController = fxmlLoader.getController();
                MyListener myListenerSelectedCard = new MyListener() {
                    @Override
                    public void onClickListener(Object object) {
                        setSelectedCardImage(null);
                    }
                };
                handCardsController.setCard(hand.get(i), myListenerSelectedCard, i, false);
                gridHandCardsOpponent.add(anchorPane, column, 0);
                column++;
                if (handSize >= 9) {
                    int x = margin * (handSize - 9);
                    GridPane.setMargin(anchorPane, new Insets(x));
                } else {
                    GridPane.setMargin(anchorPane, new Insets(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void showTurnOfPlayerHandCards() {
        gridHandCardsTurnOfPlayer.getChildren().clear();
        int margin = -5;
        int handSize = turnOfPlayer.getHand().size();
        ArrayList<Card> hand = turnOfPlayer.getHand();
        int column = 0;
        for (int i = 0; i < handSize; i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Fxmls/handCards.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                HandCardsController handCardsController = fxmlLoader.getController();
                MyListener myListenerSelectedCard = new MyListener() {
                    @Override
                    public void onClickListener(Object object) {
                        Card card = (Card) object;
                        if (card.equals(selectedCardHand))
                            deSelect();
                        else
                            setSelectedCardHand(card);
                    }
                };
                handCardsController.setCard(hand.get(i), myListenerSelectedCard, i, true);
                gridHandCardsTurnOfPlayer.add(anchorPane, column, 0);
                column++;
                if (handSize >= 9) {
                    int x = margin * (handSize - 9);
                    GridPane.setMargin(anchorPane, new Insets(x));
                } else {
                    GridPane.setMargin(anchorPane, new Insets(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void showInfo() {
        infoTextArea.setWrappingWidth(210);
        if (selectedCardGraphic != null) {
            Card card = selectedCardGraphic;

            if (card instanceof MonsterCard) {
                infoTextArea.setText("Card Name: " + card.getCardName() +
                        "\nType: " + ((MonsterCard) card).getMonsterType() +
                        "\nLevel: " + ((MonsterCard) card).getCardLevel() +
                        "\nATK: " + ((MonsterCard) card).getAttack() +
                        "\nDEF: " + ((MonsterCard) card).getDefense() +
                        "\nDescription: " + card.getCardDescription());
            } else {
                TrapAndSpellCard TPCard = (TrapAndSpellCard) card;
                infoTextArea.setText("Card Name: " + card.getCardName() +
                        "\nType: " + TPCard.getTrapOrSpellTypes() +
                        "\nDescription: " + TPCard.getCardDescription());
            }
        }
    }

    public void setSelectedCardImage(Card card) {
        if (card == null) {
            Image image = new Image(getClass().getResourceAsStream("/Images/Monster/Unknown.jpg"));
            selectedCardImage.setImage(image);
            selectedCardGraphic = null;
            infoTextArea.setText("");
        } else {
            Image image = new Image(getClass().getResourceAsStream(card.getImageSrc()));
            selectedCardImage.setImage(image);
            selectedCardGraphic = card;
            showInfo();
        }
    }

    private void showPlayersLP() {
        turnOfPlayerLP.setText(String.valueOf(turnOfPlayer.getLP()));
        oppositionLP.setText(String.valueOf(getOpposition().getLP()));
    }

    public void setPlayersProgressBar() {
        double turnPercentage = (double) (player1.getLP()) / 8000;
        double oppositionPercentage = (double) (player2.getLP()) / 8000;
        turnOfPlayerProgressBar.setProgress(turnPercentage);
        turnOfPlayerProgressBar.setProgress(oppositionPercentage);
    }

    public void setIcons() {
        ImageView imageView = new ImageView();
        Image image = new Image(getClass().getResourceAsStream("/Images/Icon/phaseActions/Summon.png"));
        imageView.setImage(image);
        imageView.setFitHeight(70);
        imageView.setFitWidth(70);
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                summon();
            }
        });
        gridPanePhaseIcons.add(imageView, 1, 0);
        GridPane.setMargin(imageView, new Insets(10, 0, 0, 0));

        imageView = new ImageView();
        image = new Image(getClass().getResourceAsStream("/Images/Icon/phaseActions/Set.png"));
        imageView.setImage(image);
        imageView.setFitHeight(70);
        imageView.setFitWidth(70);
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                set();
            }
        });
        gridPanePhaseIcons.add(imageView, 2, 0);
        GridPane.setMargin(imageView, new Insets(10, 0, 0, 0));

        imageView = new ImageView();
        image = new Image(getClass().getResourceAsStream("/Images/Icon/phaseActions/SpecialSummon.png"));
        imageView.setImage(image);
        imageView.setFitHeight(70);
        imageView.setFitWidth(70);
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ;
            }
        });
        gridPanePhaseIcons.add(imageView, 3, 0);
        GridPane.setMargin(imageView, new Insets(10, 0, 0, 0));

        imageView = new ImageView();
        image = new Image(getClass().getResourceAsStream("/Images/Icon/phaseActions/FlipSummon.png"));
        imageView.setImage(image);
        imageView.setFitHeight(70);
        imageView.setFitWidth(70);
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                flipSummon();
            }
        });
        gridPanePhaseIcons.add(imageView, 4, 0);
        GridPane.setMargin(imageView, new Insets(10, 0, 0, 0));

        imageView = new ImageView();
        image = new Image(getClass().getResourceAsStream("/Images/Icon/phaseActions/ActivateEffect.png"));
        imageView.setImage(image);
        imageView.setFitHeight(70);
        imageView.setFitWidth(70);
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ;
            }
        });
        gridPanePhaseIcons.add(imageView, 1, 1);
        GridPane.setMargin(imageView, new Insets(25, 0, 0, 0));

        imageView = new ImageView();
        image = new Image(getClass().getResourceAsStream("/Images/Icon/phaseActions/ChangeStatus.png"));
        imageView.setImage(image);
        imageView.setFitHeight(70);
        imageView.setFitWidth(70);
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });
        gridPanePhaseIcons.add(imageView, 2, 1);
        GridPane.setMargin(imageView, new Insets(25, 0, 0, 0));

        imageView = new ImageView();
        image = new Image(getClass().getResourceAsStream("/Images/Icon/phaseActions/ChangeToAttack.png"));
        imageView.setImage(image);
        imageView.setFitHeight(70);
        imageView.setFitWidth(70);
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeMonsterStatus("attack");
            }
        });
        gridPanePhaseIcons.add(imageView, 3, 1);
        GridPane.setMargin(imageView, new Insets(25, 0, 0, 0));

        imageView = new ImageView();
        image = new Image(getClass().getResourceAsStream("/Images/Icon/phaseActions/ChangeToDefense.png"));
        imageView.setImage(image);
        imageView.setFitHeight(70);
        imageView.setFitWidth(70);
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeMonsterStatus("defense");
            }
        });
        gridPanePhaseIcons.add(imageView, 4, 1);
        GridPane.setMargin(imageView, new Insets(25, 0, 0, 0));

        imageView = new ImageView();
        image = new Image(getClass().getResourceAsStream("/Images/Icon/phaseActions/Attack.png"));
        imageView.setImage(image);
        imageView.setFitHeight(70);
        imageView.setFitWidth(70);
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!isConditionsUnsuitableForAttack())
                    if (isOpponentMonsterZoneEmpty()) {
                        errorText.setText("Your opponent's monster zone is empty and you can attack directly to their LP");
                    } else {
                        battleStandby();
                    }
            }
        });
        gridPanePhaseIcons.add(imageView, 2, 2);
        GridPane.setMargin(imageView, new Insets(0, 0, 0, 0));

        imageView = new ImageView();
        image = new Image(getClass().getResourceAsStream("/Images/Icon/phaseActions/DirectAttack.png"));
        imageView.setImage(image);
        imageView.setFitHeight(70);
        imageView.setFitWidth(70);
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                directAttack();
            }
        });
        gridPanePhaseIcons.add(imageView, 3, 2);
        GridPane.setMargin(imageView, new Insets(0, 0, 0, 0));
    }

    public void setPositionsListener(Player player) {
        MyListener myListener = new MyListener() {
            @Override
            public void onClickListener(Object object) {
                Position position = (Position) object;
                if (position.equals(selectedPosition))
                    deSelect();
                else if (phase.equals(Phase.BATTLE_STANDBY)) {
                    setOppositionCardPosition((Position) object);
                } else if (phase.equals(Phase.TRIBUTE)) {
                    setTributePosition((Position) object);
                } else {
                    setSelectedPosition((Position) object);
                }
            }
        };

        for (int i = 0; i < 5; i++) {
            player.getBoard().getMonsterCards().get(i).setMyListener(myListener);
            player.getBoard().getTrapAndSpellCards().get(i).setMyListener(myListener);
        }
    }

    public void setPositionsOfTurnOfPlayer() {
        turnOfPlayer.getBoard().getMonsterCards().get(2).setImageView(imageViewMonster11);
        turnOfPlayer.getBoard().getMonsterCards().get(3).setImageView(imageViewMonster12);
        turnOfPlayer.getBoard().getMonsterCards().get(1).setImageView(imageViewMonster13);
        turnOfPlayer.getBoard().getMonsterCards().get(4).setImageView(imageViewMonster14);
        turnOfPlayer.getBoard().getMonsterCards().get(0).setImageView(imageViewMonster15);

        turnOfPlayer.getBoard().getTrapAndSpellCards().get(2).setImageView(imageViewTrap11);
        turnOfPlayer.getBoard().getTrapAndSpellCards().get(3).setImageView(imageViewTrap12);
        turnOfPlayer.getBoard().getTrapAndSpellCards().get(1).setImageView(imageViewTrap13);
        turnOfPlayer.getBoard().getTrapAndSpellCards().get(4).setImageView(imageViewTrap14);
        turnOfPlayer.getBoard().getTrapAndSpellCards().get(0).setImageView(imageViewTrap15);
    }

    public void setPositionsOfOpposition() {
        getOpposition().getBoard().getMonsterCards().get(2).setImageView(imageViewMonster21);
        getOpposition().getBoard().getMonsterCards().get(1).setImageView(imageViewMonster22);
        getOpposition().getBoard().getMonsterCards().get(3).setImageView(imageViewMonster23);
        getOpposition().getBoard().getMonsterCards().get(0).setImageView(imageViewMonster24);
        getOpposition().getBoard().getMonsterCards().get(4).setImageView(imageViewMonster25);

        getOpposition().getBoard().getTrapAndSpellCards().get(2).setImageView(imageViewTrap21);
        getOpposition().getBoard().getTrapAndSpellCards().get(1).setImageView(imageViewTrap22);
        getOpposition().getBoard().getTrapAndSpellCards().get(3).setImageView(imageViewTrap23);
        getOpposition().getBoard().getTrapAndSpellCards().get(0).setImageView(imageViewTrap24);
        getOpposition().getBoard().getTrapAndSpellCards().get(4).setImageView(imageViewTrap25);
    }

    public void showGraveyardTurnOfPlayer() {
        showGraveyard(turnOfPlayer);
    }

    public void showGraveyardOpposition() {
        showGraveyard(getOpposition());
    }

    public void showGraveyard(Player player) {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/Fxmls/Graveyard.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            GraveyardController graveyardController = fxmlLoader.getController();
            graveyardController.addCardsToGraveYardPane(player);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void puver(MouseEvent event) {
        if (cheatCounter <= 3) {
            turnOfPlayer = getOpposition();
            increaseCheatCounter();
            isSurrendered = true;
        }
    }

    public void yBdB12(MouseEvent event) {
        if (cheatCounter <= 3) {
            if (selectedPosition == null)
                errorText.setText("selected position is null");
            else {
                if (attackedCards.contains(selectedPosition)) {
                    attackedCards.remove(selectedPosition);
                    errorText.setText("cheat activated:\n" +
                            "now you can attack again with your card");
                    increaseCheatCounter();
                }
            }
        }
    }

    public void bAcOo(MouseEvent event) {
        if (getCheatCounter() < 3) {
            if (!getOpposition().getBoard().getMonsterCards().isEmpty()) {
                sendToGraveyard(getOpposition().getBoard().getMaximumPuver(), getOpposition());
                errorText.setText("cheat activated:\n" +
                        "you removed the most powerful monster of your opponent");
                increaseCheatCounter();
            }
        }
    }

    public void hPoSt2(MouseEvent event) {
        if (getCheatCounter() < 3) {
            isAnyCardSummoned = false;
            errorText.setText("cheat activated:\n" +
                    "you can now summon or set another card");
            increaseCheatCounter();
        }
    }

    public void lPi0051(MouseEvent event) {
        if (getCheatCounter() < 3) {
            turnOfPlayer.increaseLP(1500);
            errorText.setText("cheat activated:\n" +
                    "1500 LP was added to you");
            increaseCheatCounter();
            showPlayersLP();
        }
    }

    public void goToDuelMenu() {
        try {
            player1.setBoard(null);
            player2.setBoard(null);
            DuelMenuController.setFromGame(true);
            Parent root = FXMLLoader.load(getClass().getResource("/Fxmls/DuelMenu.fxml"));
            Stage stage = (Stage) errorText.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}