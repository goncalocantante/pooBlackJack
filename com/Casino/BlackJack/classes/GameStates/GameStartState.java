package com.Casino.BlackJack.classes.GameStates;

import com.Casino.BlackJack.classes.GameLogic.Game;
import com.Casino.BlackJack.interfaces.GameState;

/**
 * Game state representing the start of the game
 * Initializes the game before playing
 */
public class GameStartState implements GameState {

    private Game game;

    /**
     * Constructor to set the game of the state to the game being played
     * @param game: game being played
     */
    public GameStartState(Game game) {
        this.game = game;
    }

    /**
     * Creates the shoe according to the game mode, initializes parameters of the game
     * and sets the next state (payer's turn state)
     */
    @Override
    public void initializeGame() {
        this.game.getGameMode().InitializeShoeAndParameters(this.game);
        this.game.setInitialShoeSize(this.game.getShoe().getShoeSize());
        this.game.setGameState(game.getPlayerTurnState());

    }

    /**
     * Does nothing in game start state except printing a message
     */
    @Override
    public void playerTurn() {
        System.out.println("Game hasn't started yet.");
    }

    /**
     * Does nothing in game start state except printing a message
     */
    @Override
    public void dealerTurn() {
        System.out.println("Game hasn't started yet.");
    }

    /**
     * Does nothing in game start state except printing a message
     */
    @Override
    public void finishRound() {
        System.out.println("Game hasn't started yet.");
    }
}
