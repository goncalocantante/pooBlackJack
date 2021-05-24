package com.Casino.BlackJack.interfaces;

import com.Casino.BlackJack.classes.GameLogic.Card;

import java.util.ArrayList;

/**
 * Interface for the shoe that will be used to play
 */
public interface Shoe {

    /**
     * Creates the shoe with the specified number of decks
     * 
     * @param ndecks: number of decks contained in the shoe
     */
    void createShoe(int ndecks);

    /**
     * Creates the shoe from the specified file
     *
     * @param fileName: file name string
     */
    void createShoeFromFile(String fileName);

    /**
     * Shuffles the cards in the shoe
     */
    void shuffle();

    /**
     * Removes a card from the top of the shoe
     * @param i: index of card to remove
     */
    void removeCard(int i);

    /**
     * Gets the card from the shoe without removing it
     * @param i: index of the card to get
     * @return card: card to get
     */
    Card getCard(int i);

    /**
     * Gets the size of the shoe
     * @return shoeSize: shoe size
     */
    int getShoeSize();

    /**
     * Receives list of cards and adds them to shoe
     * Useful when reshuffling
     * @param cardsToAdd: list of cards to add
     */
    void moveAllToShoe(ArrayList<Card> cardsToAdd);

}
