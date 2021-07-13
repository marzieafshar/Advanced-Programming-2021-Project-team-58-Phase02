package Server.Controller;

import Client.View.Controller;
import Server.Model.Player;
import Server.Server;
import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfileController {

    private static HashMap<String, Player> allLoggedInPlayers = Server.getAllLoggedInPlayers();

    public static String changePassword(String str) {
        String[] info = str.split("#");
        String newPassword = info[0];
        String token = info[1];
        Player player = allLoggedInPlayers.get(token);

        if (player.getPassword().equals(newPassword)) {
            return "same password";
        } else {
            player.setPassword(newPassword);
            return "password changed";
        }
    }

    public static String changeNickname(String str) {
        String[] info = str.split("#");
        String newNickname = info[0];
        String token = info[1];
        if (Player.isNicknameExists(newNickname)) {
            return "nickname already exists";
        } else {
            allLoggedInPlayers.get(token).setNickname(newNickname);
            return "nickname changed";
        }
    }

    public static void changeProfile(Image image) {
//        String[] info = str.split("#");
//        String imageJson = info[1];
//        String token = info[0];

//        Image image = new YaGson().fromJson(imageJson, new TypeToken<Image>() {
//        }.getType());
        allLoggedInPlayers.get(Controller.getToken()).setImage(image);


    }

}
