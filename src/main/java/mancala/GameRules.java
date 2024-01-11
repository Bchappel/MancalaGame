package mancala;
import java.util.List;
import java.io.Serializable;

/**
 * Abstract class representing the rules of a Mancala game.
 * KalahRules and AyoRules will subclass this class.
 */
public abstract class GameRules implements Serializable {

    private static final long serialVersionUID = 1L;

    private MancalaDataStructure gameBoard;
    private int currentPlayer = 1; // Player number (1 or 2)
    private boolean isBonusTurn = false;

    /**
     * Constructor to initialize the game board.
     */
    public GameRules(){
        gameBoard = new MancalaDataStructure();
    }

    /**
     * 
     * @param data sets the game board to data structure
     */

    public void setMancalaDataStructure(final MancalaDataStructure data) {
        this.gameBoard = data;
    }
    
    /**
     * 
     * @param gameBoard inputs data structure
     */

    public GameRules(final MancalaDataStructure gameBoard){
        this.gameBoard = gameBoard;
    }

    protected MancalaDataStructure getGameBoard() {
        return gameBoard;
    }

    /**
     * Get the number of stones in a pit.
     *
     * @param pitNum The number of the pit.
     * @return The number of stones in the pit.
     */
    public int getNumStones(final int pitNum) {
        return gameBoard.getNumStones(pitNum);
    }

    /**
     * returns the current player.
     * @return returns the current player.
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * 
     * @param newBoard sets the board to the new board
     */


    public void setBoard(final List<Countable> newBoard) {
        gameBoard.setBoard(newBoard);
    }

    /**
     * 
     * @param turn sets the turn to the bonus
     */

    public void setBonusTurn(final boolean turn){
        isBonusTurn = turn;
    }

    /**
     * 
     * @return the bonus turn
     */

    public boolean bonusTurn(){
        return isBonusTurn;
    }

    /**
     * Get the game data structure.
     *
     * @return The MancalaDataStructure.
     */
    MancalaDataStructure getDataStructure() {
        return gameBoard;
    }

    /**
     * Check if a side (player's pits) is empty.
     * @param pitNum The number of a pit in the side.
     * @return True if the side is empty, false otherwise.
     */
    public boolean isSideEmpty(final int pitNum) {
        if (pitNum < 1 || pitNum > 12) {
            throw new RuntimeException("Error pits are out of range");
        }
    
        final int playerIndex = pitNum <= 6 ? 1 : 2;
        final int startPit = (playerIndex - 1) * 6 + 1;
        final int endPit = (playerIndex - 1) * 6 + 6;
    
        for (int i = startPit; i <= endPit; i++) {
            if (getNumStones(i) > 0) {
                return false; // At least one pit is not empty
            }
        }
        return true; // All pits are empty
    }
    


    /**
     * Set the current player.
     *
     * @param playerNum The player number (1 or 2).
     */
    public void setPlayer(final int playerNum) {
        currentPlayer = playerNum;
    }

    /**
     * Register two players and set their stores on the board.
     *
     * @param one The first player.
     * @param two The second player.
     */
    public void registerPlayers(final Player one, final Player two) {
        // this method can be implemented in the abstract class.
        /* make a new store in this method, set the owner
         then use the setStore(store,playerNum) method of the data structure*/
        final Store storeOne = new Store();
        final Store storeTwo = new Store();
        storeOne.setOwner(one);
        storeTwo.setOwner(two);
        one.setStore(storeOne);
        two.setStore(storeTwo);

        gameBoard.setStore(storeOne, 1);
        gameBoard.setStore(storeTwo, 2);
    }

    /**
     * Reset the game board by setting up pits and emptying stores.
     */
    public void resetBoard() {
        gameBoard.emptyStores();
    }

    /**
     * 
     * @param playerNum
     * @return the number in the store
     */

    public int getStoreCount(final int playerNum){
        return gameBoard.getStoreCount(playerNum);
    }

    @Override
    public String toString() {
        return "abc";
    }

    /**
     * Perform a move and return the number of stones added to the player's store.
     *
     * @param startPit  The starting pit for the move.
     * @param playerNum The player making the move.
     * @return The number of stones added to the player's store.
     * @throws InvalidMoveException If the move is invalid.
     */
    public abstract int moveStones(int startPit, int playerNum) throws InvalidMoveException;
    /**
     * Distribute stones from a pit and return the number distributed.
     *
     * @param startPit The starting pit for distribution.
     * @return The number of stones distributed.
     */
    abstract int distributeStones(int startPit);
    /**
     * Capture stones from the opponent's pit and return the number captured.
     *
     * @param stoppingPoint The stopping point for capturing stones.
     * @return The number of stones captured.
     */
    abstract int captureStones(int stoppingPoint);

}
