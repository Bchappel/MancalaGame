package mancala;

public class KalahRules extends GameRules{

    private static final long serialVersionUID = 1L;
    /**
     * Perform a move and return the number of stones added to the player's store.
     *
     * @param startPit  The starting pit for the move.
     * @param playerNum The player making the move.
     * @return The number of stones added to the player's store.
     * @throws InvalidMoveException If the move is invalid.
     */
    @Override
    public int moveStones(final int startPit, final int playerNum)throws InvalidMoveException{
        int distributeReturn = 0;
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

            distributeReturn = distributeStones(startPit); //call to distribute stones
            return data.getStoreCount(playerNum); //distributeReturn;

        } catch (InvalidMoveException e) {
            throw new InvalidMoveException("Invalid Move");
        }
    }


    /**
     * Distribute stones from a pit and return the number distributed.
     *
     * @param startPit The starting pit for distribution.
     * @return The number of stones distributed.
     */
    @Override
    public int distributeStones(final int startPit){
        final MancalaDataStructure data = getGameBoard();
        setBonusTurn(false);

        final int playerNum = getCurrentPlayer();
        int toDistribute = data.getNumStones(startPit);
        final int stones = toDistribute;
        int startingPoint = startPit;
        int storeID = 0;
        storeID = getStoreID(playerNum);
        data.removeStones(startPit);

        while(toDistribute > 0 ){

            toDistribute = checkPlayerStore(startingPoint, storeID, toDistribute, playerNum);

            if(toDistribute == 0){
                return stones;
            }
            if(startingPoint > 11){
                startingPoint = startingPoint % 12;
            }

            startingPoint++;
            data.addStones(startingPoint, 1);
            toDistribute--;

            if(toDistribute == 0){
                if(playerNum == 1){
                    if(startingPoint  >= 1 && startingPoint <=6){
                        findCapture(startingPoint);
                    }
                }else if(playerNum == 2){
                    if(startingPoint >= 7 && startingPoint <= 12){
                        findCapture(startingPoint);
                    }
                }
            }
        }

        return stones;
    }

    private int checkPlayerStore(final int startingPoint, final int storeNum, final int numStoneToDistribute, final int playerNum){
        final MancalaDataStructure data = getGameBoard();
        int distribution = numStoneToDistribute;
        if(startingPoint == storeNum){
            data.addToStore(playerNum, 1);
            distribution--;
            if(distribution == 0){
                setBonusTurn(true);
            }
        }
        return distribution;
    }

    private int getStoreID(final int playerNum){
        if(playerNum == 1){
            return 6;
        }else if(playerNum == 2){
            return 12;
        }

        return -1;
    }

    private void findCapture(final int index){
        final MancalaDataStructure data = getGameBoard();
        if(data.getNumStones(index) == 1 && data.getNumStones(index) > 0){
            captureStones(index);
        }
    }

    /**
     * Distribute stones from a pit and return the number distributed.
     *
     * @param startPit The starting pit for distribution.
     * @return The number of stones distributed.
     */
    @Override
    public int captureStones(final int stoppingPoint) {
        final MancalaDataStructure data = getGameBoard();

        int capturedIndex = 0;
        int stones = 0;
        int sourcePit = 0;

        capturedIndex = calculateOpposingPit(stoppingPoint);        
        sourcePit = data.removeStones(stoppingPoint);
        stones = data.removeStones(capturedIndex);
        stones = stones + sourcePit;
        data.addToStore(getCurrentPlayer(), stones);
        return stones;
    }

    public int calculateOpposingPit(final int startPoint) {
        return 13 - startPoint;
    }
}
