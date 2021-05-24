package com.Casino.BlackJack.classes.GameLogic;

import com.Casino.BlackJack.classes.GameStates.DealerTurnState;
import com.Casino.BlackJack.classes.GameStates.GameStartState;
import com.Casino.BlackJack.classes.GameStates.PlayerTurnState;
import com.Casino.BlackJack.classes.GameStates.RoundEndState;
import com.Casino.BlackJack.enumerations.Rank;
import com.Casino.BlackJack.interfaces.GameState;
import com.Casino.BlackJack.interfaces.Mode;
import com.Casino.BlackJack.interfaces.Shoe;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 * Game being played
 */
public class Game {

    private Shoe shoe;
    private Player player;
    //Dealer's hand
    private Hand dealer;
    //Discard pile where cards will go at the end of the round
    private ArrayList<Card> discardPile;
    //Parameters specified by player
    private int minBet, maxBet, shuffle, nShuffle;
    //Running count according to Hi-Lo strategy
    private int runningCount;
    private int previousBet;
    private int initialShoeSize;
    //Conter for the number of times the shoe was shuffled
    private int shuffleCount;

    //Variables to count the statistics
    public int totalPlayerHandsCount, totalDealerHandsCount, totalPlayerWins, playerBlackJackCount, dealerBlackJackCount, totalPushes;

    Mode gameMode;

    //Current game state
    private GameState gameState;
    //Various game states
    private GameState gameStartState;
    private GameState playerTurnState;
    private GameState dealerTurnState;
    private GameState roundEndState;

    /**
     * Constructor that initializes various variables
     * @param gameMode: game mode to be played
     */
    public Game(Mode gameMode) {
        this.gameStartState = new GameStartState(this);
        this.playerTurnState = new PlayerTurnState(this);
        this.dealerTurnState = new DealerTurnState(this);
        this.roundEndState = new RoundEndState(this);


        this.player = new Player(this);
        this.dealer = new Hand();
        this.discardPile = new ArrayList<Card>();
        this.gameMode = gameMode;

        this.gameState = this.gameStartState;

        this.totalPlayerHandsCount = 1;
        this.totalDealerHandsCount = 1;
        this.totalPlayerWins = 0;
        this.playerBlackJackCount = 0;
        this.dealerBlackJackCount = 0;
        this.totalPushes = 0;
        this.runningCount = 0;
        this.previousBet = 0;
        this.initialShoeSize = 0;
        this.shuffleCount = 0;
    }

    /**
     * Sets the initial shoe size
     * @param size: initial shoe size to be set
     */
    public void setInitialShoeSize(int size) { this.initialShoeSize = size;}

    /**
     * Sets the current state to the specified state
     * 
     * @param state: state to be set as current state
     */
    public void setGameState(GameState state) {
        this.gameState = state;
    }

    /**
     * Calls method initializeGame() for the current game state
     */
    public void initializeGame() {
        gameState.initializeGame();
    }

    /**
     * Calls method playerTurn() for the current game state
     */
    public void playerTurn() {
        gameState.playerTurn();
    }

    /**
     * Calls method dealerTurn() for the current game state
     */
    public void dealerTurn() {
        gameState.dealerTurn();
    }

    /**
     * Calls method finishRound() for the current game state
     */
    public void finishRound() {
        gameState.finishRound();
    }


    /**
     * Gets the game's start state
     * 
     * @return gameStartState: Game's start state
     */
    public GameState getGameStartState() {
        return this.gameStartState;
    }

    /**
     * Gets the player's turn state
     * 
     * @return playerTurnState: Player's turn state
     */
    public GameState getPlayerTurnState() {
        return this.playerTurnState;
    }

    /**
     * Gets the dealer's turn state
     * 
     * @return dealerTurnState: Dealer's turn state
     */
    public GameState getDealerTurnState() {
        return this.dealerTurnState;
    }

