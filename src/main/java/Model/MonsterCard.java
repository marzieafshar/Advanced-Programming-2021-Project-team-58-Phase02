package Model;

import java.util.ArrayList;

public class MonsterCard extends Card {

    private String monsterType;
    private int cardLevel;
    private Attribute cardAttribute;
    private int attack;
    private int defense;
    private Effects effect;
    private String cardTypeOfMonsters;
    private static ArrayList<MonsterCard> allMonsterCards = new ArrayList<MonsterCard>();

    public MonsterCard(String cardName, String cardTypeOfMonsters, String monsterType, int cardLevel,
                       String description, Attribute cardAttribute, int attack, int defense, int price, String imgSrc) {
        super(cardName, description, price, "Monster/" + imgSrc);
        allCards.add(this);
        setMonsterType(monsterType);
        setCardLevel(cardLevel);
        setCardAttribute(cardAttribute);
        setAttack(attack);
        setDefense(defense);
        setCardTypeOfMonsters(cardTypeOfMonsters);
    }

    public static ArrayList<MonsterCard> getAllMonsterCards() {
        return allMonsterCards;
    }

    public void setCardTypeOfMonsters(String cardTypeOfMonsters) {
        this.cardTypeOfMonsters = cardTypeOfMonsters;
    }

    public String getMonsterType() {
        return monsterType;
    }

    public void setMonsterType(String monsterType) {
        this.monsterType = monsterType;
    }

    public int getCardLevel() {
        return cardLevel;
    }

    public void setCardLevel(int cardLevel) {
        this.cardLevel = cardLevel;
    }

