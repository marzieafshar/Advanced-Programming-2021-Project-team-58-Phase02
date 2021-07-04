package Model;

public class Position {
    private StatusOfPosition status;
    private Card card;
    private boolean isStatusChanged;

    public Position(StatusOfPosition status) {
        setStatus(status);
    }

    public StatusOfPosition getStatus() {
        return status;
    }

    public void setStatus(StatusOfPosition status) {
        this.status = status;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setStatusChanged(boolean statusChanged) {
        isStatusChanged = statusChanged;
    }

    public boolean getIsStatusChanged() {
        return isStatusChanged;
    }
}
