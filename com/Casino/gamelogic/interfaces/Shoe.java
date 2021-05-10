package com.Casino.gamelogic.interfaces;

import com.Casino.gamelogic.classes.Card;

import java.util.ArrayList;


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
     * Adds cards to the shoe
     * @param cards: cards that will be added to the shoe
     */
    void addCards(ArrayList<Card> cards);

    /**
     * Removes a card from the top of the shoe
     */
    void removeCard();

    /**
     * Gets the card from the shoe without removing it
     * @param card: ----------
     */
    Card getCard();

    /**
     * Draws card from the shoe
     * @return card: card drawn
     */
    Card drawCard();

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
