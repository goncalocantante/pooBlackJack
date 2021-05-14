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
    private int minBet, maxBet, shuffle;

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
        this.gameEndState = new GameEndState(this);


        this.player = new Player(this);
        this.dealer = new Hand();
        this.discardPile = new ArrayList<Card>();
        this.gameMode = gameMode;

        this.gameState = this.gameStartState;
    }

    /**
     * Sets the current state to the specified state
     * 
     * @param state: state to be set as current state
     */
    public void setGameState(GameState state) {
        this.gameState = state;
    }

    public void initializeGame() {
        gameState.initializeGame();
    }

    public void playerTurn() {
        gameState.playerTurn();
    }

    public void dealerTurn() {
        gameState.dealerTurn();
    }

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

    public void setParameters(int minBet, int maxBet, int balance, int shuffle){
        this.minBet = minBet;
        this.maxBet = maxBet;
        this.player.addBalance(balance);
        this.shuffle = shuffle;
    }
}
