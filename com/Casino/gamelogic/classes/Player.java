package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.enumerations.Rank;
import com.Casino.gamelogic.enumerations.Suit;
import com.Casino.gamelogic.interfaces.Shoe;

import java.util.ArrayList;

public class Player {

    // Game being played
    private Game game;
    // Player's balance
    private int balance;
    // ArrayList of the player's hands
    private ArrayList<Hand> hands;
    // True if player is insured
    private int insurance;

    /**
     * Contructor to initialize the player with 0 balance
     */
    public Player(Game game) {
        this.game = game;
        this.balance = 0;
        this.insurance = 0;
        this.hands = new ArrayList<Hand>();

        this.hands.add(new Hand());
    }

    /**
     * Gets the player's balance
     * 
     * @return balance: player's balance
     */
    public int getBalance() {
        return this.balance;
    }

    /**
     * Adds a certain amount of money from player's balance
     * 
     * @return money to add
     */
    public void addBalance(int amount) {
        this.balance += amount;
    }

    /**
     * Subtracts a certain amount of money from player's balance
     * 
     * @return money to subtract
     */
    public void rmBalance(int amount) {
        this.balance -= amount;
    }

    /**
     * Returns the amount bet as insurance
     * 
     * @return insurance: amount bet as insurance, 0 if not insured
     */
    public int getInsurance() {
        return this.insurance;
    }

    /**
     * Gets one of the player
     * 
     * @return hands: player's hands
     */
    public Hand getHand(int i) {
        if (this.hands.size() > i) {
            return this.hands.get(i);
        } else {
            System.out.println("Error returning hand.");
            return null;
        }
    }

    /**
     * Gets all Hands of the player
     * 
     * @return hands: player's hands
     */
    public ArrayList<Hand> getHands() {
        return this.hands;
    }

    /**
     * Bet the requested amount, subtracting it from the player's balance
     * 
     * @param amount: amount to be bet
     */
    public void bet(int amount, int nHand) {

        int currentBet = this.hands.get(nHand).getBetAmount();

        if (this.canBet(amount)) {
            System.out.println("Player has bet " + amount + "$");
            this.rmBalance(amount);
            this.hands.get(nHand).setBetAmount(currentBet + amount);
        }
    }

    /**
     * Checks if the specified amount can be bet
     * @param amount: amount to be bet
     * @return
     */
    public boolean canBet(int amount){
        int minBet = this.game.getParameters()[0];
        int maxBet = this.game.getParameters()[1];

        System.out.println(this.balance >= amount && minBet < amount  && amount < maxBet);

        if (this.balance < amount)
            System.out.println("b: Illegal command(balance too low)");
        if(amount < minBet)
            System.out.println("b: Illegal command(bet must be higher than minimum bet)");
        if(amount > maxBet)
            System.out.println("b: Illegal command(bet must be lower than maximum bet)");

        return (this.balance >= amount && minBet <= amount  && amount <= maxBet);
    }

    /**
     * Hits, drawing a card from the shoe and adding it to one of the player´s hands
     * 
     * @param nHand: index of hand to be split
     */
    public void hit(int nHand) {
        Shoe shoe = this.game.getShoe();

        System.out.println("Player hits");
        this.hands.get(nHand).drawCard(shoe);
        System.out.println("Player's hand: " +this.game.getPlayer().getHand(nHand));
    }

    /**
     * Stands, closing the hand to further plays
     *
     * @param nHand: index of hand to close
     */
    public void stand(int nHand) {
        System.out.println("Player has stand");
        this.hands.get(nHand).closeHand();
        System.out.println("Player's hand: " +this.game.getPlayer().getHand(nHand));
    }

    /**
     * Splits, if possible to
     *
     * @param nHand
     */
    public void split(int nHand) {
        Hand newHand = null;
        Hand originalHand = this.hands.get(nHand);
        // If the hand can be split
        if (this.canSplit(nHand)) {
            System.out.println("Player splits");
            // Creates new hand
            this.hands.add(nHand+1, new Hand());
            newHand = this.hands.get(nHand+1);

            // Takes a card from originalHand and puts in newHand
            newHand.addCard(originalHand.getCard(0));
            originalHand.removeCard(0);
            // Set the new hand's bet as the same as the original hand
            newHand.setBetAmount(originalHand.getBetAmount());
            // Both hands receive another card
            this.hit(nHand);
        }
    }

