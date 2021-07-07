package Controller;

import com.sun.javafx.iio.gif.GIFImageLoader2;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
//import sun.jvm.hotspot.runtime.Threads;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class GameController {
    @FXML
    private ImageView imageView;
    @FXML
    private AnchorPane anchorPane;

    Stage stage;
    int index = 0;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        for (int i = 0; i < 1000000000; i++) {
//            String str = "/Images/Characters/Chara001.dds" + i % 35 + ".png";
//            Image image = new Image(getClass().getResourceAsStream(str));
//            imageView.setImage(image);

//            if (i % 100000000 == 0) {
//                System.out.println(i/100000000);
//                String str = "/Images/Characters/Chara001.dds" + i/100000000 + ".png";
//                Image image = new Image(getClass().getResourceAsStream(str));
//                imageView.setImage(image);
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }


//        Timer myTimer = new Timer();
//        myTimer.schedule(new TimerTask(){
//
//            @Override
//            public void run() {
//               loadNextScene();
//            }
//        }, 10000);
//        loadNextScene();
//    }

    private void loadNextScene() {
        AnchorPane pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource("/Fxmls/DeckMenu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        anchorPane.getChildren().setAll(pane);
    }

    public void loadImages(ActionEvent event) {
        String str = "/Images/Characters/Chara001.dds" + index + ".png";
        Image image = new Image(getClass().getResourceAsStream(str));
        imageView.setImage(image);
        index++;
    }
}