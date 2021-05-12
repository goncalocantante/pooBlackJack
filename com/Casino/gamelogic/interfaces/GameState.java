package com.Casino.gamelogic.interfaces;

public interface GameState {

    void initializeGame();

    void playerTurn();

    void dealerTurn();

    void finishRound();

}
