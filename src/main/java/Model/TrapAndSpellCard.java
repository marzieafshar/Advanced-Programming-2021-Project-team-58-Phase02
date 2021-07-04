package Model;


import java.util.ArrayList;

public class TrapAndSpellCard extends Card {

    private TrapOrSpellTypes cardType;
    private TrapOrSpellIcons cardIcon;
    private TrapAndSpellStatus cardStatus;
//    private Effect effect;
    private static ArrayList<TrapAndSpellCard> allSpellOrTrapCards = new ArrayList<TrapAndSpellCard>();

    public TrapAndSpellCard(String cardName, TrapOrSpellTypes cardType, TrapOrSpellIcons cardIcon, String cardDescription, TrapAndSpellStatus status, int price, String imgSrc) {
        super(cardName, cardDescription, price, imgSrc);
        setCardType(cardType);
        setCardIcon(cardIcon);
        setCardStatus(status);
        allCards.add(this);
    }
//
//    public void setEffect(Effect effect) {
//        this.effect = effect;
//    }
//
//    public Effect getEffect() {
//        return effect;
//    }

//    public static void setEffects() {
//        ((TrapAndSpellCard) Objects.requireNonNull(Card.getCardByName("Dark Hole"))).setEffect(new DarkHole());
//        ((TrapAndSpellCard) Objects.requireNonNull(Card.getCardByName("Harpie's Feather Duster"))).setEffect(new HarpieFeatherDuster());
//        ((TrapAndSpellCard) Objects.requireNonNull(Card.getCardByName("Monster Reborn"))).setEffect(new MonsterReborn());
//        ((TrapAndSpellCard) Objects.requireNonNull(Card.getCardByName("Pot of Greed"))).setEffect(new PotOfGreed());
//        ((TrapAndSpellCard) Objects.requireNonNull(Card.getCardByName("Raigeki"))).setEffect(new Raigeki());
//        ((TrapAndSpellCard) Objects.requireNonNull(Card.getCardByName("Twin Twisters"))).setEffect(new TwinTwisters());
//    }

