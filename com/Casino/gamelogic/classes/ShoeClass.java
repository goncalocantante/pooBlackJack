package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.interfaces.Shoe;
import com.Casino.gamelogic.classes.Deck;
import com.Casino.gamelogic.classes.Card;

import java.util.ArrayList;
import java.util.Collections;

public class ShoeClass implements Shoe {

    ArrayList<Card> cards = new ArrayList<Card>();

    public ShoeClass(int ndecks) {
        this.createShoe(ndecks);
    }

    /**
     * Creates the deck with the specified number of decks
     * 
     * @param ndecks: number of decks contained in the shoe
     */
    @Override
    public void createShoe(int ndecks) {
        Deck deck;
        for (int i = 0; i < ndecks; i++) {
            deck = new Deck();
            this.cards.addAll(deck.getDeck());
        }
    }

    /**
     * Shuffles the cards in the shoe
     */
    @Override
    public void shuffle() {
        Collections.shuffle(this.cards);
    }

    /**
     * Removes a card (index i) from the shoe
     */
    @Override
    public void removeCard(int i) {
        this.cards.remove(i);
    }

    /**
     * Gets the card from the shoe without removing it
     */
    @Override
    public Card getCard(int i) {
        return this.cards.get(i);
    }

    /**
     * Receives list of cards and adds them to shoe
     * 
     * Useful when reshuffling
     */
    @Override
    public void moveAllToShoe(ArrayList<Card> cardsToAdd) {
        this.cards.addAll(cardsToAdd);
        return;
    }

    /**
     * Gets the size of the shoe
     */
    @Override
    public int getShoeSize() {
        return this.cards.size();
    }

    /**
     * Prints all cards in the shoe
     */
    public String toString() {
        String cardListOutput = "";
        for (Card aCard : this.cards) {
            cardListOutput += aCard.toString() + "\n";
        }
        return cardListOutput;
    }
}
