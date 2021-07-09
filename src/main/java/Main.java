import Controller.JsonSaveAndLoad;
import Model.MonsterCard;
import Model.TrapAndSpellCard;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.media.*;
import org.apache.commons.codec.language.Soundex;

import java.io.File;
import java.io.IOException;

public class Main extends Application {
    private MediaPlayer mediaPlayer;

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

        String str = "05. Grabbing the Hatchet.mp3";
        Media media = new Media(new File(str).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.5);
        mediaPlayer.setAutoPlay(true);


        Parent root = FXMLLoader.load(getClass().getResource("/Fxmls/WelcomeMenu.fxml"));
        primaryStage.setTitle("Yu-Gi-Oh");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
