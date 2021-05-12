package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.interfaces.GameState;
import com.Casino.gamelogic.classes.Game;

public class DealerTurnState implements GameState {

    private Game game;

    /**
     * Constructor to set the game of the state to the game being played
     * 
     * @param game: game being played
     */
    public DealerTurnState(Game game) {
        this.game = game;
    }

    @Override
    public void initializeGame() {
        System.out.println("Game has already started, cannot initialize.");
    }

    @Override
    public void playerTurn() {
        System.out.println("Not player turn, dealer is playing.");

    }

    @Override
    public void dealerTurn() {
        // loop do dealer
        System.out.println("Dealer is playing.");
        game.setGameState(game.getRoundEndState());

    }

    @Override
    public void finishRound() {
        return;
    }

    /*
     * @Override public void startState() { System.out.println("Dealer's turn!"); }
     * 
     * @Override public void resolveState() { System.out.println("Dealer plays...");
     * }
     * 
     * @Override public void endState() {
     * game.setGameState(game.getRoundEndState()); }
     */
}
