package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.classes.Card;

import java.util.ArrayList;

/**
 * Class for the game's hands
 */
public class Hand {

    // List containing the cards in the hand
    ArrayList<Card> cards = new ArrayList<Card>();

    /**
     * Gets the card in position i of the cards array
     * 
     * @param ncard: index of cards ArrayList to get the card from
     * @return: requested card
     */
    public Card getCard(int ncard) {
        return cards.get(ncard);
    }

    public void drawCard() {
    }

    /**
     * Removes the card in position i of the cards array
     * 
     * @param ncard: index of cards ArrayList to remove
     */
    public void removeCard(int ncard) {
        this.cards.remove(ncard);
    }

    /**
     * Indicates the total hand value
     * 
     * @return value: value of the hand
     */
    public int handValue() {
        int cardsValue = 0;
        int totalAces = 0;

        for (Card aCard : this.cards) {
            switch (aCard.getRank()) {
                case TWO:
                    cardsValue += 2;
                    break;
                case THREE:
                    cardsValue += 3;
                    break;
                case FOUR:
                    cardsValue += 4;
                    break;
                case FIVE:
                    cardsValue += 5;
                    break;
                case SIX:
                    cardsValue += 6;
                    break;
                case SEVEN:
                    cardsValue += 7;
                    break;
                case EIGHT:
                    cardsValue += 8;
                    break;
                case NINE:
                    cardsValue += 9;
                    break;
                case TEN:
                    cardsValue += 10;
                    break;
                case JACK:
                    cardsValue += 10;
                    break;
                case KING:
                    cardsValue += 10;
                    break;
                case QUEEN:
                    cardsValue += 10;
                    break;
                case ACE:
                    totalAces += 1;
                    break;
            }
        }

        for (int i = 0; i < totalAces; i++) {
            // if bust Ace is valued at 1 otherwise 11
            if (cardsValue > 10) {
                cardsValue++;
            } else {
                cardsValue += 11;
            }
        }

        return cardsValue;
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
     * Indicates if the hand has bust
     * 
     * @return isBustTrue: is true if the hand has busted
     */
    public boolean isBust() {
        if (this.handValue() > 21) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the hand can be split
     * 
     * @return canSplitTrue: true if the hand can be split
     */
    public boolean canSplit() {
        if (this.getHandSize() == 2 && this.getCard(0).getRank().equals(this.getCard(1).getRank())) {
            return true;
        } else {
            return false;
        }
    }

}
