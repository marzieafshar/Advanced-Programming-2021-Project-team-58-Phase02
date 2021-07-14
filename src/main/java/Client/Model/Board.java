package Client.Model;

import java.util.ArrayList;

public class Board {
    private ArrayList<Card> graveYard = new ArrayList<Card>();
    private ArrayList<Position> monsterCards = new ArrayList<Position>();
    private ArrayList<Position> trapAndSpellCards = new ArrayList<Position>();
    private ArrayList<Card> mainDeck = new ArrayList<>();
    private Position fieldZone = new Position(StatusOfPosition.EMPTY);

    public Board() {
        createMonsterCardPosition();
        createSpellCardPosition();
    }

    public ArrayList<Position> getMonsterCards() {
        return monsterCards;
    }

    public ArrayList<Position> getTrapAndSpellCards() {
        return trapAndSpellCards;
    }

    public void createMonsterCardPosition() {
        for (int i = 0; i < 5; i++) {
            Position x = new Position(StatusOfPosition.EMPTY);
            monsterCards.add(x);
        }
    }

    public void createSpellCardPosition() {
        for (int i = 0; i < 5; i++) {
            Position x = new Position(StatusOfPosition.EMPTY);
            trapAndSpellCards.add(x);
        }
    }

    public boolean isMonsterZoneFull() {
        for (Position position : monsterCards) {
            if (position.getStatus().equals(StatusOfPosition.EMPTY)) {
                return false;
            }
        }
        return true;
    }


    public int cardsInMonsterZone() {
        int i = 0;
        for (Position position : monsterCards) {
            if (!position.getStatus().equals(StatusOfPosition.EMPTY)) {
                i++;
            }
        }
        return i;
    }


    public boolean isTrapAndSpellZoneFull() {
        for (Position position : trapAndSpellCards) {
            if (position.getStatus().equals(StatusOfPosition.EMPTY)) {
                return false;
            }
        }
        return true;
    }

    public void addToGraveyard(Card card) {
        graveYard.add(card);
    }

    public ArrayList<Card> getMainDeck() {
        return this.mainDeck;
    }

    public void setMainDeck(ArrayList<Card> mainDeck) {
        this.mainDeck = mainDeck;
    }

    public ArrayList<Card> getGraveYard() {
        return graveYard;
    }

    public void clearBoard() {
        graveYard.clear();
        for (Position position : monsterCards) {
            position.setStatus(StatusOfPosition.EMPTY);
            position.setCard(null);
        }
        for (Position position : trapAndSpellCards) {
            position.setStatus(StatusOfPosition.EMPTY);
            position.setCard(null);
        }
    }

    public Position getMaximumPuver() {
        int max = 0;
        int cardAttack;
        Position bestPosition = monsterCards.get(0);
        for (int i = 0; i < monsterCards.size(); i++) {
            Position position = monsterCards.get(i);
            if (position.getStatus().equals(StatusOfPosition.OFFENSIVE_OCCUPIED)) {
                cardAttack = ((MonsterCard) position.getCard()).getAttack();
                if (cardAttack >= max) {
                    bestPosition = position;
                    max = cardAttack;
                }
            }
        }
        return bestPosition;
    }

    public Position getFieldZone() {
        return this.fieldZone;
    }
}
