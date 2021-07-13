package Server;

import Client.View.Controller;
import Client.View.JsonSaveAndLoad;
import Server.Controller.LoginController;
import Server.Controller.ProfileController;
import Server.Controller.RegisterController;
import Server.Model.MonsterCard;
import Server.Model.Player;
import Server.Model.TrapAndSpellCard;
import javafx.scene.image.Image;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    private static DataOutputStream dataOutputStream;
    private static DataInputStream dataInputStream;

    private static HashMap<String, Player> allLoggedInPlayers = new HashMap<>();

    public static void main(String[] args) {
        MonsterCard.addMonster();
        TrapAndSpellCard.addTrapAndSpell();
        File file = new File("Players.txt");
        if (file.length() != 0) {
            try {
                JsonSaveAndLoad.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            while (true) {
                Socket socket = serverSocket.accept();
                makeNewThread(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, Player> getAllLoggedInPlayers() {
        return allLoggedInPlayers;
    }

    private static void makeNewThread(Socket socket) {
        new Thread(() -> {
            try {
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                ObjectInputStream dataInputStream1 = new ObjectInputStream(socket.getInputStream());
                processInput(dataInputStream, dataOutputStream , dataInputStream1);
                dataInputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void processInput(DataInputStream dataInputStream, DataOutputStream dataOutputStream , ObjectInputStream dataInputStream1) {
        while (true) {
            try {
                String message = dataInputStream.readUTF();
                Image image = null;
                if(dataInputStream1.readObject() instanceof Image)
                    image = (Image) dataInputStream1.readObject();
                if (message.startsWith("Register")) {
                    dataOutputStream.writeUTF(RegisterController.register(message.substring(8)));
                } else if (message.startsWith("Login")) {
                    dataOutputStream.writeUTF(LoginController.login(message.substring(5)));
                    System.out.println(allLoggedInPlayers);
                } else if (message.startsWith("logout")) {
                    dataOutputStream.writeUTF(LoginController.logout(message.substring(6)));
                } else if (message.startsWith("Change Nickname")) {
                    dataOutputStream.writeUTF(ProfileController.changeNickname(message.substring(15)));
                } else if (message.startsWith("Change Password")) {
                    dataOutputStream.writeUTF(ProfileController.changePassword(message.substring(15)));
                } else if (image != null) {
                    allLoggedInPlayers.get(Controller.getToken()).setImage(image);
//                    String tmp = message.substring(14);
//                    ProfileController.changeProfile(image);
//                    String[] info = tmp.split("#");
//                    String imageJson = info[1];
//                    String token = info[0];
//                    System.out.println(allLoggedInPlayers.get(token).getImage());
                } else if (message.equals("end")) {
                    break;
                }
                dataOutputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}



