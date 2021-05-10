package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.interfaces.GameState;
import com.Casino.gamelogic.interfaces.Shoe;

import java.util.ArrayList;

public class Game {

    private Shoe shoe;
    private int balance = 0;
    private ArrayList<Hand> hands;

    private GameState currentState;
    private GameStartState gameStartState;
    private ArrayList<PlayerTurnState> playerTurnState;
    private RoundEndState roundEndState;
    private GameEndState gameEndState;

    public Game(){
        this.gameStartState = new GameStartState(this);
        this.playerTurnState = new ArrayList<PlayerTurnState>();
        this.playerTurnState.add(new PlayerTurnState(this));
        this.roundEndState = new RoundEndState(this);
        this.gameEndState = new GameEndState(this);
        this.currentState = this.gameStartState;

        this.hands = new ArrayList<Hand>();
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
    public GameState getCurrentState() {return this.currentState;}

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
     * Gets the round's end state
     * @return roundEndState: Round's end state
     */
    public GameState getRoundEndState() {return this.roundEndState;}

    /**
     * Gets the game's end state
     * @return gameEndState: Game's end state
     */
    public GameState getGameEndState() {return this.gameEndState;}

    public static void main(String[] args) {

        Game test = new Game();

        while(!test.currentState.equals(test.gameEndState)) {
            test.currentState.startState();
            test.currentState.resolveState();
            test.currentState.endState();
        }

    }
}
