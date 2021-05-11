package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.enumerations.*;

public class Card {
    private Suit suit;
    private Rank rank;
    private boolean isCardUp;

    public Card(Suit suit, Rank rank, boolean isCardUp) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * idk
     * 
     * @return: não sei
     */
    public String toString() {
        return this.suit.toString() + "-" + this.rank.toString();
    }

    /**
     * Gets the rank of the card
     * 
     * @return rank: rank of the card
     */
    public Rank getRank() {
        return this.rank;
    }

    /**
     * Gets the suit of the card
     * 
     * @return suit: suit of the card
     */
    public Suit getSuit() {
        return this.suit;
    }

    /**
     * Checks if card is face up
     * 
     * @return isFaceUp: True if the card is face up
     */
    public boolean isCardFaceUp() {
        return isCardUp;
    }

    /**
     * Sets card face up
     */
    public void setCardFaceUp() {
        this.isCardUp = true;
    }
}
