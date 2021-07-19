package Client.View;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class MessageController {

    public Label senderNameLabel;
    public Text messageTextBox;

    public void setMessage (String senderName , String messageText){
        senderNameLabel.setText(senderName);
        messageTextBox.setWrappingWidth(300);
        messageTextBox.setText(messageText);
    }
}