    public void setCardStatus(TrapAndSpellStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    public TrapOrSpellTypes getTrapOrSpellTypes() {
        return cardType;
    }

    public void setCardType(TrapOrSpellTypes cardType) {
        this.cardType = cardType;
    }

    public void setCardIcon(TrapOrSpellIcons cardIcon) {
        this.cardIcon = cardIcon;
    }

    public void showCard() {
        if (this.getTrapOrSpellTypes() == TrapOrSpellTypes.SPELL_CARD) {
            System.out.println("Name: " + super.getCardName());
            System.out.println("Spell");
            System.out.println("Type: " + this.getTrapOrSpellTypes());
            System.out.println("Description: " + super.getCardDescription());
        } else if (this.getTrapOrSpellTypes() == TrapOrSpellTypes.TRAP_CARD) {
            System.out.println("Name: " + super.getCardName());
            System.out.println("Trap");
            System.out.println("Type: " + this.getTrapOrSpellTypes());
            System.out.println("Description: " + super.getCardDescription());
        }
    }

    public static void addTrapAndSpell() {
        allSpellOrTrapCards.add(new TrapAndSpellCard("Trap Hole", TrapOrSpellTypes.TRAP_CARD, TrapOrSpellIcons.NORMAL,
                "When your opponent Normal or Flip Summons 1 monster with 1000 or more ATK: Target that " +
                        "monster; destroy that target.", TrapAndSpellStatus.UNLIMITED, 2000 , "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("Mirror Force", TrapOrSpellTypes.TRAP_CARD, TrapOrSpellIcons.NORMAL,
                "When an opponent's monster declares an attack: Destroy all your opponent's Attack Position monsters.",
                TrapAndSpellStatus.UNLIMITED, 2000, "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("Magic Cylinder", TrapOrSpellTypes.TRAP_CARD, TrapOrSpellIcons.NORMAL,
                "When an opponent's monster declares an attack: Target the attacking monster; negate the" +
                        " attack, and if you do, inflict damage to your opponent equal to its ATK.", TrapAndSpellStatus.UNLIMITED, 2000, "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("Mind Crush", TrapOrSpellTypes.TRAP_CARD, TrapOrSpellIcons.NORMAL,
                "Declare 1 card name; if that card is in your opponent's hand, they must discard all copies" +
                        " of it, otherwise you discard 1 random card.", TrapAndSpellStatus.UNLIMITED, 2000, "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("Torrential Tribute", TrapOrSpellTypes.TRAP_CARD, TrapOrSpellIcons.NORMAL,
                "When a monster(s) is Summoned: Destroy all monsters on the field.", TrapAndSpellStatus.UNLIMITED, 2000, "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("Time Seal", TrapOrSpellTypes.TRAP_CARD, TrapOrSpellIcons.NORMAL,
                "Skip the Draw Phase of your opponent's next turn.", TrapAndSpellStatus.LIMITED, 2000, "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("Negate Attack", TrapOrSpellTypes.TRAP_CARD, TrapOrSpellIcons.COUNTER,
                "When an opponent's monster declares an attack: Target the attacking monster; negate the " +
                        "attack, then end the Battle Phase.", TrapAndSpellStatus.UNLIMITED, 3000, "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("Solemn Warning", TrapOrSpellTypes.TRAP_CARD, TrapOrSpellIcons.COUNTER,
                "When a monster(s) would be Summoned, OR when a Spell/Trap Card, or monster effect, is" +
                        " activated that includes an effect that Special Summons a monster(s): Pay 2000 LP; negate the " +
                        "Summon or activation, and if you do, destroy it.", TrapAndSpellStatus.UNLIMITED, 3000, "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("Magic Jamamer", TrapOrSpellTypes.TRAP_CARD, TrapOrSpellIcons.COUNTER,
                "When a Spell Card is activated: Discard 1 card; negate the activation, and if you do, destroy it.",
                TrapAndSpellStatus.UNLIMITED, 3000, "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("Call of The Haunted", TrapOrSpellTypes.TRAP_CARD, TrapOrSpellIcons.CONTINUOUS,
                "Activate this card by targeting 1 monster in your GY; Special Summon that target in " +
                        "Attack Position. When this card leaves the field, destroy that monster. When that monster is " +
                        "destroyed, destroy this card.", TrapAndSpellStatus.UNLIMITED, 3500, "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("Vanity's Emptiness", TrapOrSpellTypes.TRAP_CARD, TrapOrSpellIcons.CONTINUOUS,
                "Neither player can Special Summon monsters. If a card is sent from the Deck or the field " +
                        "to your Graveyard: Destroy this card.", TrapAndSpellStatus.LIMITED, 3500, "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("Wall of Revealing Light", TrapOrSpellTypes.TRAP_CARD, TrapOrSpellIcons.CONTINUOUS,
                "Activate by paying any multiple of 1000 Life Points. Monsters your opponent controls " +
                        "cannot attack if their ATK is less than or equal to the amount you paid.", TrapAndSpellStatus.LIMITED, 3500, "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("Monster Reborn", TrapOrSpellTypes.SPELL_CARD, TrapOrSpellIcons.NORMAL,
                "Target 1 monster in either GY; Special Summon it.", TrapAndSpellStatus.LIMITED, 2500, "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("Terraforming", TrapOrSpellTypes.SPELL_CARD, TrapOrSpellIcons.NORMAL,
                "Add 1 Field Spell from your Deck to your hand.", TrapAndSpellStatus.LIMITED, 2500, "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("Pot of Greed", TrapOrSpellTypes.SPELL_CARD, TrapOrSpellIcons.NORMAL,
                "Draw 2 cards.", TrapAndSpellStatus.LIMITED, 2500, "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("Raigeki", TrapOrSpellTypes.SPELL_CARD, TrapOrSpellIcons.NORMAL,
                "Destroy all monsters your opponent controls.", TrapAndSpellStatus.LIMITED, 2500, "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("Change of Heart", TrapOrSpellTypes.SPELL_CARD, TrapOrSpellIcons.NORMAL,
                "Target 1 monster your opponent controls; take control of it until the End Phase.", TrapAndSpellStatus.LIMITED, 2500, "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("Swords of Revealing Light", TrapOrSpellTypes.SPELL_CARD,
                TrapOrSpellIcons.NORMAL, "After this card's activation, it remains on the field, but " +
                "destroy it during the End Phase of your opponent's 3rd turn. When this card is activated: If your " +
                "opponent controls a face-down monster, flip all monsters they control face-up. While this card is " +
                "face-up on the field, your opponent's monsters cannot declare an attack.", TrapAndSpellStatus.UNLIMITED, 2500, "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("Harpie's Feather Duster", TrapOrSpellTypes.SPELL_CARD, TrapOrSpellIcons.NORMAL,
                "Destroy all Spells and Traps your opponent controls.", TrapAndSpellStatus.LIMITED, 2500, "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("Dark Hole", TrapOrSpellTypes.SPELL_CARD, TrapOrSpellIcons.NORMAL,
                "Destroy all monsters on the field.", TrapAndSpellStatus.UNLIMITED, 2500, "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("Supply Squad", TrapOrSpellTypes.SPELL_CARD, TrapOrSpellIcons.CONTINUOUS,
                "Once per turn, if a monster(s) you control is destroyed by battle or card effect: Draw " +
                        "1 card.", TrapAndSpellStatus.UNLIMITED, 4000, "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("Spell Absorption", TrapOrSpellTypes.SPELL_CARD, TrapOrSpellIcons.CONTINUOUS,
                "Each time a Spell Card is activated, gain 500 Life Points immediately after it " +
                        "resolves.", TrapAndSpellStatus.UNLIMITED, 4000, "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("Messenger of Peace", TrapOrSpellTypes.SPELL_CARD, TrapOrSpellIcons.CONTINUOUS,
                "Monsters with 1500 or more ATK cannot declare an attack. Once per turn, during your " +
                        "Standby Phase, pay 100 LP or destroy this card.", TrapAndSpellStatus.UNLIMITED, 4000, "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("Twin Twisters", TrapOrSpellTypes.SPELL_CARD, TrapOrSpellIcons.QUICK_PLAY,
                "Discard 1 card, then target up to 2 Spells/Traps on the field; destroy them.", TrapAndSpellStatus.UNLIMITED, 3500, "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("Mystical Space Typhoon", TrapOrSpellTypes.SPELL_CARD, TrapOrSpellIcons.QUICK_PLAY,
                "Target 1 Spell/Trap on the field; destroy that target.", TrapAndSpellStatus.UNLIMITED, 3500, "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("Ring of Defense", TrapOrSpellTypes.SPELL_CARD, TrapOrSpellIcons.QUICK_PLAY,
                "When a Trap effect that inflicts damage is activated: Make that effect damage 0.", TrapAndSpellStatus.UNLIMITED, 3500, "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("Yami", TrapOrSpellTypes.SPELL_CARD, TrapOrSpellIcons.FIELD,
                "All Fiend and Spellcaster monsters on the field gain 200 ATK/DEF, also all Fairy monsters" +
                        " on the field lose 200 ATK/DEF.", TrapAndSpellStatus.UNLIMITED, 4300, "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("Forest", TrapOrSpellTypes.SPELL_CARD, TrapOrSpellIcons.FIELD,
                "All Insect, Beast, Plant, and Beast-Warrior monsters on the field gain 200 ATK/DEF.",
                TrapAndSpellStatus.UNLIMITED, 4300, "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("Closed Forest", TrapOrSpellTypes.SPELL_CARD, TrapOrSpellIcons.FIELD,
                "All Beast-Type monsters you control gain 100 ATK for each monster in your Graveyard. " +
                        "Field Spell Cards cannot be activated. Field Spell Cards cannot be activated during the " +
                        "turn this card is destroyed.", TrapAndSpellStatus.UNLIMITED, 4300, "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("Umiiruka", TrapOrSpellTypes.SPELL_CARD, TrapOrSpellIcons.FIELD,
                "Increase the ATK of all WATER monsters by 500 points and decrease their DEF by 400 points.",
                TrapAndSpellStatus.UNLIMITED, 4300, "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("Sword of Dark Destruction", TrapOrSpellTypes.SPELL_CARD, TrapOrSpellIcons.EQUIP,
                "A DARK monster equipped with this card increases its ATK by 400 points and decreases " +
                        "its DEF by 200 points.", TrapAndSpellStatus.UNLIMITED, 4300, "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("Black Pendant", TrapOrSpellTypes.SPELL_CARD, TrapOrSpellIcons.EQUIP,
                "The equipped monster gains 500 ATK. When this card is sent from the field to the " +
                        "Graveyard: Inflict 500 damage to your opponent.", TrapAndSpellStatus.UNLIMITED, 4300, "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("United We Stand", TrapOrSpellTypes.SPELL_CARD, TrapOrSpellIcons.EQUIP,
                "The equipped monster gains 800 ATK/DEF for each face-up monster you control.", TrapAndSpellStatus.UNLIMITED, 4300, "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("Magnum Shield", TrapOrSpellTypes.SPELL_CARD, TrapOrSpellIcons.EQUIP,
                "Equip", TrapAndSpellStatus.UNLIMITED, 4300, "Images/mcr.jpg"));
        allSpellOrTrapCards.add(new TrapAndSpellCard("Advanced Ritual Art", TrapOrSpellTypes.SPELL_CARD, TrapOrSpellIcons.RITUAL,
                "This card can be used to Ritual Summon any 1 Ritual Monster. You must also send Normal " +
                        "Monsters from your Deck to the Graveyard whose total Levels equal the Level of that Ritual " +
                        "Monster.", TrapAndSpellStatus.UNLIMITED, 3000, "Images/mcr.jpg"));
    }
}