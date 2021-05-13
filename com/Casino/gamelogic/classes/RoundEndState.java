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

        // muda de estado consoante o jogador quer continuar ou não

        System.out.println("Ronda Terminada.");

        Hand dealer = game.getDealer();
        Player player = game.getPlayer();

        if (dealer.isBust()) {
            // Pays all hands
            for (int i = 0; i < player.getHands().size(); i++) {
                // TODO side rules
                if (!player.getHand(i).isBust()) {
                    int prize = player.getHand(i).getBetAmount() * 2;
                    player.addBalance(prize);
                }
            }
        } else {
            // Iterates over all hands
            for (int i = 0; i < player.getHands().size(); i++) {
                Hand hand = player.getHand(i);
                int prize = hand.getBetAmount() * 2;

                if (hand.handValue() > dealer.handValue()) {
                    // player wins
                    player.addBalance(prize);
                } else if (hand.handValue() < dealer.handValue()) {
                    // dealer wins
                    // player doesn't receive money
                } else {
                    // push
                    // player keeps the same balance
                    player.addBalance(prize / 2);
                }

            }
        }
        // player gets rid of his cards
        player.returnCards();
        dealer.emptyHand(game.getDiscardPile());
        // next state: EndGame ou PlayerPlays
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
