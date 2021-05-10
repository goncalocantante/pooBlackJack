package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.interfaces.GameState;
import com.Casino.gamelogic.interfaces.Shoe;

import java.util.ArrayList;

public class Game {

    private Shoe shoe;
    private Player player;
    private Hand dealer;
    private ArrayList<Card> discardPile;

    private GameState currentState;
    private GameState gameStartState;
    private ArrayList<GameState> playerTurnState;
    private GameState dealerTurnState;
    private GameState roundEndState;
    private GameState gameEndState;

    public Game(){
        this.gameStartState = new GameStartState(this);
        this.playerTurnState = new ArrayList<GameState>();
        this.playerTurnState.add(new PlayerTurnState(this));
        this.dealerTurnState = new DealerTurnState(this);
        this.roundEndState = new RoundEndState(this);
        this.gameEndState = new GameEndState(this);
        this.currentState = this.gameStartState;

        this.player = new Player(this);
        this.dealer = new Hand();
        this.discardPile = new ArrayList<Card>();
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
    public GameState getPlayerTurnState(int i) {return this.playerTurnState.get(i);}

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

    public void play() {
        char input = '0';
        for()
            switch (input) {
                case 'h':

                    break;
                case 's':

                    break;
                default:
                    System.out.println("Invalid Command");
            }
        }
    }

}
