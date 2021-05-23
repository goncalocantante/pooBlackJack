package com.Casino.BlackJack.classes.GameLogic;

import com.Casino.BlackJack.enumerations.Rank;
import com.Casino.BlackJack.interfaces.Shoe;

import java.util.ArrayList;

public class Player {

    // Game being played
    private Game game;
    // Player's balance
    private int balance, initialBalance;
    // ArrayList of the player's hands
    private ArrayList<Hand> hands;
    // True if player is insured
    private int insurance;
    // Variable that contains the number of wins/pushes/losses of previous round
    // (+1 for every win, +0 for every push and -1 for every loss)
    private int lastResult;


    /**
     * Contructor to initialize the player with 0 balance
     */
    public Player(Game game) {
        this.game = game;
        this.balance = 0;
        this.initialBalance = 0;
        this.insurance = 0;
        //Initialized as -5 to show that no hands have been played
        this.lastResult = -5;
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
     * @return money to add
     */
    public void addBalance(int amount) {
        this.balance += amount;
    }

    /**
     * Sets the initial balance
     * @param amount: initial balance
     */
    public void setInitialBalance(int amount){this.initialBalance = amount;}

    /**
     * Gets the player's initial balance
     * @return initialBalance: player's initial balace
     */
    public int getInitialBalance(){return this.initialBalance;}
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
     * Gets the specified player's hand
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
     * Gets the player's hands
     * @return hands: player's hands
     */
    public ArrayList<Hand> getHands() {
        return this.hands;
    }

    /**
     * Bet the requested amount, subtracting it from the player's balance
     * @param amount: amount to be bet
     */
    public boolean bet(int amount, int nHand) {

        int currentBet = this.hands.get(nHand).getBetAmount();

        if (this.canBet(amount)) {
            System.out.println("player is betting " + amount);
            this.rmBalance(amount);
            this.hands.get(nHand).setBetAmount(currentBet + amount);

            return true;
        }
        return false;
    }

    /**
     * Checks if the specified amount can be bet
     * @param amount: amount to be bet
     * @return
     */
    public boolean canBet(int amount){
        int minBet = this.game.getParameters()[0];
        int maxBet = this.game.getParameters()[1];

        //Check if player has enough to bet the amount and if the amount is within the required limits
        if (this.balance < amount)
            System.out.println("b: Illegal command(balance too low)");
        if(amount < minBet)
            System.out.println("b: Illegal command(bet must be higher than minimum bet)");
        if(amount > maxBet)
            System.out.println("b: Illegal command(bet must be lower than maximum bet)");

        return (this.balance >= amount && minBet <= amount  && amount <= maxBet);
    }

    /**
     * Hits, drawing a card from the shoe and adding it to one of the player´s hands,
     * prints the updated hand and updates the running count
     * @param nHand: index of hand to be split
     */
    public void hit(int nHand) {
        Shoe shoe = this.game.getShoe();

        System.out.println("player hits");
        this.hands.get(nHand).drawCard(shoe);
        System.out.println("player's hand: " + this.game.getPlayer().getHand(nHand) + "(" + this.game.getPlayer().getHand(nHand).handValue() + ")");
        System.out.println("");
        //Update running count
        this.game.updateRunningCount();
    }

    /**
     * Stands, closing the hand to further plays
     * @param nHand: index of hand to close
     */
    public void stand(int nHand) {
        System.out.println("player stands");
        this.hands.get(nHand).closeHand();
        System.out.println("player's hand " +this.game.getPlayer().getHand(nHand));
    }

    /**
     * Splits, if possible
     * @param nHand: hand to split
     */
    public void split(int nHand) {
        Hand newHand = null;
        Hand originalHand = this.hands.get(nHand);
        // If the hand can be split
        if (this.canSplit(nHand)) {
            System.out.println("player splits");
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
        else { System.out.println("p: Illegal command"); }
    }

    /**
     * Splits even if player doesn't have enough balance and even if he has split 3 or more times
     * @param nHand: hand to split
     */
    public void forcedSplit(int nHand) {
        Hand newHand = null;
        Hand originalHand = this.hands.get(nHand);

        Card card1 = this.hands.get(nHand).getCard(0);
        Card card2 = this.hands.get(nHand).getCard(1);

        //If hand only has 2 cards of the same value
        if (originalHand.getHandSize() == 2 && card1.cardValue() == card2.cardValue()) {
            System.out.println("player splits");
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
     * @param nHand: index of hand to check
     * @return canSplit: true if the hand can be split
     */
    public boolean canSplit(int nHand) {
        Card card1 = this.hands.get(nHand).getCard(0);
        Card card2 = this.hands.get(nHand).getCard(1);
        int handSize = this.hands.get(nHand).getHandSize();
        int originalBet = this.hands.get(nHand).getBetAmount();

        // Check if the hand has only 2 cards of equal rank, if the player has enough
        // money to split and if the player hasn't split more than 3 times
        if (handSize != 2)
            System.out.println("cannot split after hitting");
        if (card1.cardValue() != card2.cardValue())
            System.out.println("cannot split if cards do not have the same value");
        if (this.balance < originalBet)
            System.out.println("balance too low to split");
        if (hands.size() > 3)
            System.out.println("can only split 3 times");
        return (handSize == 2 && card1.cardValue() == card2.cardValue() && this.balance >= originalBet && hands.size() <= 4);
    }

    /**
     * Insures if possible
     */
    public void insure() {
        int originalBet = this.hands.get(0).getBetAmount();
        // If the player can insure
        if (this.canInsure()) {
            // Insure
            this.insurance = originalBet;
            this.balance -= originalBet;
            System.out.println("player insures, " + originalBet + "$ bet");
        }
        else { System.out.println("p: Illegal command");}
    }

    /**
     * Check if the player can insure
     * @return canInsure: true if the player can insure
     */
    public boolean canInsure() {
        int originalBet = this.hands.get(0).getBetAmount();
        Rank dealerCardRank = this.game.getDealer().getCard(0).getRank();

        // Check if dealer's card is an ace, if player has enough money to insure,
        // if player has already insured and if he hassn't already hit
        if(!dealerCardRank.equals(Rank.ACE))
            System.out.println("dealer's face-up card is not ace, cannot insure");
        if (this.balance < originalBet)
            System.out.println("balance too low to insure");
        if (this.insurance != 0)
            System.out.println("cannot insure twice");
        if (this.hands.get(0).getHandSize() > 2 || this.hands.size() > 1)
            System.out.println("insurance must be first command");
        return (this.insurance == 0 && this.hands.get(0).getHandSize() == 2 && this.hands.size() == 1 && this.balance >= originalBet && dealerCardRank.equals(Rank.ACE));
    }

    /**
     * Surrender, reclaiming half the bet
     */
    public void surrender(int nHand) {
        if (canSurrender(nHand)) {
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


    /**
     * Checks if the player can surrender
     * @param nHand: hand to check
     * @return canSurrender: true if the player can surrender
     */
    public boolean canSurrender (int nHand) {
        //Check if player hasn't already hit
        if (this.hands.get(nHand).getHandSize() == 2){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Doubles the bet
     * @param nHand: hand to double
     */
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
        else { System.out.println("2: Illegal command"); }
    }

    public boolean canDouble (int nHand){
        int originalBet = this.hands.get(nHand).getBetAmount();

        //Check if player has enough to double, if player hasn't already hit and if player's
        // hand value is between 9 and 11
        if (this.balance < originalBet)
            System.out.println("balance too low to double");
        if (this.hands.get(nHand).getHandSize() > 2)
            System.out.println("cannot double after hitting");
        if (this.hands.get(nHand).handValue() < 9 || this.hands.get(nHand).handValue() > 11)
            System.out.println("can only double on opening hand worth 9, 10 or 11");
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

    /**
     * Gets the last round's result
     * @return lastResult: variable representing the number of wins/pushes/losses
     */
    public int getLastResult() {
        return this.lastResult;
    }

    /**
     * Adds win/push/loss to the lastResult variable
     * @param result: int worth 1 for win, 0 for push and -1 for loss
     */
    public void setLastResult(int result) {
        this.lastResult = this.lastResult + result;
    }

    /**
     * Resets lastResult variable
     */
    public void clearLastResult (){
        this.lastResult = 0;
    }
}
