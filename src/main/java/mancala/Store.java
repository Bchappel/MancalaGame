package mancala;

import java.io.Serializable;

public class Store implements Countable, Serializable {

    private static final long serialVersionUID = 1L;
    private Player owner;
    private int stones;

    public Store() {
        this.stones = 0;
    }

    public void setOwner(Player player) {
        this.owner = player;
    }

    public Player getOwner() {
        return owner;
    }

    public int emptyStore() {
        int removedStones = stones;
        stones = 0;
        return removedStones;
    }

    @Override
    public int getStoneCount() {
        return stones;
    }

    @Override
    public void addStone() {
        stones++;
    }

    @Override
    public void addStones(int numToAdd) {
        stones += numToAdd;
    }

    @Override
    public int removeStones() {
        int removedStones = stones;
        stones = 0;
        return removedStones;
    }
}