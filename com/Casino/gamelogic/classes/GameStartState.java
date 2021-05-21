package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.interfaces.GameState;

public class GameStartState implements GameState {

    private Game game;

    /**
     * Contructor to set the game of the state to the game being played
     * 
     * @param game: game being played
     */
    public GameStartState(Game game) {
        this.game = game;
    }

    @Override
    public void initializeGame() {
        this.game.getGameMode().InitializeShoeAndParameters(this.game);
        game.setGameState(game.getPlayerTurnState());

    }

    @Override
    public void playerTurn() {
        System.out.println("Game hasn't started yet.");
    }

    @Override
    public void dealerTurn() {
        System.out.println("Game hasn't started yet.");
    }

    @Override
    public void finishRound() {
        System.out.println("Game hasn't started yet.");
    }
}
