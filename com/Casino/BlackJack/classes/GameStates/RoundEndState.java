package com.Casino.BlackJack.classes.GameStates;

import com.Casino.BlackJack.classes.GameLogic.Game;
import com.Casino.BlackJack.classes.GameLogic.Hand;
import com.Casino.BlackJack.classes.GameLogic.Player;
import com.Casino.BlackJack.interfaces.GameState;

import java.util.Collection;
import java.util.Collections;

public class RoundEndState implements GameState {

    private Game game;
    private int shuffleCount = 0;

    /**
     * Constructor to set the game of the state to the game being played
     * @param game: game being played
     */
    public RoundEndState(Game game) {
        this.game = game;
    }

    /**
     * Does nothing in round end state except printing a message
     */
    @Override
    public void initializeGame() {
        System.out.println("Game has already started, cannot initialize.");
    }

    /**
     * Does nothing in round end state except printing a message
     */
    @Override
    public void playerTurn() { System.out.println("Round has ended!"); }

    /**
     * Does nothing in round end state except printing a message
     */
    @Override
    public void dealerTurn() {
        System.out.println("Round has ended!");
    }

    @Override
    public void finishRound() {

        Hand dealer = this.game.getDealer();
        Player player = this.game.getPlayer();

        boolean dealerBlackjack = dealer.handValue() == 21 && dealer.getHandSize() == 2;

        //If dealer has a blackjack and player has insured, pay him
        if(dealerBlackjack && player.getInsurance() > 0) {
            player.addBalance(player.getInsurance());
            System.out.println("Player receives " + player.getInsurance() + "$ due to insurance win");
        }

        // Updates statistics
        this.game.totalPlayerHandsCount += player.getHands().size();
        this.game.totalDealerHandsCount++;
        //Iterates over every hand
        for (int i = 0; i < player.getHands().size(); i++) {
            System.out.println("Hand " + (i+1) + ":");
            //Prize is equal to 2 times the bet amount (unless player has blackjack)
            Hand hand = player.getHand(i);
            int bet = hand.getBetAmount();
            //If player has bust or surrendered on this hand there's no payout
            if(player.getHand(i).isBust() || player.getHand(i).getBetAmount() == 0){
                System.out.println("player loses and his current balance is " + player.getBalance());
                this.game.getPlayer().setLastResult(-1);
                continue;
            }
            //If dealer has bust and player hasn't bust or surrendered, pay him
            if(dealer.isBust()){
                this.game.totalPlayerWins++;
                int prize = player.getHand(i).getBetAmount() * 2;
                player.addBalance(bet*2);
                System.out.println("player wins and his current balance is " + player.getBalance());
                this.game.getPlayer().setLastResult(1);
                continue;
            }

            //If player has blackjack on this hand
            if(player.getHand(i).getHandSize() == 2 && player.getHand(i).handValue() == 21) {
                if(dealerBlackjack) {
                    // If the player and the dealer both have blackjack
                    // there's a push and the player gets his bet amount back
                    this.game.totalPushes++;
                    player.addBalance(bet);
                    System.out.println("player pushes and his current balance is " + player.getBalance());
                    this.game.getPlayer().setLastResult(0);
                    continue;

                }
                else {
                    // If player has blackjack and dealer doesn't, player receives 2.5 his
                    // bet  value(rounded to integer)
                    this.game.playerBlackJackCount++;
                    this.game.totalPlayerWins++;
                    player.addBalance((int)(bet * 2.5));
                    System.out.println("player wins and his current balance is " + player.getBalance());
                    this.game.getPlayer().setLastResult(1);
                    continue;

                }
            }else if(dealerBlackjack){
                //If player doesn't have blackjack and dealer does, player loses
                System.out.println("player loses and his current balance is " + player.getBalance());
                this.game.getPlayer().setLastResult(-1);
                continue;
            }

            //if player's hand value is bigger than the dealer's he wins
            if (hand.handValue() > dealer.handValue()) {
                this.game.totalPlayerWins++;
                player.addBalance(bet * 2);
                System.out.println("player wins and his current balance is " + player.getBalance());
                game.getPlayer().setLastResult(1);
            } else if (hand.handValue() < dealer.handValue()) {
                // if the opposite is true, player loses
                System.out.println("player loses and his current balance is " + player.getBalance());
                game.getPlayer().setLastResult(-1);
            } else {
                //If player and dealer have the same hand value, push, returning the player's bet
                this.game.totalPushes++;
                player.addBalance(bet);
                game.getPlayer().setLastResult(0);
                System.out.println("player pushes and his current balance is " + player.getBalance());
            }

        }

        //Player gets rid of his cards and parameters are reset
        player.playerResets();
        dealer.emptyHand(game.getDiscardPile());

        int shoeSize = this.game.getShoe().getShoeSize();
        // Percentage of shoe played until there's a shuffle
        double shufflePercentage = (double) this.game.getParameters()[2]/100;
        // Limit of cards drawn until there's a shuffle
        double nCardsShuffle = shoeSize * (shufflePercentage);


        //Shuffles if the card limit has been reached
        if(this.game.getDiscardPile().size() >= nCardsShuffle) {
            //Move cards back to shoe
            this.game.getShoe().moveAllToShoe(this.game.getDiscardPile());
            //Empty discard pile
            this.game.getDiscardPile().clear();
            this.game.getShoe().shuffle();
            //Increment number of times shuffled
            this.shuffleCount++;
            //Reset running count
            this.game.updateRunningCount();
            System.out.println("Shuffling shoe...");

        }
        //If limit number of shuffles been met and mode is simulation mode, end game and print statistics
        if(shuffleCount == game.getParameters()[3] && game.getParameters()[3] != -1){
            System.out.println("Game ended");
            this.game.statistics();
            System.exit(0);
        }
        game.setGameState(game.getPlayerTurnState());
    }
}
