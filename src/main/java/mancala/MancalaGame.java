package mancala;

import java.util.ArrayList;
import java.io.Serializable;

public class MancalaGame implements Serializable{

    private static final long serialVersionUID = 1L;
    private ArrayList<Player> players;
    private GameRules gameRules;
    private Player theCurrentPlayer;
    private MancalaDataStructure data;
    private String playerOneFile;
    private String playerTwoFile;

    public MancalaGame() {
        gameRules = new KalahRules();
        data = new MancalaDataStructure();
        setGameRules(gameRules);
        setPlayers(new Player(), new Player());
        setCurrentPlayer(players.get(0));
        gameRules.setMancalaDataStructure(data);
    }
    
    public MancalaGame(final GameRules rules, final String playerOne, final String playerTwo, final String playerOneFile, final String playerTwoFile) {
        setGameRules(rules);
        setPlayers(new Player(), new Player());
        setCurrentPlayer(players.get(0)); // Set the current player to the first player
        setPlayerNames(playerOne, playerTwo);
        loadPlayers(playerOneFile, playerTwoFile);
    }

    public void loadPlayers(final String playerOneFile, final String playerTwoFile){
        if(playerOneFile != null){
            loadUserProfileForPlayer(getPlayer(0), playerOneFile);
        }
        if(playerTwoFile != null){
            loadUserProfileForPlayer(getPlayer(1), playerTwoFile);
        }
    }

    public void setGameRules(final GameRules rules) {
        gameRules = rules;
    }

    public void setCurrentPlayer(final Player player) {
        theCurrentPlayer = player;
        gameRules.setPlayer(player.getPlayerIndex());
    }

    public void startNewGame() {
        gameRules.resetBoard();
    }

    public GameRules getBoard(){
        return gameRules;
    }

    public void setBoard(final GameRules theBoard){

    }

    public Player getCurrentPlayer() {
        return theCurrentPlayer;
    }

    /**
     * 
     */
    public void restartGame(){
        for(int i = 1; i <= 12; i++){
            gameRules.getDataStructure().removeStones(i);
        }
        gameRules.getDataStructure().emptyStores();
        gameRules.getDataStructure().setUpPits();
        setCurrentPlayer(players.get(0));
    }

    /**
     * 
     * @param startPit
     * @return
     * @throws InvalidMoveException
     */

    public int move(final int startPit) throws InvalidMoveException {
        try {
            return gameRules.moveStones(startPit, getCurrentPlayer().getPlayerIndex());
        } catch (InvalidMoveException e) {
            throw new RuntimeException("ERROR INVALID MOVE");
        }
    }

    /**
     *  
     * @param player
     * @return
     */

    public int getStoreCount(final Player player){
        return player.getStoreCount();
    }

    /**
     * 
     * @param playerOne
     * @param playerTwo
     */

    public void setPlayerNames(final String playerOne, final String playerTwo){
        getPlayer(0).setName(playerOne);
        getPlayer(1).setName(playerTwo);
    }

    /**
     * 
     * @param onePlayer
     * @param twoPlayer
     */

    public void setPlayers(final Player onePlayer, final Player twoPlayer) {
        players = new ArrayList<>();
        players.add(onePlayer);
        players.add(twoPlayer);
        gameRules.registerPlayers(onePlayer, twoPlayer);
        onePlayer.setPlayerIndex(1);
        twoPlayer.setPlayerIndex(2);
    }

    /**
     * sets the player
     */

    public void switchPlayer(){
        if (theCurrentPlayer == getPlayer(0)){
            setCurrentPlayer(getPlayer(1));
        } else {
            setCurrentPlayer(getPlayer(0));
        }
    }

    /**
     * 
     * @return bonus turn
     */

    public boolean getBonusTurn(){
        return gameRules.bonusTurn();
    }

