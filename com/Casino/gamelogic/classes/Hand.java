package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.classes.Card;
import com.Casino.gamelogic.enumerations.Rank;
import com.Casino.gamelogic.enumerations.Suit;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class for the game's hands
 */
public class Hand {

    // List containing the cards in the hand
    private ArrayList<Card> cards;
    // True if the hand is finished playing
    private boolean handClosed;

    /**
     * Constructor to initialize hand
     */
    public Hand() {
        this.cards = new ArrayList<Card>();
        this.handClosed = false;
    }

    /**
     * Removes card from the Hand
     *
     * @param ncard: index of the card to remove.
     */
    public void removeCard(int ncard) {
        this.cards.remove(ncard);
    }

    /**
     * Gets the card in position i of the cards array
     *
     * @param ncard: index of cards ArrayList to get the card from
     * @return Card: requested card
     */
    public Card getCard(int ncard) {
        return cards.get(ncard);
    }

    /**
     * Adds card to the hand
     *
     * @param card: card to receive
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    // Draws a card from the Shoe
    public void drawCard(ShoeClass comingFrom) {
        this.cards.add(comingFrom.getCard(0));
        comingFrom.removeCard(0);
    }

    /**
     * Empties the hand
     *
     * @return buff: cards emptied from the hand
     */
    public ArrayList<Card> emptyHand() {
        ArrayList<Card> buff = new ArrayList<Card>();

        buff.addAll(this.cards);
        this.cards.clear();
        return buff;
    }

    /**
     * Indicates the total hand value
     *
     * @return value: value of the hand
     */
    public int handValue() {
        int value = 0;
        int nAces = 0;

        for (Card card : this.cards) {
            switch (card.getRank()) {
                case TWO:
                    value += 2;
                    break;
                case THREE:
                    value += 3;
                    break;
                case FOUR:
                    value += 4;
                    break;
                case FIVE:
                    value += 5;
                    break;
                case SIX:
                    value += 6;
                    break;
                case SEVEN:
                    value += 7;
                    break;
                case EIGHT:
                    value += 8;
                    break;
                case NINE:
                    value += 9;
                    break;
                case TEN:
                case JACK:
                case KING:
                case QUEEN:
                    value += 10;
                    break;
                case ACE:
                    nAces++;
                    break;
            }
        }
        for (int i = 0; i < nAces; i++) {
            if (value + 11 < 21) {
                value += 11;
            } else {
                value += 1;
            }
        }
        return value;
    }

    /**
     * Indicates the number of cards in the hand
     *
     * @return ncards: number of cards in the hand
     */
    public int getHandSize() {
        return this.cards.size();
    }

    /**
     * Checks if the hand is closed
     *
     * @return handClosed: true if the hand is closed
     */
    public boolean isHandClosed() {
        return handClosed;
    }

    /**
     * Closes hand
     */
    public void closeHand() {
        this.handClosed = true;
    }

    /**
     * Indicates if the hand has bust
     *
     * @return isBustTrue: is true if the hand has busted
     */

    public boolean isBust() {
        return this.handValue() > 21;
    }

    /**
     * Checks if the hand can be split
     *
     * @return canSplitTrue: true if the hand can be split
     */
    public boolean canSplit() {
        return this.getHandSize() == 2 && this.getCard(0).getRank().equals(this.getCard(1).getRank());
    }

    public String toString() {
        String cardListOutput = "";
        for (Card aCard : this.cards) {
            cardListOutput += aCard.toString() + " ";
        }
        return cardListOutput;
    }
}