    /**
     * Gets the round's end state
     * 
     * @return roundEndState: Round's end state
     */
    public GameState getRoundEndState() {
        return this.roundEndState;
    }

    /**
     * Gets shoe
     * 
     * @return shoe: game's shoe
     */
    public Shoe getShoe() {
        return this.shoe;
    }

    /**
     * Sets the game's shoe to the provided shoe
     * @param shoe: shoe to be set used in the game
     */
    public void setShoe(Shoe shoe) {
        if(this.shoe == null)
            this.shoe = shoe;
        else
            System.out.println("Shoe already set");
    }

    /**
     * Gets the player
     * 
     * @return player: game's player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Gets the dealer
     * 
     * @return player: game's dealer
     */
    public Hand getDealer() {
        return dealer;
    }

    /**
     * Gets the discard pile
     * @return discardPile: discard pile
     */
    public ArrayList<Card> getDiscardPile() {
        return discardPile;
    }

    /**
     * Gets the game mode
     * @return gameMode: game mode
     */
    public Mode getGameMode() { return this.gameMode; }

    /**
     * Gets the previous bet
     * @return previousBet: previous bet
     */
    public int getPreviousBet() { return this.previousBet;}

    /**
     * Sets the previous bet
     * @param prevBet: previous bet to set
     */
    public void setPreviousBet(int prevBet) { this.previousBet = prevBet;}

    /**
     * Sets the game parameters: min bet, max bet, balance, shuffle percentage and number of shuffles (simulation mode)
     * @param minBet: minimum bet
     * @param maxBet: maximum bet
     * @param balance: player's balance
     * @param shuffle: shuffle percentage (percentage of cards played until shuffle)
     * @param nShuffle: number of shuffles until game ends
     */
    public void setParameters(int minBet, int maxBet, int balance, int shuffle, int nShuffle){
        this.minBet = minBet;
        this.maxBet = maxBet;
        this.player.addBalance(balance);
        this.player.setInitialBalance(balance);
        this.shuffle = shuffle;
        this.nShuffle = nShuffle;
    }
    /**
     * Gets the game parameters: min bet, max bet, balance, shuffle percentage and number
     * of shuffles until game end(-1 in interactive mode)
     * @return params: game parameters
     */
    public int[] getParameters(){
        int[] params = new int[]{this.minBet, this.maxBet, this.shuffle, this.nShuffle};

        return params;
    }

    /**
     * Gets the running count of the game
     * @return runningCount: Running count
     */
    public int getRunningCount() { return this.runningCount;}

    /**
     * Updates the running count or resets it
     */
    public void updateRunningCount() {
        int count = 0;

        //If there are no cards in the discard pile or in the player's hands, reset the counter
        if(this.discardPile.isEmpty() && this.player.getHand(0).isEmpty()) {
            this.runningCount = 0;
            return;
        }

        //Count the cards in discard pile
        for(int i=0; i< this.discardPile.size(); i++){
            switch(this.discardPile.get(i).getRank()){
                case TWO:
                case THREE:
                case FOUR:
                case FIVE:
                case SIX:
                    count++;
                    break;
                case TEN:
                case JACK:
                case KING:
                case QUEEN:
                case ACE:
                    count--;
                    break;
            }
        }

        //Count the face-up cards in the dealer's hand if it's not empty
        if(!this.dealer.isEmpty()){
            for(int i=0; i < this.dealer.getHandSize(); i++){
                if(this.dealer.getCard(i).isCardFaceUp()) {
                    switch (this.dealer.getCard(i).getRank()) {
                        case TWO:
                        case THREE:
                        case FOUR:
                        case FIVE:
                        case SIX:
                            if (this.dealer.getCard(i).isCardFaceUp())
                                count++;
                            break;
                        case TEN:
                        case JACK:
                        case KING:
                        case QUEEN:
                        case ACE:
                            if (this.dealer.getCard(i).isCardFaceUp())
                                count--;
                            break;
                    }
                }
            }
        }

        //Count the cards in the player's hands if they're not empty
        for(Hand hand : this.player.getHands()) {
            if (!hand.isEmpty()) {
                for (int i = 0; i < hand.getHandSize(); i++) {
                    switch (hand.getCard(i).getRank()) {
                        case TWO:
                        case THREE:
                        case FOUR:
                        case FIVE:
                        case SIX:
                            count++;
                            break;
                        case TEN:
                        case JACK:
                        case KING:
                        case QUEEN:
                        case ACE:
                            count--;
                            break;
                    }
                }
            }
        }

        this.runningCount = count;
    }

