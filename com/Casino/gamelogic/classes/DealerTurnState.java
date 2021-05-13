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
        Hand dealer = game.getDealer();

        // the dealer turns over his hidden card
        dealer.getCard(0).setCardFaceUp();
        // Display dealers cards
        System.out.println("dealer's hand " + dealer + " " + "(" + dealer.handValue() + ")");

        // If the dealer has a blackjack with his two cards
        if (dealer.handValue() == 21) {
            // Doesn't take any more cards
            dealer.closeHand();
        } else {
            // Dealer Hits at 16, Holds at 17
            while (dealer.handValue() < 17) {
                System.out.println("dealer hits");
                dealer.drawCard(game.getShoe());
                System.out.println("dealer's hand " + dealer + " " + "(" + dealer.handValue() + ")");
            }
            dealer.closeHand();
        }
        // Sets next state
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
