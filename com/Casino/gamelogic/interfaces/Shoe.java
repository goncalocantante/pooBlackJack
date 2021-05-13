package com.Casino.gamelogic.interfaces;

import com.Casino.gamelogic.classes.Card;

import java.util.ArrayList;

/**
 * Interface for the shoe that will be used to play
 */
public interface Shoe {

    /**
     * Creates the deck with the specified number of decks
     * 
     * @param ndecks: number of decks contained in the shoe
     */
    void createShoe(int ndecks);

    /**
     * Shuffles the cards in the shoe
     */
    void shuffle();

    /**
     * Removes a card from the top of the shoe
     */
    void removeCard(int i);

    /**
     * Gets the card from the shoe without removing it
     * 
     * @param card: ----------
     */
    Card getCard(int i);

    /**
     * Gets the size of the shoe
     * 
     * @param shoe: Shoe to get the size of
     */
    int getShoeSize();

    /**
     * ------------
     */
    void moveAllToShoe(ArrayList<Card> cardsToAdd);

}
