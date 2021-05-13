package com.Casino.gamelogic.classes;

import java.util.Scanner;

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
        Scanner scanner = new Scanner(System.in);
        Scanner betScan;
        char input = '0';
        String inputString;
        int nHand = 0;
        boolean deal = false;
        int previousBet = 0;
        int currentBet;

        while (!deal) {
            inputString = scanner.nextLine();
            switch (inputString.charAt(0)) {
                case 'b':
                    // If there are digits in the input string
                    if (inputString.matches(".*\\d.*")) {
                        // Scan the first integer in the string (bet amount)
                        betScan = new Scanner(inputString);
                        currentBet = betScan.useDelimiter("\\D+").nextInt();
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
                    System.out.println("Illegal command");
            }
        }
        // Deal cards
        this.dealCards();
        System.out.println("dealer's hand " + game.getDealer());

        System.out.println("Player is playing.");
        // loops through all of the player's hands
        for (Hand hand : this.game.getPlayer().getHands()) {
            // While hand is playable
            while (!hand.isHandClosed()) {

                System.out.println(hand);
                System.out.println("Enter command:");
                input = scanner.next().charAt(0);
                switch (input) {
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
                }
            }
            // Next hand
            nHand++;
        }
        // Set next state
        game.setGameState(game.getDealerTurnState());
        scanner.close();
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
        // Player is dealt two face up cards
        // and the dealer is dealt one face up card and one face down
        game.getPlayer().getHand(0).drawCard(game.getShoe());
        game.getDealer().drawCard(game.getShoe());
        game.getPlayer().getHand(0).drawCard(game.getShoe());
        game.getDealer().drawCard(game.getShoe());
        game.getDealer().getCard(0).setCardFaceDown();
    }
}
