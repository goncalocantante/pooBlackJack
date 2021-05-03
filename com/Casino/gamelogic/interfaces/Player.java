package com.Casino.gamelogic.interfaces;

public interface Player {

    /**
     * Bet the requested chips
     */
    public void bet(double amount);

    /**
     * Request another card from the dealer
     */
    public void hit();

    /**
     * Do not receive any more cards
     */
    public void stand();

    /**
     * Double the current bet
     */
    public void doubleBet();

    /**
     * Surrender this hand
     */
    public void surrender();

    /**
     * Insure this hand
     */
    public void insure();
}