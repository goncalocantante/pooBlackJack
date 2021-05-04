package com.Casino.gamelogic.interfaces;

import com.Casino.gamelogic.classes.Card;


/**
 * Interface for the shoe that will be used to play
 */
public interface Shoe {

    /**
     *Creates the deck with the specified number of decks
     * @param ndecks: number of decks contained in the shoe
     */
    void createShoe(int ndecks);


    /**
     * Shuffles the cards in the shoe
     */
    void shuffle();

    /**
     * Adds a card to the shoe
     * @param card: card that will be added to the shoe
     */
    void addCard(Card card);

    /**
     * Removes a card from the shoe
     * @param card: card that will be removed from the shoe
     */
    void removeCard();

    /**
     * Gets the card from the shoe without removing it
     * @param card: ----------
     */
    void getCard();

    /**
     * Gets the size of the shoe
     * @param shoe: Shoe to get the size of
     */
    int getShoeSize();

    /**
     * ------------
     */
    void moveAllToShoe();

}
