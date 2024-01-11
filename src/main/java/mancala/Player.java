package mancala;

import java.io.Serializable;

public class Player implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private UserProfile userProfile;
    private Store playerStore;
    private int playerIndex = 1;
    private int playerNumber;

    // Default constructor
    public Player() {
        this.userProfile = new UserProfile();
    }

    // Constructor with UserProfile parameter
    public Player(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public String getName() {
        return userProfile.getUserName();
    }

    public void setStore(Store playerStore) {
        this.playerStore = playerStore;
    }

    public void setName(String name) {
        userProfile.setUserName(name);
    }

    public int getStoreCount() {
        return playerStore.getStoneCount();
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public Store getPlayerStore() {
        return playerStore;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public void playKalahGame() {
        userProfile.setKalahGamesPlayed(userProfile.getKalahGamesPlayed() + 1);
    }

    public void playAyoGame() {
        userProfile.setAyoGamesPlayed(userProfile.getAyoGamesPlayed() + 1);
    }

    //required
    @Override
    public String toString() {
        return " " + userProfile.getUserName();
    }
}
