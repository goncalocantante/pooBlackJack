package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.enumerations.Rank;
import com.Casino.gamelogic.enumerations.Suit;

import java.util.ArrayList;

public class Player {

    //Game being played
    private Game game;
    //Player's balance
    private int balance;
    //ArrayList of the player's hands
    private ArrayList<Hand> hands;
    //True if player is insured
    private int insurance;

    /**
     * Contructor to initialize the player with 0 balance
     */
    public Player(Game game) {
        this.game = game;
        this.balance = 100;
        this.insurance = 0;
        this.hands = new ArrayList<Hand>();

        this.hands.add(new Hand());
    }

    /**
     * Constructor to initialize the player with desired balance
     * 
     * @param amount: initial balance of the player
     */
    public Player(Game game, int amount) {
        this.game = game;
        this.balance = amount;
        this.insurance = 0;
        this.hands = new ArrayList<Hand>();

        this.hands.add(new Hand());
    }

    /**
     * Gets the player's balance
     * @return balance: player's balance
     */
    public int getBalance() {
        return this.balance;
    }


    /**
     * Returns the amount bet as insurance
     * @return insurance: amount bet as insurance, 0 if not insured
     */
    private int getInsurance() { return this.insurance;}

    /**
     * Gets the player's hands
     * 
     * @return hands: player's hands
     */
    public ArrayList<Hand> getHands() {
        return hands;
    }

    /**
     * Adds a card to the specified hand and sets it face up
     * 
     * @param card:  card to be added
     * @param nHand: index of hand to which to add the card
     */
    public void addCard(Card card, int nHand) {
        card.setCardFaceUp();
        this.hands.get(nHand).receiveCard(card);
    }

    /**
     * Bet the requested amount, subtracting it from the player's balance
     * 
     * @param amount: amount to be bet
     */
    public void bet(int amount, int nHand) {

        if (this.balance >= amount) {
            System.out.println("Player has bet " + amount + "$");
            this.balance -= amount;
            this.hands.get(nHand).setBetAmount(amount);
        } else
            System.out.println("b: Illegal command(balance too low)");
    }

    /**
     * Hits, drawing a card from the shoe and adding it to one of the player´s hands
     * @param nHand: index of hand to be split
     */
    public void hit(int nHand) {
        System.out.println("Player has hit");
        this.addCard(this.game.getShoe().drawCard(), nHand);
        this.hands.get(nHand).drawCard();
    }

    /**
     * Stands, closing the hand to further plays
     *
     * @param nHand: index of hand to close
     */
    public void stand(int nHand) {
        System.out.println("Player has stand");
        this.hands.get(nHand).closeHand();
    }

    /**
     * Splits, if possible to
     *
     * @param nHand
     */
    public void split(int nHand) {
        Hand newHand = null;
        Hand originalHand = this.hands.get(nHand);
        //If the hand can be split
        if(this.canSplit(nHand)) {
            // Creates new hand
            this.hands.add(new Hand());

            newHand = this.hands.get(this.hands.size() - 1);
            // Draws card from hand to be split and adds it to a new hand at the end of
            // hands' list
            newHand.receiveCard(originalHand.drawCard());
            //Set the new hand's bet as the same as the original hand
            newHand.setBetAmount(originalHand.getBetAmount());
            //Both hands receive another card
            this.hit(nHand);
            this.hit(this.hands.size() - 1);
        }
    }

    /**
     * Checks if the specified hand can be split
     * @param nHand: index of hand to check
     * @return canSplit: true if the hand can be split
     */
    public boolean canSplit(int nHand) {
        Card card1 = this.hands.get(nHand).getCard(0);
        Card card2 = this.hands.get(nHand).getCard(1);
        int handSize = this.hands.get(nHand).getHandSize();
        int originalBet = this.hands.get(nHand).getBetAmount();
        //True if the hand has only 2 cards of equal rank and the player has enough money to split

        if(handSize != 2)
            System.out.println("p: Illegal command(cannot split after hitting)");
        if(!card1.getRank().equals(card2.getRank()))
            System.out.println("p: Illegal command(cards do not have the same rank)");
        if(this.balance < originalBet)
            System.out.println("p: Illegal command(balance too low)");
        return (handSize == 2 && card1.getRank().equals(card2.getRank()) && this.balance >= originalBet);
    }

    public void insure() {
        int originalBet = this.hands.get(0).getBetAmount();
        //If the player hasn't already insured
        if(this.insurance == 0) {
            //if he has enough money to insure
            if (this.balance >= originalBet) {
                //Insure
                this.insurance = originalBet;
                this.balance -= originalBet;
                System.out.println("Player has insured, " + originalBet + "$ bet");
            } else
                System.out.println("i: Illegal command(balance too low)");
        } else
            System.out.println("i: Illegal command(Cannot insure twice)");
    }

    /**
     * Surrender, reclaiming half your bet
     */
    public void surrender(int nHand) {
        if(this.hands.get(nHand).getHandSize() == 2) {
            //Print out informative message
            System.out.println("Player has surrendered. " + this.hands.get(nHand).getBetAmount() / 2 + "$ returned");
            //Return half the bet to the player
            this.balance += this.hands.get(0).getBetAmount() / 2;
            //Set the bet amount to 0
            this.hands.get(0).setBetAmount(0);
            //Close Hand
            this.hands.get(nHand).closeHand();
        } else
            System.out.println("u: Illegal command (cannot surrender after hitting)");

    }

    public void doubleBet(int nHand) {
        int originalBet = this.hands.get(nHand).getBetAmount();
        //If the player has enough to double his bet and has not hit yet
        if(this.balance >= originalBet && this.hands.get(nHand).getHandSize() == 2){
            //Double his bet
            this.hands.get(nHand).setBetAmount(2 * originalBet);
            System.out.println("Player has doubled his bet to " + 2*originalBet + "$");
            //Gets one more card
            this.hit(nHand);
            //Closes hand, not getting any more cards
            this.hands.get(nHand).closeHand();
        }else
            System.out.println("2: Illegal command(balance too low)");
    }

    /**
     * Returns the player's cards to the discard pile and sets the number of his
     * hands to 1
     */
    public void returnCards() {

        if (hands.size() > 1) {
            while (hands.size() != 0) {
                game.getDiscardPile().addAll(hands.get(1).emptyHand());
            }
        }

    }
}