    /**
     * Gets the shuffle count
     * @return shuffleCount: shuffle count
     */
    public int getShuffleCount() { return this.shuffleCount;}

    /**
     * Shuffles if shuffle percentage has been met
     */
    public void shuffleIfNeeded() {
        //Number of cards outside of shoe
        int cardsInUse = this.discardPile.size() + this.dealer.getHandSize();

        for(Hand hand: this.player.getHands())
            cardsInUse += hand.getHandSize();

        double shufflePercentage = (double) this.shuffle/100d;
        // Limit of cards drawn until there's a shuffle
        double nCardsShuffle = this.initialShoeSize * (shufflePercentage);

        //Shuffles if the card limit has been reached
        if(cardsInUse >= nCardsShuffle) {
            //Move cards back to shoe
            this.shoe.moveAllToShoe(this.discardPile);
            //Empty discard pile
            this.discardPile.clear();
            this.shoe.shuffle();
            //Increment number of times shuffled
            this.shuffleCount++;
            //Reset running count
            this.updateRunningCount();
        }
    }

    /**
     * Calculates and prints out the statistics of the game
     */
    public void statistics(){
        //Player blackjack ratio
        float N1 = (float)this.playerBlackJackCount/(float)this.totalPlayerHandsCount;
        //Dealer blackjack ratio
        float N2 = (float)this.dealerBlackJackCount/(float)this.totalDealerHandsCount;
        //Win ratio
        float N3 = (float)this.totalPlayerWins/(float)this.totalPlayerHandsCount;
        float totalLosses = this.totalPlayerHandsCount - this.totalPlayerWins - this.totalPushes;
        //Loss ratio
        float N4 = totalLosses/(float)this.totalPlayerHandsCount;
        //Push ratio
        float N5 = (float)this.totalPushes/(float)this.totalPlayerHandsCount;
        //Gains in percentage relative to initial balance
        int N7 = (int)((float)this.player.getBalance()/(float)this.player.getInitialBalance()*100f);

        //Print Statistics
        System.out.format("%-10s%-10s%-2.3f/%-2.3f%-8s\n", "BJ", "P/D", N1, N2, "");
        System.out.format("%-10s%-10s%-10.3f\n", "Win", "", N3);
        System.out.format("%-10s%-10s%-10.3f\n", "Lose", "", N4);
        System.out.format("%-10s%-10s%-10.3f\n", "Push", "", N5);
        System.out.format("%-10s%-10s%-5d(%d%%)\n", "Balance", "", this.player.getBalance(), N7);
    }

