package Client.View;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class MessageController {

    public Label senderNameLabel;
    public Text messageTextBox;
    public HBox messageHBox;

    public void setMessage(String senderName, String messageText) {
        senderNameLabel.setText(senderName);
        messageTextBox.setText(messageText);
        if (messageText.length() > 30) {
            messageTextBox.setWrappingWidth(320);
        } else {
            messageTextBox.setWrappingWidth(11 * (messageText.length()));
        }
    }
}