package com.Casino.gamelogic.interfaces;

public interface Player {

    /**
     * Bet the requested chips
     */
    void bet(double amount);

    /**
     * Request another card from the dealer
     */
    void hit();

    /**
     * Do not receive any more cards
     */
    void stand();

    /**
     * Double the current bet
     */
    void doubleBet();

    /**
     * Surrender this hand
     */
    void surrender();

    /**
     * Insure this hand
     */
    void insure();
}