    /**
     * Analizes the current hand and returns the appropriate command according to basic strategy
     * @param nHand: index of the hand to analize
     * @return action: appropriate command
     */
    public String basicStrategy (int nHand) {

        //Basic strategy table
        Tables table = new Tables();

        String action = "";
        Card card1 = this.player.getHand(nHand).getCard(0);
        Card card2 = this.player.getHand(nHand).getCard(1);
        int playerValue = this.player.getHand(nHand).handValue();
        int dealerValue = this.dealer.getCard(0).cardValue();

        //If player's hand is a pair access pair table
        if (card1.getRank().equals(card2.getRank()) && this.player.getHand(nHand).getHandSize() == 2){
            action = String.valueOf(table.getAction (1, playerValue, dealerValue));
        }//If player's hand is hard, access hard table
        else if (!this.player.getHand(nHand).isSoft()){
            action = String.valueOf(table.getAction (2, playerValue, dealerValue));
        }//If player's is soft access soft table
        else if (this.player.getHand(nHand).isSoft()){
            action = String.valueOf(table.getAction (3, playerValue, dealerValue));
        }//If action is double if possible or hit, double if possible or hit
        if (action.equals("D")){
            if (this.player.canDouble(nHand)){
                action = "2";
            } else {
                action = "h";
            }
        }//If action is double if possible or stand, double if possible or stand
        else if (action.equals("d")){
            if (this.player.canDouble(nHand)){
                action = "2";
            } else {
                action = "s";
            }
        }//If action is surrender if possible or hit, surrender if possible or hit
        else if (action.equals("R")){
            if (this.player.canSurrender(nHand)){
                action = "u";
            } else {
                action = "h";
            }
        }//If action is split, split if possible or forced split (split even if balance is insufficient or if already has split 3 times)
        else if(action.equals("P")) {
            if (!this.player.canSplit(nHand)){
                System.out.println("On second thought, more split allowed. Exception for simulation mode made");
                action = "f";
            }
        }//Set the action string to lower case
            action = action.toLowerCase(Locale.ROOT);

        return action;
    }

    /**
     * Returns the appropriate betting command according to ace five strategy
     * @return action: appropriate betting command
     */
    public String aceFiveBet () {

        int counter = 0;
        int aceFive = 0;
        int toBet;

        //Increments counter for every five in discard pile and decrements it for every ace
        for (counter = 0; counter < this.discardPile.size(); counter++){
            if (this.discardPile.get(counter).equals(Rank.FIVE)) {
                aceFive++;
            } else if (this.discardPile.get(counter).equals(Rank.ACE)) {
                aceFive--;
            }
        }

        //Double the previous bet
        toBet = this.previousBet*2;

        //Set bet to maximum bet if it surpasses this value
        if (toBet > this.maxBet){
            toBet = this.maxBet;
        }

        //Bet minimum bet if counter is equal or less than 1
        if (aceFive <= 1){
            this.previousBet = this.minBet;
            return ("b " + this.minBet);
        }
        else {//Else, double the bet
            this.previousBet = toBet;
            return ("b " + toBet);
        }
    }

    /**
     * Returns the appropriate betting command according to standard betting strategy
     * @return action: appropriate betting command
     */
    public String standardBetStrategy() {
        //Value to bet
        int toBet;

        //if it is the first bet of the game, use minBet
        if (this.player.getLastResult() == -5){
            this.previousBet = this.minBet;
            return ("b " + this.minBet);
        }

        //Set next bet equal to previous bet and add minimum bet
        // for every win and subtract minimum bet for every loss in previous round
        toBet = this.player.getLastResult()*this.minBet + previousBet;

        //If the bet exceeds maxBet or is less than minBet, then set it as maxBet or minBet respectively
        if (toBet < this.minBet){
            toBet = this.minBet;
        }
        else if (toBet > this.maxBet){
            toBet = this.maxBet;
        }
        this.previousBet = toBet;
        return ("b " + toBet);
    }

    /**
     * Returns the appropriate command according to Hi-Lo strategy, checking Illustrious 18
     *  and Fab 4 rules according to the running count and using basic strategy otherwise
     * @param nHand: index of hand being played
     * @param cardsInShoe: initial number of cards in the shoe
     * @return toReturn: appropriate command
     */
    public String hiLo(int nHand, int cardsInShoe) {
        //Running count according to Hi-Lo strategy
        int runningCount = 0;
        int cardsDiscarded = 0;
        double decksRemaining = 0;
        double trueCount = 0;
        String toReturn;

        cardsDiscarded = this.discardPile.size();

        runningCount = this.getRunningCount();

        decksRemaining = (cardsInShoe - cardsDiscarded)/52d;

        trueCount = runningCount/decksRemaining;

        toReturn = fab4(nHand, trueCount);
        if (!toReturn.equals("basic")){
            return toReturn;
        }

        return (illustrious18(nHand, trueCount));
    }

