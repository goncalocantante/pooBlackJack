package com.Casino.BlackJack.classes.GameStates;

import com.Casino.BlackJack.classes.GameLogic.Hand;
import com.Casino.BlackJack.interfaces.GameState;
import com.Casino.BlackJack.classes.GameLogic.Game;

public class DealerTurnState implements GameState {

    private Game game;

    /**
     * Constructor to set the game of the state to the game being played
     * @param game: game being played
     */
    public DealerTurnState(Game game) {
        this.game = game;
    }

    /**
     * Does nothing in dealer state except printing a message
     */
    @Override
    public void initializeGame() {
        System.out.println("Game has already started, cannot initialize.");
    }

    /**
     * Does nothing in dealer state except printing a message
     */
    @Override
    public void playerTurn() { System.out.println("Not player turn, dealer is playing."); }

    @Override
    public void dealerTurn() {
        Hand dealer = game.getDealer();

        // the dealer turns over his hidden card
        dealer.getCard(1).setCardFaceUp();
        // Display dealers cards
        System.out.println("dealer's hand " + dealer);

        // If the dealer has a blackjack with his two cards
        if (dealer.handValue() == 21) {
            System.out.println("dealer blackjack!");
            this.game.dealerBlackJackCount++;
            // Doesn't take any more cards
        } else {
            // Dealer Hits at 16, Holds at 17
            while (dealer.handValue() < 17) {
                //System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
                System.out.println("dealer hits");
                dealer.drawCard(game.getShoe());
                System.out.println("dealer's hand " + dealer);
            }
            if(dealer.isBust())
                System.out.println("dealer busts");
        }

        //Update running count after dealer plays
        this.game.updateRunningCount();

        dealer.closeHand();
        // Sets next state
        game.setGameState(game.getRoundEndState());
    }

    /**
     * Does nothing in dealer state
     */
    @Override
    public void finishRound() {
        System.out.println("Cannot finish round, dealer is playing.");
    }


}
