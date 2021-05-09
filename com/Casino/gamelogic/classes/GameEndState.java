package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.interfaces.GameState;
import com.Casino.gamelogic.classes.Game;

public class GameEndState implements GameState{
    private Game game;

    /**
     * Contructor to set the game of the state to the game being played
     * @param game: game being played
     */
    public GameEndState(Game game) { this.game = game;}

    @Override
    public void startState() { System.out.println("Game is gonna end");}

    @Override
    public void resolveState() { System.out.println("Game has ended.");}

    @Override
    public void endState() { return;}
}
