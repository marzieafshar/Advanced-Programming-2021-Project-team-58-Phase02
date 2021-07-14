package Client.View;

import Client.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CardCreatorController implements Initializable {
    @FXML
    private TextField spellCardName;
    String spellCardNameStr;

    @FXML
    private ComboBox<String> spellComboBox;

    @FXML
    private TextArea spellTextArea;
    String spellDescription;

    @FXML
    private TextField trapCardName;
    String trapCardNameStr;

    @FXML
    private ComboBox<String> trapComboBox;

    @FXML
    private TextArea trapTextArea;
    String trapDescription;

    @FXML
    private TextField monsterCardName;
    String monsterCardNameStr;

    @FXML
    private TextField monsterATK;
    String monsterAttack;

    @FXML
    private TextField monsterDEF;
    String monsterDefense;

    @FXML
    private ComboBox<String> monsterComboBox;

    @FXML
    private TextArea monsterTextArea;
    String monsterDescription;

    @FXML
    private Label monsterLabel;

    @FXML
    private Label trapLabel;

    @FXML
    private Label spellLabel;

    @FXML
    private ComboBox<String> monsterTypeComboBox;

    String spellEffect;
    String monsterLevel;
    String trapEffect;
    String monsterType;

    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> availableLevels = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8");
        ObservableList<String> availableEffects = FXCollections.observableArrayList("Harpie's Feather Duster", "Monster Reborn",
                "Pot of Greed", "Raigeki", "Twin Twisters");
        ObservableList<String> availableMonsterTypes = FXCollections.observableArrayList("Aqua", "Beast-Warrior",
                "Dragon", "Fiend", "Insect", "Pyro", "Spellcaster", "Thunder", "Warrior");
        spellComboBox.setItems(availableEffects);
        spellComboBox.setOnAction(this::spellAction);
        monsterComboBox.setItems(availableLevels);
        monsterComboBox.setOnAction(this::monsterLevelAction);
        trapComboBox.setItems(availableEffects);
        trapComboBox.setOnAction(this::trapAction);
        monsterTypeComboBox.setItems(availableMonsterTypes);
        monsterTypeComboBox.setOnAction(this::monsterTypeAction);
    }

    public void spellAction(ActionEvent event) {
        setSpellEffect(spellComboBox.getValue());
    }

    public void monsterLevelAction(ActionEvent event) {
        setMonsterLevel(monsterComboBox.getValue());
    }

    public void monsterTypeAction(ActionEvent event) {
        setMonsterType(monsterTypeComboBox.getValue());
    }

    public void trapAction(ActionEvent event) {
        setTrapEffect(trapComboBox.getValue());
    }

    public void setSpellEffect(String spellEffect) {
        this.spellEffect = spellEffect;
    }

    public void setMonsterLevel(String monsterLevel) {
        this.monsterLevel = monsterLevel;
    }

    public void setTrapEffect(String trapEffect) {
        this.trapEffect = trapEffect;
    }

    private void setMonsterType(String monsterType) {
        this.monsterType = monsterType;
    }

    @FXML
    void submitMonster(ActionEvent event) {
        monsterCardNameStr = monsterCardName.getText();
        monsterDescription = monsterTextArea.getText();
        monsterAttack = monsterATK.getText();
        monsterDefense = monsterDEF.getText();
        monsterLabel.setTextFill(Color.RED);
        if (monsterCardNameStr.equals(""))
            monsterLabel.setText("Please enter a name");
        else if (monsterAttack.equals(""))
            monsterLabel.setText("Please enter an attack number");
        else if (monsterDefense.equals(""))
            monsterLabel.setText("Please enter a defense number");
        else if (monsterLevel == null)
            monsterLabel.setText("Please select a level");
        else if (monsterType == null)
            monsterLabel.setText("Please select a type");
        else if (monsterDescription.equals(""))
            monsterLabel.setText("Please enter some description");
        else {
            monsterLabel.setTextFill(Color.GREEN);
            int price = getMonsterPrice(Integer.parseInt(monsterAttack),
                    Integer.parseInt(monsterDefense), Integer.parseInt(monsterLevel));

            MonsterCard.getAllMonsterCards().add(new MonsterCard(monsterCardNameStr, "Normal", monsterType,
                    Integer.parseInt(monsterLevel), monsterDescription, Attribute.EARTH, Integer.parseInt(monsterAttack),
                    Integer.parseInt(monsterDefense), price, "Monster2.jpg"));
            Controller.getLoggedInPlayer().decreaseMoney(price / 10);
            monsterLabel.setText("Card created successfully");

        }
    }

    @FXML
    void submitSpell(ActionEvent event) {
        spellCardNameStr = spellCardName.getText();
        spellDescription = spellTextArea.getText();
        spellLabel.setTextFill(Color.RED);
        if (spellCardNameStr.equals(""))
            spellLabel.setText("Please enter a name");
        else if (spellEffect == null)
            spellLabel.setText("Please select an effect");
        else if (spellDescription.equals(""))
            spellLabel.setText("Please enter some description");
        else {
            spellLabel.setTextFill(Color.GREEN);
            int price = getTrapAndSpellPrice(spellEffect);
            TrapAndSpellCard.getAllSpellOrTrapCards().add(new TrapAndSpellCard(spellCardNameStr, TrapOrSpellTypes.SPELL_CARD,
                    TrapOrSpellIcons.NORMAL, spellDescription, TrapAndSpellStatus.UNLIMITED, price, "Spell.jpg"));
            Controller.getLoggedInPlayer().decreaseMoney(price / 10);
            spellLabel.setText("Card created successfully");
        }
    }

    @FXML
    void submitTrap(ActionEvent event) {
        trapCardNameStr = trapCardName.getText();
        trapDescription = trapTextArea.getText();
        trapLabel.setTextFill(Color.RED);
        if (trapCardNameStr.equals(""))
            trapLabel.setText("Please enter a name");
        else if (trapEffect == null)
            trapLabel.setText("Please select an effect");
        else if (trapDescription.equals(""))
            trapLabel.setText("Please enter some description");
        else {
            trapLabel.setTextFill(Color.GREEN);
            int price = getTrapAndSpellPrice(trapEffect);
            TrapAndSpellCard.getAllSpellOrTrapCards().add(new TrapAndSpellCard(trapCardNameStr, TrapOrSpellTypes.TRAP_CARD,
                    TrapOrSpellIcons.NORMAL, trapDescription, TrapAndSpellStatus.UNLIMITED, price, "Trap.jpg"));
            Controller.getLoggedInPlayer().decreaseMoney(price / 10);
            trapLabel.setText("Card created successfully");
        }
    }


    public int getMonsterPrice(int attack, int defense, int level) {
        return ((attack + defense) / 4) + level * 300;
    }

    public int getTrapAndSpellPrice(String effect) {
        if (effect.equals("Harpie's Feather Duster") || effect.equals("Pot of Greed") ||
                effect.equals("Twin Twisters"))
            return 3000;
        else return 2000;
    }

    public void backToMainMenu(ActionEvent event) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/MainMenu.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
}
