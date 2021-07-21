package Client.View;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class MessageController {

    public Label senderNameLabel;
    public Text messageTextBox;
    public HBox messageHBox;
    private MyListener myListener;
    private int index;

    public void click(){
        myListener.onClickListener(this);
    }

    public void setMessage(String senderName, String messageText , int index , MyListener listener) {
        this.myListener = listener;
        this.index = index;
        senderNameLabel.setText(senderName);
        messageTextBox.setText(messageText);
        if (messageText.length() > 30) {
            messageTextBox.setWrappingWidth(320);
        } else {
            messageTextBox.setWrappingWidth(11 * (messageText.length()));
        }
    }

    public int getIndex() {
        return index;
    }
}
