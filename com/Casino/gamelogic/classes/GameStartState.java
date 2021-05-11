package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.interfaces.GameState;
import com.Casino.gamelogic.classes.Game;

public class GameStartState implements GameState {

    private Game game;

    /**
     * Contructor to set the game of the state to the game being played
     * @param game: game being played
     */
    public GameStartState(Game game) { this.game = game;}

    @Override
    public void startState() { System.out.println("Game started!");}

    @Override
    public void resolveState() { System.out.println("Game is started.");}

    @Override
    public void endState() { game.setCurrentState(game.getPlayerTurnState());}
}
