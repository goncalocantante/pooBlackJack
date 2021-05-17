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
        int previousBet = 0;
        int currentBet = 0;

        while (!deal) {
            inputString = this.game.getGameMode().getCommand(nHand);
            switch (inputString.charAt(0)) {

                case 'b':
                    // If there are digits in the input string
                    if (inputString.matches(".*\\d.*")) {
                        // Scan the first integer in the string (bet amount)
                        betScan = new Scanner(inputString);
                        currentBet = betScan.useDelimiter("\\D+").nextInt();
                        System.out.println("curr Bet " + currentBet);
                        // Bet the specified amount
                        game.getPlayer().bet(currentBet, 0);
                        // Set previous bet value
                        previousBet = currentBet;
                        betScan.close();
                    } else {
                        // If no amount is specified, bet previous bet value or minimum
                        // bet value if there is no previous bet
                        if (previousBet == 0) {
                            game.getPlayer().bet(5 /* minbet */, 0);
                            previousBet = 5;
                        } else {
                            game.getPlayer().bet(previousBet, 0);
                        }
                    }
                    break;
                case 'd':
                    // If the player has already bet, deal. Else he must bet
                    if (previousBet != 0)
                        deal = true;
                    else
                        System.out.println("Please bet before cards are dealt");
                    break;
                case '$':
                    System.out.println("Current balance: " + game.getPlayer().getBalance() + "$");
                    break;
                default:
                    //System.out.println("Illegal command");
            }
        }


        // Deal cards
        this.dealCards();
        System.out.println("dealer's hand " + game.getDealer());

        //System.out.println("Player is playing.");
        // loops through all of the player's hands
        for (int j = 0; j < this.game.getPlayer().getHands().size(); j++) {

            Hand hand = this.game.getPlayer().getHand(j);

            //System.out.println("Playing hand " + (nHand + 1) + ":");
            // While hand is playable

            //If there was a split - Hit
            if(hand.getHandSize() == 1)
                this.game.getPlayer().hit(nHand);
            else
                System.out.println("player's hand " + hand);

            while (!hand.isHandClosed()) {

                //System.out.println("Enter command:");
                //Get the input
                inputString = this.game.getGameMode().getCommand(nHand);
                //Check which command is in input
                switch (inputString.charAt(0)) {
                    case 'h':
                        this.game.getPlayer().hit(nHand);
                        break;
                    case 's':
                        this.game.getPlayer().stand(nHand);
                        break;
                    case '$':
                        System.out.println("Current balance: " + game.getPlayer().getBalance() + "$");
                        break;
                    case 'i':
                        game.getPlayer().insure();
                        break;
                    case 'u':
                        game.getPlayer().surrender(nHand);
                        break;
                    case 'p':
                        game.getPlayer().split(nHand);
                        break;
                    case '2':
                        game.getPlayer().doubleBet(nHand);
                        break;
                    default:
                        System.out.println("Illegal command");
                }

                // If hand busts it closes, becoming not playable
                if (hand.isBust()) {
                    hand.closeHand();
                    System.out.println("Player busts");
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