    public void setCardAttribute(Attribute cardAttribute) {
        this.cardAttribute = cardAttribute;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void showCard() {
        System.out.println("Name: " + this.getCardName());
        System.out.println("Level: " + this.getCardLevel());
        System.out.println("Type: " + this.getMonsterType());
        System.out.println("ATK: " + this.getAttack());
        System.out.println("DEF: " + this.getDefense());
        System.out.println("Description: " + this.getCardDescription());
    }

    public static void addMonster() {
        allMonsterCards.add(new MonsterCard("Battle OX", "Normal", "Beast-Warrior",
                4, "A monster with tremendous power,it destroys enemies with a swing of its axe.",
                Attribute.EARTH, 1700, 1000, 2900, "BattleOx.jpg"));
        allMonsterCards.add(new MonsterCard("Axe Raider", "Normal", "Warrior",
                4, "An axe-wielding monster of tremendous strength and agility",
                Attribute.EARTH, 1700, 1150, 3100, "AxeRaider.jpg"));
        allMonsterCards.add(new MonsterCard("Yomi Ship", "Effect", "Aqua", 3,
                "If this card is destroyed by battle and sent to GY:Destroy the monster that destroyed this card",
                Attribute.WATER, 800, 1400, 1700 , "YomiShip.jpg"));
        allMonsterCards.add(new MonsterCard("Horn Imp", "Normal", "Fiend", 4,
                "A small fiend that dwells in the dark,its single horn makes it a formidable opponent",
                Attribute.DARK, 1300, 1000, 2500, "HornImp.jpg"));
        allMonsterCards.add(new MonsterCard("Silver Fang", "Normal", "Beast", 3,
                "A snow wolf that's beautiful to the eye, but absolutely vicious in battle",
                Attribute.EARTH, 1200, 800, 1700, "SilverFang.jpg"));
        allMonsterCards.add(new MonsterCard("Suijin", "Effect", "Aqua", 7,
                "During damage calculation in your opponent's turn, if this card in being attacked:" +
                        " You can target the attacking monster;make that target's ATK 0 during damage", Attribute.WATER,
                2500, 2400, 8700, "Suijin.jpg"));
        allMonsterCards.add(new MonsterCard("Fireyarou", "Normal", "Pyro", 4,
                "A malevolent creature wrapped in flames that attacks enemies with intense fire.",
                Attribute.FIRE, 1300, 1000, 2500, "Fireyarou.jpg"));
        allMonsterCards.add(new MonsterCard("Curtain of the dark ones", "Normal", "Spellcaster",
                2, "A curtain that a spellcaster made,it is said to raise a dark power.",
                Attribute.DARK, 600, 500, 700, "CurtainOfTheDarkOnes.jpg"));
        allMonsterCards.add(new MonsterCard("Feral Imp", "Normal", "Fiend", 4,
                "A playful little fiend that lurks in the dark, waiting to attack an unwary enemy",
                Attribute.DARK, 1300, 1400, 2800, "FeralImp.jpg"));
        allMonsterCards.add(new MonsterCard("Dark magician", "Normal", "Spellcaster", 7,
                "the ultimate wizard in terms of attack and defense",
                Attribute.DARK, 2500, 2100, 8300, "DarkMagician.jpg"));
        allMonsterCards.add(new MonsterCard("Wattkid", "Normal", "Thunder", 3,
                "A creature that electrocutes opponents with bolts of lightning.",
                Attribute.LIGHT, 1000, 500, 1300, "Wattkid.jpg"));
        allMonsterCards.add(new MonsterCard("Baby dragon", "Normal", "Dragon", 3,
                "Much more than just a child, this dragon is gifted with untrapped power.",
                Attribute.WIND, 1200, 700, 1600, "BabyDragon.jpg"));
        allMonsterCards.add(new MonsterCard("Hero of the east", "Normal", "Warrior", 3,
                "Feel da strength ah dis sword-swinging samurai from da Far East.",
                Attribute.EARTH, 110, 1000, 1700, "HeroOfTheEast.jpg"));
        allMonsterCards.add(new MonsterCard("Battle warrior", "Normal", "Warrior", 3,
                "A warrior that flights with bare hands!!!", Attribute.EARTH, 700, 1000, 1300, "BattleWarrior.jpg"));
        allMonsterCards.add(new MonsterCard("Crawling dragon", "Normal", "Dragon", 5,
                "This weakened dragon can no longer fly, but is still a deadly force to be reckoned with.",
                Attribute.EARTH, 1600, 1400, 3900, "CrawlingDragon.jpg"));
        allMonsterCards.add(new MonsterCard("Flame manipulator", "Normal", "Spellcaster",
                3, "This Spellcaster attacks enemies with fire-related spells such" +
                " as \"Sea of Flames\" and \"Wall of Fire\".",
                Attribute.FIRE, 900, 1000, 1500, "FlameManipulator.jpg"));
        allMonsterCards.add(new MonsterCard("Blue-Eyes white dragon", "Normal", "Dragon",
                8, "This legendary dragon is a powerful engine of destruction. Virtually invincible," +
                " very few have faced this awesome creature and lived to tell the tale.",
                Attribute.LIGHT, 3000, 2500, 11300, "BlueEyesWhiteDragon.jpg"));
        allMonsterCards.add(new MonsterCard("Crab Turtle", "Ritual", "Aqua", 8,
                "This monster can only be Ritual Summoned with the Ritual Spell Card, \"Turtle Oath\". You must " +
                        "also offer monsters whose total Level Stars equal 8 or " +
                        "more as a Tribute from the field or your hand.", Attribute.WATER, 2550, 2500, 10200, "CrabTurtle.jpg"));
        allMonsterCards.add(new MonsterCard("Skull Guardian", "Ritual", "Warrior", 7,
                "This monster can only be Ritual Summoned with the Ritual Spell Card, \"Novox's Prayer\". You " +
                        "must also offer monsters whose total Level Stars equal 7 or more as a Tribute from the field or " +
                        "your hand.", Attribute.LIGHT, 2050, 2500, 7900, "SkullGuardian.jpg"));
        allMonsterCards.add(new MonsterCard("Slot Machine", "Normal", "Machine", 7,
                "The machine's ability is said to vary according to its slot results.",
                Attribute.DARK, 2000, 2300, 7500, "SlotMachine.jpg"));
        allMonsterCards.add(new MonsterCard("Haniwa", "Normal", "Rock", 2,
                "An earthen figure that protects the tomb of an ancient ruler.",
                Attribute.EARTH, 500, 500, 600, "Haniwa.jpg"));
        allMonsterCards.add(new MonsterCard("Man-Eater Bug", "Effect", "Insect", 2,
                "FLIP: Target 1 monster on the field; destroy that target.",
                Attribute.EARTH, 450, 600, 600, "ManEaterBug.jpg"));
        allMonsterCards.add(new MonsterCard("Gate Guardian", "Effect", "Warrior", 11,
                "Cannot be Normal Summoned/Set. Must first be Special Summoned (from your hand) by " +
                        "Tributing 1 \"Sanga of the Thunder\", \"Kazejin\", and \"Suijin\".",
                Attribute.DARK, 3750, 3400, 20000, "GateGuardian.jpg"));
        allMonsterCards.add(new MonsterCard("Scanner", "Effect", "Machine", 1,
                "Once per turn, you can select 1 of your opponent's monsters that is removed from play. Until " +
                        "the End Phase, this card's name is treated as the selected monster's name, and this card has " +
                        "the same Attribute, Level, ATK, and DEF as the selected monster. If this card is removed from " +
                        "the field while this effect is applied, remove it from play.",
                Attribute.LIGHT, 0, 0, 8000, "Scanner.jpg"));
        allMonsterCards.add(new MonsterCard("Bitron", "Normal", "Cyberse", 2,
                "A new species found in electronic space. There's not much information on it.",
                Attribute.EARTH, 200, 2000, 1000, "Bitron.jpg"));
        allMonsterCards.add(new MonsterCard("Marshmallon", "Effect", "Fairy", 3,
                "Cannot be destroyed by battle. After damage calculation, if this card was attacked, and was " +
                        "face-down at the start of the Damage Step: The attacking player takes 1000 damage.",
                Attribute.LIGHT, 300, 500, 700, "Marshmallon.jpg"));
        allMonsterCards.add(new MonsterCard("Beast King Barbaros", "Effect", "Beast-Warrior", 8,
                "You can Normal Summon/Set this card without Tributing, but its original ATK becomes 1900. You" +
                        " can Tribute 3 monsters to Tribute Summon (but not Set) this card. If Summoned this way: " +
                        "Destroy all cards your opponent controls.",
                Attribute.EARTH, 3000, 1200, 9200, "BeastKingBarbaros.jpg"));
        allMonsterCards.add(new MonsterCard("Texchanger", "Effect", "Cyberse", 1,
                "Once per turn, when your monster is targeted for an attack: You can negate that attack, then " +
                        "Special Summon 1 Cyberse Normal Monster from your hand, Deck, or GY.",
                Attribute.DARK, 100, 100, 200, "Texchanger.jpg"));
        allMonsterCards.add(new MonsterCard("Leotron", "Normal", "Cyberse", 4,
                "A territorial electronic monster that guards its own domain.",
                Attribute.EARTH, 2000, 0, 2500, "Leotron.jpg"));
        allMonsterCards.add(new MonsterCard("The Calculator", "Effect", "Thunder", 2,
                "The ATK of this card is the combined Levels of all face-up monsters you control x 300.",
                Attribute.LIGHT, 0, 0, 8000, "TheCalculator.jpg"));
        allMonsterCards.add(new MonsterCard("Alexandrite Dragon", "Normal", "Dragon", 4,
                "Many of the czars' lost jewels can be found in the scales of this priceless dragon. Its " +
                        "creator remains a mystery, along with how they acquired the imperial treasures. But whosoever " +
                        "finds this dragon has hit the jackpot... whether they know it or not.",
                Attribute.LIGHT, 2000, 100, 2600, "AlexandriteDragon.jpg"));
        allMonsterCards.add(new MonsterCard("Mirage Dragon", "Effect", "Dragon", 4,
                "Your opponent cannot activate Trap Cards during the Battle Phase.",
                Attribute.LIGHT, 1600, 600, 2500, "MirageDragon.jpg"));
        allMonsterCards.add(new MonsterCard("Herald of Creation", "Effect", "Spellcaster", 4,
                "Once per turn: You can discard 1 card, then target 1 Level 7 or higher monster in your " +
                        "Graveyard; add that target to your hand.",
                Attribute.LIGHT, 1800, 600, 2700, "HeraldOfCreation.jpg"));
        allMonsterCards.add(new MonsterCard("Exploder Dragon", "Effect", "Dragon", 3,
                "If this card is destroyed by battle and sent to the Graveyard: Destroy the monster that " +
                        "destroyed it. Neither player takes any battle damage from attacks involving this attacking card.",
                Attribute.EARTH, 1000, 0, 1000, "ExploderDragon.jpg"));
        allMonsterCards.add(new MonsterCard("Warrior Dai Grepher", "Normal", "Warrior", 4,
                "The warrior who can manipulate dragons. Nobody knows his mysterious past.",
                Attribute.EARTH, 1700, 1600, 3400, "WarriorDaiGrepher.jpg"));
        allMonsterCards.add(new MonsterCard("Dark Blade", "Normal", "Warrior", 4,
                "They say he is a dragon-manipulating warrior from the dark world. His attack is tremendous, " +
                        "using his great swords with vicious power.",
                Attribute.DARK, 1800, 1500, 3500, "DarkBlade.jpg"));
        allMonsterCards.add(new MonsterCard("Wattaildragon", "Normal", "Dragon", 6,
                "\"Capable of indefinite flight. Attacks by wrapping its body with electricity and ramming into opponents.\n" +
                        "IMPORTANT: Capturing the \"\"Wattaildragon\"\" is forbidden by the Ancient Rules and is a Level 6 " +
                        "offense, the minimum sentence for which is imprisonment for no less than 2500 heliocycles.\"",
                Attribute.LIGHT, 2500, 1000, 5800, "Wattaildragon.jpg"));
        allMonsterCards.add(new MonsterCard("Terratiger, the Empowered Warrior", "Effect", "Warrior", 4,
                "When this card is Normal Summoned: You can Special Summon 1 Level 4 or lower Normal Monster " +
                        "from your hand in Defense Position.", Attribute.EARTH, 1800, 1200, 3200, "Terratiger.jpg"));
        allMonsterCards.add(new MonsterCard("The Tricky", "Effect", "Spellcaster", 5,
                "You can Special Summon this card (from your hand) by discarding 1 card.",
                Attribute.WIND, 2000, 1200, 4300, "TheTricky.jpg"));
        allMonsterCards.add(new MonsterCard("Spiral Serpent", "Normal", "Sea Serpent", 8,
                "When huge whirlpools lay cities asunder, it is the hunger of this sea serpent at work. No one" +
                        " has ever escaped its dreaded Spiral Wave to accurately describe the terror they experienced.",
                Attribute.WATER, 2900, 2900, 11700, "SpiralSerpent.jpg"));
        allMonsterCards.add(new MonsterCard("Command Knight", "Effect", "Warrior", 4,
                "All Warrior-Type monsters you control gain 400 ATK. If you control another monster, monsters " +
                        "your opponent controls cannot target this card for an attack.",
                Attribute.FIRE, 1000, 1000, 2100, "CommandKnight.jpg"));
    }
}