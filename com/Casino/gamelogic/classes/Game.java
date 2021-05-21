package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.interfaces.GameState;
import com.Casino.gamelogic.interfaces.Mode;
import com.Casino.gamelogic.interfaces.Shoe;

import java.util.ArrayList;

public class Game {

    private ShoeClass shoe;
    private Player player;
    private Hand dealer;
    private ArrayList<Card> discardPile;
    private int minBet, maxBet, shuffle, nShuffle;

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
}
