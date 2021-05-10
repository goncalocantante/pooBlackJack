package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.enumerations.Rank;
import com.Casino.gamelogic.enumerations.Suit;

import java.util.ArrayList;

public class Player {

    private Game game;
    private int balance;
    private ArrayList<Hand> hands;

    /**
     * Contructor to initialize the player with 0 balance
     */
    public Player(Game game){
        this.balance=0;
        this.hands = new ArrayList<Hand>();

        this.hands.add(new Hand());
    }

    /**
     * Constructor to initialize the player with desired balance
     * @param amount: initial balance of the player
     */
    public Player(int amount){
        this.balance = amount;
        this.hands = new ArrayList<Hand>();
    }

    /**
     * Gets the player's balance
     * @return balance: player's balance
     */
    public int getBalance() { return this.balance;}

    /**
     * Adds a card to the specified hand
     * @param card: card to be added
     * @param i: index of hand to which to add the card
     */
    public void addCard(Card card, int i) {
        card.setCardFaceUp();
        this.hands.get(i).cards.add(card);
    }

    /**
     * Returns the player's cards to the discard pile and sets the number of his hands to 1
     */
    public void returnCards() {

        game.getDiscardPile().addAll(hands.get(0).cards);
        hands.get(0).cards.clear();

        if(hands.size() > 1) {
            while (hands.size() != 1){
                game.getDiscardPile().addAll(hands.get(1).cards);
                hands.remove(1);
            }
        }

    }
}
