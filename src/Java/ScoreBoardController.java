import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;

public class ScoreBoardController implements Initializable {

    @FXML
    private TableView<ScoreBoardPlayer> table;
    @FXML
    private TableColumn<ScoreBoardPlayer, Integer> Rank;
    @FXML
    private TableColumn<ScoreBoardPlayer, String> Nickname;
    @FXML
    private TableColumn<ScoreBoardPlayer, Integer> Score;

    ObservableList<ScoreBoardPlayer> list = FXCollections.observableArrayList(
            new ScoreBoardPlayer(1, 500, "bardia"),
            new ScoreBoardPlayer(2, 400, "reza"),
            new ScoreBoardPlayer(3, 300, "ali"),
            new ScoreBoardPlayer(4, 200, "mmd"),
            new ScoreBoardPlayer(5, 100, "mohsen"),
            new ScoreBoardPlayer(6, 50, "kia"),
            new ScoreBoardPlayer(7, 25, "komeil"),
            new ScoreBoardPlayer(8, 10, "morteza")
    );

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Rank.setCellValueFactory(new PropertyValueFactory<>("Rank"));
            Nickname.setCellValueFactory(new PropertyValueFactory<>("Nickname"));
            Score.setCellValueFactory(new PropertyValueFactory<>("Score"));
            table.setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
