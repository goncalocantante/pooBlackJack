package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.interfaces.GameState;
import com.Casino.gamelogic.classes.Game;

public class RoundEndState implements GameState {

    private Game game;

    /**
     * Contructor to set the game of the state to the game being played
     * @param game: game being played
     */
    public RoundEndState(Game game) { this.game = game;}

    @Override
    public void startState() { System.out.println("Round about to end");}

    @Override
    public void resolveState() { System.out.println("Round has ended.");}

    @Override
    public void endState() { game.setCurrentState(game.getGameEndState());}
}
