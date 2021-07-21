package Client.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditMessageController implements Initializable {

    private static MessageController messageController;

    @FXML
    private TextArea messageTextArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messageTextArea.setText(messageController.messageTextBox.getText());
    }

    @FXML
    void editMessage(ActionEvent event) {
        if(!messageTextArea.getText().equals("")) {
            try {
                Controller.getDataOutputStream().writeUTF("Chat change message index"
                        + messageController.getIndex() + "#" + messageTextArea.getText());
                Controller.getDataOutputStream().flush();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setMessageController(MessageController messageController) {
        EditMessageController.messageController = messageController;
    }


}