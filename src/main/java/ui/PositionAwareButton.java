package ui;

import java.awt.Dimension;

import javax.swing.JButton;

/**
 * Represents a GUI button component that knows its position in a grid.
 */
public class PositionAwareButton extends JButton {
    private int xPos;
    private int yPos;
    private int pitNumber; 

    /**
     * Constructs a new PositionAwareButton.
     */
    public PositionAwareButton() {
        super();
        setPreferredSize(new Dimension(100, 100));
    }

    /**
     * Constructs a new PositionAwareButton with the specified text.
     *
     * @param val The text to display on the button.
     */
    public PositionAwareButton(String val) {
        super(val);
    }

    /**
     * Gets the horizontal position (across) of the button in a grid.
     *
     * @return The horizontal position of the button.
     */
    public int getAcross() {
        return xPos;
    }

    /**
     * Gets the vertical position (down) of the button in a grid.
     *
     * @return The vertical position of the button.
     */
    public int getDown() {
        return yPos;
    }

    /**
     * Sets the horizontal position (across) of the button in a grid.
     *
     * @param val The horizontal position to set.
     */
    public void setAcross(int val) {
        xPos = val;
    }

    /**
     * Sets the vertical position (down) of the button in a grid.
     *
     * @param val The vertical position to set.
     */
    public void setDown(int val) {
        yPos = val;
    }

    /**
     * Gets the pit number assigned to the button.
     *
     * @return The pit number.
     */
    public int getPitNumber() {
        return pitNumber;
    }

    /**
     * Sets the pit number for the button.
     *
     * @param pitNumber The pit number to set.
     */
    public void setPitNumber(int pitNumber) {
        this.pitNumber = pitNumber;
    }


}
