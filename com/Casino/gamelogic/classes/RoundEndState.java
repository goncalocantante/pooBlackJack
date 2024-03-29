package com.Casino.gamelogic.classes;

import com.Casino.gamelogic.interfaces.GameState;

public class RoundEndState implements GameState {

    private Game game;

    /**
     * Constructor to set the game of the state to the game being played
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

    /**
     * Constructor to set the game of the state to the game being played
     */
    @Override
    public void finishRound() {

        Hand dealer = game.getDealer();
        Player player = game.getPlayer();

        boolean dealerBlackjack = dealer.handValue() == 21 && dealer.getHandSize() == 2;

        //If dealer has a blackjack and player has insured, pay him
        if(dealerBlackjack && player.getInsurance() > 0) {
            player.addBalance(player.getInsurance());
            System.out.println("player receives " + player.getInsurance() + "$ due to insurance win");
        }

        // Updates statistics
        this.game.totalPlayerHandsCount += player.getHands().size();
        this.game.totalDealerHandsCount++;
        //Iterates over every hand
        for (int i = 0; i < player.getHands().size(); i++) {
            //Prize is equal to 2 times the bet amount (unless player has blackjack)
            Hand hand = player.getHand(i);
            int bet = hand.getBetAmount();
            //If player has bust or surrendered on this hand there's no payout
            if(player.getHand(i).isBust() || player.getHand(i).getBetAmount() == 0){
                System.out.println("player loses and his current balance is " + player.getBalance());
                game.getPlayer().setLastResult(-1);
                continue;
            }
            //If dealer has bust and player hasn't bust or surrendered, pay him
            if(dealer.isBust()){
                this.game.totalPlayerWins++;
                int prize = player.getHand(i).getBetAmount() * 2;
                player.addBalance(bet*2);
                System.out.println("player wins and his current balance is " + player.getBalance());
                game.getPlayer().setLastResult(1);
                continue;
            }

            //If player has blackjack on this hand
            if(player.getHand(i).getHandSize() == 2 && player.getHand(i).handValue() == 21) {
                if(dealerBlackjack) {
                    // if the player and the dealer both have blackjack
                    // there's a push and the player gets his bet amount back
                    this.game.totalPushes++;
                    player.addBalance(bet);
                    System.out.println("player pushes and his current balance is" + player.getBalance());
                    game.getPlayer().setLastResult(0);
                    continue;

                }
                else {
                    //Pays 2.5 to 1 -------------------------------------
                    this.game.playerBlackJackCount++;
                    this.game.totalPlayerWins++;
                    player.addBalance(bet * 3);
                    System.out.println("player wins and his current balance is " + player.getBalance());
                    game.getPlayer().setLastResult(1);
                    continue;

                }
            }else if(dealerBlackjack){
                //If player doesn't have blackjack and dealer does, player loses
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
                System.out.println("player loses and his current balance is" + player.getBalance());
                game.getPlayer().setLastResult(-1);
            } else {
                //Push
                this.game.totalPushes++;
                player.addBalance(bet);
                game.getPlayer().setLastResult(0);
                System.out.println("player pushes and his current balance is" + player.getBalance());
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
            this.game.getShoe().moveAllToShoe(this.game.getDiscardPile());
            this.game.getShoe().shuffle();
        }
        // Next state: EndGame ou PlayerPlays
        game.setGameState(game.getPlayerTurnState());
    }
}