    /**
     * Checks every one of the Illustrious 18 rules and returns the appropriate command if
     * the condition verifies, returning "basic" otherwise
     * @param nHand: index of hand being played
     * @param trueCount: true count of the game
     * @return action: appropriate command
     */
    public String illustrious18 (int nHand, double trueCount){
        //Player's hand value
        int playerHand = this.getPlayer().getHand(nHand).handValue();
        //Dealer's face-up card value
        int dealerHand = this.getDealer().handValue();
        boolean playerHandSizeIs2 = this.getPlayer().getHand(nHand).getHandSize() == 2;
        boolean cardsEqual = this.getPlayer().getHand(nHand).getCard(0).cardValue() == this.getPlayer().getHand(nHand). getCard(1).cardValue();

        //Checks every Illustrious 18 rule
        if(trueCount >= 3 && this.getPlayer().canInsure()){
            this.getPlayer().insure();
        } else if (playerHand == 16 && dealerHand == 10){
            return standOrHit(trueCount, 0);
        } else if (playerHand == 15 && dealerHand == 10){
            return standOrHit(trueCount, 4);
        } else if (playerHand == 20 && dealerHand == 5 && playerHandSizeIs2 && cardsEqual){
            return splitOrStand(trueCount, 5);
        } else if (playerHand == 20 && dealerHand == 6 && playerHandSizeIs2 && cardsEqual){
            return splitOrStand(trueCount, 4);
        } else if (playerHand == 10 && dealerHand == 10 && playerHandSizeIs2){
            return doubleOrHit(trueCount, 4);
        } else if (playerHand == 12 && dealerHand == 3){
            return standOrHit(trueCount, 2);
        } else if (playerHand == 12 && dealerHand == 2){
            return standOrHit(trueCount, 3);
        } else if (playerHand == 11 && dealerHand == 11 && playerHandSizeIs2){
            return doubleOrHit(trueCount, 1);
        } else if (playerHand == 9 && dealerHand == 2 && playerHandSizeIs2){
            return doubleOrHit(trueCount, 1);
        } else if (playerHand == 10 && dealerHand == 11 && playerHandSizeIs2){
            return doubleOrHit(trueCount, 4);
        } else if (playerHand == 9 && dealerHand == 7 && playerHandSizeIs2){
            return doubleOrHit(trueCount, 3);
        } else if (playerHand == 16 && dealerHand == 9){
            return standOrHit(trueCount, 5);
        } else if (playerHand == 13 && dealerHand == 2){
            return standOrHit(trueCount, -1);
        } else if (playerHand == 12 && dealerHand == 4){
            return standOrHit(trueCount, 0);
        } else if (playerHand == 12 && dealerHand == 5){
            return standOrHit(trueCount, -2);
        } else if (playerHand == 12 && dealerHand == 6){
            return standOrHit(trueCount, -1);
        }else if (playerHand == 13 && dealerHand == 3){
            return standOrHit(trueCount, -2);
        }//Return basic if no rule verifies
        return "basic";
    }

    /**
     * Stand if index less than or equal to true count, hit otherwise
     * @param trueCount: true count of the game
     * @param indexNumber: index according to the respective Illustrious 18 rule
     * @return command: appropriate command
     */
    public String standOrHit (double trueCount, int indexNumber) {

        if (trueCount >= indexNumber){
            return "s";
        }
        else{
            return "h";
        }
    }

    /**
     * Split (forced split) if index less than or equal to true count, hit otherwise
     * @param trueCount: true count of the game
     * @param indexNumber: index according to the respective Illustrious 18 rule
     * @return command: appropriate command
     */
    public String splitOrStand (double trueCount, int indexNumber) {

        if (trueCount >= indexNumber){
            return "f";
        }
        else{
            return "s";
        }
    }

