package Client.View;

import Client.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.*;

public class ChatRoomController implements Initializable {

    @FXML
    private GridPane gridMessages;

    @FXML
    private TextField clientMessageTextField;

    @FXML
    private ScrollPane messagesScrollPane;

    @FXML
    private GridPane gridPane;

    private ArrayList<String> messages = new ArrayList<>();

    private Parent root;
    private Stage stage;
    private Scene scene;

    public static ChatRoomController chatRoomController;
    private static boolean isInChatRoom = false;

    private static String message;
    private Thread thread;

//    String str = "Button_Click.mp3";
//    private MediaPlayer mediaPlayer;
    private static boolean isFromLobby;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isInChatRoom = true;
        chatRoomController = this;
        makeServerInputThread();
        loadMessages();
        loadOnlineUsers();
    }

    private void loadOnlineUsers() {
        try {
            Controller.getDataOutputStream().writeUTF("Chat get all nicknames"+Controller.getToken());
            Controller.getDataOutputStream().flush();

            String[] tokens = Controller.getDataInputStream().readUTF().split("#");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMessages() {
        messages.clear();
        try {
            Controller.getDataOutputStream().writeUTF("Chat get all messages");
            Controller.getDataOutputStream().flush();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String result = message;
            if (!result.equals("")) {
                String[] str = result.split("#");
                messages.addAll(Arrays.asList(str));
                showMessages();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showMessages() {
        gridMessages.getChildren().clear();
        int row = 1;
        for (int i = 0; i < messages.size(); i++) {
            try {
                String[] tmp = messages.get(i).split("@");
                String senderName = tmp[0];
                String messageText = tmp[1];
                FXMLLoader fxmlLoader = new FXMLLoader();

                if (senderName.equals(Controller.getUsername())) {
                    fxmlLoader.setLocation(getClass().getResource("/Fxmls/userMessages.fxml"));
                } else {
                    fxmlLoader.setLocation(getClass().getResource("/Fxmls/Message.fxml"));
                }
                AnchorPane hBox = fxmlLoader.load();

                MessageController messageController = fxmlLoader.getController();
                messageController.setMessage(senderName, messageText);

                gridMessages.add(hBox, 0, row);
                row++;
                GridPane.setMargin(hBox, new Insets(5));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        messagesScrollPane.vvalueProperty().bind(gridMessages.heightProperty());
    }

    public void sendMessage(MouseEvent event) {
        String message = clientMessageTextField.getText();
        if (message.equals("")) {

        } else if (message.contains("#") || message.contains("@")) {

        } else {
            try {
                Controller.getDataOutputStream().writeUTF("Chat new message"
                        + message + "#" + Controller.getToken());
                Controller.getDataOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        loadMessages();
        clientMessageTextField.setText("");
    }

    public static boolean isIsInChatRoom() {
        return isInChatRoom;
    }

    public void backToMainMenu(ActionEvent event) throws IOException {
        isInChatRoom = false;
//        Media media = new Media(new File(str).toURI().toString());
//        mediaPlayer = new MediaPlayer(media);
//        mediaPlayer.setAutoPlay(true);
        isInChatRoom = false;
        Controller.getDataOutputStream().writeUTF("Chat tamoomesh kon");
        Controller.getDataOutputStream().flush();
        if (isFromLobby)
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/Lobby.fxml")));
        else
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/MainMenu.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        isFromLobby = false;
    }

    public static ChatRoomController getChatRoomController() {
        return chatRoomController;
    }

    public void makeServerInputThread() {
        Thread thread = new Thread(() -> {
            try {
                while (isIsInChatRoom()) {
                    message = Controller.getDataInputStream().readUTF();
                    if (message.startsWith("Server load message")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                loadMessages();
                            }
                        });
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        this.thread = thread;
        thread.start();
    }

    public static void setIsFromLobby(boolean value) {
        isFromLobby = value;
    }
}
