package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.interfaces.Shoe;
import com.Casino.gamelogic.classes.Deck;
import  com.Casino.gamelogic.classes.Card;

import java.util.ArrayList;
import java.util.Collections;

public class ShoeClass implements Shoe{

    ArrayList<Card> cards = new ArrayList<Card>();

    public ShoeClass(int ndecks){
        this.createShoe(ndecks);
    }
    /**
     *Creates the deck with the specified number of decks
     * @param ndecks: number of decks contained in the shoe
     */
    @Override
    public void createShoe(int ndecks){
        Deck deck;
        for(int i=0; i<ndecks; i++){
            deck = new Deck();
            this.cards.addAll(deck.getDeck());
        }
    }

    /**
     * Prints all cards in the shoe
     */
    public void printShoe(){
        for(Card card: this.cards){
            System.out.println(card.getRank() + " " + card.getSuit());
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
     * Adds a card to the shoe
     * @param cards: card that will be added to the shoe
     */
    @Override
    public void addCards(ArrayList<Card> cards) { this.cards.addAll(cards); }

    /**
     * Removes a card from the shoe
     */
    @Override
    public void removeCard() {this.cards.remove(0);}

    /**
     * Gets the card from the shoe without removing it
     */
    @Override
    public Card getCard() {return this.cards.get(0);}

    /**
     * Draws card from the shoe
     * @return card: card drawn
     */
    public Card drawCard() {
        Card buff = cards.get(0);
        removeCard();
        return buff;
    }

    /**
     * Gets the size of the shoe
     */
    @Override
    public int getShoeSize() {return this.cards.size();}

    /**
     * ------------
     */
    @Override
    public void moveAllToShoe() { return;}
}
