package com.Casino.BlackJack.classes.GameStates;

import java.util.Scanner;

import com.Casino.BlackJack.classes.GameLogic.Game;
import com.Casino.BlackJack.classes.GameLogic.Hand;
import com.Casino.BlackJack.interfaces.GameState;

public class PlayerTurnState implements GameState {

    private Game game;

    /**
     * Contructor to set the game of the state to the game being played
     * @param game: game being played
     */
    public PlayerTurnState(Game game) {
        this.game = game;
    }

    /**
     * Does nothing in player's turn state except printing a message
     */
    @Override
    public void initializeGame() { System.out.println("Game has already started, cannot initialize."); }

    /**
     * Executes the player's turn where the player will decide to quit or play and subsequently
     * bet and play
     */
    @Override
    public void playerTurn() {
        Scanner betScan;
        String inputString;

        //True if cards have already been dealt
        boolean deal = false;

        //Index of hand being played
        int nHand = 0;
        int currentBet = 0;

        //While card's haven't been dealt
        while (!deal) {
            //Get player's command
            inputString = this.game.getGameMode().getCommand(nHand);
            for (int k = 0; k < 10000; k++){

            }
            //Check which command player has made
            switch (inputString.charAt(0)) {
                case ('b'):
                    // If there are digits in the input string, bet the specified amount
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
                case 'q':
                    //Quit, ending the game and printing the statistics
                    System.out.println("Game ended");
                    this.game.statistics();
                    System.exit(0);
                case '$':
                    //Prints current balance
                    System.out.println("Current balance: " + game.getPlayer().getBalance() + "$");
                    break;
                case 'a':
                    //If command is "ad" get advice
                    if(inputString.charAt(1) == 'd')
                        this.game.advice(0);
                    else
                        System.out.println("Illegal command");
                    break;
                case 's':
                    //If command is "st" get statistics
                    if (inputString.length() == 2 && inputString.charAt(1) == 't' )
                        game.statistics();
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

            //If there was a split and this hand only hand one card, hit. Else print hand
            if(hand.getHandSize() == 1)
                this.game.getPlayer().hit(nHand);
            else
                System.out.println("player's hand " + hand + "(" + hand.handValue() + ")");

            System.out.println("");

            //While hand isn't closed, player plays
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
                        System.out.println("Current balance: " + this.game.getPlayer().getBalance() + "$");
                        break;
                    case "i":
                        this.game.getPlayer().insure();
                        break;
                    case "u":
                        this.game.getPlayer().surrender(nHand);
                        break;
                    case "p":
                        this.game.getPlayer().split(nHand);
                        break;
                    case "f":
                        this.game.getPlayer().forcedSplit(nHand);
                        break;
                    case "2":
                        this.game.getPlayer().doubleBet(nHand);
                        break;
                    case "st":
                        this.game.statistics();
                        break;
                    case "ad":
                        this.game.advice(nHand);
                        break;
                    default:
                        System.out.println("Illegal command");
                }

                // If hand busts it closes, becoming not playable
                if (hand.isBust()) {
                    hand.closeHand();
                    System.out.println("player busts");
                }

                //If player gets blackjack on this hand close hand
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

    /**
     * Does nothing in player's turn state except printing a message
     */
    @Override
    public void dealerTurn() {
        System.out.println("Not dealer's turn, player is still playing.");
    }

    /**
     * Does nothing in player's turn state except printing a message
     */
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
