package com.Casino.gamelogic.interfaces;

public interface GameState {

    /**
     * Initializes the state
     */
    void startState();

    /**
     * Resolves the state
     */
    void resolveState();

    /**
     * Ens the state and sets the next state
     */
    void endState();
}
