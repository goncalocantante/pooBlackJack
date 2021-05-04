package com.Casino.gamelogic.interfaces;

/**
 * Interface for the chips that the player will bet
 */
public interface Chip {

    /*
     * Get the value of the chip
     * 
     * @return Value: value of the chip
     */
    int getValue();

    /**
     * Get the color of the chip
     * 
     * @return Color: String containing the color of the chip
     */
    String getColor();
}