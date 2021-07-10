package Controller;

import Model.*;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;

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


//-----------------

    private static int nextPhaseCheck;
    static Scanner scanner = new Scanner(System.in);

    private Player player1;
    private Player player2;

    private Phase phase = Phase.DRAW;
    private Player turnOfPlayer;
    public boolean isSurrendered = false;
    private Position selectedPosition = null;
    private Card selectedCardHand;
    private boolean isAnyCardSummoned;
    private int cheatCounter;

    List<Position> attackedCards = new ArrayList<Position>();
    List<Position> activatedSpells = new ArrayList<>();

//    public Game(Player player1, Player player2) {
//        setPlayer1(player1);
//        setPlayer2(player2);
//        drawAtFirstTurn(player1);
//        drawAtFirstTurn(player2);
//    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPlayer1(Controller.getLoggedInPlayer());
        setPlayer2(SetOpponentController.getPlayer2());
        startOfGameSettings();
    }

    public void startOfGameSettings() {
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

    public void setPlayersLp() {//حله
        turnOfPlayer.setLP(7000);
        getOpposition().setLP(8000);
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
        nextPhaseCheck = 0;
        setPhase(Phase.DRAW);
        showPhaseAlert();
        if (isAnyoneWin()) return;
        draw();
//        standbyPhase();
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

//    public void run() {
//        try {
//            while (!isAnyoneWin()) {
//                nextPhaseCheck = 0;
//                isAnyCardSummoned = false;
//                setAllPositionsChangeStatus();
//
//                showTurnOfPlayerAlert();
//                if (turnOfPlayer instanceof AIClass) {
//                    ((AIClass) turnOfPlayer).play(this);
//                    endPhase();
//                } else {
//                    drawPhase();
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("I have caught an exception");
//            e.printStackTrace();
//        }
//    }

    public void setSelectedPosition(Position selectedPosition) {
        selectedCardHandNulling();
        this.selectedPosition = selectedPosition;
        if(getOpposition().getBoard().getMonsterCards().contains(selectedPosition) &&
                (selectedPosition.getStatus().equals(StatusOfPosition.DEFENSIVE_HIDDEN)))
            setSelectedCardImage(null);
        else if(selectedPosition.getStatus().equals(StatusOfPosition.SPELL_OR_TRAP_HIDDEN) &&
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

    public Phase getPhase() {
        return phase;
    }

    public Player getTurnOfPlayer() {
        return turnOfPlayer;
    }

    public void endPhase(ActionEvent event) {
        setAllPositionsChangeStatus();
        changeTurnOfPlayer();
        showWhichPlayersTurn();
        isAnyCardSummoned = false;
    }

    public void mainPhase() {
        if (phase.equals(Phase.DRAW) || (phase.equals(Phase.BATTLE))) {
            setPhase(Phase.MAIN);
            showPhaseAlert();
            setMainPhaseIcons();
            if (nextPhaseCheck == 0) {
                nextPhaseCheck = 1;
            }
        } else {
            phaseNavigationAlert(Phase.MAIN);
        }
    }


    private void deSelect() {
        selectedPositionNulling();
        selectedCardHandNulling();
        infoTextArea.setText("No card is selected!");
    }

//    public void select(Matcher matcher) {
//        int number = Integer.parseInt(matcher.group(3));
//
//        if ((matcher.group(1).equals("monster")) && (matcher.group(2) == null)) {
//            if ((number >= 1) && (number <= 5)) {
//                if (turnOfPlayer.getBoard().getMonsterCards().get(convertIndex(number)).getCard() == null) {
//                    System.out.println("no card found in the given position");
//                } else {
//                    selectedCardHandNulling();
//                    selectedPosition = turnOfPlayer.getBoard().getMonsterCards().get(convertIndex(number));
//                    System.out.println("card selected");
//                }
//            } else {
//                System.out.println("invalid selection");
//            }
//        } else if ((matcher.group(1).equals("spell")) && (matcher.group(2) == null)) {
//            if ((number >= 1) && (number <= 5)) {
//                if (turnOfPlayer.getBoard().getTrapAndSpellCards().get(convertIndex(number)).getCard() == null) {
//                    System.out.println("no card found in the given position");
//                } else {
//                    selectedCardHandNulling();
//                    selectedPosition = turnOfPlayer.getBoard().getTrapAndSpellCards().get(convertIndex(number));
//                    System.out.println("card selected");
//                }
//            } else {
//                System.out.println("invalid selection");
//            }
//        } else if ((matcher.group(1).equals("hand")) && (matcher.group(2) == null)) {
//            if ((number >= 1) && (number <= turnOfPlayer.getHand().size())) {
//                selectedPositionNulling();
//                selectedCardHand = turnOfPlayer.getHand().get(number - 1);
//                System.out.println("card selected");
//            } else {
//                System.out.println("invalid selection");
//            }
//        } else if ((matcher.group(1).equals("monster")) && (matcher.group(2).equals("--opponent "))) {
//            if ((number >= 1) && (number <= 5)) {
//                if (getOpposition().getBoard().getMonsterCards().get(convertIndex(number)).getCard() == null) {
//                    System.out.println("no card found in the given position");
//                } else {
//                    selectedCardHandNulling();
//                    selectedPosition = getOpposition().getBoard().getMonsterCards().get(convertIndex(number));
//                    System.out.println("card selected");
//                }
//            } else {
//                System.out.println("invalid selection");
//            }
//        } else if ((matcher.group(1).equals("spell")) && (matcher.group(2).equals("--opponent "))) {
//            if ((number >= 1) && (number <= 5)) {
//                if (getOpposition().getBoard().getTrapAndSpellCards().get(convertIndex(number)).getCard() == null) {
//                    System.out.println("no card found in the given position");
//                } else {
//                    selectedCardHandNulling();
//                    selectedPosition = getOpposition().getBoard().getTrapAndSpellCards().get(convertIndex(number));
//                    System.out.println("card selected");
//                }
//            } else {
//                System.out.println("invalid selection");
//            }
//        } else if ((matcher.group(1).equals("hand")) && (matcher.group(2).equals("--opponent "))) {
//            System.out.println("invalid selection");
//        } else {
//            System.out.println("invalid selection");
//        }
//    }

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

    public void setAnyCardSummoned(boolean anyCardSummoned) {
        isAnyCardSummoned = anyCardSummoned;
    }

    public void battlePhase(ActionEvent event) {
        if(phase.equals(Phase.MAIN) && nextPhaseCheck==1){
            nextPhaseCheck = 0;
            setPhase(Phase.BATTLE);
            showPhaseAlert();
        }
        else{
            phaseNavigationAlert(Phase.BATTLE);
        }
//        String input;
//        while (!(input = scanner.nextLine()).equals("next phase")) {
////            Matcher matchSelect = getCommandMatcher(input, "^select --(hand|monster|spell) (--opponent )*([0-9]+)$");
//
//            if (input.trim().matches("^(?i)(attack direct)$"))
//                directAttack();
//            else if (input.trim().matches("^(?i)(attack (.+))$")) {
////                Matcher matcher = getCommandMatcher(input, "^(?i)(attack (.+))$");
////                if (matcher.find()) {
//                int index;
//                try {
////                        index = convertIndex(Integer.parseInt(matcher.group(2)));
//                } catch (Exception e) {
//                    System.out.println("Please enter an integer");
//                    return;
//                }
////                    attackToMonster(index);
//            }
////            } else if (matchSelect.find())
////                select(matchSelect);
//            else if (input.equals("show current-phase"))
//                System.out.println(phase);
//            else if (input.equals("surrender"))
//                surrender();
//            else if (input.equals("card show selected")) {
//                showCard();
//            } else if (input.matches("card show (.+)")) {
////                cardShow(getCommandMatcher(input, "card show (.+)"));
//            } else {
//                if (!cheat(input))
//                    System.out.println("invalid command for this phase");
//            }
//
//            showBoard();
//            if (isAnyoneWin()) {
//                return;
//            }
//        }
        clearAttackedCardsArrayList();
//        mainPhase();
    }

    public void standbyPhase() {
        setPhase(Phase.STANDBY);
        showPhaseAlert();
        showPhaseAlert();
        mainPhase();
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

    public Position getSelectedPosition() {
        return selectedPosition;
    }


    public void surrender() {
        isSurrendered = true;
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

    private String printOpponentMonsterOnBoard(Player player) {
        String result = "";
        String character = "";
        for (int i = 4; i >= 0; i--) {
            character = convertStatusToChar(player.getBoard().getMonsterCards().get(i).getStatus());
            if (character.length() == 2) {
                result = result + character + "   ";
            } else {
                result = result + character + "    ";
            }
        }
        return result;
    }

    private String printOpponentSpellCardsOnBoard(Player player) {
        String result = "";
        String character = "";
        for (int i = 4; i >= 0; i--) {
            character = convertStatusToChar(player.getBoard().getTrapAndSpellCards().get(i).getStatus());
            result = result + character + "    ";
        }
        return result;
    }

    private String printMonsterCardOnBoard(Player player) {
        String result = "";
        String character = "";
        for (int i = 0; i < 5; i++) {
            character = convertStatusToChar(player.getBoard().getMonsterCards().get(i).getStatus());
            if (character.length() == 2) {
                result = result + character + "   ";
            } else {
                result = result + character + "    ";
            }
        }
        return result;
    }

    private String printSpellCardsOnBoard(Player player) {
        String result = "";
        String character = "";
        for (int i = 0; i < 5; i++) {
            character = convertStatusToChar(player.getBoard().getTrapAndSpellCards().get(i).getStatus());
            result = result + character + "    ";
        }
        return result;
    }

    private String printCardsOnBoard(Player player) {
        int handCardNumbers = player.getHand().size();
        String result = "";
        for (int i = 0; i < handCardNumbers; i++) {
            result = result + "c    ";
        }
        return result;
    }

    public void showBoard() {
        System.out.println(getOpposition().getNickname() + " : " + getOpposition().getLP());
        System.out.println("     " + printCardsOnBoard(getOpposition()));
        System.out.println(getOpposition().getBoard().getMainDeck().size());
        System.out.println("     " + printOpponentSpellCardsOnBoard(getOpposition()));
        System.out.println("     " + printOpponentMonsterOnBoard(getOpposition()));
        System.out.println(getOpposition().getBoard().getGraveYard().size() +
                "                            " + showFieldZone(getOpposition()));
        System.out.println();
        System.out.println("-------------------------------");
        System.out.println();
        System.out.println(showFieldZone(turnOfPlayer) +
                "                            " + turnOfPlayer.getBoard().getGraveYard().size());
        System.out.println("     " + printMonsterCardOnBoard(turnOfPlayer));
        System.out.println("     " + printSpellCardsOnBoard(turnOfPlayer));
        System.out.println("                             " + turnOfPlayer.getBoard().getMainDeck().size());
        System.out.println(printCardsOnBoard(turnOfPlayer));
        System.out.println(turnOfPlayer.getNickname() + " : " + turnOfPlayer.getLP());
    }

    private String showFieldZone(Player player) {
        return convertStatusToChar(player.getBoard().getFieldZone().getStatus());
    }

    public void selectedPositionNulling() {
        selectedPosition = null;
        setSelectedCardImage(null);
    }

    public void selectedCardHandNulling() {
        selectedCardHand = null;
        setSelectedCardImage(null);
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
        boolean isTributeSucceeds = false;
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
                        tribute(1);
                        return lastStepForSummon();
                    }
                } else {
                    if (turnOfPlayer.getBoard().cardsInMonsterZone() < 2) {
                        errorText.setText("there are not enough cards for tribute");
                        return false;
                    } else {
                        tribute(2);
                        return lastStepForSummon();
                    }
                }
            }
        }
    }

    private boolean lastStepForSummon() {
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

    private void tribute(int numberOfCards) {
        int numOfCardsTributed = 0;
        errorText.setText("Please choose " + (numberOfCards - numOfCardsTributed) + " monsters for tribute");
        if (selectedPosition != null) {
            if (turnOfPlayer.getBoard().getMonsterCards().contains(selectedPosition)) {
                sendToGraveyard(selectedPosition, turnOfPlayer);
                selectedPositionNulling();
            } else {
                errorText.setText("you can't tribute this card");
            }
        }
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
            System.out.println("set successfully");
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

    public void attackToMonster(int index) {

        if (isConditionsUnsuitableForAttack())
            return;
        Position oppositionCardPosition = getOpposition().getBoard().getMonsterCards().get(index);
        StatusOfPosition statusOfOpposition = oppositionCardPosition.getStatus();
        if (isOpponentMonsterZoneEmpty())
            System.out.println("your opponent's monster zone is empty and you can attack directly to their LP");
        else if (statusOfOpposition.equals(StatusOfPosition.EMPTY))
            System.out.println("there is no card to attack here");
        else {
            int selectedCardAttack = ((MonsterCard) selectedPosition.getCard()).getAttack();
            int oppositionCardAttack = ((MonsterCard) oppositionCardPosition.getCard()).getAttack();
            int oppositionCardDefense = ((MonsterCard) oppositionCardPosition.getCard()).getDefense();

            if (statusOfOpposition.equals(StatusOfPosition.OFFENSIVE_OCCUPIED)) {
                if (selectedCardAttack > oppositionCardAttack) {
                    int damage = selectedCardAttack - oppositionCardAttack;
                    sendToGraveyard(oppositionCardPosition, getOpposition());
                    getOpposition().decreaseLP(damage);
                    System.out.println("your opponent’s monster is destroyed and your opponent receives "
                            + damage + " battle damage");
                } else if (selectedCardAttack < oppositionCardAttack) {
                    int damage = oppositionCardAttack - selectedCardAttack;
                    sendToGraveyard(selectedPosition, turnOfPlayer);
                    turnOfPlayer.decreaseLP(damage);
                    System.out.println("Your monster card is destroyed and you received " + damage +
                            " battle damage");
                } else {
                    sendToGraveyard(oppositionCardPosition, getOpposition());
                    sendToGraveyard(selectedPosition, turnOfPlayer);
                    System.out.println("both you and your opponent monster cards "
                            + "are destroyed and no one receives damage");
                }
            } else {
                String cardName = oppositionCardPosition.getCard().getCardName();
                if (selectedCardAttack > oppositionCardDefense) {
                    sendToGraveyard(oppositionCardPosition, getOpposition());
                    if (statusOfOpposition.equals(StatusOfPosition.DEFENSIVE_OCCUPIED))
                        System.out.println("the defense position monster is destroyed");
                    else {
                        System.out.println("opponent’s monster card was " + cardName +
                                " and the defense position monster is destroyed");
                    }
                } else if (selectedCardAttack < oppositionCardDefense) {
                    int damage = oppositionCardDefense - selectedCardAttack;
                    turnOfPlayer.decreaseLP(damage);
                    if (statusOfOpposition.equals(StatusOfPosition.DEFENSIVE_OCCUPIED))
                        System.out.println("no card is destroyed and you received " + damage + " battle damage");
                    else
                        System.out.println("opponent’s monster card was " + cardName +
                                " no card is destroyed and you received " + damage + " battle damage");
                } else {
                    if (statusOfOpposition.equals(StatusOfPosition.DEFENSIVE_OCCUPIED))
                        System.out.println("no card is destroyed");
                    else
                        System.out.println("opponent’s monster card was " + cardName + " no card is destroyed");
                }
            }
            attackedCards.add(selectedPosition);
            selectedCardHandNulling();
            selectedPositionNulling();
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
        if (isConditionsUnsuitableForAttack())
            return;
        if (!isOpponentMonsterZoneEmpty())
            errorText.setText("you can’t attack the opponent directly");
        else {
            int damage = ((MonsterCard) selectedPosition.getCard()).getAttack();
            getOpposition().decreaseLP(damage);
            errorText.setText("you opponent receives " + damage + " battle damage");
            attackedCards.add(selectedPosition);
            showPlayersLP();

            selectedPositionNulling();
            selectedCardHandNulling();
        }

    }

    public boolean isAnyoneWin() {
        if (getPhase().equals(Phase.DRAW) && turnOfPlayer.getBoard().getMainDeck().size() == 0) {
            System.out.println(turnOfPlayer.getNickname() + " has no more card to draw and lost the round");
            return true;
        }
        if (isSurrendered) return true;
        return (player1.getLP() <= 0) || (player2.getLP() <= 0);
    }

//    --------------------------------------------------------------------------------------------------------
//    --------------------------------------------------------------------------------------------------------

    public void sendToGraveyard(Position position, Player player) {
        if (!(position.getStatus().equals(StatusOfPosition.EMPTY))) {
            player.getBoard().addToGraveyard(position.getCard());
            position.setCard(null);
            position.setStatus(StatusOfPosition.EMPTY);
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
            setCardToFieldZone(selectedCardHand);//TODO:add fieldzone to graphic
        } else {
            int i = firstEmptyIndex(turnOfPlayer.getBoard().getTrapAndSpellCards());
            turnOfPlayer.getBoard().getTrapAndSpellCards().get(i).setStatus(StatusOfPosition.SPELL_OR_TRAP_HIDDEN);
            turnOfPlayer.getBoard().getTrapAndSpellCards().get(i).setStatusChanged(true);
            turnOfPlayer.getBoard().getTrapAndSpellCards().get(i).setCard(selectedCardHand);
            turnOfPlayer.getHand().remove(selectedCardHand);
            selectedCardHandNulling();
            showTurnOfPlayerHandCards();
            System.out.println("set successfully");
        }
    }

    private boolean isAnyMonsterInArray(ArrayList<Card> array) {
        for (Card card : array) {
            if (card instanceof MonsterCard) {
                return true;
            }
        }
        return false;
    }

    private void specialSummonHelping(ArrayList<Card> array) {
        int n = 0;
        while (n == 0) {
            int intNumber = 0;
            while (true) {
                System.out.println("Please enter an index");
                String number = scanner.nextLine();
                try {
                    intNumber = Integer.parseInt(number);
                    break;
                } catch (Exception e) {
                    System.out.println("The index must be only an integer");
                }
            }
            if (intNumber < 1) {
                System.out.println("index must be a positive number.");
            } else if (intNumber > array.size()) {
                System.out.println("given number is greater than number of cards");
            } else if (!(array.get(intNumber - 1) instanceof MonsterCard)) {
                System.out.println("you can't summon this card");
            } else {
                int i = firstEmptyIndex(turnOfPlayer.getBoard().getMonsterCards());
                turnOfPlayer.getBoard().getMonsterCards().get(i).setStatus(StatusOfPosition.OFFENSIVE_OCCUPIED);
                turnOfPlayer.getBoard().getMonsterCards().get(i).setCard(array.get(intNumber - 1));
                array.remove(intNumber - 1);
                System.out.println("special summoned successfully");
                n = 1;
            }
        }
    }

    public void specialSummon(String arrayName) {
        if (arrayName.equals("deck")) {
            if (!isAnyMonsterInArray(turnOfPlayer.getBoard().getMainDeck())) {
                System.out.println("there is no way you could special summon a monster");
            } else {
                if (turnOfPlayer.getBoard().isMonsterZoneFull()) {
                    System.out.println("monster zone is full");
                } else {
                    specialSummonHelping(turnOfPlayer.getBoard().getMainDeck());
                }
            }
        } else if (arrayName.equals("hand")) {
            if (!isAnyMonsterInArray(turnOfPlayer.getHand())) {
                System.out.println("there is no way you could special summon a monster");
            } else {
                if (turnOfPlayer.getBoard().isMonsterZoneFull()) {
                    System.out.println("monster zone is full");
                } else {
                    specialSummonHelping(turnOfPlayer.getHand());
                }
            }
        } else if (arrayName.equals("opponents graveyard")) {
            if (!isAnyMonsterInArray(getOpposition().getBoard().getGraveYard())) {
                System.out.println("there is no way you could special summon a monster");
            } else {
                if (turnOfPlayer.getBoard().isMonsterZoneFull()) {
                    System.out.println("monster zone is full");
                } else {
                    specialSummonHelping(getOpposition().getBoard().getGraveYard());
                }
            }
        } else {
            if (!isAnyMonsterInArray(turnOfPlayer.getBoard().getGraveYard())) {
                System.out.println("there is no way you could special summon a monster");
            } else {
                if (turnOfPlayer.getBoard().isMonsterZoneFull()) {
                    System.out.println("monster zone is full");
                } else {
                    specialSummonHelping(turnOfPlayer.getBoard().getGraveYard());
                }
            }
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
            selectedPosition.setStatus(StatusOfPosition.OFFENSIVE_OCCUPIED);
            errorText.setText("flip summoned successfully");
        }
    }

    public void showGraveyard() {
        int graveSize = turnOfPlayer.getBoard().getGraveYard().size();
        if (graveSize == 0) {
            System.out.println("graveyard is empty");
        } else {
            for (int i = 0; i < graveSize; i++) {
                String cardName = turnOfPlayer.getBoard().getGraveYard().get(i).getCardName();
                String cardDescription = turnOfPlayer.getBoard().getGraveYard().get(i).getCardDescription();
                int rank = i + 1;
                System.out.println(rank + ". " + cardName + " : " + cardDescription);
            }
        }
    }

//    public void activateSpell() {
//        if (selectedPosition == null) {
//            System.out.println("no card is selected yet");
//        } else if (!(selectedPosition.getCard() instanceof TrapAndSpellCard) ||
//                !((TrapAndSpellCard) selectedPosition.getCard()).getTrapOrSpellTypes().equals(TrapOrSpellTypes.SPELL_CARD)) {
//            System.out.println("activate effect is only for spell cards.");
//        } else if (!getPhase().equals(Phase.MAIN)) {
//            System.out.println("you can’t activate an effect on this turn");
//        } else if (activatedSpells.contains(selectedPosition)) {
//            System.out.println("you have already activated this card");
//        } else {
//            if (!((TrapAndSpellCard) Objects.requireNonNull(getSelectedPosition().getCard())).getEffect().isSuitableForActivate(this)) {
//                System.out.println("preparations of this spell are not done yet");
//            } else {
//                activatedSpells.add(selectedPosition);
//                System.out.println("spell activated");
//                ((TrapAndSpellCard) Objects.requireNonNull(getSelectedPosition().getCard())).getEffect().activate(this);
//                sendToGraveyard(selectedPosition, turnOfPlayer);
//                selectedCardHandNulling();
//                selectedPositionNulling();
//            }
//        }
//    }

    //until adding effect class !!

    private boolean isPositionInOpponentsBoard() {
        if (getOpposition().getBoard().getMonsterCards().contains(selectedPosition)) {
            return true;
        } else return getOpposition().getBoard().getTrapAndSpellCards().contains(selectedPosition);
    }

    private boolean isPositionHidden() {
        return (selectedPosition.getStatus().equals(StatusOfPosition.DEFENSIVE_HIDDEN)) ||
                (selectedPosition.getStatus().equals(StatusOfPosition.SPELL_OR_TRAP_HIDDEN));
    }

    public void showCard() {
        if ((selectedCardHand == null) && (selectedPosition == null)) {
            System.out.println("no card is selected yet");
        } else if ((isPositionInOpponentsBoard()) && (isPositionHidden())) {
            System.out.println("card is not visible");
        } else {
            if (selectedPosition == null) {
                selectedCardHand.showCard();
            } else if (selectedCardHand == null) {
                selectedPosition.getCard().showCard();
            }
        }
    }

    public void cardShow(Matcher matcher) {
        if (matcher.find()) {
            String cardName = matcher.group(1);
            if (Card.getCardByName(cardName) == null)
                System.out.println("No card with this name was found!");
            else Objects.requireNonNull(Card.getCardByName(cardName)).showCard();
        }
    }

    public boolean cheat(String cheatCode) {
        if (cheatCode.equals("0051iPl")) {
            if (getCheatCounter() < 3) {
                turnOfPlayer.increaseLP(1500);
                System.out.println("cheat activated:\n" +
                        "1500 LP was added to you");
                increaseCheatCounter();
            } else {
                System.out.println("you can't use cheats anymore");
            }
            return true;
        } else if (cheatCode.equals("bAcOo")) {
            if (getCheatCounter() < 3) {
                if (!getOpposition().getBoard().getMonsterCards().isEmpty()) {
                    sendToGraveyard(getOpposition().getBoard().getMaximumPuver(), getOpposition());
                    System.out.println("cheat activated:\n" +
                            "you removed the most powerful monster of your opponent");
                    increaseCheatCounter();
                    return true;
                }
            } else {
                System.out.println("you can't use cheats anymore");
                return false;
            }
            return true;

        } else if (cheatCode.equals("12yBdB")) {
            if (getCheatCounter() < 3) {
                if (selectedPosition == null)
                    System.out.println("selected position is null");
                else {
                    if (attackedCards.contains(selectedPosition)) {
                        attackedCards.remove(selectedPosition);
                        System.out.println("cheat activated:\n" +
                                "now you can attack again with your card");
                        increaseCheatCounter();
                    }
                }
            } else
                System.out.println("you can't use cheats anymore");

            return true;
        } else if (cheatCode.equals("hPoSt2")) {
            if (getCheatCounter() < 3) {
                isAnyCardSummoned = false;
                System.out.println("cheat activated:\n" +
                        "you can now summon or set another card");
                increaseCheatCounter();
            } else
                System.out.println("you can't use cheats anymore");

            return true;
        } else if (cheatCode.equals("pUvEr")) {
            if (getCheatCounter() < 3) {
                System.out.println("puver activated:\n" +
                        "now you win the round");
                turnOfPlayer = getOpposition();
                increaseCheatCounter();
                surrender();
            } else
                System.out.println("you can't use cheats anymore");
            return true;
        }
        return false;
    }

    private int getCheatCounter() {
        return this.cheatCounter;
    }

//    public void showHand() {
//
//        for (int i = 0; i < turnOfPlayer.getHand().size(); i++) {
//            System.out.print((i + 1) + ". ");
//            turnOfPlayer.getHand().get(i).showCard();
//            System.out.println();
//        }
//    }

    public void setCardToFieldZone(Card card) {
        if (turnOfPlayer.getBoard().getFieldZone().getCard() != null) {
            sendToGraveyard(turnOfPlayer.getBoard().getFieldZone(), turnOfPlayer);
        }
        turnOfPlayer.getBoard().getFieldZone().setCard(card);
        turnOfPlayer.getBoard().getFieldZone().setStatus(StatusOfPosition.SPELL_OR_TRAP_OCCUPIED);
    }

    //----------------------------------------Alerts---------------------------------------
    public void showTurnOfPlayerAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("It's " + turnOfPlayer.getNickname() + "’s turn");
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
                " phase and " + destination.name() + "is not possible");
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
//        alert.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("Images/Bitron.jpg"))));
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
            selectedCardGraphic = card;//TODO: delete this attribute
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

    public void setMainPhaseIcons() {


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
//                specialSummon();
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
                // I don't know
            }
        });
        gridPanePhaseIcons.add(imageView, 1, 1);
        GridPane.setMargin(imageView, new Insets(25, 0, 0, 0));

        imageView = new ImageView();
        image = new Image(getClass().getResourceAsStream("/Images/Icon/phaseActions/ChangeStatus.png"));
        imageView.setImage(image);
        imageView.setFitHeight(70);
        imageView.setFitWidth(70);
//        imageView.
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
                attackToMonster(1);
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

    public void setPositionsListener(Player player){
        MyListener myListener = new MyListener() {
            @Override
            public void onClickListener(Object object) {
                Position position = (Position) object;
                if (position.equals(selectedPosition))
                    deSelect();
                else
                    setSelectedPosition((Position) object);
            }
        };

        for(int i=0 ; i<5 ; i++){
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

    public void showGraveyardPlayer1() {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/Fxmls/Graveyard.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            turnOfPlayer.getBoard().addToGraveyard(Card.getCardByName("Suijin"));
            turnOfPlayer.getBoard().addToGraveyard(Card.getCardByName("Silver Fang"));
            turnOfPlayer.getBoard().addToGraveyard(Card.getCardByName("Battle OX"));
            GraveyardController graveyardController = fxmlLoader.getController();
            graveyardController.addCardsToGraveYardPane(turnOfPlayer);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //---------------------------------------------------------------------------------------


}