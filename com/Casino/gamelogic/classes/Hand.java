package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.classes.Card;
import com.Casino.gamelogic.enumerations.Rank;
import com.Casino.gamelogic.enumerations.Suit;
import com.Casino.gamelogic.interfaces.Shoe;

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
    // Amount which the player has bet
    private int betAmount;

    /**
     * Constructor to initialize hand
     */
    public Hand() {
        this.cards = new ArrayList<Card>();
        this.handClosed = false;
        this.betAmount = 0;
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
    public void drawCard(Shoe shoe) {
        this.cards.add(shoe.getCard(0));
        shoe.removeCard(0);
    }

    /**
     * Clears hand and moves to discard pile
     *
     * @return buff: cards emptied from the hand / public ArrayList<Card>
     */
    public void emptyHand(ArrayList<Card> discardPile) {

        //Set all cards face up
        for(Card card : this.cards){ card.setCardFaceUp(); }
        //Add them to discard pile
        discardPile.addAll(this.cards);
        //Empty the hand
        this.cards.clear();
        return;
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
            if( card.getRank() == Rank.ACE){
                nAces++;
            }else{
                value += card.cardValue();
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
     * Indicates if the hand is soft
     *
     * @return value: true if soft, false if hard
     */
    public boolean isSoft() {
        int value = 0;
        int nAces = 0;

        for (Card card : this.cards) {
            if( card.getRank() == Rank.ACE){
                nAces++;
            }else{
                value += card.cardValue();
            }
        }
        for (int i = 0; i < nAces; i++) {
            if (value + 11 < 21) {
                return true;
            } else {
                value += 1;
            }
        }
        return false;
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
     * Opens hand
     */
    public void openHand() { this.handClosed = false;}

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

    /**
     * Gets the player's bet amount
     * 
     * @return betAmount:player's bet amount
     */
    public int getBetAmount() {
        return this.betAmount;
    }

    /**
     * Sets the bet amount for this hand
     * 
     * @param bet: bet amount
     */
    public void setBetAmount(int bet) {
        this.betAmount = bet;
    }

    /**
     * Indicates whether or not hand is empty
     *
     * @return
     */
    public boolean isEmpty() {
        return this.cards.isEmpty();
    }

    public String toString() {
        String cardListOutput = "";
        for (Card aCard : this.cards) {
            cardListOutput += aCard.toString() + " ";
        }
        return cardListOutput;
    }


}
