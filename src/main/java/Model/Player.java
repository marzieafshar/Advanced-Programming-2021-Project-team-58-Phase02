package Model;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.LinkedList;

public class Player {
    private String username;
    private String password;
    private String nickname;
    private Deck activeDeck;
    private int winMatches;
    private int loseMatches;
    private int score;
    private int LP;
    private int money;
    private Board board;

    private ArrayList<Card> allCards = new ArrayList<>();
    private LinkedList<Deck> decks = new LinkedList<Deck>();  //These are NOT the deck that is being used in the game
    private ArrayList<Card> hand = new ArrayList<Card>();
    private static ArrayList<Player> allPlayers = new ArrayList<Player>();
    private Image image;

    public static void setAllPlayers(ArrayList<Player> players) {
        allPlayers = players;
    }

    public ArrayList<Card> getAllCards() {
        return this.allCards;
    }

    public Player(String username, String password, String nickname) {
        setUsername(username);
        setPassword(password);
        setNickname(nickname);
        setMoney(100000);

        setImage(new Image(getClass().getResourceAsStream("/Images/Characters/Chara001.dds1.png")));
        this.board = new Board();
        allPlayers.add(this);
    }

    public Player() {
    }

    public int getLoseMatches() {
        return loseMatches;
    }

    public int getWinMatches() {
        return winMatches;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setLP(int LP) {
        this.LP = LP;
    }

    public int getLP() {
        if (this.LP < 0) {
            return 0;
        }
        return this.LP;
    }

    public void increaseLP(int LP) {
        this.LP += LP;
    }

    public void decreaseLP(int LP) {
        this.LP -= LP;
    }

    public void setActiveDeck(Deck activeDeck) {
        this.activeDeck = activeDeck;
    }

    public Deck getActiveDeck() {
        return this.activeDeck;
    }

    public int getScore() {
        return this.score;
    }

    public void increaseScore(int score) {
        this.score += score;
    }

    public void increaseMoney(int money) {
        this.money += money;
    }

    public void decreaseMoney(int money) {
        this.money -= money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMoney() {
        return this.money;
    }

    public Board getBoard() {
        return this.board;
    }

    public LinkedList<Deck> getDecks() {
        return this.decks;
    }

    public ArrayList<Card> getHand() {
        return this.hand;
    }

    public static Player getPlayerByUsername(String userName) {
        for (Player player : allPlayers)
            if (player.getUsername().equals(userName))
                return player;
        return null;
    }

    public void addCardToHand(Card card) {
        this.hand.add(card);
    }

    public static boolean isNicknameExists(String nickname) {
        for (Player player : allPlayers)
            if (player.getNickname().equals(nickname))
                return true;
        return false;
    }

    public static ArrayList<Player> getAllPlayers() {

        return allPlayers;
    }

    public boolean hasADeck(Deck deck) {
        if (deck == null) return false;
        for (Deck d : decks) {
            if (d.getDeckName().equals(deck.getDeckName()))
                return true;
        }
        return false;
    }

    public Deck getDeckByName(String deckName) {
        for (Deck deck : decks)
            if (deck.getDeckName().equals(deckName))
                return deck;
        return null;
    }

    public Image getImage() {
        return this.image;
    }
}
