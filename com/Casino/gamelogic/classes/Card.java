package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.enumerations.*;

public class Card {
    private Suit suit;
    private Rank rank;
    private boolean isCardUp;

    public Card(Suit suit, Rank rank, boolean isCardUp) {
        this.rank = rank;
        this.suit = suit;
        this.isCardUp = isCardUp;
    }

    /**
     * Returns the card's value (ace returns 1)
     * @return cardValue: card's value
     */
    public int cardValue() {
        switch(this.rank){
            case TWO:
                return 2;
            case THREE:
                return 3;
            case FOUR:
                return 4;
            case FIVE:
                return 5;
            case SIX:
                return 6;
            case SEVEN:
                return 7;
            case EIGHT:
                return 8;
            case NINE:
                return 9;
            case TEN:
            case JACK:
            case KING:
            case QUEEN:
                return 10;
            case ACE:
                return 1;
            default:
                return 0;
        }
    }

    public String toString() {
        String cardString = "";
        if (this.isCardUp == false) {
            cardString = "X";
        } else {
            switch (this.rank) {
                case TWO:
                    cardString += "2";
                    break;
                case THREE:
                    cardString += "3";
                    break;
                case FOUR:
                    cardString += "4";
                    break;
                case FIVE:
                    cardString += "5";
                    break;
                case SIX:
                    cardString = "6";
                    break;
                case SEVEN:
                    cardString += "7";
                    break;
                case EIGHT:
                    cardString += "8";
                    break;
                case NINE:
                    cardString += "9";
                    break;
                case TEN:
                    cardString += "10";
                    break;
                case JACK:
                    cardString += "J";
                    break;
                case KING:
                    cardString += "K";
                    break;
                case QUEEN:
                    cardString += "Q";
                    break;
                case ACE:
                    cardString += "A";
                    break;
                default:
                    break;
            }
            switch (this.suit) {
                case CLUBS:
                    cardString += "C";
                    break;
                case DIAMONDS:
                    cardString += "D";
                    break;
                case SPADES:
                    cardString += "S";
                    break;
                case HEARTS:
                    cardString += "H";
                    break;
                default:
                    break;
            }
        }
        return cardString;
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

    /**
     * Sets card face up
     */
    public void setCardFaceDown() {
        this.isCardUp = false;
    }
}
