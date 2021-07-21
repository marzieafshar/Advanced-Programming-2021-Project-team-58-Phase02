package Client.View;

import Client.Model.ScoreBoardPlayer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

public class ScoreBoardController implements Initializable {

    private Parent root;
    private Stage stage;
    @FXML
    private GridPane gridPane;
    private ArrayList<String> onlineTokens = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadPage();
            loadOnlineUsers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private TableView<ScoreBoardPlayer> myTable;

    @FXML
    private TableColumn<?, ?> rankColumn;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private TableColumn<?, ?> scoreColumn;

    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    String token;

    private void loadPage() throws IOException {
        dataOutputStream = Controller.getDataOutputStream();
        dataInputStream = Controller.getDataInputStream();
        token = Controller.getToken();
        dataOutputStream.writeUTF("Scoreboard get players" + token);
        dataOutputStream.flush();

        String input = dataInputStream.readUTF();
        String[] data = input.split("#");

        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nickName"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        myTable.setItems(getPlayers(data));
        rankColumn.setStyle("-fx-background-color: transparent");
        nameColumn.setStyle("-fx-background-color: transparent");
        scoreColumn.setStyle("-fx-background-color: transparent");


        myTable.setRowFactory(tv -> new TableRow<ScoreBoardPlayer>() {
            @Override
            public void updateItem(ScoreBoardPlayer player, boolean empty) {
                super.updateItem(player, empty);
                if (player == null) {
                    setStyle("-fx-background-color: rgba(239,234,234,0.42)");
                } else if (player.getNickName().equals(ProfileController.getPlayerInfo("nickname" , Controller.getToken()))) {
                    setStyle("-fx-background-color: rgba(234,224,79,0.82); -fx-border-color: #000000");
                } else {
                    setStyle("-fx-background-color: rgba(239,234,234,0.42)");
                    setFont(Font.font("Verdana", 20));
                }
            }
        });
    }

    @FXML
    void backToMainMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxmls/MainMenu.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void refreshPage(MouseEvent event) throws IOException {
        loadPage();
    }

    public ObservableList<ScoreBoardPlayer> getPlayers(String[] data) {
        ObservableList<ScoreBoardPlayer> players = FXCollections.observableArrayList();
        for (int i = 0; i < data.length; i = i + 3)
            if (i <= data.length - 3)
                players.add(new ScoreBoardPlayer(data[i], data[i + 1], data[i + 2]));
        return players;
    }

    private void loadOnlineUsers() {
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

                String nickname = ProfileController.getPlayerInfo("nickname" , onlineToken);
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
}
