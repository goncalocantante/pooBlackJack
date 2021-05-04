package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.classes.Card;
import com.Casino.gamelogic.enumerations.Rank;
import com.Casino.gamelogic.enumerations.Suit;

import java.util.ArrayList;


public class Deck {

    //ArrayList containing the cards of the deck
    private ArrayList<Card> cards = new ArrayList<Card>();

    // constructor
    public Deck() {this.createDeck(); }

    /**
     * Creates a standard deck
     */
    public void createDeck(){
        for (Suit cardSuit : Suit.values()) {
            for (Rank cardValue : Rank.values()) {
                this.cards.add(new Card(cardSuit, cardValue));
            }
        }
    }

    /**
     * Gets the Deck's Cards
     * @return cards: List of Cards
     */
    public ArrayList<Card> getDeck() { return this.cards;}

    /**
     * Prints Deck (for test purposes)
     */
    public void printDeck( ){
        for(Card card : this.cards){
            System.out.println(card.getRank() + " " + card.getSuit());
        }
    }

}
