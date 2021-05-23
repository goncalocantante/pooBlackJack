package com.Casino.BlackJack.classes.GameLogic;

import com.Casino.BlackJack.enumerations.*;

import java.util.ArrayList;

public class Deck {

    // ArrayList containing the cards of the deck
    private ArrayList<Card> cards = new ArrayList<Card>();

    /**
     * Constructor that creates standard deck
     */
    public Deck() {
        this.createDeck();
    }

    /**
     * Creates a standard deck
     */
    public void createDeck() {
        for (Suit cardSuit : Suit.values()) {
            for (Rank cardValue : Rank.values()) {
                this.cards.add(new Card(cardSuit, cardValue, true));
            }
        }
    }

    /**
     * Gets the Deck's Cards
     * 
     * @return cards: List of Cards
     */
    public ArrayList<Card> getDeck() {
        return this.cards;
    }

    /**
     * Overrides toString() method to print Deck
     */
    public String toString() {
        String cardListOutput = "";
        for (Card aCard : this.cards) {
            cardListOutput += aCard.toString() + "\n";
        }
        return cardListOutput;
    }

}