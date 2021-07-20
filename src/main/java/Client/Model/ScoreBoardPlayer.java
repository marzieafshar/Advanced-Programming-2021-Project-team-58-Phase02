package Client.Model;

public class ScoreBoardPlayer {
    String rank;
    String nickName;
    String score;

    public ScoreBoardPlayer(String rank, String nickName, String score) {
        this.rank = rank;
        this.nickName = nickName;
        this.score = score;
    }

    public String getRank() {
        return rank;
    }

    public String getNickName() {
        return nickName;
    }

    public String getScore() {
        return score;
    }
}
