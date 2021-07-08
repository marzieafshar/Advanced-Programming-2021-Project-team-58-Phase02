import Controller.JsonSaveAndLoad;
import Model.MonsterCard;
import Model.TrapAndSpellCard;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        MonsterCard.addMonster();
        TrapAndSpellCard.addTrapAndSpell();
        File file = new File("Players.txt");
        if(file.length() != 0) {
            try {
                JsonSaveAndLoad.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Parent root = FXMLLoader.load(getClass().getResource("/Fxmls/WelcomeMenu.fxml"));
        primaryStage.setTitle("Yu-Gi-Oh");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
