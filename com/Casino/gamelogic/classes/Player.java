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
     * @param nHand: index of hand to which to add the card
     */
    public void addCard(Card card, int nHand) {
        card.setCardFaceUp();
        this.hands.get(nHand).receiveCard(card);
    }

    /**
     * Bet the requested amount, subtracting it from the player's balance
     * @param amount: amount to be bet
     */
    public void bet(int amount) { }

    /**
     * Hits, drawing a card from the shoe and adding it to one of the player´s hands
     * @param nHand: index of hand to be split
     */
    public void hit(int nHand) { this.addCard(this.game.getShoe().drawCard(), nHand); }

    public void stand() {}

    /**
     * Splits, if possible to
     * @param nHand
     */
    public void split(int nHand, int bet){
        //O if para ver se dá para fazer split fica aqui ou na cena para obter o input
            //Creates new hand
            this.hands.add(new Hand());
            //Draws card from hand to be split and adds it to a new hand at the end of hands' list
            this.hands.get(this.hands.size()-1).receiveCard(this.hands.get(nHand).drawCard());

            //-------------------------BET-------------------------------------------
        }

    public void insure() {}

    public void surrender() {}

    public void doubleBet() {}

    /**
     * Returns the player's cards to the discard pile and sets the number of his hands to 1
     */
    public void returnCards() {

        game.getDiscardPile().addAll(hands.get(0).emptyHand());

        if(hands.size() > 1) {
            while (hands.size() != 1){
                game.getDiscardPile().addAll(hands.get(1).emptyHand());
            }
        }

    }
}
