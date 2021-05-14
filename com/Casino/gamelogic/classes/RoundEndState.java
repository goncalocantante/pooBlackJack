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

        boolean dealerBlackjack = dealer.handValue() == 21 && dealer.getHandSize() == 2;

        //If dealer has a blackjack and player has insured, pay him
        if(dealerBlackjack && player.getInsurance() > 0) {
            player.addBalance(player.getInsurance());
            System.out.println("Player receives " + player.getInsurance() + "$ due to insurance win");
        }

        //Iterates over every hand
        for (int i = 0; i < player.getHands().size(); i++) {
            System.out.println("Hand " + (i+1) + ":");
            //Prize is equal to 2 times the bet amount (unless player has blackjack)
            Hand hand = player.getHand(i);
            int bet = hand.getBetAmount();
            //If player has bust or surrendered on this hand there's no payout
            if(player.getHand(i).isBust() || player.getHand(i).getBetAmount() == 0){
                System.out.println("Player loses");
                continue;
            }
            //If dealer has bust and player hasn't bust or surrendered, pay him
            if(dealer.isBust()){
                System.out.println("Player wins, receives " + bet*2 + "$");
                int prize = player.getHand(i).getBetAmount() * 2;
                player.addBalance(bet*2);
                continue;
            }

            //If player has blackjack on this hand
            if(player.getHand(i).getHandSize() == 2 && player.getHand(i).handValue() == 21) {
                if(dealerBlackjack) {
                    // if the player and the dealer both have blackjack
                    // there's a push and the player gets his bet amount back
                    System.out.println("Push! Player receives " + bet + "$ back");
                    continue;
                }
                else {
                    //Pays 2.5 to 1 --------------------------------------
                    System.out.println("Player wins with blackjack redeiving " + bet*3 + "$");
                    player.addBalance(bet * 3);
                    continue;
                }
            }else if(dealerBlackjack){
                //If player doesn't have blackjack and dealer does, player loses
                continue;
            }

            //if player's hand value is bigger than the dealer's he wins
            if (hand.handValue() > dealer.handValue()) {
                System.out.println("Player wins, receives " + bet*2 + "$");
                player.addBalance(bet * 2);
            } else if (hand.handValue() < dealer.handValue()) {
                // if the opposite is true, player loses
                System.out.println("Player loses");
            } else {
                //Push
                System.out.println("Push! Player receives " + bet + "$ back");
                player.addBalance(bet / 2);
            }

        }

        /*if (dealer.isBust()) {
            // Pays all hands
            for (int i = 0; i < player.getHands().size(); i++) {
                // TODO side rules
                //If player hasn't bust and hasn't surrendered pay him
                if (!((player.getHand(i).isBust() || player.getHand(i).getBetAmount() == 0)) {
                    int prize = player.getHand(i).getBetAmount() * 2;
                    player.addBalance(prize);
                }
            }
        } else {
            // Iterates over all hands
            for (int i = 0; i < player.getHands().size(); i++) {
                Hand hand = player.getHand(i);
                int prize = hand.getBetAmount() * 2;
                //If player hasn't bust and hasn't surrendered
                if(!((player.getHand(i).isBust() || player.getHand(i).getBetAmount() == 0)) {
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
        }*/

        // player gets rid of his cards
        player.playerResets();
        dealer.emptyHand(game.getDiscardPile());
        // next state: EndGame ou PlayerPlays
        game.setGameState(game.getPlayerTurnState());
    }
}
