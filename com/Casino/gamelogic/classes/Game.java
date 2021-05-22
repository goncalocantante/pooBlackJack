package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.enumerations.Rank;
import com.Casino.gamelogic.interfaces.GameState;
import com.Casino.gamelogic.interfaces.Mode;
import com.Casino.gamelogic.interfaces.Shoe;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Game {

    private ShoeClass shoe;
    private Player player;
    //Dealer's hand
    private Hand dealer;
    //Discard pile where cards will go at the end of the round
    private ArrayList<Card> discardPile;
    //Parameters specified by player
    private int minBet, maxBet, shuffle, nShuffle;
    //Running count according to HiLo strategy
    private int runningCount = 0;
    private int previousBet = 0;

    public int totalPlayerHandsCount, totalDealerHandsCount, totalPlayerWins, playerBlackJackCount, dealerBlackJackCount, totalPushes;

    Mode gameMode;

    private GameState gameState;
    private GameState gameStartState;
    private GameState playerTurnState;
    private GameState dealerTurnState;
    private GameState roundEndState;
    private GameState gameEndState;

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
    }

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
     * Gets the current state
     * 
     * @return gameState: Current state
     */
    public GameState getGameState() {
        return this.gameState;
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
     * Gets the game's end state
     * 
     * @return gameEndState: Game's end state
     */
    public GameState getGameEndState() {
        return this.gameEndState;
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
    public void setShoe(ShoeClass shoe) {
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
     * Sets the game parameters: min bet, max bet, balance and shuffle percentage
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
                switch(this.dealer.getCard(i).getRank()){
                    case TWO:
                    case THREE:
                    case FOUR:
                    case FIVE:
                    case SIX:
                        if(this.dealer.getCard(i).isCardFaceUp())
                        count++;
                        break;
                    case TEN:
                    case JACK:
                    case KING:
                    case QUEEN:
                    case ACE:
                        if(this.dealer.getCard(i).isCardFaceUp())
                        count--;
                        break;
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

    public void statistics(){
        int N1 = this.playerBlackJackCount/this.totalPlayerHandsCount, N2 = this.dealerBlackJackCount/this.totalDealerHandsCount;
        int N3 = this.totalPlayerWins/this.totalPlayerHandsCount;
        int totalLosses = this.totalPlayerHandsCount - this.totalPlayerWins - this.totalPushes;
        int N4 = totalLosses/this.totalPlayerHandsCount;
        int N7 = (this.player.getBalance()/this.player.getInitialBalance())*100;


        System.out.format("%-10s%-10s%-2d/%-2d%-8s\n", "BJ", "P/D", N1, N2, "");
        System.out.format("%-10s%-10s%-10d\n", "Win", "", N3);
        System.out.format("%-10s%-10s%-10d\n", "Lose", "", N4);
        System.out.format("%-10s%-10s%-10d\n", "Push", "", this.totalPushes);
        System.out.format("%-10s%-10s%-5d(%3d)\n", "Balance", "", this.player.getBalance(), N7);
    }

    public String basicStrategy (int nHand) {

        Tables table = new Tables();

        String action = "";
        Card card1 = this.player.getHand(nHand).getCard(0);
        Card card2 = this.player.getHand(nHand).getCard(1);
        int playerValue = this.player.getHand(nHand).handValue();
        int dealerValue = this.dealer.getCard(0).cardValue();

        //If player's hand is a pair acess pair table
        if (card1.getRank().equals(card2.getRank()) && this.player.getHand(nHand).getHandSize() == 2){
            action = String.valueOf(table.getAction (1, playerValue, dealerValue));
        }//If player's hand is hard, acess hard table
        else if (!this.player.getHand(nHand).isSoft()){
            action = String.valueOf(table.getAction (2, playerValue, dealerValue));
        }//If player's is soft acess soft table
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
        else if(action.equals("p")) {
            if (this.player.canSplit(nHand)){
                action = "p";
            } else {
                action = "f";
            }
        }
            action = action.toLowerCase(Locale.ROOT);
        return action;
    }

    //bets certain amount according to Ace Five strategy
    public String aceFiveBet () {

        int counter = 0;
        int aceFive = 0;
        int toBet;

        for (counter = 0; counter < this.discardPile.size(); counter++){
            if (this.discardPile.get(counter).equals(Rank.FIVE)) {
                aceFive++;
            } else if (this.discardPile.get(counter).equals(Rank.ACE)) {
                aceFive--;
            }
        }

        toBet = this.previousBet*2;
        if (toBet > this.maxBet){
            toBet = this.maxBet;
        }

        if (aceFive <= 1){
            this.previousBet = this.minBet;
            return ("b " + this.minBet);
        }
        else {
            this.previousBet = toBet;
            return ("b " + toBet);
        }
    }

    //amount to bet when we are not using the Ave Five strategy
    public String StandardBetStrategy() {
        int toBet;

        //if it is the first bet of the game, then use minBet
        if (this.player.getLastResult() == -5){
            this.previousBet = this.minBet;
            return ("b " + this.minBet);
        }

        //get next bet by seeing last results
        toBet = this.player.getLastResult()*this.minBet + previousBet;
        //if this exceeds maxBet or smaller than minBet, then set them as the true bet
        if (toBet < this.minBet){
            toBet = this.minBet;
        }
        else if (toBet > this.maxBet){
            toBet = this.maxBet;
        }
        this.previousBet = toBet;
        return ("b " + toBet);
    }


    public String HiLo(int nHand, int cardsInShoe) {
        int runningCount = 0;
        int cardsDiscarded = 0;
        int decksRemaining = 0;
        int trueCount = 0;
        String toReturn;

        runningCount = this.getRunningCount();

        decksRemaining = (cardsInShoe - cardsDiscarded)/52;
        trueCount = runningCount/decksRemaining;
        //Inteiro?

        toReturn = fab4(nHand, trueCount);
        if (!toReturn.equals("basic")){
            return toReturn;
        }
        return (illustrious18(nHand, trueCount));
    }

    public String illustrious18 (int nHand, int trueCount){
        int playerHand = this.getPlayer().getHand(nHand).handValue();
        int dealerHand = this.getDealer().handValue();

        if (playerHand == 16 && dealerHand == 10){
            return standOrHit(trueCount, 0);
        } else if (playerHand == 15 && dealerHand == 10){
            return standOrHit(trueCount, 4);
        } else if (playerHand == 20 && dealerHand == 5){
            return splitOrStand(trueCount, 5);
        } else if (playerHand == 20 && dealerHand == 6){
            return splitOrStand(trueCount, 4);
        } else if (playerHand == 10 && dealerHand == 10){
            return doubleOrHit(trueCount, 4);
        } else if (playerHand == 12 && dealerHand == 3){
            return standOrHit(trueCount, 2);
        } else if (playerHand == 12 && dealerHand == 2){
            return standOrHit(trueCount, 3);
        } else if (playerHand == 11 && dealerHand == 11){
            return doubleOrHit(trueCount, 1);
        } else if (playerHand == 9 && dealerHand == 2){
            return doubleOrHit(trueCount, 1);
        } else if (playerHand == 10 && dealerHand == 11){
            return doubleOrHit(trueCount, 4);
        } else if (playerHand == 9 && dealerHand == 7){
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
        }
        return "basic";
    }

    public String standOrHit (int trueCount, int indexNumber) {

        if (trueCount >= indexNumber){
            return "s";
        }
        else{
            return "h";
        }
    }

    public String splitOrStand (int trueCount, int indexNumber) {

        if (trueCount >= indexNumber){
            return "p";
        }
        else{
            return "s";
        }
    }

    public String doubleOrHit (int trueCount, int indexNumber) {

        if (trueCount >= indexNumber){
            return "2";
        }
        else{
            return "h";
        }
    }

    public String fab4 (int nHand, int trueCount){
        int playerHand = this.getPlayer().getHand(nHand).handValue();
        int dealerHand = this.getDealer().handValue();

        if (playerHand == 14 && dealerHand == 10){
            return surrenderOrBasic(trueCount, 3);
        } else if (playerHand == 15 && dealerHand == 9){
            return surrenderOrBasic(trueCount, 2);
        } else if (playerHand == 15 && dealerHand == 11){
            return surrenderOrBasic(trueCount, 1);
        } else if (playerHand == 15 && dealerHand == 10){
            if (trueCount <= 0 && trueCount >= 3){
                return "u";
            } else if (trueCount >= 4){
                return "s";
            }
            else {
                return "h";
            }
        }
        return "basic";

    }

    public String surrenderOrBasic (int trueCount, int indexNumber){

        if (trueCount >= indexNumber){
            return "u";
        }
        else{
            return "basic";
        }
    }

    public void advice(int nHand) {
        String action;

        //If cards haven't been dealt(if dealer's hand is empty), print bet advice
        if(this.player.getHand(0).isEmpty()) {
            action = this.aceFiveBet();
            Scanner scanner = new Scanner(action);
            //skip b character
            scanner.next();
            //Print "bet betAmount"
            if (scanner.hasNextInt()){
                System.out.println("bet " + scanner.nextInt());
            }
            scanner.close();
        }//If cards have already been dealt
        else{
            //Get HiLo advice and print it
            action = this.HiLo(nHand, this.getShoe().getShoeSize());
            //If HiLo advice is basic strategy, print advice according to basic strategy
            if(action.equals("basic")){
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
            }
            System.out.println("Hilo: " + action);

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
