package com.Casino.BlackJack.interfaces;

import com.Casino.BlackJack.classes.GameLogic.Game;

/**
 * Interface for the game's modes
 * Used for implementng strategy design pattern
 */
public interface Mode {

    /**
     * Receives and validates the program's input parameters
     * Passes these parameters to the Game object
     * Initializes the game's shoe
     * @param game: game being played
     */
    void InitializeShoeAndParameters(Game game);

    /**
     * Gets the next command according to the game mode
     * @param nHand: index of hand being played
     * @return action: command
     */
    String getCommand(int nHand);
}
