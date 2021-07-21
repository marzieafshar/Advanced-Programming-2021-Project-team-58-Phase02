package Client.View;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
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

    @FXML
    private ImageView pinnedMessagePic;

    @FXML
    private Text pinnedMessageText;

    @FXML
    private ImageView deleteIcon;

    @FXML
    private ImageView pinMessage;

    @FXML
    private ImageView editIcon;

    @FXML
    private Label onlinePlayersNumber;

    private ArrayList<String> messages = new ArrayList<>();

    private Parent root;
    private Stage stage;
    private Scene scene;

    public static ChatRoomController chatRoomController;
    private static boolean isInChatRoom = false;
    private ArrayList<String> onlineTokens = new ArrayList<>();

    private static String message = "";
    private Thread thread;

    private MessageController selectedMessage;

    String str = "Button_Click.mp3";
    private MediaPlayer mediaPlayer;
    private static boolean isFromLobby;

    public static String getMessage() {
        return message;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        message = "";
        messages.clear();
        isInChatRoom = true;
        chatRoomController = this;
        loadPinnedMessage();
        setVisibilityMessageIcons(false);
        loadOnlineUsers();
        onlinePlayersNumber.setText(String.valueOf(onlineTokens.size() + 1));
        makeServerInputThread();
        loadMessages();
    }

    private synchronized void loadOnlineUsers() {
        try {
            Controller.getDataOutputStream().writeUTF("Chat get all tokens" + Controller.getToken());
            Controller.getDataOutputStream().flush();

            String[] tokens = Controller.getDataInputStream().readUTF().split("#");
            if (!tokens[0].equals("")) {
                onlineTokens.addAll(Arrays.asList(tokens));
            }
            showOnlinePlayers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showOnlinePlayers() {
        gridPane.getChildren().clear();
        int row = 0;
        for (String onlineToken : onlineTokens) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Fxmls/onlinePlayers.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                String nickname = ProfileController.getPlayerInfo("nickname", onlineToken);
                Image image = ProfileController.getImage(onlineToken);
                OnlinePlayersController controller = fxmlLoader.getController();
                controller.setItem(image, nickname);

                gridPane.add(anchorPane, 0, row);
                row++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void loadMessages() {
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
                for (int i = 0; i < str.length; i++) {
                    if (!str[i].startsWith("Server load message"))
                        messages.add(str[i]);
                }
                showMessages();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showMessages() {
        gridMessages.getChildren().clear();
        int row = 1;
        MyListener myListener = new MyListener() {
            @Override
            public void onClickListener(Object object) {
                setSelectedMessage((MessageController) object);
            }
        };
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
                messageController.setMessage(senderName, messageText, i, myListener);

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
        messages.clear();
        message = "";
        isInChatRoom = false;
        Media media = new Media(new File(str).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
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
                    } else if (message.startsWith("Server pin message")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                visiblePinMessageIcons(message.substring(18));
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

    private void setVisibilityMessageIcons(boolean value) {
        deleteIcon.setVisible(value);
        editIcon.setVisible(value);
        pinMessage.setVisible(value);
    }

    private void invisiblePinMessageIcons() {
        pinnedMessagePic.setVisible(false);
        pinnedMessageText.setText("");
    }

    private void visiblePinMessageIcons(String message) {
        pinnedMessagePic.setVisible(true);
        pinnedMessageText.setText(message);
    }

    private void setSelectedMessage(MessageController object) {
        if (object.equals(selectedMessage)) {
            selectedMessage = null;
            setVisibilityMessageIcons(false);
        } else {
            this.selectedMessage = object;
            setVisibilityMessageIcons(true);
        }
    }

    public void pinMessage(MouseEvent event) {
        try {
            Controller.getDataOutputStream().writeUTF("Chat pin message"
                    + selectedMessage.messageTextBox.getText());
            Controller.getDataOutputStream().flush();
            setSelectedMessage(selectedMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteMessage(MouseEvent event) {
        try {
            Controller.getDataOutputStream().writeUTF("Chat delete message index"
                    + selectedMessage.getIndex());
            Controller.getDataOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPinnedMessage() {
        try {
            Controller.getDataOutputStream().writeUTF("Chat get pinned message");
            Controller.getDataOutputStream().flush();
            String result = Controller.getDataInputStream().readUTF();
            if (result.equals("@")) {
                invisiblePinMessageIcons();
            } else {
                visiblePinMessageIcons(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editMessage(MouseEvent event) {
        try {
            EditMessageController.setMessageController(selectedMessage);
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/EditMessage.fxml")));
            Stage stageEdit = new Stage();
            scene = new Scene(root);
            stageEdit.setScene(scene);
            stageEdit.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
