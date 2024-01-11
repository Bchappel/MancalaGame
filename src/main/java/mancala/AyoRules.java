package mancala;

public class AyoRules extends GameRules{

    private static final long serialVersionUID = 1L;

    public AyoRules(){
        super();
    }

    /**
     * Perform a move and return the number of stones added to the player's store.
     *
     * @param startPit  The starting pit for the move.
     * @param playerNum The player making the move.
     * @return The number of stones added to the player's store.
     * @throws InvalidMoveException If the move is invalid.
     */

    @Override
    public int moveStones(final int startPit, final int playerNum) throws InvalidMoveException{
        final MancalaDataStructure data = getGameBoard();

        try {
            if(startPit > 13){
                throw new InvalidMoveException("Invalid Pit");
            }

            if(playerNum == 1){
                if(startPit >= 7 && startPit <= 12){
                    return -1;
                }
            }else if(playerNum == 2){
                if(startPit  >= 1 && startPit <=6){
                    return -1;
                }
            }

            distributeStones(startPit); //call to distribute stones
            return data.getStoreCount(playerNum); //distributeReturn;

        } catch (InvalidMoveException e) {
            throw new InvalidMoveException("Invalid Move");
        }
    }
    /**
     * @param start
     * @return number of stone distributed
     */
    @Override
    public int distributeStones(final int startPit) {
        final MancalaDataStructure data = getGameBoard();
        setBonusTurn(false); // always false (no bonus turn for this version)
        final int playerNum = getCurrentPlayer();
        int toDistribute = getNumStones(startPit); // number of stones to distribute
        int counter = startPit;
        final int stones = toDistribute;
        final int storeID = playerNum == 1 ? 6 : 12;
        data.removeStones(startPit);

        boolean firstLap = true;
        boolean captureOccurred = false;

        while (toDistribute > 0) {
            toDistribute = checkPlayerStore(counter, storeID, toDistribute, playerNum);

            if (toDistribute == 0 && !firstLap && data.getNumStones(counter) > 0) {
                final int capturedStones = captureStones(counter);
                if (capturedStones > 0) {
                    return stones;
                }
            }
            if (toDistribute == 0) {
                return stones;
            }
            if (counter > 11) {
                counter = counter % 12;
                firstLap = false;
            }
            // ensures that nothing will ever go in the start pit
            if (data.getNumStones(startPit) < 0) {
                data.removeStones(startPit);
            }

            counter++;
            data.addStones(counter, 1);
            toDistribute--;
        }
        return stones;
    }

    /**
     * @param helper method
     * @return captured stones
     */

    @Override
    public int captureStones(final int stoppingPoint) {
        final MancalaDataStructure data = getGameBoard();
        final int oppositePit = 12 - stoppingPoint;
        if (data.getNumStones(stoppingPoint) == 0 && data.getNumStones(oppositePit) > 0) {
            final int capturedStones = data.getNumStones(oppositePit);
            data.addToStore(getCurrentPlayer(), capturedStones);
            data.removeStones(oppositePit);
            return capturedStones;
        }
        return 0; //No stones captured
    }
    

    /**
     * @param startingPoint
     * 
     * @return captured stones
     */
    private int checkPlayerStore(final int startingPoint, final int storeNum, final int numStoneToDistribute, final int playerNum){
        final MancalaDataStructure data = getGameBoard();
        System.out.println("Checking Player Stores");
        int distribution = numStoneToDistribute;
        if(startingPoint == storeNum){
            data.addToStore(playerNum, 1);
            distribution--;
        }
        return distribution;
    }
}

