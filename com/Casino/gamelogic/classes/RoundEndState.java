package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.interfaces.GameState;

public class RoundEndState implements GameState {

    private Game game;

    /**
     * Contructor to set the game of the state to the game being played
     * 
     * @param game: game being played
     */
    public RoundEndState(Game game) {
        this.game = game;
    }

    @Override
    public void initializeGame() {
        System.out.println("Game has already started, cannot initialize.");
    }

    @Override
    public void playerTurn() {
        System.out.println("Round has ended!");

    }

    @Override
    public void dealerTurn() {
        System.out.println("Round has ended!");
    }

    @Override
    public void finishRound() {
        // ve quem ganhou
        // paga o que tem a pagar
        // muda de estado consoante o jogador quer continuar ou não
        // game.isOver = true;
        // prepara a proxima round e faz set next state
        // EndGame ou PlayerPlays
        System.out.println("Ronda Terminada.");
        game.setGameState(game.getPlayerTurnState());
    }

    /*
     * @Override public void startState() {
     * System.out.println("Round about to end");}
     * 
     * @Override public void resolveState() {
     * System.out.println("Round has ended.");}
     * 
     * @Override public void endState() {
     * game.setGameState(game.getGameEndState());}
     */
}
