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
     * @return Card: requested card
     */
    public Card getCard(int ncard) { return cards.get(ncard);}

    /**
     * Receives and adds card to the hand
     * @param card: card to receive
     */
    public void receiveCard(Card card) { cards.add(card);}

    /**
     * Removes the card in position i of the cards array
     * @param ncard: index of cards ArrayList to remove
     */
    public void removeCard(int ncard) { this.cards.remove(ncard);}

    /**
     * Indicates the total hand value
     * @return value: value of the hand
     */
    public int handValue() { return 0;}

    /**
     * Indicates the number of cards in the hand
     * @return ncards: number of cards in the hand
     */
    public int getHandSize() {return this.cards.size();}

    /**
     * Indicates if the hand has bust
     * @return isBustTrue: is true if the hand has busted
     */

    public boolean isBust() { return this.handValue() > 21;}

    /**
     * Checks if the hand can be split
     * @return canSplitTrue: true if the hand can be split
     */
    public boolean canSplit() {
        if(this.getHandSize() == 2 && this.getCard(0).getRank().equals(this.getCard(1).getRank()) ){
            return true;
        } else { return false;}
    }

}
