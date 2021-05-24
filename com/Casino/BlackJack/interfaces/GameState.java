package com.Casino.BlackJack.interfaces;

/**
 * Interface for the game's states
 * Used for implementing state design pattern
 */
public interface GameState {

    void initializeGame();

    void playerTurn();

    void dealerTurn();

    void finishRound();

}
