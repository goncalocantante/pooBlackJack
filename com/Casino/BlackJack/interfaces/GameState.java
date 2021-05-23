package com.Casino.BlackJack.interfaces;

public interface GameState {

    void initializeGame();

    void playerTurn();

    void dealerTurn();

    void finishRound();

}
