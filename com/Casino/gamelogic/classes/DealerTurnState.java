package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.interfaces.GameState;
import com.Casino.gamelogic.classes.Game;

public class DealerTurnState implements GameState {

    private Game game;

    /**
     * Constructor to set the game of the state to the game being played
     * @param game: game being played
     */
    public DealerTurnState(Game game) { this.game = game;}

    @Override
    public void startState() { System.out.println("Dealer's turn!");}

    @Override
    public void resolveState() { System.out.println("Dealer plays...");}

    @Override
    public void endState() { game.setCurrentState(game.getRoundEndState());}
}