    /**
     * 
     * @return the winner 
     */
    public Player getWinner() {
        int totalPlayerOne = 0;
        int totalPlayerTwo = 0;
        int maxTotal = 0;
    
        for (int i = 1; i <= 6; i++) {
            totalPlayerOne += gameRules.getNumStones(i);
        }
        for (int i = 7; i <= 12; i++) {
            totalPlayerTwo += gameRules.getNumStones(i);
        }
    
        if (gameRules instanceof KalahRules) {
            if (totalPlayerOne > totalPlayerTwo) {
                maxTotal = totalPlayerOne;
                getPlayer(0).getUserProfile().setKalahGamesWon(getPlayer(0).getUserProfile().getKalahGamesWon() + 1);
                return getPlayer(0);
            } else if (totalPlayerOne < totalPlayerTwo) {
                maxTotal = totalPlayerTwo;
                getPlayer(0).getUserProfile().setKalahGamesWon(getPlayer(0).getUserProfile().getKalahGamesWon() + 1);
                return getPlayer(1);
            } else {
                return null; // tie
            }
        } else if (gameRules instanceof AyoRules){
            if (totalPlayerOne > totalPlayerTwo) {
                maxTotal = totalPlayerOne;
                getPlayer(0).getUserProfile().setAyoGamesWon(getPlayer(0).getUserProfile().getAyoGamesWon() + 1);
                return getPlayer(0);
            } else if (totalPlayerOne < totalPlayerTwo) {
                maxTotal = totalPlayerTwo;
                getPlayer(1).getUserProfile().setAyoGamesWon(getPlayer(1).getUserProfile().getAyoGamesWon() + 1);
                return getPlayer(1);
            } else {
                return null; // tie
            }
        }else{
            return null; //somthing goes wrong
        }

    }
    /**
     * 
     * @return game status
     */
    public boolean isGameOver(){
        if(gameRules.isSideEmpty(1) || gameRules.isSideEmpty(7)){
            return true;
        }
        return false;
    }

    /**
     * 
     * @return players
     */
    public ArrayList<Player> getPlayers(){
        return players;
    }

    /**
     * 
     * @param var variable
     * @return get players @ var
     */

    public Player getPlayer(final int var){
        return getPlayers().get(var);
    }

    /**
     * 
     * @return gamerules
     */

    public GameRules getGameRules() {
        return gameRules;
    }

    /**
     * 
     * @param fileName
     * @return the loaded game
     */
    public static MancalaGame loadGame(String fileName) {
        try {
            MancalaGame loadedGame = (MancalaGame) Saver.loadObject(fileName);
            if (loadedGame != null) {
                if (loadedGame.data == null) {
                    loadedGame.data = new MancalaDataStructure(); // Initialize the data structure if it's null
                }
                loadedGame.setGameRules(loadedGame.gameRules);
                return loadedGame;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 
     * @param fileName returns the file name
     */

    public void saveGame(String fileName) {
        Saver.saveObject(this, fileName);
    }

    /**
     * 
     * @param player
     * @param fileName
     */

    public void loadUserProfileForPlayer(Player player, String fileName) {
        UserProfile loadedUserProfile = Saver.loadUserProfile(fileName);
    
        if (gameRules instanceof KalahRules) {
            loadedUserProfile.setKalahGamesPlayed(loadedUserProfile.getKalahGamesPlayed() + 1);
        } else if (gameRules instanceof AyoRules) {
            loadedUserProfile.setAyoGamesPlayed(loadedUserProfile.getAyoGamesPlayed() + 1);
        }
        
        if (loadedUserProfile != null) {
            player.setUserProfile(loadedUserProfile);
        } else {
            throw new RuntimeException("Error Loading the user profile");
        }
    }

    /**
     * 
     * @param player
     * @param fileName
     */
    public void saveUserProfile(Player player, String fileName) {
        Saver.saveUserProfile(player.getUserProfile(), fileName);
    }


    /**
     * return string
     */
    @Override
    public String toString() {
        return "abc";
    }
}