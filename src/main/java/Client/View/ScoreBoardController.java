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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ScoreBoardController implements Initializable {

    private Parent root;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadPage();
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
//        int i = 0;
//        for (Node n : myTable.) {
//            if (n instanceof TableRow) {
//                TableRow row = (TableRow) n;
//                if (myTable.getItems().get(i).getNickName().equals("marzie")) {
//                    row.getStyleClass().add("-fx-background-color: pink");
//                } i++;
//            }
//        }
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
}
