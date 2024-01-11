package mancala;
import java.io.Serializable;

public class UserProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    private int numKalahGamesPlayed;
    private int numKalahGamesWon;
    private int numAyoGamesPlayed;
    private int numAyoGamesWon;
    private String userName;


    // Default constructor
    public UserProfile() {
        this.userName = "_defaultName";
    }

    // Parameterized constructor
    public UserProfile(String userName, int numKalahGamesPlayed, int numKalahGamesWon, int numAyoGamesPlayed, int numAyoGamesWon) {
        this.userName = userName;
        this.numKalahGamesPlayed = numKalahGamesPlayed;
        this.numKalahGamesWon = numKalahGamesWon;
        this.numAyoGamesPlayed = numAyoGamesPlayed;
        this.numAyoGamesWon = numAyoGamesWon;
    }

    // Getters and setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getKalahGamesPlayed() {
        return numKalahGamesPlayed;
    }

    public void setKalahGamesPlayed(int numKalahGamesPlayed) {
        this.numKalahGamesPlayed = numKalahGamesPlayed;
    }

    public int getKalahGamesWon() {
        return numKalahGamesWon;
    }

    public void setKalahGamesWon(int numKalahGamesWon) {
        this.numKalahGamesWon = numKalahGamesWon;
    }

    public int getAyoGamesPlayed() {
        return numAyoGamesPlayed;
    }

    public void setAyoGamesPlayed(int numAyoGamesPlayed) {
        this.numAyoGamesPlayed = numAyoGamesPlayed;
    }

    public int getAyoGamesWon() {
        return numAyoGamesWon;
    }



    public void setAyoGamesWon(int numAyoGamesWon) {
        this.numAyoGamesWon = numAyoGamesWon;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\nPlayer: " + getUserName() + "\n");
        sb.append("Ayo Games Won: " + getAyoGamesWon() + "\nAyo Games Played: " + getAyoGamesPlayed() + "\n");
        sb.append("Kalah Games Won: " + getKalahGamesWon() + "\nKalah Games Played: " + getKalahGamesPlayed() +"\n");

        return sb.toString();
    }

}