    /**
     * Double if index less than or equal to true count, hit otherwise
     * @param trueCount: true count of the game
     * @param indexNumber: index according to the respective Illustrious 18 rule
     * @return command: appropriate command
     */
    public String doubleOrHit (double trueCount, int indexNumber) {

        if (trueCount >= indexNumber){
            return "2";
        }
        else{
            return "h";
        }
    }

    /**
     * Checks every one of the Fab 4 rules and returns the appropriate command if
     * the condition verifies, returning "basic" otherwise
     * @param nHand: index of hand being played
     * @param trueCount: true count of the game
     * @return action: appropriate command
     */
    public String fab4 (int nHand, double trueCount){
        int playerHand = this.getPlayer().getHand(nHand).handValue();
        int dealerHand = this.getDealer().handValue();

        //Checks every Fab 4 rule
        if (playerHand == 14 && dealerHand == 10){
            return surrenderOrBasic(trueCount, 3, nHand);
        } else if (playerHand == 15 && dealerHand == 9){
            return surrenderOrBasic(trueCount, 2, nHand);
        } else if (playerHand == 15 && dealerHand == 11){
            return surrenderOrBasic(trueCount, 1, nHand);
        } else if (playerHand == 15 && dealerHand == 10){
            if (trueCount <= 0 && trueCount >= 3){
                return "u";
            } else if (trueCount >= 4){
                return "s";
            }
            else {
                return "h";
            }
        }//Returns basic if no rule verifies
        return "basic";

    }

    /**
     * Surrender if index less than or equal to true count and the player can surrender, hit otherwise
     * @param trueCount: true count of the game
     * @param indexNumber: index according to the respective Illustrious 18 rule
     * @param nHand: index of hand being played
     * @return command: appropriate command
     */
    public String surrenderOrBasic (double trueCount, int indexNumber, int nHand){

        if (trueCount >= indexNumber && player.canSurrender(nHand)){
            return "u";
        }
        else{
            return "basic";
        }
    }

    /**
     * Prints the advised action to take according to Ace-Five strategy if player is betting
     * or according to Hi-Lo and basic strategy if player is playing the hand
     * @param nHand: index of hand being played
     */
    public void advice(int nHand) {
        String action;

        //If cards haven't been dealt(if dealer's hand is empty), print bet advice
        if(this.player.getHand(0).isEmpty()) {
            action = this.aceFiveBet();
            Scanner scanner = new Scanner(action);
            //skip b character
            scanner.next();
            //Gets the advised bet amount and prints advice
            if (scanner.hasNextInt()){
                System.out.println("bet " + scanner.nextInt());
            }
            scanner.close();
        }//If cards have already been dealt
        else{
            //Get Hi-Lo command
            action = this.hiLo(nHand, this.getShoe().getShoeSize());
            //If Hi-Lo advice is basic strategy, get basic strategy command
            if(action.equals("basic"))
                action = this.basicStrategy(nHand);

            //Change command from character to string
            switch(action.charAt(0)){
                case 'h':
                    action = "hit";
                    break;
                case 's':
                    action = "stand";
                    break;
                case '2':
                    action = "double";
                    break;
                case 'u':
                    action = "surrender";
                    break;
                case 'p':
                case 'f':
                    action = "split";
                    break;
                }
            //Print out Hi-Lo advice
            System.out.println("Hi-lo: " + action);

            //Get basic strategy advice and print it
            action = this.basicStrategy(nHand);
            switch(action.charAt(0)){
                case 'h':
                    action = "hit";
                    break;
                case 's':
                    action = "stand";
                    break;
                case '2':
                    action = "double";
                    break;
                case 'u':
                    action = "surrender";
                    break;
                case 'p':
                case 'f':
                    action = "split";
                    break;
            }
            System.out.println("Basic Strategy: " + action);
        }
    }

}
