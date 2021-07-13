package Server.Controller;

import Client.View.JsonSaveAndLoad;
import Server.Model.Player;

import java.io.IOException;

public class RegisterController {

    public static synchronized String register(String str) {
        String[] info = str.split("#");
        String username = info[0];
        String nickname = info[1];
        String password = info[2];

        if (Player.getPlayerByUsername(username) != null) {
            return "username already exists";
        } else if (Player.isNicknameExists(nickname)) {
            return "nickname already exists";
        } else {
            new Player(username, password, nickname);
            try {
                JsonSaveAndLoad.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "user created successfully";
        }
    }
}
