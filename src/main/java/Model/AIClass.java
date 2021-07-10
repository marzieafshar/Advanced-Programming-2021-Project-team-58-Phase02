package Model;

import Controller.Game;
import com.gilecode.yagson.YaGson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AIClass extends Player {

    public AIClass() {
        setUsername("AI");
        setPassword("*****");
        setNickname("AI");
        setBoard(new Board());
        createAnActiveDeck();
    }

    public void createAnActiveDeck() {

        String str = null;
        try {
            str = new String(Files.readAllBytes(Paths.get("AIDeck.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<Deck> decks = new YaGson().fromJson(str, new TypeToken<ArrayList<Deck>>() {
        }.getType());
        setActiveDeck(decks.get(0));
    }

    public void play(Game game) {
        game.draw();
        game.setPhase(Phase.MAIN);
        chooseBestCardInHand(game);
        if (game.getSelectedCardHand() != null) {
            if (game.getSelectedCardHand() instanceof MonsterCard)
                game.summon();
            else
                game.set();
        }
        chooseAndSetSpellCard(game);
        game.showBoard();

//        activatePotOfGreed(game);
//        activateRaigeki(game);
//        activateDarkHole(game);

        game.showBoard();
        System.out.println("Thinking...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        game.setPhase(Phase.BATTLE);
        if (game.getOpposition().getBoard().isMonsterZoneEmpty()) {
            selectAttackMonster(game);
            game.directAttack();
        } else {
            selectAttackMonster(game);
//            game.attackToMonster(selectOpponentMonster(game));
        }
        game.showBoard();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void chooseBestCardInHand(Game game) {
        Collections.sort(getHand(), new Comparator<Card>() {
            @Override
            public int compare(Card card1, Card card2) {
                if (card1 instanceof MonsterCard && card2 instanceof MonsterCard)
                    return ((MonsterCard) card2).getAttack() - ((MonsterCard) card1).getAttack();
                if (card2 instanceof TrapAndSpellCard && card1 instanceof MonsterCard) return -1;
                return 0;
            }

            @Override
            public boolean equals(Object obj) {
                return false;
            }
        });
        for (int i = 0; i < getHand().size(); i++) {
            if (getHand().get(i) instanceof TrapAndSpellCard) {
                game.setSelectedCardHand(getHand().get(i));
                break;
            } else {
                if (((MonsterCard) getHand().get(i)).getCardLevel() < 5) {
                    game.setSelectedCardHand(getHand().get(i));
                    break;
                } else if (isSuitableForTribute((MonsterCard) getHand().get(i))) {
                    game.setSelectedCardHand(getHand().get(i));
                    break;
                }
            }
        }
    }

    public boolean isSuitableForTribute(MonsterCard card) {
        if (card.getCardLevel() < 7) {
            return (getBoard().cardsInMonsterZone() >= 1);
        } else {
            return (getBoard().cardsInMonsterZone() >= 2);
        }
    }

    public int selectOpponentMonster(Game game) {
        return game.getOpposition().getBoard().getMinimumAttackPosition();
    }

    public void selectAttackMonster(Game game) {
        game.setSelectedPosition(getBoard().getMaximumPuver());
    }

    public boolean isPotOfGreedExistsInHand() {
        for (int i = 0; i < getHand().size(); i++) {
            if(getHand().get(i).getCardName().equals("Pot of Greed")){
                return true;
            }
        }
        return false;
    }

    public boolean isRaigekiExistsInHand() {
        for (int i = 0; i < getHand().size(); i++) {
            if(getHand().get(i).getCardName().equals("Raigeki")){
                return true;
            }
        }
        return false;
    }

    public boolean isDarkHoleExistsInHand() {
        for (int i = 0; i < getHand().size(); i++) {
            if(getHand().get(i).getCardName().equals("Dark Hole")){
                return true;
            }
        }
        return false;
    }

    public void chooseAndSetSpellCard(Game game) {
        if (isPotOfGreedExistsInHand()) {
            for (int i = 0; i < getHand().size(); i++) {
                if (getHand().get(i).getCardName().equals("Pot of Greed")) {
                    game.setSelectedCardHand(getHand().get(i));
                    break;
                }
            }
            game.set();
        }
        else{
            System.out.println("nabood pot");
        }
        if (isRaigekiExistsInHand()) {
            for (int i = 0; i < getHand().size(); i++) {
                if (getHand().get(i).getCardName().equals("Raigeki")) {
                    game.setSelectedCardHand(getHand().get(i));
                    break;
                }
            }
            game.set();
        }
        else{
            System.out.println("raigeki nbood");
        }
        if (isDarkHoleExistsInHand()) {
            for (int i = 0; i < getHand().size(); i++) {
                if (getHand().get(i).getCardName().equals("Dark Hole")) {
                    game.setSelectedCardHand(getHand().get(i));
                    break;
                }
            }
            game.set();
        }
        else{
            System.out.println("dark hole nabood");
        }
    }

//    public void activatePotOfGreed(Game game){
//        for (int i = 0; i < 5; i++) {
//            Position position = getBoard().getTrapAndSpellCards().get(i);
//            if(!position.getStatus().equals(StatusOfPosition.EMPTY)) {
//                if (position.getCard().getCardName().equals("Pot of Greed")) {
//                    game.setSelectedPosition(position);
//                    break;
//                }
//            }
//        }
//        if(game.getSelectedPosition() != null){
//            TrapAndSpellCard potOfGreed = ((TrapAndSpellCard) Objects.requireNonNull(Card.getCardByName("Pot of Greed")));
//            if(potOfGreed.getEffect().isSuitableForActivate(game)) {
//                potOfGreed.getEffect().activate(game);
//                game.sendToGraveyard(game.getSelectedPosition() , game.getTurnOfPlayer());
//            }
//        }
//        game.selectedPositionNulling();
//    }
//
//    public void activateRaigeki(Game game){
//        for (int i = 0; i < 5; i++) {
//            Position position = getBoard().getTrapAndSpellCards().get(i);
//            if(!position.getStatus().equals(StatusOfPosition.EMPTY)) {
//                if (position.getCard().getCardName().equals("Raigeki")) {
//                    game.setSelectedPosition(position);
//                    break;
//                }
//            }
//        }
//        if(game.getSelectedPosition() != null){
//            TrapAndSpellCard raigeki = ((TrapAndSpellCard) Objects.requireNonNull(Card.getCardByName("Raigeki")));
//            if(raigeki.getEffect().isSuitableForActivate(game)) {
//                raigeki.getEffect().activate(game);
//                game.sendToGraveyard(game.getSelectedPosition() , game.getTurnOfPlayer());
//            }
//        }
//        game.selectedPositionNulling();
//    }
//
//    public void activateDarkHole(Game game){
//        for (int i = 0; i < 5; i++) {
//            Position position = getBoard().getTrapAndSpellCards().get(i);
//            if(!position.getStatus().equals(StatusOfPosition.EMPTY)) {
//                if (position.getCard().getCardName().equals("Dark Hole")) {
//                    game.setSelectedPosition(position);
//                    break;
//                }
//            }
//        }
//        if(game.getSelectedPosition() != null){
//            if(getBoard().cardsInMonsterZone() < game.getOpposition().getBoard().cardsInMonsterZone()){
//                ((TrapAndSpellCard) Objects.requireNonNull(Card.getCardByName("Dark Hole"))).getEffect().activate(game);
//                game.sendToGraveyard(game.getSelectedPosition() , game.getTurnOfPlayer());
//            }
//        }
//        game.selectedPositionNulling();
//    }
}
