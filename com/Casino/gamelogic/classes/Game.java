package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.interfaces.GameState;
import com.Casino.gamelogic.interfaces.Shoe;

import java.util.ArrayList;

public class Game {

    private Shoe shoe;
    private Player player;
    private Hand dealer;
    //Pile where the discarded cards go to
    private ArrayList<Card> discardPile;
    //1 pile per hand where the bet amount goes
    private ArrayList<Integer> piles;

    private GameState currentState;
    private GameState gameStartState;
    private GameState playerTurnState;
    private GameState dealerTurnState;
    private GameState roundEndState;
    private GameState gameEndState;

    public Game(){
        this.gameStartState = new GameStartState(this);
        this.playerTurnState = new PlayerTurnState(this);
        this.dealerTurnState = new DealerTurnState(this);
        this.roundEndState = new RoundEndState(this);
        this.gameEndState = new GameEndState(this);
        this.currentState = this.gameStartState;

        this.player = new Player(this);
        this.dealer = new Hand();
        this.discardPile = new ArrayList<Card>();
        this.piles = new ArrayList<Integer>();

        this.piles.add(new Integer(0));
        this.shoe = new ShoeClass(1);
    }

    /**
     * Sets the current state to the specified state
     * @param state: state to be set as current state
     */
    public void setCurrentState(GameState state) {this.currentState  = state;}

    /**
     * Gets the current state
     * @return currentState: Current state
     */
    public GameState getCurrentState() { return this.currentState;}

    /**
     * Gets the game's start state
     * @return gameStartState: Game's start state
     */
    public GameState getGameStartState() {return this.gameStartState;}

    /**
     * Gets the player's turn state
     * @return playerTurnState: Player's turn state
     */
    public GameState getPlayerTurnState() {return this.playerTurnState;}

    /**
     * Gets the dealer's turn state
     * @return dealerTurnState: Dealer's turn state
     */
    public GameState getDealerTurnState() { return this.dealerTurnState;}

    /**
     * Gets the round's end state
     * @return roundEndState: Round's end state
     */
    public GameState getRoundEndState() {return this.roundEndState;}

    /**
     * Gets the game's end state
     * @return gameEndState: Game's end state
     */
    public GameState getGameEndState() {return this.gameEndState;}

    /**
     * Gets shoe
     * @return shoe: game's shoe
     */
    public Shoe getShoe() { return this.shoe;}

    /**
     * Gets the player
     * @return player: game's player
     */
    public Player getPlayer() { return this.player;}

    /**
     * Gets the list of piles
     * @return piles: piles relative to each hand
     */
    public ArrayList<Integer> getPiles() { return this.piles;}
    /**
     * Adds money to the pile
     * @param amount: amount to add to the pile
     */
    public void addToPile(int amount, int nHand) {
        Integer buff = this.piles.get(nHand) + amount;

        this.piles.set(nHand, buff);
    }

    /**
     * Gets the discard pile
     */
    public ArrayList<Card> getDiscardPile() { return discardPile;}

    /**
     * Deals the cards at the beginning of a round
     */
    public void dealCards(){

        this.player.addCard(this.shoe.drawCard(), 0);
        this.dealer.receiveCard(this.shoe.drawCard());
        this.dealer.getCard(0).setCardFaceUp();
        this.player.addCard(this.shoe.drawCard(), 0);
        this.dealer.receiveCard(this.shoe.drawCard());
    }

}
