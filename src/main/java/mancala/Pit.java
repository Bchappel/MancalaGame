package mancala;

import java.io.Serializable;

public class Pit implements Countable, Serializable {
    private int stones;

    public Pit() {
        this.stones = 0;
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

    @Override
    public String toString() {
        return ": " + stones + " stones";
    }
}