    /**
     * Checks if the specified hand can be split
     * 
     * @param nHand: index of hand to check
     * @return canSplit: true if the hand can be split
     */
    public boolean canSplit(int nHand) {
        Card card1 = this.hands.get(nHand).getCard(0);
        Card card2 = this.hands.get(nHand).getCard(1);
        int handSize = this.hands.get(nHand).getHandSize();
        int originalBet = this.hands.get(nHand).getBetAmount();
        // True if the hand has only 2 cards of equal rank and the player has enough
        // money to split

        if (handSize != 2)
            System.out.println("p: Illegal command(cannot split after hitting)");
        if (card1.cardValue() != card2.cardValue())
            System.out.println("p: Illegal command(cards do not have the same value)");
        if (this.balance < originalBet)
            System.out.println("p: Illegal command(balance too low)");
        if (hands.size() > 3)
            System.out.println("p: Illegal command(can only split 3 times)");
        return (handSize == 2 && card1.cardValue() == card2.cardValue() && this.balance >= originalBet && hands.size() <= 4);
    }

    public void insure() {
        int originalBet = this.hands.get(0).getBetAmount();
        // If the player hasn't already insured
        if (this.canInsure()) {
            // Insure
            this.insurance = originalBet;
            this.balance -= originalBet;
            System.out.println("Player has insured, " + originalBet + "$ bet");
        }
    }

    public boolean canInsure() {
        int originalBet = this.hands.get(0).getBetAmount();
        Rank dealerCardRank = this.game.getDealer().getCard(0).getRank();
        //checks if we haven't insured yet and no other command given
        if(!dealerCardRank.equals(Rank.ACE))
            System.out.println("p: Illegal command(dealer's face-up card is not ace)");
        if (this.balance < originalBet)
            System.out.println("i: Illegal command(balance too low)");
        if (this.insurance != 0)
            System.out.println("i: Illegal command(cannot insure twice)");
        if (this.hands.get(0).getHandSize() > 2 || this.hands.size() > 1)
            System.out.println("i: Illegal command(insurance must be first command)");
        return (this.insurance == 0 && this.hands.get(0).getHandSize() == 2 && this.hands.size() == 1 && this.balance >= originalBet && dealerCardRank.equals(Rank.ACE));
    }

    /**
     * Surrender, reclaiming half your bet
     */
    public void surrender(int nHand) {
        if (this.hands.get(nHand).getHandSize() == 2) {
            // Print out informative message
            System.out.println("Player has surrendered. " + this.hands.get(nHand).getBetAmount() / 2 + "$ returned");
            // Return half the bet to the player
            this.balance += this.hands.get(0).getBetAmount() / 2;
            // Set the bet amount to 0
            this.hands.get(0).setBetAmount(0);
            // Close Hand
            this.hands.get(nHand).closeHand();
        } else
            System.out.println("u: Illegal command (cannot surrender after hitting)");

    }

    public void doubleBet(int nHand) {
        int originalBet = this.hands.get(nHand).getBetAmount();
        // if player can double
        if (canDouble (nHand)) {
            // Double his bet
            this.hands.get(nHand).setBetAmount(2 * originalBet);
            System.out.println("Player has doubled his bet to " + 2 * originalBet + "$");
            // Gets one more card
            this.hit(nHand);
            // Closes hand, not getting any more cards
            this.hands.get(nHand).closeHand();
        }
    }

    public boolean canDouble (int nHand){
        int originalBet = this.hands.get(nHand).getBetAmount();

        if (this.balance < originalBet)
            System.out.println("2: Illegal command(balance too low)");
        if (this.hands.get(nHand).getHandSize() > 2)
            System.out.println("2: Illegal command(cannot double after hitting)");
        if (this.hands.get(nHand).handValue() < 9 || this.hands.get(nHand).handValue() > 11)
            System.out.println("2: Illegal command(can only double on opening hand worth 9, 10 or 11)");
        return (this.balance >= originalBet && this.hands.get(nHand).getHandSize() == 2 && this.hands.get(nHand).handValue() >= 9 && this.hands.get(nHand).handValue() <= 11);
    }

    /**
     * Returns the player's cards to the discard pile and sets the number of his
     * hands to 1
     */
    public void playerResets() {

        ArrayList<Hand> playerHands = this.hands;

        // Clears all the hands to the discard pile
        for (Hand hand : playerHands) {
            hand.emptyHand(game.getDiscardPile());
        }
        // Reset player's hands to One
        while (playerHands.size() > 1) {
            playerHands.remove(0);
        }
        playerHands.get(0).setBetAmount(0);
        playerHands.get(0).openHand();

        //Insurance amount is set to 0
        this.insurance = 0;
    }


}
