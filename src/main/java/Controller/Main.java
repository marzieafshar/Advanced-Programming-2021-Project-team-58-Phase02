package Controller;

import Model.MonsterCard;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        MonsterCard.addMonster();
        jsonSaveAndLoad.load();
//        TrapAndSpellCard.addTrapAndSpell();
        Parent root = FXMLLoader.load(getClass().getResource("/WelcomeMenu.fxml"));
        primaryStage.setTitle("Shop");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
