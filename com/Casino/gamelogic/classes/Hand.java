package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.classes.Card;

import java.util.ArrayList;

/**
 * Class for the game's hands
 */
public class Hand {

    //List containing the cards in the hand
    ArrayList<Card> cards = new ArrayList<Card>();

    /**
     * Gets the card in position i of the cards array
     * @param ncard: index of cards ArrayList to get the card from
     * @return: requested card
     */
    public Card getCard(int ncard) { return cards.get(i);}


    public void drawCard() {}

    /**
     * Removes the card in position i of the cards array
     * @param ncard: index of cards ArrayList to remove
     */
    public void removeCard(int ncard) { this.cards.remove(ncard);}

    /**
     * Indicates the total hand value
     * @return value: value of the hand
     */
    public int handValue() { }

    /**
     * Indicates the number of cards in the hand
     * @return ncards: number of cards in the hand
     */
    public int getHandSize() {return this.cards.size();}

    /**
     * Indicates if the hand has bust
     * @return isBustTrue: is true if the hand has busted
     */
    public boolean isBust() {
        if(this.handValue() >21) {
            return true;
        } else {return false;}
    }

}
