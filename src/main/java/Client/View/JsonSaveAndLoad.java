package Client.View;

import Server.Model.Player;
import com.gilecode.yagson.*;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class JsonSaveAndLoad {

    public static void save() throws IOException {
        FileWriter writer = new FileWriter("Players.txt");
        writer.write(new YaGson().toJson(Player.getAllPlayers()));
        writer.close();
    }

    public static void load() throws IOException {
        String str = new String(Files.readAllBytes(Paths.get("Players.txt")));
        ArrayList<Player> players = new YaGson().fromJson(str, new TypeToken<ArrayList<Player>>() {
        }.getType());
        Player.setAllPlayers(players);
    }
}
