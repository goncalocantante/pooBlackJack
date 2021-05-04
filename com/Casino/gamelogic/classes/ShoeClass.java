package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.interfaces.Shoe;
import com.Casino.gamelogic.classes.Deck;

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

    public static void main(String[] args) {
        ShoeClass shoe = new ShoeClass(5);
        shoe.shuffle();
        System.out.println(shoe.getShoeSize());
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
     * @param card: card that will be added to the shoe
     */
    @Override
    public void addCard(Card card) { this.cards.add(card); }

    /**
     * Removes a card from the shoe
     * @param card: card that will be removed from the shoe
     */
    @Override
    public void removeCard() {this.cards.remove(0);}

    /**
     * Gets the card from the shoe without removing it
     * @param card: ----------
     */
    @Override
    public void getCard() {this.cards.get(0);}

    /**
     * Gets the size of the shoe
     * @param shoe: Shoe to get the size of
     */
    @Override
    public int getShoeSize() {return this.cards.size();}

    /**
     * ------------
     */
    @Override
    public void moveAllToShoe() {return;}
}
