package com.Casino.gamelogic.classes;

import java.util.Scanner;

import com.Casino.gamelogic.enumerations.Rank;
import com.Casino.gamelogic.interfaces.GameState;

public class PlayerTurnState implements GameState {

    private Game game;

    /**
     * Contructor to set the game of the state to the game being played
     * 
     * @param game: game being played
     */
    public PlayerTurnState(Game game) {
        this.game = game;
    }

    @Override
    public void initializeGame() {
        System.out.println("Game has already started, cannot initialize.");
    }

    @Override
    public void playerTurn() {
        Scanner betScan;
        String inputString;

        boolean deal = false;

        int nHand = 0;
        int currentBet = 0;

        while (!deal) {
            inputString = this.game.getGameMode().getCommand(nHand);
            for (int k = 0; k < 10000; k++){

            }
            switch (inputString.charAt(0)) {
                case ('b'):
                    // If there are digits in the input string
                    if (inputString.matches(".*\\d.*")) {
                        // Scan the first integer in the string (bet amount)
                        betScan = new Scanner(inputString);
                        currentBet = betScan.useDelimiter("\\D+").nextInt();
                        System.out.println("curr Bet " + currentBet);
                        // Bet the specified amount
                        if(game.getPlayer().bet(currentBet, 0)) {
                            this.game.setPreviousBet(currentBet);  // Set previous bet value
                            betScan.close();
                        }
                    } else {
                        // If no amount is specified, bet previous bet value or minimum
                        // bet value if there is no previous bet
                        if (this.game.getPreviousBet() == 0) {
                            //Bet minimum bet
                            game.getPlayer().bet(this.game.getParameters()[0], 0);
                            this.game.setPreviousBet(this.game.getParameters()[0]);
                        } else {
                            //Bet previous bet
                            game.getPlayer().bet(this.game.getPreviousBet(), 0);
                        }
                    }
                    break;
                case 'd':
                    // If the player has already bet, deal. Else he must bet
                    if (this.game.getPreviousBet() != 0)
                        deal = true;
                    else
                        System.out.println("Please bet before cards are dealt");
                    break;
                case '$':
                    System.out.println("Current balance: " + game.getPlayer().getBalance() + "$");
                    break;
                case 's':
                    if (inputString.length() == 2 && inputString.charAt(1) == 't' ){
                        game.statistics();
                    }
                    break;
                default:
                    System.out.println("Illegal Command");
            }
        }

        this.game.getPlayer().clearLastResult();

        // Deal cards
        this.dealCards();
        System.out.println("dealer's hand " + game.getDealer());

        //Update running count after deal
        this.game.updateRunningCount();

        // loops through all of the player's hands
        for (int j = 0; j < this.game.getPlayer().getHands().size(); j++) {
            Hand hand = this.game.getPlayer().getHand(j);

            //If there was a split - Hit
            if(hand.getHandSize() == 1)
                this.game.getPlayer().hit(nHand);
            else
                System.out.println("player's hand " + hand + "(" + hand.handValue() + ")");

            while (!hand.isHandClosed()) {

                //Get the input
                inputString = this.game.getGameMode().getCommand(nHand);
                //Check which command is in input
                switch (inputString) {
                    case "h":
                        this.game.getPlayer().hit(nHand);
                        break;
                    case "s":
                        this.game.getPlayer().stand(nHand);
                        break;
                    case "$":
                        System.out.println("Current balance: " + game.getPlayer().getBalance() + "$");
                        break;
                    case "i":
                        game.getPlayer().insure();
                        break;
                    case "u":
                        game.getPlayer().surrender(nHand);
                        break;
                    case "p":
                        game.getPlayer().split(nHand);
                        break;
                    case "f":
                        game.getPlayer().forcedSplit(nHand);
                        break;
                    case "2":
                        game.getPlayer().doubleBet(nHand);
                        break;
                    case "st":
                        game.statistics();
                        break;
                    default:
                        System.out.println("Illegal command");
                }

                // If hand busts it closes, becoming not playable
                if (hand.isBust()) {
                    hand.closeHand();
                    System.out.println("player busts");
                }

                //If player gets blackjack on this hand close it
                if(hand.handValue() == 21 && hand.getHandSize() == 2){
                    hand.closeHand();
                    System.out.println("Blackjack!");
                }
            }
            // Next hand
            nHand++;
        }
        // Set next state
        game.setGameState(game.getDealerTurnState());
    }

    @Override
    public void dealerTurn() {
        System.out.println("Not dealer's turn, player is still playing.");
    }

    @Override
    public void finishRound() {
        return;
    }

    /**
     * Deals the cards at the beginning of a round
     */
    public void dealCards() {

        // Dealer is dealt one card faced up and one faced down
        game.getDealer().drawCard(game.getShoe());
        game.getDealer().drawCard(game.getShoe());
        game.getDealer().getCard(1).setCardFaceDown();

        // Player is dealt two face up cards
        game.getPlayer().getHand(0).drawCard(game.getShoe());
        game.getPlayer().getHand(0).drawCard(game.getShoe());
    }
}
